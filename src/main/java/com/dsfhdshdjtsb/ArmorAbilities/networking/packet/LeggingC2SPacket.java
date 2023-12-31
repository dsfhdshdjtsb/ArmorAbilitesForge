package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.client.particle.DragonBreathParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
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
            Level level = player.level();
            int dashLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DASH.get(), player);
            int blinkLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.BLINK.get(), player);
            int rushLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RUSH.get(), player);

            TimerAccess timerAccess = (TimerAccess) player;

            if(dashLevel > 0) {
                double distanceMult = .80 + dashLevel * .1;

                Vec3 viewVector = player.getViewVector(1);

                double pitch = Math.asin(-viewVector.y);
                double velY = -Math.sin(pitch) * distanceMult;
                double mult = Math.cos(pitch);

                double yaw = Math.atan2(viewVector.x, viewVector.z);
                double velX = (Math.sin(yaw) * mult) * distanceMult;
                double velZ = (Math.cos(yaw) * mult) * distanceMult;

                player.serverLevel().sendParticles(ParticleTypes.POOF, player.getX(), player.getY(0.5), player.getZ(), 5,0.3, 0.5, 0.3, 0 );
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 0.7f, 1.0f);
                player.setDeltaMovement(new Vec3(velX, velY, velZ));
            }
            else if (rushLevel > 0){
                int speedLevel = 0;
                int strengthLevel = 0;
                switch (rushLevel) {
                    case 2, 3 -> speedLevel = 1;
                    case 4 -> speedLevel = 2;
                    case 5 -> {
                        speedLevel = 2;
                        strengthLevel = 1;
                    }
                }
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, speedLevel));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, strengthLevel));
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_PREPARE_BLINDNESS, SoundSource.PLAYERS, 0.7f, 1.0f);
                player.serverLevel().sendParticles(ParticleTypes.ANGRY_VILLAGER, player.getX(), player.getY(0.5), player.getZ(), 3,0.4, 0.5, 0.4, 0 );

            }
            else if(blinkLevel > 0){
                System.out.println("server side blink");
                Vec3 viewVector = player.getViewVector(1);

                double yaw = Math.atan2(viewVector.x, viewVector.z);
                double posX = Math.sin(yaw) * (2+blinkLevel) + player.getX();
                double posZ = Math.cos(yaw) * (2+blinkLevel) + player.getZ();
                double posY = player.getY();

                double velX = Math.sin(yaw) * 0.2;
                double velZ = Math.cos(yaw) * 0.2;
                double velY = 0;


                BlockState blockState = player.level().getBlockState(new BlockPos((int) posX, (int) posY, (int) posZ));
                if (!blockState.isSolid()) {
                    player.serverLevel().sendParticles(ParticleTypes.DRAGON_BREATH, player.getX(), player.getY(0.5), player.getZ(), 15,0.3, 0.5, 0.3, 0 );
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.7f, 1.0f);
                    player.setPos(posX, posY, posZ);
                    player.addDeltaMovement(new Vec3(velX, velY, velZ));
                }
            }
//            timerAccess.aabilities_setLeggingCooldown(200);
        });
        return true;
    }
}
