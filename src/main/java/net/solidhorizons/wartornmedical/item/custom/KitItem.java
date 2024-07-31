package net.solidhorizons.wartornmedical.item.custom;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class KitItem extends Item {

    public KitItem(Properties properties) {
        //usage based
        super(
                properties.stacksTo(1)
        );

        MinecraftForge.EVENT_BUS.register(this);
    }
}
