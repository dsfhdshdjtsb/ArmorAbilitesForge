package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.timers.TimerProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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

            int frostStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FROST_STOMP.get(), player);

            if(frostStompLevel > 0)
            {
                if(player.onGround())
                {
                    player.jumpFromGround();
                }

                player.getCapability(TimerProvider.TIMER).ifPresent(timer -> {
                    timer.frostStompTimer = 100;
                    timer.helmetCooldown = 200;
                });

            }
        });
        return true;
    }
}
