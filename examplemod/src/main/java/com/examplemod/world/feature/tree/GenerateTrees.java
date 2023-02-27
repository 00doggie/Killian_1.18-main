
package com.examplemod.world.feature.tree;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Set;

public class GenerateTrees   {
    public static void generateTrees(final BiomeLoadingEvent event){

    ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
    Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);


    if(types.contains(BiomeDictionary.Type.SAVANNA)) {
        List<Holder<PlacedFeature>> base =
                event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);

        base.add(TreePlacedFeatures.EBONY_PLACED);

    }
}

}


