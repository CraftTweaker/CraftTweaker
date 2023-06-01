package com.blamejared.crafttweaker.api.bracket;


import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This class contains the "simple" Forge Bracket handlers from CraftTweaker.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.ForgeBracketHandlers")
@Document("forge/api/BracketHandlers")
public final class ForgeBracketHandlers {
    
    @ZenCodeType.Method
    @BracketResolver("toolaction")
    public static ToolAction getToolType(String tokens) {
        
        return ToolAction.get(tokens);
    }
    
    
    /**
     * Gets the fluid Stack based on registry name. Throws an error if it can't find the fluid.
     *
     * @param tokens The Fluid's resource location
     *
     * @return A stack of the liquid with amount == 1mb
     *
     * @docParam tokens "minecraft:water"
     */
    @ZenCodeType.Method
    @BracketResolver("fluid")
    public static IFluidStack getFluidStack(String tokens) {
        
        final ResourceLocation resourceLocation = ResourceLocation.tryParse(tokens);
        if(resourceLocation == null) {
            throw new IllegalArgumentException("Could not get fluid for <fluid:" + tokens + ">. Syntax is <fluid:modid:fluidname>");
        }
        
        if(!BuiltInRegistries.FLUID.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("Could not get fluid for <fluid:" + tokens + ">. Fluid does not appear to exist!");
        }
        
        return IFluidStack.of(new FluidStack(BuiltInRegistries.FLUID.get(resourceLocation), 1));
    }
    
}
