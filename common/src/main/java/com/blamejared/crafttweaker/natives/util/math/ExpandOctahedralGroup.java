package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.math.OctahedralGroup;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import org.joml.Matrix3f;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/OctahedralGroup")
@NativeTypeRegistration(value = OctahedralGroup.class, zenCodeName = "crafttweaker.api.util.math.OctahedralGroup")
@BracketEnum("minecraft:math/octahedralgroup")
public class ExpandOctahedralGroup {
    
    @ZenCodeType.Method
    public static OctahedralGroup compose(OctahedralGroup internal, OctahedralGroup group) {
        
        return internal.compose(group);
    }
    
    @ZenCodeType.Method
    public static OctahedralGroup inverse(OctahedralGroup internal) {
        
        return internal.inverse();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("transformation")
    public static Matrix3f transformation(OctahedralGroup internal) {
        
        return internal.transformation();
    }
    
    @ZenCodeType.Method
    public static Direction rotate(OctahedralGroup internal, Direction direction) {
        
        return internal.rotate(direction);
    }
    
    @ZenCodeType.Method
    public static boolean inverts(OctahedralGroup internal, Direction.Axis axis) {
        
        return internal.inverts(axis);
    }
    
    @ZenCodeType.Method
    public static FrontAndTop rotate(OctahedralGroup internal, FrontAndTop frontAndTop) {
        
        return internal.rotate(frontAndTop);
    }
    
}
