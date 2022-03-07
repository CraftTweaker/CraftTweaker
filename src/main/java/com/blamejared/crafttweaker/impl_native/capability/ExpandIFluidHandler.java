package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/capability/IFluidHandler")
@NativeTypeRegistration(value = IFluidHandler.class, zenCodeName = "crafttweaker.api.capability.IFluidHandler")
public class ExpandIFluidHandler {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tanks")
    public static int getTanks(IFluidHandler internal) {
        
        return internal.getTanks();
    }
    
    @ZenCodeType.Method
    public static IFluidStack getFluidInTank(IFluidHandler internal, int tank) {
        
        return new MCFluidStack(internal.getFluidInTank(tank));
    }
    
    @ZenCodeType.Method
    public static int getTankCapacity(IFluidHandler internal, int tank) {
        
        return internal.getTankCapacity(tank);
    }
    
    @ZenCodeType.Method
    public static boolean isFluidValid(IFluidHandler internal, int tank, IFluidStack stack) {
        
        return internal.isFluidValid(tank, stack.getInternal());
    }
    
    @ZenCodeType.Method
    public static int fill(IFluidHandler internal, IFluidStack resource, IFluidHandler.FluidAction action) {
        
        return internal.fill(resource.getInternal(), action);
    }
    
    @ZenCodeType.Method
    public static IFluidStack drain(IFluidHandler internal, IFluidStack resource, IFluidHandler.FluidAction action) {
        
        return new MCFluidStack(internal.drain(resource.getInternal(), action));
    }
    
    @ZenCodeType.Method
    public static IFluidStack drain(IFluidHandler internal, int maxDrain, IFluidHandler.FluidAction action) {
        
        return new MCFluidStack(internal.drain(maxDrain, action));
    }
    
}
