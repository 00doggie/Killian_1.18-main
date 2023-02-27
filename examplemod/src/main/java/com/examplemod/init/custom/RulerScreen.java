package com.examplemod.init.custom;

import com.examplemod.ExampleMod;
import com.examplemod.network.DisplayDistancePacket;
import com.examplemod.network.ModMessages;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;


public class RulerScreen  extends AbstractContainerScreen<RulerMenu> implements Button.OnPress {
    private EditBox NameBox;


    public static String name;


    public static Player player;




    public RulerScreen(RulerMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    public void resize(Minecraft p_97677_, int p_97678_, int p_97679_) {
        String s = this.NameBox.getValue();
        this.init(p_97677_, p_97678_, p_97679_);
        this.NameBox.setValue(s);


    }

    @Override
    public void containerTick() {
        this.NameBox.tick();
        super.containerTick();

    }


    @Override
    protected void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);

        NameBox = new EditBox(this.font, this.width / 2 - 92, this.height / 4 - 10, 184, 20, new TextComponent("name"));
        this.NameBox.setCanLoseFocus(false);


        this.NameBox.setMaxLength(16);


        this.addWidget(this.NameBox);
        this.setInitialFocus(this.NameBox);
        this.NameBox.setEditable(true);


        this.addRenderableWidget(new Button(this.width / 2 - 75, this.height * 3 / 4 - 10, 150, 20,
                CommonComponents.GUI_DONE,  this::onPress));


    }

    @Override
    public void onPress(Button button){

ModMessages.sendToServer(new DisplayDistancePacket(name));

onClose();


    }





    public boolean keyPressed(int p_97878_, int p_97879_, int p_97880_) {
        if (p_97878_ == 256) {
            this.minecraft.player.closeContainer();

        }




        return !this.NameBox.keyPressed(p_97878_, p_97879_, p_97880_) && !this.NameBox.canConsumeInput() ? super.keyPressed(p_97878_, p_97879_, p_97880_) : true;
    }
    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        this.renderBackground(poseStack);
        this.NameBox.render(poseStack, pMouseX, pMouseY, pPartialTick);
    }

    public void onClose() {
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
        super.onClose();



    }

}
