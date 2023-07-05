package com.dsfhdshdjtsb.ArmorAbilities.events;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.timers.FireStompTimer;
import com.dsfhdshdjtsb.ArmorAbilities.timers.FrostStompTimer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
            if(!event.getObject().getCapability(ArmorAbilities.frostStompTimerProvider.TIMER).isPresent()) {
                event.addCapability(new ResourceLocation(ArmorAbilities.MODID, "properties"), ArmorAbilities.frostStompTimerProvider);
            }
            if(!event.getObject().getCapability(ArmorAbilities.fireStompTimerProvider.TIMER).isPresent()) {
                event.addCapability(new ResourceLocation(ArmorAbilities.MODID, "properties"), ArmorAbilities.fireStompTimerProvider);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(ArmorAbilities.frostStompTimerProvider.TIMER).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ArmorAbilities.frostStompTimerProvider.TIMER).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(ArmorAbilities.fireStompTimerProvider.TIMER).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ArmorAbilities.fireStompTimerProvider.TIMER).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FrostStompTimer.class);
        event.register(FireStompTimer.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            Player player = event.player;
            event.player.getCapability(ArmorAbilities.frostStompTimerProvider.TIMER).ifPresent(timer -> {
                timer.subTimer(1);
                System.out.println("frost: " + timer.getTimer());

                if(timer.getTimer() > 0 && player.onGround())
                {
                    int frostStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FROST_STOMP.get(), player);

                    List<Entity> list = player.level().getEntities(player, player.getBoundingBox().expandTowards(7, 1, 7));
                    list.remove(player);

                    System.out.println(list);

                    if(!list.isEmpty())
                    {
                        timer.setTimer(0);
                    }
                }
            });
            event.player.getCapability(ArmorAbilities.fireStompTimerProvider.TIMER).ifPresent(timer -> {
                timer.subTimer(1);
                System.out.println("fire: " + timer.getTimer());
            });


        }
    }
}
