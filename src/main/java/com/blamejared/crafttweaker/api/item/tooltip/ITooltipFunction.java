package com.blamejared.crafttweaker.api.item.tooltip;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@FunctionalInterface
@ZenCodeType.Name("crafttweaker.api.item.tooltip.ITooltipFunction")
@ZenRegister
public interface ITooltipFunction {
    
    @ZenCodeType.Method
    void apply(IItemStack stack, List<MCTextComponent> tooltip, boolean isAdvanced);
}
