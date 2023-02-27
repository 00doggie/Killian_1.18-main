package com.examplemod.init;

import com.examplemod.ExampleMod;
import com.examplemod.init.custom.Ruler;
import com.examplemod.util.ArmorMaterials;
import com.examplemod.util.CopperSword;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import com.examplemod.util.Tiers;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);

    public static final RegistryObject<Item> PLATINUM_INGOT = register("platinum_ingot",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> RULER = register("ruler",
            () -> new Ruler(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> TOMATO = register("tomato",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(FoodInit.TOMATO)));

    public static final RegistryObject<Item> CHEESE = register("cheese",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));


    /*public static final RegistryObject<Item> PLATINUM_HELMET = ITEMS.register("platinum_helmet",
            () -> new ArmorItem(ArmorMaterials.PLATINUM_ARMOR, EquipmentSlot.HEAD,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PLATINUM_CHESTPLATE = ITEMS.register("platinum_chestplate",
            () -> new ArmorItem(ArmorMaterials.PLATINUM_ARMOR, EquipmentSlot.CHEST,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PLATINUM_LEGGINGS = ITEMS.register("platinum_leggings",
            () -> new ArmorItem(ArmorMaterials.PLATINUM_ARMOR, EquipmentSlot.LEGS,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PLATINUM_BOOTS = ITEMS.register("platinum_boots",
            () -> new ArmorItem(ArmorMaterials.PLATINUM_ARMOR, EquipmentSlot.FEET,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

     */


    public static final RegistryObject<Item> PLATINUM_SWORD = ITEMS.register("platinum_sword",
            () -> new SwordItem(Tiers.PLATINUM, 4, -2.4f,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> PLATINUM_SHOVEL = ITEMS.register("platinum_shovel",
            () -> new ShovelItem(Tiers.PLATINUM, 1f, -3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> PLATINUM_HOE = ITEMS.register("platinum_hoe",
            () -> new HoeItem(Tiers.PLATINUM, -2, -1f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));


    public static final RegistryObject<Item> PLATINUM_BOOTS = ITEMS.register("platinum_boots",
            () -> new ArmorItem(ArmorMaterials.Hard_PLATINUM_ARMOR, EquipmentSlot.FEET,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PLATINUM_AXE = ITEMS.register("platinum_axe",
            () -> new AxeItem(Tiers.PLATINUM, 7, -3.1f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new CopperSword(Tiers.COPPER, 3, -2.4F, CopperSword.Rust.CLEAN,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> EXPOSED_COPPER_SWORD = ITEMS.register("exposed_copper_sword",
            () -> new CopperSword(Tiers.EXPOSED_COPPER, 4, -2.4F, CopperSword.Rust.EXPOSE,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> WEATHERED_COPPER_SWORD = ITEMS.register("weathered_copper_sword",
            () -> new CopperSword(Tiers.WEATHERED_COPPER, 4, -2.4F, CopperSword.Rust.WEATHER,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> OXODIZED_COPPER_SWORD = ITEMS.register("oxodized_copper_sword",
            () -> new CopperSword(Tiers.OXODIZED_COPPER, 4, -2.4F, CopperSword.Rust.OXIDIZED,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));


    public static final RegistryObject<Item> FISHY = ITEMS.register("fishy",
            () -> new FishingRodItem(new Item.Properties().durability(300).tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(BlockInit.TOMATO_PLANT.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));


    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item){
        return ITEMS.register(name, item);
    }
}
