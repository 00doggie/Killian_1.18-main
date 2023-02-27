package com.examplemod.init.custom;

import com.examplemod.init.ItemInit;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModArmorItem extends ArmorItem {
    public ModArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (player.getInventory().getArmor(3) != null && player.getInventory().getArmor(3).getItem().equals(ItemInit.PLATINUM_AXE)
                && player.getInventory().getArmor(2) != null && player.getInventory().getArmor(2).getItem().equals(ItemInit.PLATINUM_AXE)
                && player.getInventory().getArmor(1) != null && player.getInventory().getArmor(1).getItem().equals(ItemInit.PLATINUM_AXE)
                && player.getInventory().getArmor(0) != null && player.getInventory().getArmor(0).getItem().equals(ItemInit.PLATINUM_AXE)


        ) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0, false, false));
        }
    }
}

