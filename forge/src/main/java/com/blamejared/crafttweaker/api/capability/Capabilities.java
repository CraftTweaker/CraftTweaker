package com.blamejared.crafttweaker.api.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/capability/Capabilities")
@ZenCodeType.Name("crafttweaker.api.capability.Capabilities")
public class Capabilities {
    
    @ZenCodeType.Field
    public static final Capability<IEnergyStorage> ENERGY = ForgeCapabilities.ENERGY;
    
    @ZenCodeType.Field
    public static final Capability<IFluidHandler> FLUID = ForgeCapabilities.FLUID_HANDLER;
    
    @ZenCodeType.Field
    public static final Capability<IFluidHandlerItem> FLUID_ITEM = ForgeCapabilities.FLUID_HANDLER_ITEM;
    
    @ZenCodeType.Field
    public static final Capability<IItemHandler> ITEM = ForgeCapabilities.ITEM_HANDLER;
    
}
