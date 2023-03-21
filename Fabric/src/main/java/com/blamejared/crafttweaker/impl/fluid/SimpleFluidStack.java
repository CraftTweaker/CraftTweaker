package com.blamejared.crafttweaker.impl.fluid;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.*;
import org.jetbrains.annotations.Nullable;

public class SimpleFluidStack {
    
    private final Fluid fluid;
    private long amount;
    private CompoundTag tag;
    
    public SimpleFluidStack(Fluid fluid, long amount) {
        
        this(fluid, amount, null);
    }
    
    public SimpleFluidStack(Fluid fluid, long amount, @Nullable CompoundTag tag) {
        
        this.fluid = fluid;
        this.amount = amount;
        this.tag = tag;
    }
    
    public SimpleFluidStack copy() {
        
        return new SimpleFluidStack(fluid(), amount(), tag());
    }
    
    public boolean isEmpty() {
        
        return fluid == Fluids.EMPTY || amount() <= 0;
    }
    
    public Fluid fluid() {
        
        return fluid;
    }
    
    public long amount() {
        
        return amount;
    }
    
    public CompoundTag tag() {
        
        return tag;
    }
    
    public void amount(long amount) {
        
        this.amount = amount;
    }
    
    public void tag(CompoundTag tag) {
        
        this.tag = tag;
    }
    
}
