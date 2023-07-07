package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HelmetC2SPacket {

    int level;
    public HelmetC2SPacket(){

    }

    public HelmetC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //here we are on server
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            System.out.println("server level:" + level);
            EntityType.COW.spawn(level, (ItemStack) null, null, player.blockPosition(), MobSpawnType.COMMAND, true, false);

            TimerAccess timerAccess = (TimerAccess) player;

//            timerAccess.aabilities_setHelmetCooldown(200);

        });
        return true;
    }
}
