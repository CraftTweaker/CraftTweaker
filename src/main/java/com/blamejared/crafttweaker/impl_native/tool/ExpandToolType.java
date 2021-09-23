package com.blamejared.crafttweaker.impl_native.tool;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This is the vanilla ToolType.
 */
@ZenRegister
@Document("vanilla/api/tool/ToolType")
@NativeTypeRegistration(value = ToolType.class, zenCodeName = "crafttweaker.api.tool.ToolType")
public class ExpandToolType {
    
    /**
     * Gets the name of this ToolType.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(ToolType _this) {
        
        return _this.getName();
    }
    
    @ZenCodeType.Caster
    public static String asString(ToolType _this) {
        
        return _this.toString();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(ToolType _this) {
        
        return "<tooltype:" + _this.getName() + ">";
    }
    
}
