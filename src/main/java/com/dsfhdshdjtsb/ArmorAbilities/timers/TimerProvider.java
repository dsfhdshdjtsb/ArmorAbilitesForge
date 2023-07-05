package com.dsfhdshdjtsb.ArmorAbilities.timers;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class TimerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Timer> TIMER = CapabilityManager.get(new CapabilityToken<>() {});;
    private Timer timer = null;
    private final LazyOptional<Timer> optional = LazyOptional.of(this::createTimer);

    private Timer createTimer() {
        if(this.timer == null)
        {
            this.timer = new Timer();
        }

        return this.timer;
    }

    @Override
    public @NotNull <Y> LazyOptional<Y> getCapability(@NotNull Capability<Y> cap, @Nullable Direction side) {
        if(cap == TIMER)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createTimer().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createTimer().loadNBTData(nbt);
    }
}
