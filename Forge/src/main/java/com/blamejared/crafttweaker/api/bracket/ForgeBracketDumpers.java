package com.blamejared.crafttweaker.api.bracket;


import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.natives.tool.ExpandToolAction;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.ForgeBracketDumpers")
@Document("forge/api/ForgeBracketDumpers")
public final class ForgeBracketDumpers {
    
    @ZenCodeType.StaticExpansionMethod
    @BracketDumper("toolaction")
    public static Collection<String> getToolActionDump() {
        
        return ToolAction.getActions().stream().map(ExpandToolAction::getCommandString).toList();
    }
    
    @ZenCodeType.StaticExpansionMethod
    @BracketDumper("fluid")
    public static Collection<String> getFluidStackDump() {
        
        return ForgeRegistries.FLUIDS.getValues()
                .stream()
                .map(fluid -> new MCFluidStack(new FluidStack(fluid, 1)).getCommandString())
                .collect(Collectors.toList());
    }
    
    
}
