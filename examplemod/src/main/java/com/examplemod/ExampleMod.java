package com.examplemod;

import com.examplemod.init.BlockInit;
import com.examplemod.init.FoodInit;
import com.examplemod.init.ItemInit;
import com.examplemod.network.ModMessages;
import com.examplemod.util.*;
import com.examplemod.world.WorldEvents;
import com.examplemod.world.feature.tree.TreePlacedFeatures;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.beans.EventHandler;

@Mod("examplemod")
public class ExampleMod{

    public static final String MOD_ID = "examplemod";

    public ExampleMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        MenuUtil.MENUS.register(bus);
        ModRecipes.SERIALIZERS.register(bus);








        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(ExampleMod::clientSetup);
        bus.addListener(ExampleMod::setup);

    }
    private static void clientSetup(final FMLClientSetupEvent event) {
        ModItemProperties.addCustomItemProperties();

        ItemBlockRenderTypes.setRenderLayer(BlockInit.TOMATO_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.EBONY_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.EBONY_SAPLING.get(), RenderType.cutout());
        WoodType.register(ModWoodTypes.EBONY);
    }


    private static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {


            Sheets.addWoodType(ModWoodTypes.EBONY);
        });
    }
}
