package net.solidhorizons.wartornmedical.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import java.util.*;


public class KitItem extends Item {

    private final int maxDurability;

    public KitItem(Properties properties, int maxDurability) {
        //usage based
        super(
                properties.stacksTo(1).durability(maxDurability)
        );
        this.maxDurability = maxDurability;

        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final Map<String, String> TOOLTIP_MAP = new HashMap<>();

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.wartornmedical.durability", (maxDurability - stack.getDamageValue())));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getDamageValue() < maxDurability) {

            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));

            if (stack.getDamageValue() >= maxDurability) {

                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }

            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }
}
