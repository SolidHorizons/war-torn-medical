package net.solidhorizons.wartornmedical.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.*;


public class KitItem extends Item {

    private final int maxDurability;
    private static final Map<String, String> TOOLTIP_MAP = new HashMap<>();


    public KitItem(Properties properties, int maxDurability) {
        //usage based
        super(
                properties.stacksTo(1).durability(maxDurability)
        );
        this.maxDurability = maxDurability;

        MinecraftForge.EVENT_BUS.register(this);
    }

    static {
        TOOLTIP_MAP.put("item.wartornmedical.antibiotics", "Heals light bleeding, cures infection");
        TOOLTIP_MAP.put("item.wartornmedical.splint", "Heals broken femur");
        TOOLTIP_MAP.put("item.wartornmedical.painkillers", "Ignores all pain");
        TOOLTIP_MAP.put("item.wartornmedical.medkit", "Removes all effects");
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String kitType = pStack.getDescriptionId(); // Using the item's translation key
        String tooltip = TOOLTIP_MAP.get(kitType);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        if (tooltip != null) {
            pTooltipComponents.add(Component.translatable(tooltip));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.wartornmedical.default.tooltip")); // Fallback tooltip
        }

        pTooltipComponents.add(Component.translatable("item.wartornmedical.durability", (maxDurability - pStack.getDamageValue())));
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
