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
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0x00000000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "c8083219-b584-44f4-8750-90b6fdd5ca96",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> LIGHT_BLEEDING_EFFECT = MOB_EFFECTS.register("light_bleeding",
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0x00000000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "83ac7c50-65cb-415a-9089-6234ce50b1f6",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> HEAVY_BLEEDING_EFFECT = MOB_EFFECTS.register("heavy_bleeding",
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0x00000000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "d9e949b8-d088-4c47-835d-c8da52d92e3d",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> INFECTION_S1 = MOB_EFFECTS.register("infection_s1",
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0x00000000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "3ff3bb59-8d6a-41ff-ab43-a1a10386a072",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> INFECTION_S2 = MOB_EFFECTS.register("infection_s2",
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0xfc7401)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "86a50666-3238-4ee4-a8dc-3faccc59cc1b",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> INFECTION_S3 = MOB_EFFECTS.register("infection_s3",
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0x001418)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "9c8d4c0c-6c29-4455-86c7-e29ac8746c3b",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<MobEffect> ADDICTION = MOB_EFFECTS.register("addiction",
            () -> new BrokenLegEffect(MobEffectCategory.HARMFUL, 0x00000000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "d3bdf08e-9319-4e3e-b925-f3ef07a4d660",
                    -0.25f,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
