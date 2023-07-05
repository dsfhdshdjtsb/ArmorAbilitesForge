package com.dsfhdshdjtsb.ArmorAbilities.timers;

import net.minecraft.nbt.CompoundTag;

public class Timer {
    public int frostStompTimer;
    public int fireStompTimer;
    public int anvilStompTimer;

    public int frostStompAnimTimer;
    public int fireStompAnimTimer;

    public int helmetCooldown;
    public int chestplateCooldown;
    public int leggingCooldown;
    public int bootCooldown;

    public void copyFrom(Timer timer)
    {
        frostStompTimer = timer.frostStompTimer;
        fireStompTimer = timer.fireStompTimer;
        anvilStompTimer = timer.anvilStompTimer;

        helmetCooldown = timer.helmetCooldown;
        chestplateCooldown = timer.chestplateCooldown;
        leggingCooldown = timer.leggingCooldown;
        bootCooldown = timer.bootCooldown;

    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("frostStompTimer", frostStompTimer);
        nbt.putInt("fireStompTimer", fireStompTimer);
        nbt.putInt("anvilStompTimer", anvilStompTimer);

        nbt.putInt("helmetCooldown", helmetCooldown);
        nbt.putInt("chestplateCooldown", chestplateCooldown);
        nbt.putInt("leggingCooldown", leggingCooldown);
        nbt.putInt("bootCooldown", bootCooldown);

    }

    public void loadNBTData(CompoundTag nbt) {
        frostStompTimer = nbt.getInt("frostStompTimer");
        fireStompTimer = nbt.getInt("fireStompTimer");
        anvilStompTimer = nbt.getInt("anvilStompTimer");

        helmetCooldown = nbt.getInt("helmetCooldown");
        chestplateCooldown = nbt.getInt("chestplateCooldown");
        leggingCooldown = nbt.getInt("leggingCooldown");
        bootCooldown = nbt.getInt("bootCooldown");
    }

}
