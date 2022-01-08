package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.math.OctahedralGroup;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/Rotation")
@NativeTypeRegistration(value = Rotation.class, zenCodeName = "crafttweaker.api.util.math.Rotation")
@BracketEnum("minecraft:direction/rotation")
public class ExpandRotation {
    
    @ZenCodeType.Method
    public static Rotation getRotated(Rotation internal, Rotation rotation) {
        
        return internal.getRotated(rotation);
    }
    
    @ZenCodeType.Method
    public static OctahedralGroup rotation(Rotation internal) {
        
        return internal.rotation();
    }
    
    @ZenCodeType.Method
    public static Direction rotate(Rotation internal, Direction direction) {
        
        return internal.rotate(direction);
    }
    
    @ZenCodeType.Method
    public static int rotate(Rotation internal, int rotation, int positionCount) {
        
        return internal.rotate(rotation, positionCount);
    }
    
}
