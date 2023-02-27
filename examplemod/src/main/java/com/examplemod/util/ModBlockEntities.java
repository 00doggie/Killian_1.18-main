package com.examplemod.util;

import com.examplemod.ExampleMod;
import com.examplemod.init.BlockInit;
import com.examplemod.init.custom.CheeseMakerEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ExampleMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<CheeseMakerEntity>> CHEESE_MAKER_ENTITY =
            BLOCK_ENTITIES.register("cheese_maker_entity", () ->
                    BlockEntityType.Builder.of(CheeseMakerEntity::new,
                            BlockInit.CHEESE_MAKER.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
