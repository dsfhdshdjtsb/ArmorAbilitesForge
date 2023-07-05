package com.dsfhdshdjtsb.ArmorAbilities.events;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.timers.FrostStompTimer;
import com.dsfhdshdjtsb.ArmorAbilities.timers.Timer;
import com.dsfhdshdjtsb.ArmorAbilities.timers.TimerProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.util.SystemNanoClock;
@Mod.EventBusSubscriber(modid = ArmorAbilities.MODID)
public class ModEvents {

    public static TimerProvider<FrostStompTimer> timer = new TimerProvider<>(FrostStompTimer.INSTANCE);
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(timer.TIMER).isPresent()) {
                event.addCapability(new ResourceLocation(ArmorAbilities.MODID, "properties"), timer);
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(timer.TIMER).ifPresent(oldStore -> {
                event.getOriginal().getCapability(timer.TIMER).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FrostStompTimer.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        System.out.println("test");
        if(event.side == LogicalSide.SERVER) {
            System.out.println("te2st");
            event.player.getCapability(timer.TIMER).ifPresent(timer -> {
                timer.subTimer(1);
                event.player.sendSystemMessage(Component.literal("Subtracted Timer"));
                System.out.println(timer.getTimer());

            });
        }
    }
}
