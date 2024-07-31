package net.solidhorizons.wartornmedical.effect; // Adjust the package accordingly

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.solidhorizons.wartornmedical.WarTornMedical;

public class BrokenLegEffect extends MobEffect {
    public BrokenLegEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // Reduce player speed by 50%
            player.setDeltaMovement(player.getDeltaMovement().multiply(0.5, 1, 0.5));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // Applies effect every tick while active
    }
}