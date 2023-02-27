package com.examplemod.util;

import com.examplemod.ExampleMod;
import com.examplemod.init.custom.CheeseMakerRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExampleMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CheeseMakerRecipe>> CHEESE_MAKING_SERIALIZER =
            SERIALIZERS.register("cheese_making", () -> CheeseMakerRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
