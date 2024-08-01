package net.solidhorizons.wartornmedical.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
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
                    "eee3f4f6-d5b1-4dfe-833d-e82c413ac710",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> BROKEN_ARM_EFFECT = MOB_EFFECTS.register("broken_arm",
            () -> new BrokenArmEffect(MobEffectCategory.HARMFUL, 0x00000000));

    public static final RegistryObject<MobEffect> LIGHT_BLEEDING_EFFECT = MOB_EFFECTS.register("light_bleeding",
            () -> new BrokenArmEffect(MobEffectCategory.HARMFUL, 0x00000000));

    public static final RegistryObject<MobEffect> HEAVY_BLEEDING_EFFECT = MOB_EFFECTS.register("heavy_bleeding",
            () -> new BrokenArmEffect(MobEffectCategory.HARMFUL, 0x00000000));

    public static final RegistryObject<MobEffect> INFECTION_S1 = MOB_EFFECTS.register("infection_s1",
            () -> new BrokenArmEffect(MobEffectCategory.HARMFUL, 0x00000000));

    public static final RegistryObject<MobEffect> INFECTION_S2 = MOB_EFFECTS.register("infection_s2",
            () -> new BrokenArmEffect(MobEffectCategory.HARMFUL, 0x00000000));

    public static final RegistryObject<MobEffect> INFECTION_S3 = MOB_EFFECTS.register("infection_s3",
            () -> new BrokenArmEffect(MobEffectCategory.HARMFUL, 0x00000000));

    public static final RegistryObject<MobEffect> ADDICTION = MOB_EFFECTS.register("addiction",
            () -> new BrokenArmEffect(MobEffectCategory.HARMFUL, 0x00000000));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
