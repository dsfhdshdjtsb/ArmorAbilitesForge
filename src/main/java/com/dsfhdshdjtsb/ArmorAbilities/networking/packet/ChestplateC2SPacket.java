package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
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

            if(explodeLevel > 0)
            {
                timerAccess.aabiliites_setFuse(80);
                player.serverLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 0.7f, 1.0f);
                //DO SERVER RENDERING HERE
            }
            else if(cleanseLevel > 0)
            {
                player.setTicksFrozen(0);
                player.clearFire();
                player.removeAllEffects();
                player.serverLevel().sendParticles(ParticleTypes.ENTITY_EFFECT, player.getX(), player.getY(0.5), player.getZ(), 20,0.7, 0.5, 0.7, 2.0D );
                player.serverLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.7f, 1.0f);
            }
            else if(siphonLevel > 0)
            {
                List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3 + siphonLevel,1,3 + siphonLevel) );
                list.remove(player);
                int counter = 0;

                list.remove(player);
                for (LivingEntity e : list) {
                    counter++;
                    e.hurt(player.level().damageSources().magic(), 1.0f);
                    double xdif = e.getX() - player.getX();
                    double ydif = e.getY(0.5D) - player.getY(0.5D);
                    double zdif = e.getZ() - player.getZ();

                    int particleNumConstant = 20; //number of particles
                    double x = 0;
                    double y = 0;
                    double z = 0;
                    while (Math.abs(x) < Math.abs(xdif)) {
                        player.serverLevel().sendParticles(ParticleTypes.COMPOSTER, player.getX() + x,
                                player.getY(0.5D) + y, player.getZ() + z, 0, 1, 0.0D, 1, 0.0D);
                        x = x + xdif / particleNumConstant;
                        y = y + ydif / particleNumConstant;
                        z = z + zdif / particleNumConstant;

                    }
                    player.serverLevel().sendParticles(ParticleTypes.HEART, player.getX(), player.getY(0.5D), player.getZ(), 2, 0.4, 0.5, 0.4, 0.0D);
                }
                player.heal(counter + (siphonLevel - 1));
                player.serverLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.7f, 1.0f);

            }
        });
        return true;
    }
}
