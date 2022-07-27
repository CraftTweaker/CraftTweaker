package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/capability/IFluidHandler")
@NativeTypeRegistration(value = IFluidHandler.class, zenCodeName = "crafttweaker.api.capability.IFluidHandler")
public class ExpandIFluidHandler {
    
    /**
     * Gets the number of tanks in this fluid handler.
     *
     * <p>A single handler can have many tanks holding different fluids</p>
     *
     * @return The number of tanks in this fluid handler.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("tanks")
    public static int getTanks(IFluidHandler internal) {
        
        return internal.getTanks();
    }
    
    /**
     * Gets the fluid in the given tank.
     *
     * @param tank The tank to get the fluid of.
     *
     * @return The fluid in the tank.
     *
     * @docParam tank 0
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static IFluidStack getFluidInTank(IFluidHandler internal, int tank) {
        
        return new MCFluidStack(internal.getFluidInTank(tank));
    }
    
    /**
     * Gets the capacity of the given tank.
     *
     * @param tank The tank to get the capacity of.
     *
     * @return The capacity of the given tank.
     *
     * @docParam tank 0
     */
    @ZenCodeType.Method
    public static int getTankCapacity(IFluidHandler internal, int tank) {
        
        return internal.getTankCapacity(tank);
    }
    
    /**
     * Checks if the given fluid is valid for the given tank.
     *
     * @param tank  The tank to check.
     * @param stack The fluid to check.
     *
     * @return true if the fluid is valid, false otherwise.
     *
     * @docParam tank 0
     * @docParam stack <fluid:minecraft:water>
     */
    @ZenCodeType.Method
    public static boolean isFluidValid(IFluidHandler internal, int tank, IFluidStack stack) {
        
        return internal.isFluidValid(tank, stack.getInternal());
    }
    
    /**
     * Fills the internal tanks with the given fluid.
     *
     * @param resource The fluid to fill.
     * @param action   Determines if the fill is simulated or executed.
     *
     * @return The amount of the fluid that will be used by the fill.
     *
     * @docParam resource <fluid:minecraft:water>
     * @docParam action <constant:forge:fluid_action:execute>
     */
    @ZenCodeType.Method
    public static int fill(IFluidHandler internal, IFluidStack resource, IFluidHandler.FluidAction action) {
        
        return internal.fill(resource.getInternal(), action);
    }
    
    /**
     * Drains fluid out of the internal tanks.
     *
     * @param resource The fluid and the maximum amount of the fluid to drain.
     * @param action   Determines if the drain is simulated or executed.
     *
     * @return A new FluidStack representing the fluid that was drained along with how much was drained.
     *
     * @docParam resource <fluid:minecraft:water> * 400
     * @docParam action <constant:forge:fluid_action:execute>
     */
    @ZenCodeType.Method
    public static IFluidStack drain(IFluidHandler internal, IFluidStack resource, IFluidHandler.FluidAction action) {
        
        return new MCFluidStack(internal.drain(resource.getInternal(), action));
    }
    
    /**
     * Drains fluid out of the internal tanks.
     *
     * @param maxDrain How much fluid should be drained.
     * @param action   Determines if the drain is simulated or executed.
     *
     * @return A new FluidStack representing the fluid that was drained along with how much was drained.
     *
     * @docParam maxDrain 400
     * @docParam action <constant:forge:fluid_action:execute>
     */
    @ZenCodeType.Method
    public static IFluidStack drain(IFluidHandler internal, int maxDrain, IFluidHandler.FluidAction action) {
        
        return new MCFluidStack(internal.drain(maxDrain, action));
    }
    
}
