package com.examplemod.util;

import com.examplemod.init.ItemInit;


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import net.minecraft.world.entity.Entity;

import net.minecraft.world.item.*;


import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;


public class CopperSword extends SwordItem {


    private final Rust rust;

    public CopperSword(Tier tier, int attackDamage, float attackSpeed, Rust rust, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
        this.rust = rust;
    }

    public enum Rust {


        CLEAN(() -> ItemInit.EXPOSED_COPPER_SWORD.get(), 300,15),
        EXPOSE(() -> ItemInit.WEATHERED_COPPER_SWORD.get(), 500,25),
        WEATHER(() -> ItemInit.OXODIZED_COPPER_SWORD.get(), 800, 40),
        OXIDIZED(() -> null, 0,0);



        private final Supplier<Item> rusting;
        private final int ticks;
        private final int seconds;




        Rust(Supplier<Item> rusting, int ticks, int seconds) {
            this.rusting = rusting;
            this.ticks = ticks;
            this.seconds = seconds;




        }


        public Item getRusting() {
            return rusting.get();


        }
        public int getTicks() {
            return ticks;


        }
        public int getSeconds() {
            return seconds;


        }


    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {

        if (rust == Rust.OXIDIZED)
            return;

        entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {

            int index = -1;

            for (int i = 0; i < handler.getSlots(); i++) {
                if (handler.getStackInSlot(i) == stack)
                    index = i;
            }

            if (index >= 0) {
                if (getTicks(stack) >= rust.getTicks()) {
                    ItemStack newStack = new ItemStack(rust.getRusting());
                    if(stack.hasTag()){
                        newStack.setTag(stack.getTag().copy());
                    }

                    setTicks(newStack, 0);


                    handler.extractItem(index, 1, false);
                    handler.insertItem(index, newStack, false);


                }
                setTicks(stack, getTicks(stack) + 1);

            }

        });


        super.inventoryTick(stack, level, entity, slot, selected);
    }


    //this was just to see the ticks in the tooltip to test it

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        if (flag.isAdvanced()) {


        if (rust == Rust.OXIDIZED) {

         } else {

               list.add(new TextComponent(rust.getSeconds() - getTicks(stack) / 20 + " " + "Seconds Until Rust"));
      }
}

        super.appendHoverText(stack, level, list, flag);
    }


    public static void setTicks(ItemStack stack, int ticks) {
        stack.getOrCreateTag().putInt("ticks", ticks);
    }


    public static int getTicks(ItemStack stack) {

        return stack.hasTag() ? stack.getTag().getInt("ticks") : 0;

    }






}






