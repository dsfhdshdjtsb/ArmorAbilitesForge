package com.dsfhdshdjtsb.ArmorAbilities.events;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.client.CooldownData;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.timers.Timer;
import com.dsfhdshdjtsb.ArmorAbilities.timers.TimerData;
import com.dsfhdshdjtsb.ArmorAbilities.timers.TimerProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(TimerProvider.TIMER).isPresent()) {
                event.addCapability(new ResourceLocation(ArmorAbilities.MODID, "properties"), new TimerProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(TimerProvider.TIMER).ifPresent(oldStore -> {
                event.getOriginal().getCapability(TimerProvider.TIMER).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Timer.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            ServerPlayer player = (ServerPlayer) event.player;
            event.player.getCapability(TimerProvider.TIMER).ifPresent(timer -> {
                timer.frostStompTimer--;
                TimerData.HelmetCooldown = timer.helmetCooldown--;
                TimerData.ChestplateCooldown = timer.chestplateCooldown--;
                TimerData.LeggingCooldown = timer.leggingCooldown--;
                TimerData.BootCooldown = timer.bootCooldown--;

                if(timer.frostStompTimer > 0 && player.onGround())
                {
                    timer.frostStompTimer = 0;
                    timer.frostStompAnimTimer = 5;
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
                if(--timer.frostStompAnimTimer >= 0)
                {
                    for (double i = 0; i <= Math.PI * 2; i += Math.PI / 6) {
                        double x = player.getX() + Math.sin(i) * (7 - timer.frostStompAnimTimer * 1.5);
                        double y = player.getBlockY() - 1;
                        double z = player.getZ() + Math.cos(i) * (7 - timer.frostStompAnimTimer * 1.5);

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

            });
        }

    }
}
