package net.solidhorizons.wartornmedical.item.custom;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.solidhorizons.wartornmedical.WarTornMedical;

public class MedItem extends Item{

    public MedItem(Properties properties) {
        //stackable based
        super(
                properties.stacksTo(4)
        );

        MinecraftForge.EVENT_BUS.register(this);
    }
}
