package com.blamejared.crafttweaker.api.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/EnergyStorageCaps")
@ZenCodeType.Name("crafttweaker.api.capability.EnergyStorageCaps")
public class EnergyStorageCaps {
    
    @ZenCodeType.Field
    public static final BlockCapability<IEnergyStorage, @ZenCodeType.Nullable Direction> BLOCK = Capabilities.EnergyStorage.BLOCK;
    @ZenCodeType.Field
    public static final EntityCapability<IEnergyStorage, @ZenCodeType.Nullable Direction> ENTITY = Capabilities.EnergyStorage.ENTITY;
    @ZenCodeType.Field
    public static final ItemCapability<IEnergyStorage, Void> ITEM = Capabilities.EnergyStorage.ITEM;
    
}
