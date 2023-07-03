package com.dsfhdshdjtsb.ArmorAbilities.events;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.networking.ModMessages;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.C2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ArmorAbilities.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents{
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.BOOT_ABILITY_KEY.consumeClick()){
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("pressed a key"));
                ModMessages.sendToServer(new C2SPacket());
            }
        }


    }
    @Mod.EventBusSubscriber(modid = ArmorAbilities.MODID, value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.BOOT_ABILITY_KEY);
        }
    }
}
