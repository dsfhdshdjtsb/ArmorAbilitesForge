package com.dsfhdshdjtsb.ArmorAbilities.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY = "key.category.aabilities.abilities";
    private static final String KEY_BOOT_ABILITY = "key.aabilities.boot_ability";
    private static final String KEY_LEGGING_ABILITY = "key.aabilities.legging_ability";
    private static final String KEY_CHESTPLATE_ABILITY = "key.aabilities.chestplate_ability";
    private static final String KEY_HELMET_ABILITY = "key.aabilities.helmet_ability";

    public static final KeyMapping BOOT_ABILITY_KEY = new KeyMapping(KEY_BOOT_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY);
    public static final KeyMapping LEGGING_ABILITY_KEY = new KeyMapping(KEY_LEGGING_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY);
    public static final KeyMapping CHESTPLATE_ABILITY_KEY = new KeyMapping(KEY_CHESTPLATE_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY);
    public static final KeyMapping HELMET_ABILITY_KEY = new KeyMapping(KEY_HELMET_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY);
}
