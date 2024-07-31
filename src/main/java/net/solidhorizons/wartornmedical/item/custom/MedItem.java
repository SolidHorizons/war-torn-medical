package net.solidhorizons.wartornmedical.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedItem extends Item{

    private static final Map<String, String> TOOLTIP_MAP = new HashMap<>();

    public MedItem(Properties properties) {
        //stackable based
        super(
                properties.stacksTo(4)
        );

        MinecraftForge.EVENT_BUS.register(this);
    }

    static {
        TOOLTIP_MAP.put("item.wartornmedical.gauze", "Chance to heal light bleeding");
        TOOLTIP_MAP.put("item.wartornmedical.bandage", "Heals light bleeding");
        TOOLTIP_MAP.put("item.wartornmedical.di_bandage", "Heals light bleeding, cures infection");
        TOOLTIP_MAP.put("item.wartornmedical.tourniquet", "Heals heavy bleeding");
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

    }
}
