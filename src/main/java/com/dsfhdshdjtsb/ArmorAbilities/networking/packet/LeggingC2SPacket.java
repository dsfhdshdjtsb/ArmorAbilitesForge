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

public class LeggingC2SPacket {

    int level;
    public LeggingC2SPacket(){

    }

    public LeggingC2SPacket(FriendlyByteBuf buf){

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

            int dashLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DASH.get(), player);
            double distanceMult = .80 + dashLevel * .1;

            Vec3 viewVector = player.getViewVector(1);

            double pitch = Math.asin(-viewVector.y);
            double velY = -Math.sin(pitch) * distanceMult;
            double mult = Math.cos(pitch);

            double yaw = Math.atan2(viewVector.x, viewVector.z);
            double velX = (Math.sin(yaw) * mult) * distanceMult;
            double velZ = (Math.cos(yaw) * mult) * distanceMult;

            player.setDeltaMovement(new Vec3(velX, velY, velZ));

        });
        return true;
    }
}
