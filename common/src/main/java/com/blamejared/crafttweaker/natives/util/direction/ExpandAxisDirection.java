package com.blamejared.crafttweaker.natives.util.direction;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Direction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/direction/AxisDirection")
@NativeTypeRegistration(value = Direction.AxisDirection.class, zenCodeName = "crafttweaker.api.util.AxisDirection")
@BracketEnum("minecraft:direction/axisdirection")
public class ExpandAxisDirection {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("step")
    public static int getStep(Direction.AxisDirection internal) {
        
        return internal.getStep();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(Direction.AxisDirection internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("opposite")
    public static Direction.AxisDirection opposite(Direction.AxisDirection internal) {
        
        return internal.opposite();
    }
    
}
