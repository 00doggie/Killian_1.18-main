package com.examplemod.world;


import com.examplemod.ExampleMod;
import com.examplemod.world.feature.ore.GenerateOres;
import com.examplemod.world.feature.tree.GenerateTrees;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID)
public class WorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        GenerateOres.generateOres(event);
        GenerateTrees.generateTrees(event);




    }
}
