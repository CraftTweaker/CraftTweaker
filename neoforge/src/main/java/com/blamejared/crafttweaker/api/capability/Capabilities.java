package com.blamejared.crafttweaker.api.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/Capabilities")
@ZenCodeType.Name("crafttweaker.api.capability.Capabilities")
public class Capabilities {
    
    @ZenCodeType.Field
    public static final Capability<IEnergyStorage> ENERGY = net.neoforged.neoforge.common.capabilities.Capabilities.ENERGY;
    
    @ZenCodeType.Field
    public static final Capability<IFluidHandler> FLUID = net.neoforged.neoforge.common.capabilities.Capabilities.FLUID_HANDLER;
    
    @ZenCodeType.Field
    public static final Capability<IFluidHandlerItem> FLUID_ITEM = net.neoforged.neoforge.common.capabilities.Capabilities.FLUID_HANDLER_ITEM;
    
    @ZenCodeType.Field
    public static final Capability<IItemHandler> ITEM = net.neoforged.neoforge.common.capabilities.Capabilities.ITEM_HANDLER;
    
}
