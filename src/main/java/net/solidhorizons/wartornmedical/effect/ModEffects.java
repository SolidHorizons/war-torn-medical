package net.solidhorizons.wartornmedical.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.solidhorizons.wartornmedical.WarTornMedical;


public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WarTornMedical.MOD_ID);

    public static final RegistryObject<MobEffect> BROKEN_LEG_EFFECT = MOB_EFFECTS.register("broken_leg",
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0x00000000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "1cdba704-0d33-4905-a7b5-ca047c023245",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
