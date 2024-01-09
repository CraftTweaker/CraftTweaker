package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/IFluidHandlerAction")
@NativeTypeRegistration(value = IFluidHandler.FluidAction.class, zenCodeName = "crafttweaker.api.capability.IFluidHandlerAction")
@BracketEnum("neoforge:fluid_action")
public class ExpandFluidHandlerAction {
    
    /**
     * If the action should execute.
     *
     * @return true if the action should execute, false otherwise.
     */
    @ZenCodeType.Getter("execute")
    public static boolean execute(IFluidHandler.FluidAction internal) {
        
        return internal.execute();
    }
    
    /**
     * If the action should simulate.
     *
     * @return true if the action should simulate, false otherwise.
     */
    @ZenCodeType.Getter("simulate")
    public static boolean simulate(IFluidHandler.FluidAction internal) {
        
        return internal.simulate();
    }
    
}
