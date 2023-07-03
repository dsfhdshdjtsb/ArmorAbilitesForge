package com.dsfhdshdjtsb.ArmorAbilities.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY = "key.category.aabilities.abiliites";
    private static final String KEY_BOOT_ABILITY = "key.aabilities.boot_ability";

    public static final KeyMapping BOOT_ABILITY_KEY = new KeyMapping(KEY_BOOT_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY);
}
