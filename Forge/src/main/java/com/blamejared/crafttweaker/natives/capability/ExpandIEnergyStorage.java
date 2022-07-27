package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.energy.IEnergyStorage;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/capability/IEnergyStorage")
@NativeTypeRegistration(value = IEnergyStorage.class, zenCodeName = "crafttweaker.api.capability.IEnergyStorage")
public class ExpandIEnergyStorage {
    
    /**
     * Adds energy to the storage.
     *
     * @param maxReceive The max amount of energy to be inserted.
     * @param simulate   If the energy should actually be inserted or not.
     *
     * @return the amount of energy that was accepted.
     *
     * @docParam maxReceive 300
     * @docParam simulate false
     */
    @ZenCodeType.Method
    public static int receiveEnergy(IEnergyStorage internal, int maxReceive, boolean simulate) {
        
        return internal.receiveEnergy(maxReceive, simulate);
    }
    
    /**
     * Extracts energy from the storage.
     *
     * @param maxExtract The max amount of energy to be extracted.
     * @param simulate   If the energy should actually be extracted or not.
     *
     * @return the amount of energy that was extracted.
     *
     * @docParam maxExtract 400
     * @docParam simulate false
     */
    @ZenCodeType.Method
    public static int extractEnergy(IEnergyStorage internal, int maxExtract, boolean simulate) {
        
        return internal.extractEnergy(maxExtract, simulate);
    }
    
    /**
     * Gets the amount of energy stored.
     *
     * @return The amount of energy stored.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("energyStored")
    public static int getEnergyStored(IEnergyStorage internal) {
        
        return internal.getEnergyStored();
    }
    
    /**
     * Gets the max energy that can be stored.
     *
     * @return The max energy that can be stored.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxEnergyStored")
    public static int getMaxEnergyStored(IEnergyStorage internal) {
        
        return internal.getMaxEnergyStored();
    }
    
    /**
     * Checks if energy can be extracted from this storage.
     *
     * @return true if the storage can be extracted from, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canExtract")
    public static boolean canExtract(IEnergyStorage internal) {
        
        return internal.canExtract();
    }
    
    /**
     * Checks if this storage can receive energy.
     *
     * @return true if this storage can receive energy, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canReceive")
    public static boolean canReceive(IEnergyStorage internal) {
        
        return internal.canReceive();
    }
    
}