package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/AxisCycle")
@NativeTypeRegistration(value = AxisCycle.class, zenCodeName = "crafttweaker.api.util.math.AxisCycle")
@BracketEnum("minecraft:direction/axiscycle")
public class ExpandAxisCycle {
    
    @ZenCodeType.Method
    public static int cycle(AxisCycle internal, int x, int y, int z, Direction.Axis axis) {
        
        return internal.cycle(x, y, z, axis);
    }
    
    @ZenCodeType.Method
    public static double cycle(AxisCycle internal, double x, double y, double z, Direction.Axis axis) {
        
        return internal.cycle(x, y, z, axis);
    }
    
    @ZenCodeType.Method
    public static Direction.Axis cycle(AxisCycle internal, Direction.Axis axis) {
        
        return internal.cycle(axis);
    }
    
    @ZenCodeType.Method
    public static AxisCycle inverse(AxisCycle internal) {
        
        return internal.inverse();
    }
    
}
