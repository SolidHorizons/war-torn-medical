package net.solidhorizons.wartornmedical;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.solidhorizons.wartornmedical.effect.ModEffects;
import net.solidhorizons.wartornmedical.item.ModCreativeModeTabs;
import net.solidhorizons.wartornmedical.item.ModItems;
import net.solidhorizons.wartornmedical.effect.BrokenArmEffect;
import org.slf4j.Logger;

import java.util.Random;

@Mod(WarTornMedical.MOD_ID)
public class WarTornMedical
{
    public static final String MOD_ID = "wartornmedical";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final double FALL_HEIGHT_THRESHOLD = 10.0;
    public Random rand;

    public WarTornMedical()
    {
        this.rand = new Random();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    //variables for logic below
    boolean fellFarEnough = false;
    boolean gotHurt = false;
    boolean brokenLegChanceHappened = true;
    boolean hasLBleeding = false;
    boolean hasHBleeding = false;
    boolean hasBrokenArm = false;
    final int damageTickPerBlocksWalked = 7;
    int ticksPassed = 0;
    int ticksForDamage = 0;
    private int lBleedingTimer = 0;
    private int hBleedingTimer = 0;
    final int LIGHT_BLEEDING_DELAY = 900;
    final int HEAVY_BLEEDING_DELAY = 900;

    //used every tick
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player = event.player;
        groundCheckLeg(player);

        hasLBleeding = player.hasEffect(ModEffects.LIGHT_BLEEDING_EFFECT.get());
        hasHBleeding = player.hasEffect(ModEffects.HEAVY_BLEEDING_EFFECT.get());

        if(player.hasEffect(ModEffects.BROKEN_LEG_EFFECT.get())){
            if (ticksForDamage < 20){
                ticksForDamage ++;
            }else{
                ticksForDamage = 0;
            }
            travelDistanceDamageTick(player);
        }

        if (hasLBleeding) {
            lBleedingTimer++;
            if (lBleedingTimer >= LIGHT_BLEEDING_DELAY) {

                player.hurtMarked = true;
                player.hurt(player.damageSources().magic(), 1);
                LOGGER.info("" + lBleedingTimer);
                lBleedingTimer = 0;
                LOGGER.info("lbleed tick");
            }
        }else{
            lBleedingTimer = 0;
        }

        if (hasHBleeding) {
            hBleedingTimer++;
            if (hBleedingTimer >= HEAVY_BLEEDING_DELAY) {

                player.hurtMarked = true;
                player.hurt(player.damageSources().magic(), 2);
                LOGGER.info("" + hBleedingTimer);
                hBleedingTimer = 0;
                LOGGER.info("hbleed tick");
            }
        }else{
            hBleedingTimer = 0;
        }

        if (ticksPassed >= 10){
            fellFarEnough = false;
            gotHurt = false;
            ticksPassed = 0;
        }

        if (fellFarEnough){ ticksPassed ++; }
        if (player.fallDistance >= FALL_HEIGHT_THRESHOLD) { fellFarEnough = true; }
        if (player.isHurt()){ gotHurt = true; }

    }

    //------------------Broken leg logic start-------------------


    public void groundCheckLeg(Player player){

        if(player.onGround() && fellFarEnough && gotHurt){
            player.addEffect(new MobEffectInstance(ModEffects.BROKEN_LEG_EFFECT.get(), 6000));
            fellFarEnough = false;
            gotHurt = false;
        }
    }

    public void travelDistanceDamageTick(Player player){
        double walked = player.walkDist;
        int chanceAtLegDamage = rand.nextInt(1,20);

        if((int)walked % damageTickPerBlocksWalked == 0 && !brokenLegChanceHappened){ //checks every so many blocks for a random damage chance
            if(chanceAtLegDamage == 1) {
                player.hurt(player.damageSources().fall(), 1);
            }
            brokenLegChanceHappened = true;
        }else{
            brokenLegChanceHappened = false;
        }
    }

    //------------------Broken leg logic end-------------------
    //------------------Broken arm logic start-----------------

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent bEvent) {

        Player player = bEvent.getPlayer();

        if (player.hasEffect(ModEffects.BROKEN_ARM_EFFECT.get())) {

            player.hurtMarked = true;
            player.hurt(player.damageSources().generic(), 2.0F);
        }
    }

    //------------------Broken arm logic end-------------------

    //------------------Bleeding logic start-------------------
    @SubscribeEvent
    public void onPlayerHit(LivingAttackEvent event) {
        int effectChance = rand.nextInt(1, 101);
        if(effectChance <= 12){

            if (event.getEntity() instanceof Player player &&
                    (event.getSource().getEntity() instanceof Entity ||
                    event.getSource().getEntity() instanceof LivingEntity) ) {

                player.addEffect(new MobEffectInstance(ModEffects.LIGHT_BLEEDING_EFFECT.get(),
                        2000, 0, false, false,true));
                hasLBleeding = true;
            }

        } else if (effectChance >= 97) {

            if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Entity) {

                player.addEffect(new MobEffectInstance(ModEffects.HEAVY_BLEEDING_EFFECT.get(),
                        2000, 0, false, false,true));
                hasHBleeding = true;
            }
        } else if (effectChance >= 15 && effectChance <= 20) {

            if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Entity) {

                player.addEffect(new MobEffectInstance(ModEffects.BROKEN_ARM_EFFECT.get(),
                        2000, 0, false, false,true));
                hasBrokenArm = true;
            }
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
