package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/capability/IFluidHandlerAction")
@NativeTypeRegistration(value = IFluidHandler.FluidAction.class, zenCodeName = "crafttweaker.api.capability.IFluidHandlerAction")
public class ExpandFluidHandlerAction {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("execute")
    public static boolean execute(IFluidHandler.FluidAction internal) {
        
        return internal.execute();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("simulate")
    public static boolean simulate(IFluidHandler.FluidAction internal) {
        
        return internal.simulate();
    }
    
}
