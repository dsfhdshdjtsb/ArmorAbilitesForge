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

public class TimerProvider<T extends Timer> implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    Supplier<T> supplier;
    public Capability<Timer> TIMER = CapabilityManager.get(new CapabilityToken<>() {});;
    private T timer = null;
    private final LazyOptional<T> optional = LazyOptional.of(this::createTimer);

    public TimerProvider(Supplier<T> supplier)
    {
        this.supplier = supplier;
    }

    private T createTimer() {
        if(this.timer == null)
        {
            this.timer = supplier.get();
        }

        return this.timer;
    }

    @Override
    public @NotNull <Y> LazyOptional<Y> getCapability(@NotNull Capability<Y> cap, @Nullable Direction side) {
        if(cap == TIMER)
        {
            System.out.println("true");
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
