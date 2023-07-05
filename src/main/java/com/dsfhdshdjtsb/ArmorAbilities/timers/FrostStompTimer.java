package com.dsfhdshdjtsb.ArmorAbilities.timers;

import net.minecraft.nbt.CompoundTag;

public class FrostStompTimer extends Timer{
    public static FrostStompTimer INSTANCE = new FrostStompTimer();
    @Override
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("frostStompTimer", this.getTimer());
    }

    @Override
    public void loadNBTData(CompoundTag nbt) {
        this.setTimer(nbt.getInt("frostStompTimer"));
    }

}
