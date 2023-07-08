package com.dsfhdshdjtsb.ArmorAbilities.mixin;

import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class AabilitiesPlayerEntityMixin implements TimerAccess {
    private long ticksUntilFireStomp = 0;
    private long ticksFireStompAnim = -1;
    private long ticksUntilFrostStomp = 0;
    private long ticksFrostStompAnim = -1;
    private long ticksAnvilStomp = -5;
    private long ticksAnvilStompAnim = 0;
    public long helmetCooldown = 0;
    public long chestCooldown = 0;
    public long leggingCooldown = 0;
    public long bootCooldown = 0;
    public boolean shouldRenderAnvil = false;
    private long fuse = 0;

    @Inject(at = @At("HEAD"), method = "hurt", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) ((Object)this);
        if(((TimerAccess) player).aabilities_getAnvilStompTimer() > -5)
        {
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "attack", cancellable = true)
    private void attack(Entity target, CallbackInfo ci)
    {
        TimerAccess timerAccess = (this);
        if(timerAccess.aabilities_getAnvilStompTimer() > -5 || timerAccess.aabilities_getFuse() > 0)
        {
            ci.cancel();
        }
    }

    @Override
    public void aabilities_setFireStompTimer(long ticksUntilFireStomp) {
        this.ticksUntilFireStomp = ticksUntilFireStomp;
    }
    @Override
    public void aabilities_setFireStompAnimTimer(long ticks) {
        this.ticksFireStompAnim = ticks;
    }
    @Override
    public void aabilities_setAnvilStompAnimTimer(long ticks) {
        this.ticksAnvilStompAnim = ticks;
    }

    @Override
    public long aabilities_getAnvilStompAnimTimer() {
        return ticksAnvilStompAnim;
    }

    @Override
    public void aabilities_setShouldAnvilRender(boolean bool) {
        this.shouldRenderAnvil = bool;
    }

    @Override
    public boolean aabilities_getShouldAnvilRender() {
        return shouldRenderAnvil;
    }

    @Override
    public void aabilities_setFrostStompTimer(long ticksUntilFrostStomp) {
        this.ticksUntilFrostStomp = ticksUntilFrostStomp;
    }

    @Override
    public void aabilities_setFrostStompAnimTimer(long ticks) {
        this.ticksFrostStompAnim = ticks;
    }

    @Override
    public long aabilities_getTicksUntilFireStomp() {
        return ticksUntilFireStomp;
    }
    @Override
    public long aabilities_getTicksFireStompAnim() {
        return ticksFireStompAnim;
    }
    @Override
    public long aabilities_getTicksUntilFrostStomp() {
        return ticksUntilFrostStomp;
    }
    @Override
    public long aabilities_getTicksFrostStompAnim() {
        return ticksFrostStompAnim;
    }

    @Override
    public void aabiliites_setFuse(long ticks) {
        this.fuse = ticks;
    }

    @Override
    public void aabilities_setAnvilStompTimer(long ticks) {
        this.ticksAnvilStomp = ticks;
    }

    @Override
    public void aabilities_setHelmetCooldown(long ticks) {
        this.helmetCooldown = ticks;
    }

    @Override
    public void aabilities_setChestCooldown(long ticks) {
        this.chestCooldown = ticks;
    }

    @Override
    public void aabilities_setLeggingCooldown(long ticks) {
        this.leggingCooldown = ticks;
    }

    @Override
    public void aabilities_setBootCooldown(long ticks) {
        this.bootCooldown = ticks;
    }

    @Override
    public long aabilities_getHelmetCooldown() {
        return this.helmetCooldown;
    }

    @Override
    public long aabilities_getChestCooldown() {
        return this.chestCooldown;
    }

    @Override
    public long aabilities_getLeggingCooldown() {
        return this.leggingCooldown;
    }

    @Override
    public long aabilities_getBootCooldown() {
        return this.bootCooldown;
    }

    @Override
    public long aabilities_getFuse() {
        return fuse;
    }

    @Override
    public long aabilities_getAnvilStompTimer() {
        return this.ticksAnvilStomp;
    }
}
