package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.networking.ModMessages;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
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

            TimerAccess timerAccess = (TimerAccess) player;

            int frostStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FROST_STOMP.get(), player);
            int fireStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FIRE_STOMP.get(), player);
            int anvilStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ANVIL_STOMP.get(), player);

            if(frostStompLevel > 0)
            {
                if(player.onGround())
                {
                    player.jumpFromGround();
                }

                timerAccess.aabilities_setFrostStompTimer(100);
            }
            else if (fireStompLevel > 0)
            {
                if(player.onGround())
                {
                    player.jumpFromGround();
                }

                timerAccess.aabilities_setFireStompTimer(100);
            }
            else if (anvilStompLevel > 0)
            {
                if(player.onGround())
                {
                    player.jumpFromGround();
                }

                timerAccess.aabilities_setAnvilStompTimer(100);
                player.serverLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.COPPER_PLACE, SoundSource.PLAYERS, 0.7f, 1.0f);
                //INSERT PACKETS TO OTHER PLAYERS HERE
                player.sendSystemMessage(Component.literal(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player).toString()));
                List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10,2,10 ) );
                list.remove(player);
                ModMessages.INSTANCE.send(PacketDistributor.ALL.noArg(), new TimerS2CPacket(timerAccess.aabilities_getAnvilStompTimer(), timerAccess.aabilities_getFuse(), player.getId()));



            }
//            timerAccess.aabilities_setBootCooldown(200);
        });

        return true;
    }
}
