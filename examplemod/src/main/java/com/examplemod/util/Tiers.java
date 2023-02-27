package com.examplemod.util;

import com.examplemod.init.ItemInit;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class Tiers {
    public static final ForgeTier PLATINUM = new ForgeTier(2, 500, 6f,
            2f, 12, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ItemInit.PLATINUM_INGOT.get()));

    public static final ForgeTier COPPER = new ForgeTier(2, 250, 6.0F,
            2.0F, 14, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(Items.COPPER_INGOT));
    public static final ForgeTier EXPOSED_COPPER = new ForgeTier(2, 190, 7.5F,
            3.0F, 16, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(Items.COPPER_INGOT));

    public static final ForgeTier WEATHERED_COPPER = new ForgeTier(2, 160, 9F,
            4.0F, 18, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(Items.COPPER_INGOT));

    public static final ForgeTier OXODIZED_COPPER = new ForgeTier(2, 130, 11F,
            5.0F, 20, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(Items.COPPER_INGOT));
}
