package com.dsfhdshdjtsb.ArmorAbilities.events;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.client.CooldownHudOverlay;
import com.dsfhdshdjtsb.ArmorAbilities.init.EnchantmentInit;
import com.dsfhdshdjtsb.ArmorAbilities.networking.ModMessages;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.BootC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.ChestplateC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.HelmetC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.networking.packet.LeggingC2SPacket;
import com.dsfhdshdjtsb.ArmorAbilities.util.KeyBinding;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import io.netty.buffer.UnpooledByteBufAllocator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ArmorAbilities.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents{
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            LocalPlayer player = Minecraft.getInstance().player;

            TimerAccess timerAccess = (TimerAccess) player;

            if(KeyBinding.HELMET_ABILITY_KEY.consumeClick() && timerAccess.aabilities_getHelmetCooldown() <= 0){
                int cooldown = 0;
                int mindControlLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIND_CONTROL.get(), player);
                int telekinesisLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), player);
                int focusLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FOCUS.get(), player);

                if(mindControlLevel > 0)
                {

                    List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5 + mindControlLevel,mindControlLevel + 5,5 + mindControlLevel) );
                    list.remove(player);
                    if(list.isEmpty())
                        return;

                    cooldown = 600 - focusLevel * 40;
                    ModMessages.sendToServer(new HelmetC2SPacket());
                }
                else if(telekinesisLevel > 0)
                {

                    List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5 + telekinesisLevel,2,5 + telekinesisLevel) );
                    list.remove(player);
                    if(list.isEmpty())
                        return;

                    cooldown = 300 - telekinesisLevel * 20;
                    ModMessages.sendToServer(new HelmetC2SPacket());
                }
                else if(focusLevel > 0)
                {
                    cooldown = 1100 - focusLevel * 100;
                    timerAccess.aabilities_setChestCooldown(0);
                    timerAccess.aabilities_setBootCooldown(0);
                    timerAccess.aabilities_setLeggingCooldown(0);
                    ModMessages.sendToServer(new HelmetC2SPacket());
                }
                timerAccess.aabilities_setHelmetCooldown(cooldown);

            }
            if(KeyBinding.CHESTPLATE_ABILITY_KEY.consumeClick() && timerAccess.aabilities_getChestCooldown() <= 0){
                int cooldown = 0;
                int explodeLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.EXPLODE.get(), player);
                int siphonLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SIPHON.get(), player);
                int cleanseLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.CLEANSE.get(), player);

                if(explodeLevel > 0 && timerAccess.aabilities_getAnvilStompTimer() < -5)
                {
                    cooldown = 400 - explodeLevel * 20;

                    timerAccess.aabiliites_setFuse(80);
                    ModMessages.sendToServer(new ChestplateC2SPacket());
                }
                else if(cleanseLevel > 0)
                {
                    cooldown = 400 - cleanseLevel * 40;

                    ModMessages.sendToServer(new ChestplateC2SPacket());
                }
                else if(siphonLevel > 0)
                {
                    List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3 + siphonLevel,1,3 + siphonLevel) );
                    list.remove(player);
                    if(list.isEmpty())
                        return;
                    cooldown = 400 - siphonLevel * 20;
                    ModMessages.sendToServer(new ChestplateC2SPacket());
                }
                timerAccess.aabilities_setChestCooldown(cooldown);
            }
            if(KeyBinding.LEGGING_ABILITY_KEY.consumeClick() && timerAccess.aabilities_getLeggingCooldown() <= 0){
                int cooldown = 0;
                int dashLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DASH.get(), player);
                int blinkLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.BLINK.get(), player);
                int rushLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RUSH.get(), player);

                if(dashLevel > 0 && ((timerAccess.aabilities_getFuse() < 0 && timerAccess.aabilities_getAnvilStompTimer() < -5) || !player.onGround())) {
                    cooldown = 300 - dashLevel * 20;

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
                else if (rushLevel > 0){
                    cooldown = 300 - rushLevel * 20;

                    ModMessages.sendToServer(new LeggingC2SPacket());
                }
                if(blinkLevel > 0)
                {
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
                        player.setPos(posX, posY, posZ);
                        player.addDeltaMovement(new Vec3(velX, velY, velZ));
                        cooldown = 300 - blinkLevel * 20;
                        ModMessages.sendToServer(new LeggingC2SPacket());
                    }
                }
                timerAccess.aabilities_setLeggingCooldown(cooldown);
            }
            if(KeyBinding.BOOT_ABILITY_KEY.consumeClick() && timerAccess.aabilities_getBootCooldown() <= 0){
                int cooldown = 0;
                int frostStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FROST_STOMP.get(), player);
                int fireStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FIRE_STOMP.get(), player);
                int anvilStompLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ANVIL_STOMP.get(), player);
                if(fireStompLevel > 0)
                {
                    cooldown = 300 - fireStompLevel * 20;
                    if(player.onGround())
                    {
                        player.jumpFromGround();
                    }
                    ModMessages.sendToServer(new BootC2SPacket());
                }
                else if(frostStompLevel > 0)
                {
                    cooldown = 300 - frostStompLevel * 20;
                    if(player.onGround())
                    {
                        player.jumpFromGround();
                    }
                    ModMessages.sendToServer(new BootC2SPacket());
                }
                else if(anvilStompLevel > 0 && timerAccess.aabilities_getFuse() < 0 && !player.isFallFlying())
                {
                    cooldown = 300 - anvilStompLevel * 20;
                    if(player.onGround())
                    {
                        player.jumpFromGround();
                    }
                    timerAccess.aabilities_setAnvilStompTimer(100);
                    timerAccess.aabilities_setShouldAnvilRender(true);
                    ModMessages.sendToServer(new BootC2SPacket());
                }
                timerAccess.aabilities_setBootCooldown(cooldown);

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

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("cooldownhud", CooldownHudOverlay.HUD_COOLDOWN);
        }
    }

}
