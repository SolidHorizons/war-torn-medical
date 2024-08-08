package net.solidhorizons.wartornmedical.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class BrokenArmEffect extends MobEffect {
    public BrokenArmEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            if(!player.hasEffect(MobEffects.WEAKNESS) && !player.hasEffect(MobEffects.DIG_SLOWDOWN)) {

                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0,
                        false, false, false));

                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, 0,
                        false, false, false));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}