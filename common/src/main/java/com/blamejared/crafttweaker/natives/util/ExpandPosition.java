package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Position;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/Position")
@NativeTypeRegistration(value = Position.class, zenCodeName = "crafttweaker.api.util.Position")
public class ExpandPosition {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("x")
    public static double x(Position internal) {
        
        return internal.x();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("y")
    public static double y(Position internal) {
        
        return internal.y();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("z")
    public static double z(Position internal) {
        
        return internal.z();
    }
    
}
