package com.dsfhdshdjtsb.ArmorAbilities.networking.packet;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.init.EffectsInit;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
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

            TimerAccess timerAccess = (TimerAccess) player;

            int mindControlLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIND_CONTROL.get(), player);
            int telekinesisLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), player);
            int focusLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FOCUS.get(), player);

            if(mindControlLevel > 0) {
                List<Mob> list = player.level().getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(mindControlLevel + 5, 1, mindControlLevel + 5));
                list.remove(player);

                if (!(list.size() <= 1)) {
                    for (int i = 0; i < list.size(); i++) {
                        Mob e = list.get(i);
                        if (e.getMaxHealth() < player.getMaxHealth() * 2 && !e.hasEffect(EffectsInit.MIND_CONTROL_COOLDOWN.get())) {
                            if (i + 1 < list.size()) {
                                e.setTarget(list.get(i + 1));
                            } else {
                                e.setTarget(list.get(0));
                            }
//                            e.addStatusEffect(new StatusEffectInstance(ArmorAbilities.MIND_CONTROLLED_EFFECT, 160, 0));
                            if (!(e instanceof Creeper))
                                e.addEffect(new MobEffectInstance(EffectsInit.MIND_CONTROL_COOLDOWN.get(), 1200, 0, false, false));
                            double xdif = e.getX() - player.getX();
                            double ydif = e.getY(0.5D) - player.getY(0.5D);
                            double zdif = e.getZ() - player.getZ();

                            int particleNumConstant = 20; //number of particles
                            double x = 0;
                            double y = 0;
                            double z = 0;
                            while (Math.abs(x) < Math.abs(xdif)) {
                                player.serverLevel().sendParticles(ParticleTypes.ELECTRIC_SPARK, player.getX() + x,
                                        player.getY(1D) + y, player.getZ() + z, 0, 1, 0.0D, 1, 0.0D);
                                x = x + xdif / particleNumConstant;
                                y = y + ydif / particleNumConstant;
                                z = z + zdif / particleNumConstant;
                            }
                        }
                    }
                }
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 0.7f, 1.0f);
            }
            else if (telekinesisLevel > 0)
            {
                List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5 + telekinesisLevel,2,5 + telekinesisLevel) );
                list.remove(player);

                for(LivingEntity e : list)
                {
                    float str = player.distanceTo(e) / 7;
                    e.knockback(str, e.getX() - player.getX(), e.getZ() - player.getZ());
                    if(e instanceof Player)
                    {
                        //SEND PACKET TO UPDATE VEL HERE
                    }
                    player.serverLevel().sendParticles(ParticleTypes.POOF, e.getX(),
                            e.getY(1D), e.getZ(), 5, 0.3, 0.5, 0.3, 0.0D);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 0.7f, 1.0f);

                }
                player.serverLevel().sendParticles(ParticleTypes.ELECTRIC_SPARK, player.getX(),
                        player.getY(1D) + 0.25D, player.getZ(), 5, 0.1, 0.1, 0.1, 0.0D);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 0.7f, 1.0f);


            }
            else if(focusLevel > 0)
            {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 0.7f, 1.0f);
            }
//            timerAccess.aabilities_setHelmetCooldown(200);

        });
        return true;
    }
}
