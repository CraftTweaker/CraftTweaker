package com.blamejared.crafttweaker.api.item.tooltip;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@FunctionalInterface
@Document("vanilla/api/item/tooltip/ITooltipFunction")
@ZenCodeType.Name("crafttweaker.api.item.tooltip.ITooltipFunction")
public interface ITooltipFunction {
    
    @ZenCodeType.Method
    void apply(IItemStack stack, List<Component> tooltip, TooltipFlag isAdvanced);
    
}
