package com.dsfhdshdjtsb.ArmorAbilities.client;

import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class ClientMethods {
    public static boolean timerUpdate(boolean shouldAnvilRender, long fuse, int playerId)
    {
        TimerAccess timerAccess = (TimerAccess) Minecraft.getInstance().level.getEntity(playerId);
        timerAccess.aabilities_setShouldAnvilRender(shouldAnvilRender);
        timerAccess.aabiliites_setFuse(fuse);
        Minecraft.getInstance().level.getEntity(playerId).sendSystemMessage(Component.literal("timerUpdate"));
        System.out.println(Minecraft.getInstance().level.getEntity(playerId));
        System.out.println(timerAccess.aabilities_getAnvilStompTimer());
        return true;
    }

    public static boolean update(Player player, Vec3 vec3)
    {
        System.out.println("update method");
        player.setDeltaMovement(vec3);
        return true;
    }
}
