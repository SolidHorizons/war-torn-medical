package net.solidhorizons.wartornmedical.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BrokenArmEffect extends MobEffect {
    public BrokenArmEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // Reduce player speed by 50%
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5000, 1, false, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 5000, 1, false, false, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}