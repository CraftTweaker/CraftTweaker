package com.blamejared.crafttweaker.api.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/capability/Capabilities")
@ZenCodeType.Name("crafttweaker.api.capability.Capabilities")
public class Capabilities {
    
    @ZenCodeType.Field
    public static final Capability<IEnergyStorage> ENERGY = CapabilityEnergy.ENERGY;
    
    @ZenCodeType.Field
    public static final Capability<IFluidHandler> FLUID = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    
    @ZenCodeType.Field
    public static final Capability<IFluidHandlerItem> FLUID_ITEM = CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    
    @ZenCodeType.Field
    public static final Capability<IItemHandler> ITEM = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    
}
