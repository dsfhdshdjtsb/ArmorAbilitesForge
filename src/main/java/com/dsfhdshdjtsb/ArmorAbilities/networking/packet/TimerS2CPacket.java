package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.client.ClientMethods;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.networking.ModMessages;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class TimerS2CPacket {

    public long anvilStompTimer;
    public long fuse;
    public int playerId;

    public TimerS2CPacket(long anvilStompTimer, long fuse, int playerId){
        this.anvilStompTimer = anvilStompTimer;
        this.fuse = fuse;
        this.playerId = playerId;

    }

    public TimerS2CPacket(FriendlyByteBuf buf){
        this(buf.readLong(), buf.readLong(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeLong(anvilStompTimer);
        buf.writeLong(fuse);
        buf.writeInt(playerId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientMethods.timerUpdate(anvilStompTimer, fuse, playerId));
        });
        context.setPacketHandled(true);
        return true;
    }


}
