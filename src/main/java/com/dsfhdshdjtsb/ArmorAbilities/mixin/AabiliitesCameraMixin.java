package com.dsfhdshdjtsb.ArmorAbilities.mixin;

import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class AabiliitesCameraMixin {

    @Inject(at = @At("HEAD"), method = "isDetached", cancellable = true)
    private void isDetachedInject(CallbackInfoReturnable<Boolean> cir)
    {
        TimerAccess timerAccess = (TimerAccess) Minecraft.getInstance().player;
        if(timerAccess.aabilities_getFuse() >= 0 || timerAccess.aabilities_getAnvilStompTimer() >= -5)
        {
            cir.setReturnValue(true);
        }

    }


}
