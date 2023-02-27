package com.examplemod.util;

import com.examplemod.ExampleMod;
import com.examplemod.init.BlockInit;
import com.examplemod.init.ItemInit;


import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = ExampleMod.MOD_ID, bus =  Mod.EventBusSubscriber.Bus.FORGE)

public class Events {







    @SubscribeEvent
    public static void negateFallDamage(LivingFallEvent event) {

        if (event.getEntityLiving() instanceof Player) {
            Player player1 = (Player) event.getEntityLiving();


            if (((Player) event.getEntityLiving()).getInventory().getArmor(0) != null
                    && ((Player) event.getEntityLiving()).getInventory().getArmor(0).getItem().equals(ItemInit.PLATINUM_BOOTS.get())
                    && event.getEntityLiving().level.dimension() != Level.NETHER) {
                event.setCanceled(true);
            }


        }








    }
    @SubscribeEvent
    public static void giveGold(PlayerInteractEvent.RightClickBlock event){

       Player player = event.getPlayer();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        ItemStack stack = event.getItemStack();
        if(stack.isEmpty()&& block.equals(BlockInit.PLATINUM_BLOCK)){
            event.setUseBlock(Event.Result.ALLOW);
          InteractionHand hand = stack.equals(event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND)) ? InteractionHand.MAIN_HAND : InteractionHand.MAIN_HAND;
            stack = new ItemStack(Items.GOLD_INGOT, 5);
            player.setItemInHand(hand, stack);
            System.out.println("working?");




        }

    }


   /* @SubscribeEvent
    public static void makeConcrete (PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        int stackNumber = item.getCount();
        if (!item.isEmpty() && stackNumber. == stackNumber && heldStack.getItem().equals(ModItems.FIRE_CRYSTAL.get()) && block.equals(Blocks.FIRE)) {

        }

    */

    }

















