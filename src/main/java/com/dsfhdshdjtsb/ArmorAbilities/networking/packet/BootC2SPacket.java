package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BootC2SPacket {

    int level;
    public BootC2SPacket(){

    }

    public BootC2SPacket(FriendlyByteBuf buf){

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

        });
        return true;
    }
}
