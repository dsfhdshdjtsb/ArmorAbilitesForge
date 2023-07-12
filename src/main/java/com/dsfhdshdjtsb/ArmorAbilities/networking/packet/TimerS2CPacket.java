package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.client.ClientMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TimerS2CPacket {

    public boolean shouldAnvilRender;
    public long fuse;
    public int playerId;

    public TimerS2CPacket(boolean shouldAnvilRender, long fuse, int playerId){
        this.shouldAnvilRender = shouldAnvilRender;
        this.fuse = fuse;
        this.playerId = playerId;

    }

    public TimerS2CPacket(FriendlyByteBuf buf){
        this(buf.readBoolean(), buf.readLong(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeBoolean(shouldAnvilRender);
        buf.writeLong(fuse);
        buf.writeInt(playerId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientMethods.timerUpdate(shouldAnvilRender, fuse, playerId));
        });
        context.setPacketHandled(true);
        return true;
    }


}
