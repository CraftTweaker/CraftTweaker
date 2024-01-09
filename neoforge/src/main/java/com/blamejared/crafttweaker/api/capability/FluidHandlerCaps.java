package com.blamejared.crafttweaker.api.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/FluidHandlerCaps")
@ZenCodeType.Name("crafttweaker.api.capability.FluidHandlerCaps")
public class FluidHandlerCaps {
    
    @ZenCodeType.Field
    public static final BlockCapability<IFluidHandler, @ZenCodeType.Nullable Direction> BLOCK = Capabilities.FluidHandler.BLOCK;
    @ZenCodeType.Field
    public static final EntityCapability<IFluidHandler, @ZenCodeType.Nullable Direction> ENTITY = Capabilities.FluidHandler.ENTITY;
    @ZenCodeType.Field
    public static final ItemCapability<IFluidHandlerItem, Void> ITEM = Capabilities.FluidHandler.ITEM;
    
}
