package com.blamejared.crafttweaker.impl_native.dispenser;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.dispenser.IPosition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/dispenser/IPosition")
@NativeTypeRegistration(value = IPosition.class, zenCodeName = "crafttweaker.api.dispenser.IPosition")
public class ExpandIPosition {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("x")
    public static double getX(IPosition internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("y")
    public static double getY(IPosition internal) {
        
        return internal.getY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("z")
    public static double getZ(IPosition internal) {
        
        return internal.getZ();
    }
    
}
