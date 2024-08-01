package net.solidhorizons.wartornmedical;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
    boolean hasDamaged = true;
    int ticksPassed = 0;
    int ticksForDamage = 0;
    int randint = 0;
    private int lBleedingTimer = 0; // For light bleeding effect
    private int hBleedingTimer = 0; // For heavy bleeding effect
    private static final int LIGHT_BLEEDING_DELAY = 900; // Delay for light bleeding (in ticks, 20 ticks = 1 second)
    private static final int HEAVY_BLEEDING_DELAY = 360; // Delay for heavy bleeding (every 0.5 seconds)

    //used every tick
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player = event.player;
        groundCheckLeg(player);

        if(player.hasEffect(ModEffects.BROKEN_LEG_EFFECT.get())){
            if (ticksForDamage < 20){
                ticksForDamage ++;
            }else{
                randint = rand.nextInt(1, 20);
                ticksForDamage = 0;
            }
            travelDistanceDamageTick(player);
        }

        if (player.hasEffect(ModEffects.LIGHT_BLEEDING_EFFECT.get())) {
            lBleedingTimer++;
            if (lBleedingTimer >= LIGHT_BLEEDING_DELAY) {

                player.hurt(player.damageSources().generic(), 2.0F);
                LOGGER.info("" + lBleedingTimer);
                lBleedingTimer = 0;
                LOGGER.info("lbleed tick");
            }
        }else{
            lBleedingTimer = 0;
        }

        if (player.hasEffect(ModEffects.HEAVY_BLEEDING_EFFECT.get())) {
            hBleedingTimer++;
            if (hBleedingTimer >= HEAVY_BLEEDING_DELAY) {

                player.hurt(player.damageSources().generic(), 2.0F);
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

        if((int)walked % 3 == 0){ //checks every 3 blocks for a random damage chance
            if(randint == 1){
                player.hurt(player.damageSources().fall(), 1);
                hasDamaged = true;
            }else{
                hasDamaged = false;
            }
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
