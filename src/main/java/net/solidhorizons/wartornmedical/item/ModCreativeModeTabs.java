package net.solidhorizons.wartornmedical.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.solidhorizons.wartornmedical.WarTornMedical;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WarTornMedical.MOD_ID);


    public static final RegistryObject<CreativeModeTab> WARTORN_TAB = CREATIVE_MODE_TABS.register("wartorn_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PAINKILLERS.get()))
                    .title(Component.translatable("creativetab.wartorn-medical_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.GAUZE.get());
                        pOutput.accept(ModItems.BANDAGE.get());
                        pOutput.accept(ModItems.DI_BANDAGE.get());
                        pOutput.accept(ModItems.TOURNIQUET.get());
                        pOutput.accept(ModItems.SPLINT.get());
                        pOutput.accept(ModItems.PAINKILLERS.get());
                        pOutput.accept(ModItems.ANTIBIOTIC.get());
                        pOutput.accept(ModItems.MEDKIT.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
    CREATIVE_MODE_TABS.register(eventBus);
    }
}
