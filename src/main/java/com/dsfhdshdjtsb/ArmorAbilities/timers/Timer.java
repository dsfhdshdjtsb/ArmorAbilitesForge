package com.dsfhdshdjtsb.ArmorAbilities.timers;

import net.minecraft.nbt.CompoundTag;

public abstract class Timer {
    private int timer;

    public int getTimer(){
        return timer;
    }

    public void setTimer(int time)
    {
        timer = time;
    }

    public void copyFrom(Timer source)
    {
        this.timer = source.timer;
    }

    public void subTimer(int sub)
    {
        this.timer = Math.max(timer - sub, 0);
    }

    public void addTimer(int add)
    {
        this.timer+= add;
    }

    public abstract void saveNBTData(CompoundTag nbt);
    public abstract void loadNBTData(CompoundTag nbt);

}
