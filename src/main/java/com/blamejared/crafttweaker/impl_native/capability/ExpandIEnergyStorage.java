package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.energy.IEnergyStorage;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/capability/IEnergyStorage")
@NativeTypeRegistration(value = IEnergyStorage.class, zenCodeName = "crafttweaker.api.capability.IEnergyStorage")
public class ExpandIEnergyStorage {
    
    @ZenCodeType.Method
    public static int receiveEnergy(IEnergyStorage internal, int maxReceive, boolean simulate) {
        
        return internal.receiveEnergy(maxReceive, simulate);
    }
    
    @ZenCodeType.Method
    public static int extractEnergy(IEnergyStorage internal, int maxExtract, boolean simulate) {
        
        return internal.extractEnergy(maxExtract, simulate);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("energyStored")
    public static int getEnergyStored(IEnergyStorage internal) {
        
        return internal.getEnergyStored();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxEnergyStored")
    public static int getMaxEnergyStored(IEnergyStorage internal) {
        
        return internal.getMaxEnergyStored();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canExtract")
    public static boolean canExtract(IEnergyStorage internal) {
        
        return internal.canExtract();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canReceive")
    public static boolean canReceive(IEnergyStorage internal) {
        
        return internal.canReceive();
    }
    
}
