package net.solidhorizons.wartornmedical;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.solidhorizons.wartornmedical.effect.ModEffects;
import net.solidhorizons.wartornmedical.item.ModCreativeModeTabs;
import net.solidhorizons.wartornmedical.item.ModItems;
import net.solidhorizons.wartornmedical.effect.BrokenLegEffect;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WarTornMedical.MOD_ID)
public class WarTornMedical
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "wartornmedical";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final double FALL_HEIGHT_THRESHOLD = 10.0;


    public WarTornMedical()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    boolean fellFarEnough = false;
    boolean gotHurt = false;
    int ticksPassed = 0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player = event.player;
        groundCheckLeg(player);

        if (ticksPassed >= 10){
            fellFarEnough = false;
            gotHurt = false;
            ticksPassed = 0;
        }

        if (fellFarEnough){
            ticksPassed ++;
        }

        if (player.fallDistance >= FALL_HEIGHT_THRESHOLD) {
            fellFarEnough = true;
        }

        if(player.isHurt()){
            gotHurt = true;
        }

    }

    public void groundCheckLeg(Player player){

        if(player.onGround() && fellFarEnough && gotHurt){
            player.addEffect(new MobEffectInstance(ModEffects.BROKEN_LEG_EFFECT.get(), 6000));
            fellFarEnough = false;
            gotHurt = false;
        }
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

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
