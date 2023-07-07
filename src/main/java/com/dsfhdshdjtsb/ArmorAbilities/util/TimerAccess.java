package com.dsfhdshdjtsb.ArmorAbilities.util;

public interface TimerAccess {

    void aabilities_setFireStompTimer(long ticksUntilFireStomp);
    void aabilities_setFireStompAnimTimer(long ticksUntilFireStomp);
    long aabilities_getTicksUntilFireStomp();
    long aabilities_getTicksFrostStompAnim();

    void aabilities_setFrostStompTimer(long ticksUntilFrostStomp);
    void aabilities_setFrostStompAnimTimer(long ticksUntilFrostStomp);
    long aabilities_getTicksFireStompAnim();
    long aabilities_getTicksUntilFrostStomp();


    void aabiliites_setFuse(long ticks);

    void aabilities_setAnvilStompTimer(long ticks);
    void aabilities_setAnvilStompAnimTimer(long ticks);
    void aabilities_setShouldAnvilRender(boolean bool);
    boolean aabilities_getShouldAnvilRender();

    void aabilities_setHelmetCooldown(long ticks);
    void aabilities_setChestCooldown(long ticks);
    void aabilities_setLeggingCooldown(long ticks);
    void aabilities_setBootCooldown(long ticks);

    long aabilities_getHelmetCooldown();
    long aabilities_getChestCooldown();
    long aabilities_getLeggingCooldown();
    long aabilities_getBootCooldown();

    long aabilities_getFuse();
    long aabilities_getAnvilStompTimer();
}
