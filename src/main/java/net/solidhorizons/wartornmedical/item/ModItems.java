package net.solidhorizons.wartornmedical.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.solidhorizons.wartornmedical.WarTornMedical;
import net.solidhorizons.wartornmedical.item.custom.KitItem;
import net.solidhorizons.wartornmedical.item.custom.MedItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WarTornMedical.MOD_ID);

    //meditems
    public static final RegistryObject<Item> GAUZE = ITEMS.register("gauze",
            () -> new MedItem(new Item.Properties()));

    public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage",
            () -> new MedItem(new Item.Properties()));

    public static final RegistryObject<Item> DI_BANDAGE = ITEMS.register("di_bandage",
            () -> new MedItem(new Item.Properties()));

    public static final RegistryObject<Item> TOURNIQUET = ITEMS.register("tourniquet",
            () -> new MedItem(new Item.Properties()));

    //kititems
    public static final RegistryObject<Item> SPLINT = ITEMS.register("splint",
            () -> new KitItem(new Item.Properties(), 2));

    public static final RegistryObject<Item> PAINKILLERS = ITEMS.register("painkillers",
            () -> new KitItem(new Item.Properties(), 6));

    public static final RegistryObject<Item> ANTIBIOTIC = ITEMS.register("antibiotic",
            () -> new KitItem(new Item.Properties(), 5));

    public static final RegistryObject<Item> MEDKIT = ITEMS.register("medkit",
            () -> new KitItem(new Item.Properties(), 3));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
