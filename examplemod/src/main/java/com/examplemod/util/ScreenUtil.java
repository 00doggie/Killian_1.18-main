package com.examplemod.util;

import com.examplemod.init.custom.CheeseMakerScreen;
import com.examplemod.init.custom.RulerScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//runs client side
public class ScreenUtil {


    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuUtil.RULER, RulerScreen::new);
            MenuScreens.register(MenuUtil.CHEESE_MAKER_MENU.get(), CheeseMakerScreen::new);
            //registers TrainingStickScreen

        });
    }
}
