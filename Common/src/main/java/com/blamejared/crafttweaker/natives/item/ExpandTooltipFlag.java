package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.TooltipFlag;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/TooltipFlag")
@NativeTypeRegistration(value = TooltipFlag.class, zenCodeName = "crafttweaker.api.item.TooltipFlag")
public class ExpandTooltipFlag {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("advanced")
    public static boolean isAdvanced(TooltipFlag internal) {
        
        return internal.isAdvanced();
    }
    
}
