package com.dsfhdshdjtsb.ArmorAbilities;

import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.networking.ModMessages;
import com.dsfhdshdjtsb.ArmorAbilities.timers.AnvilStompTimer;
import com.dsfhdshdjtsb.ArmorAbilities.timers.FireStompTimer;
import com.dsfhdshdjtsb.ArmorAbilities.timers.FrostStompTimer;
import com.dsfhdshdjtsb.ArmorAbilities.timers.TimerProvider;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArmorAbilities.MODID)
public class ArmorAbilities
{
    public static final String MODID = "aabilities";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static TimerProvider<FrostStompTimer> frostStompTimerProvider = new TimerProvider<>(FrostStompTimer::new);
    public static TimerProvider<AnvilStompTimer> anvilStompTimerProvider = new TimerProvider<>(AnvilStompTimer::new);
    public static TimerProvider<FireStompTimer> fireStompTimerProvider = new TimerProvider<>(FireStompTimer::new);

    public ArmorAbilities()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        EnchantmentInit.ENCHANTMENTS.register(modEventBus);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModMessages.register();
    }

//    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event)
//    {
//        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
