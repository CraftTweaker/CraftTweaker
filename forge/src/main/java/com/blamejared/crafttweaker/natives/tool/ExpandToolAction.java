package com.blamejared.crafttweaker.natives.tool;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.common.ToolAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/tool/ToolAction")
@NativeTypeRegistration(value = ToolAction.class, zenCodeName = "crafttweaker.api.tool.ToolAction")
public class ExpandToolAction {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String name(ToolAction internal) {
        
        return internal.name();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(ToolAction internal) {
        
        return "<toolaction:" + name(internal) + ">";
    }
    
}
