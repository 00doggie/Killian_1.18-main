package com.examplemod.network;

import com.examplemod.init.custom.Ruler;
import com.examplemod.init.custom.RulerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class DisplayDistancePacket  {
    public static String name = RulerScreen.name;
    public static UUID nameUUID = UUID.fromString(name);
    public static double distanceBetween;
    public static String dis = Double.toString(distanceBetween);






    public DisplayDistancePacket(String Name ){
        this.name = Name;
    }

    public DisplayDistancePacket(FriendlyByteBuf buf) {
        this.nameUUID = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
    buf.writeUUID(this.nameUUID);


    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
       ServerPlayer player = context.getSender();
       ServerLevel serverLevel = player.getLevel();

            Optional<ServerPlayer> targetOp = serverLevel.getPlayers(playerTest -> playerTest.getName().getString().equals(name)).stream().findFirst();

            if (targetOp.isPresent()) {
                distanceBetween = targetOp.get().distanceTo(player);
                Minecraft.getInstance().player.displayClientMessage(new TextComponent(name), false);


            } else {
                Minecraft.getInstance().player.displayClientMessage(new TextComponent("User Does not Exist"), false);
                //User does not exist!

            }








        });
        return true;
    }
    public String getName() {
        return this.name;
    }





}
