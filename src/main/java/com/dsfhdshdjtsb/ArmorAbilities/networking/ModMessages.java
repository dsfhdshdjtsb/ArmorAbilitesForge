package com.dsfhdshdjtsb.ArmorAbilities.networking;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.BootC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.ChestplateC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.HelmetC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.LeggingC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetid = 0;
    private static int id() {
        return packetid++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ArmorAbilities.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(BootC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BootC2SPacket::new)
                .encoder(BootC2SPacket::toBytes)
                .consumerMainThread(BootC2SPacket::handle)
                .add();
        net.messageBuilder(ChestplateC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChestplateC2SPacket::new)
                .encoder(ChestplateC2SPacket::toBytes)
                .consumerMainThread(ChestplateC2SPacket::handle)
                .add();
        net.messageBuilder(HelmetC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(HelmetC2SPacket::new)
                .encoder(HelmetC2SPacket::toBytes)
                .consumerMainThread(HelmetC2SPacket::handle)
                .add();
        net.messageBuilder(LeggingC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LeggingC2SPacket::new)
                .encoder(LeggingC2SPacket::toBytes)
                .consumerMainThread(LeggingC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
