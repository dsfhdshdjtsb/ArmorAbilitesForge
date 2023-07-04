package com.dsfhdshdjtsb.ArmorAbilities.events;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.networking.ModMessages;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.BootC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.ChestplateC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.HelmetC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.LeggingC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.util.KeyBinding;
import io.netty.buffer.UnpooledByteBufAllocator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
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
            LocalPlayer player = Minecraft.getInstance().player;
            if(KeyBinding.HELMET_ABILITY_KEY.consumeClick()){
                player.sendSystemMessage(Component.literal("helmet"));
                ModMessages.sendToServer(new HelmetC2SPacket());
            }
            if(KeyBinding.CHESTPLATE_ABILITY_KEY.consumeClick()){
                player.sendSystemMessage(Component.literal("chestplate"));
                ModMessages.sendToServer(new ChestplateC2SPacket());
            }
            if(KeyBinding.LEGGING_ABILITY_KEY.consumeClick()){
                player.sendSystemMessage(Component.literal("legging"));
                int dashLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DASH.get(), player);
                double distanceMult = .80 + dashLevel * .1;

                Vec3 viewVector = player.getViewVector(1);

                double pitch = Math.asin(-viewVector.y);
                double velY = -Math.sin(pitch) * distanceMult;
                double mult = Math.cos(pitch);

                double yaw = Math.atan2(viewVector.x, viewVector.z);
                double velX = (Math.sin(yaw) * mult) * distanceMult;
                double velZ = (Math.cos(yaw) * mult) * distanceMult;

                player.setDeltaMovement(new Vec3(velX, velY, velZ));

                ModMessages.sendToServer(new LeggingC2SPacket());
            }
            if(KeyBinding.BOOT_ABILITY_KEY.consumeClick()){
                player.sendSystemMessage(Component.literal("boot"));
                ModMessages.sendToServer(new BootC2SPacket());
            }

        }


    }
    @Mod.EventBusSubscriber(modid = ArmorAbilities.MODID, value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.BOOT_ABILITY_KEY);
            event.register(KeyBinding.CHESTPLATE_ABILITY_KEY);
            event.register(KeyBinding.LEGGING_ABILITY_KEY);
            event.register(KeyBinding.HELMET_ABILITY_KEY);
        }
    }
}
