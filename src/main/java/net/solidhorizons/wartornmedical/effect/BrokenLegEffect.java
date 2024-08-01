package net.solidhorizons.wartornmedical.effect; // Adjust the package accordingly

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;


public class BrokenLegEffect extends MobEffect {
    public BrokenLegEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // Reduce player speed by 50%
            player.setDeltaMovement(player.getDeltaMovement().multiply(0.36, 1, 0.36));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}