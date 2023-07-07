package com.dsfhdshdjtsb.ArmorAbilities.client;

import com.dsfhdshdjtsb.ArmorAbilities.ArmorAbilities;
import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CooldownHudOverlay {
    public static final ResourceLocation HELMET = new ResourceLocation(ArmorAbilities.MODID, "textures/gui/helmet.png");
    public static final ResourceLocation CHESTPLATE = new ResourceLocation(ArmorAbilities.MODID, "textures/gui/chestplate.png");
    public static final ResourceLocation LEGGING = new ResourceLocation(ArmorAbilities.MODID, "textures/gui/leggings.png");
    public static final ResourceLocation BOOT = new ResourceLocation(ArmorAbilities.MODID, "textures/gui/boots.png");

    public static final IGuiOverlay HUD_COOLDOWN = (((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);


        float color;
        Level level = Minecraft.getInstance().level;

        TimerAccess timerAccess = (TimerAccess) Minecraft.getInstance().player;

        double curCooldown = timerAccess.aabilities_getHelmetCooldown();
        if(curCooldown > 0 ) {
            if (curCooldown < 40) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 2.0));
            } else if (curCooldown < 100) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 10.0));
            } else {
                color = 1.0f;
            }
            RenderSystem.setShaderColor(color, color, color, 1.0f);
            RenderSystem.setShaderTexture(0, HELMET);

            guiGraphics.blit(HELMET, x - 187, y - 23, 0, 0, 20, 20, 20, 20);
        }

        curCooldown = timerAccess.aabilities_getChestCooldown();
        if(curCooldown > 0 ) {
            if (curCooldown < 40) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 2.0));
            } else if (curCooldown < 100) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 10.0));
            } else {
                color = 1.0f;
            }
            RenderSystem.setShaderColor(color, color, color, 1.0f);
            RenderSystem.setShaderTexture(0, CHESTPLATE);

            guiGraphics.blit(CHESTPLATE, x - 172, y - 23, 0, 0, 20, 20, 20, 20);
        }

        curCooldown = timerAccess.aabilities_getLeggingCooldown();
        if(curCooldown > 0 ) {
            if (curCooldown < 40) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 2.0));
            } else if (curCooldown < 100) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 10.0));
            } else {
                color = 1.0f;
            }
            RenderSystem.setShaderColor(color, color, color, 1.0f);
            RenderSystem.setShaderTexture(0, LEGGING);

            guiGraphics.blit(LEGGING, x - 157, y - 23, 0, 0, 20, 20, 20, 20);
        }

        curCooldown = timerAccess.aabilities_getBootCooldown();
        if(curCooldown > 0 ) {
            if (curCooldown < 40) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 2.0));
            } else if (curCooldown < 100) {
                color = (float) Math.abs(Math.sin(level.getGameTime() / 10.0));
            } else {
                color = 1.0f;
            }
            RenderSystem.setShaderColor(color, color, color, 1.0f);
            RenderSystem.setShaderTexture(0, BOOT);

            guiGraphics.blit(BOOT, x - 141, y - 23, 0, 0, 20, 20, 20, 20);
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }));
}
