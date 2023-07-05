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

public class TimerProviderTest implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public Capability<FrostStompTimer> TIMER = CapabilityManager.get(new CapabilityToken<FrostStompTimer>() {});
    private FrostStompTimer timer = null;
    private final LazyOptional<FrostStompTimer> optional = LazyOptional.of(this::createTimer);


    private FrostStompTimer createTimer() {
        if(this.timer == null)
        {
            this.timer = new FrostStompTimer();
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
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createTimer().loadNBTData(nbt);
    }
}
