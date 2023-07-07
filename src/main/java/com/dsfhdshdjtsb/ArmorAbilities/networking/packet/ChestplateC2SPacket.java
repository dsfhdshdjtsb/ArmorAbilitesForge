package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChestplateC2SPacket {

    int level;
    public ChestplateC2SPacket(){

    }

    public ChestplateC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //here we are on server
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            int explodeLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.EXPLODE.get(), player);
            int siphonLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SIPHON.get(), player);
            int cleanseLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.CLEANSE.get(), player);

            TimerAccess timerAccess = (TimerAccess) player;

//            timerAccess.aabilities_setChestCooldown(200);
        });
        return true;
    }
}
