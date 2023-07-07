package com.dsfhdshdjtsb.ArmorAbilities.events;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.client.CooldownData;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ArmorAbilities.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        TimerAccess timerAccess = (TimerAccess) event.player;
        if(event.phase == TickEvent.Phase.END)
        {
            timerAccess.aabilities_setAnvilStompTimer(timerAccess.aabilities_getAnvilStompTimer() - 1);
            timerAccess.aabilities_setFrostStompTimer(timerAccess.aabilities_getTicksUntilFrostStomp() - 1);
            timerAccess.aabilities_setFireStompTimer(timerAccess.aabilities_getTicksUntilFireStomp() - 1);
            timerAccess.aabilities_setFrostStompAnimTimer(timerAccess.aabilities_getTicksFrostStompAnim() - 1);
            timerAccess.aabilities_setFireStompAnimTimer(timerAccess.aabilities_getTicksFireStompAnim() - 1);
            timerAccess.aabilities_setAnvilStompAnimTimer(timerAccess.aabilities_getAnvilStompTimer() - 1);

            timerAccess.aabiliites_setFuse(timerAccess.aabilities_getFuse() - 1);
            timerAccess.aabilities_setHelmetCooldown(timerAccess.aabilities_getHelmetCooldown() - 1);
            timerAccess.aabilities_setChestCooldown(timerAccess.aabilities_getChestCooldown() - 1);
            timerAccess.aabilities_setLeggingCooldown(timerAccess.aabilities_getLeggingCooldown() - 1);
            timerAccess.aabilities_setBootCooldown(timerAccess.aabilities_getBootCooldown() - 1);
        }
        //could decrement cooldowns client side and init them with keybinds
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            ServerPlayer player = (ServerPlayer) event.player;


            if(timerAccess.aabilities_getTicksUntilFrostStomp() > 0 && player.onGround())
            {
                timerAccess.aabilities_setFrostStompTimer(0);
                timerAccess.aabilities_setFrostStompAnimTimer(5);
                int frostStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FROST_STOMP.get(), player);

                List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(7,1,7) );
                list.remove(player);

                if(!list.isEmpty())
                {
                    for(Entity e : list)
                    {
                        if(e instanceof LivingEntity)
                        {
                            e.setTicksFrozen(140 + frostStompLevel * 80);
                            int amp = 0;
                            if(frostStompLevel >= 4)
                                amp++;
                            ((LivingEntity) e).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, amp));
                            player.serverLevel().sendParticles(ParticleTypes.SNOWFLAKE, e.getX(), e.getY(0.5) - 1, e.getZ(), 10,0.5, 0.0, 0.5, 0 );
                        }
                    }
                }
                player.serverLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_SMALL_FALL, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
            if(timerAccess.aabilities_getTicksFrostStompAnim() >= 0)
            {
                for (double i = 0; i <= Math.PI * 2; i += Math.PI / 6) {
                    double x = player.getX() + Math.sin(i) * (7 - timerAccess.aabilities_getTicksFrostStompAnim() * 1.5);
                    double y = player.getBlockY() - 1;
                    double z = player.getZ() + Math.cos(i) * (7 - timerAccess.aabilities_getTicksFrostStompAnim() * 1.5);

                    BlockPos blockPos = new BlockPos((int)x, (int)y, (int)z);
                    BlockState blockState = player.level().getBlockState(blockPos);
                    if(Math.random() < .10)
                    {
                        player.serverLevel().sendParticles(ParticleTypes.SNOWFLAKE, x,
                                y + 1, z, 4, 1, 0.0D, 1, 0.0D);
                    }
                    else
                    {
                        player.serverLevel().sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), x,
                                y + 1, z, 4, 1, 0.0D, 1, 0.0D);
                    }
                }
            }
            if(timerAccess.aabilities_getTicksUntilFireStomp() > 0 && player.onGround())
            {
                timerAccess.aabilities_setFireStompTimer(0);
                timerAccess.aabilities_setFireStompAnimTimer(5);
                int fireStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FIRE_STOMP.get(), player);

                List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(7,1,7) );
                list.remove(player);

                if(!list.isEmpty())
                {
                    for(Entity e : list)
                    {
                        if(e instanceof LivingEntity) {
                            e.setRemainingFireTicks(20 * fireStompLevel);
                            e.hurt(player.level().damageSources().magic(), 2 + fireStompLevel);
                            ServerLevel level = player.serverLevel();
                            BlockPos pos = e.blockPosition();
                            if (level.getBlockState(pos) == Blocks.AIR.defaultBlockState()) {
                                BlockState fire = Blocks.FIRE.defaultBlockState();
                                level.setBlock(pos, fire, 1);
                            }
                        }
                    }
                }
                player.serverLevel().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_SMALL_FALL, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
            if(timerAccess.aabilities_getTicksFireStompAnim() >= 0)
            {
                for (double i = 0; i <= Math.PI * 2; i += Math.PI / 6) {
                    double x = player.getX() + Math.sin(i) * (7 - timerAccess.aabilities_getTicksFireStompAnim() * 1.5);
                    double y = player.getBlockY() - 1;
                    double z = player.getZ() + Math.cos(i) * (7 - timerAccess.aabilities_getTicksFireStompAnim() * 1.5);

                    BlockPos blockPos = new BlockPos((int)x, (int)y, (int)z);
                    BlockState blockState = player.level().getBlockState(blockPos);
                    if(Math.random() < .10)
                    {
                        player.serverLevel().sendParticles(ParticleTypes.FLAME, x,
                                y + 1, z, 4, 1, 0.0D, 1, 0.0D);
                    }
                    else
                    {
                        player.serverLevel().sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), x,
                                y + 1, z, 4, 1, 0.0D, 1, 0.0D);
                    }
                }
            }

        }
    }
}
