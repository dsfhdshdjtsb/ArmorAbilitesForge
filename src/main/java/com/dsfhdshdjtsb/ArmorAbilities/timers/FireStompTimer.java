package com.dsfhdshdjtsb.ArmorAbilities.timers;

import net.minecraft.nbt.CompoundTag;

public class FireStompTimer extends Timer{
    @Override
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("fireStompTimer", this.getTimer());
    }

    @Override
    public void loadNBTData(CompoundTag nbt) {
        this.setTimer(nbt.getInt("fireStompTimer"));
    }

}
