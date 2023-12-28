package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;

@ZenRegister
@Document("vanilla/api/util/math/AABB")
@NativeTypeRegistration(value = AABB.class, zenCodeName = "crafttweaker.api.util.math.AABB")
public class ExpandAABB {
    
    @ZenCodeType.Method
    public static AABB setMinX(AABB internal, double minX) {
        
        return internal.setMinX(minX);
    }
    
    @ZenCodeType.Method
    public static AABB setMinY(AABB internal, double minY) {
        
        return internal.setMinY(minY);
    }
    
    @ZenCodeType.Method
    public static AABB setMinZ(AABB internal, double minZ) {
        
        return internal.setMinZ(minZ);
    }
    
    @ZenCodeType.Method
    public static AABB setMaxX(AABB internal, double maxX) {
        
        return internal.setMaxX(maxX);
    }
    
    @ZenCodeType.Method
    public static AABB setMaxY(AABB internal, double maxY) {
        
        return internal.setMaxY(maxY);
    }
    
    @ZenCodeType.Method
    public static AABB setMaxZ(AABB internal, double maxZ) {
        
        return internal.setMaxZ(maxZ);
    }
    
    @ZenCodeType.Method
    public static double min(AABB internal, Direction.Axis direction) {
        
        return internal.min(direction);
    }
    
    @ZenCodeType.Method
    public static double max(AABB internal, Direction.Axis direction) {
        
        return internal.max(direction);
    }
    
    @ZenCodeType.Method
    public static AABB contract(AABB internal, double x, double y, double z) {
        
        return internal.contract(x, y, z);
    }
    
    @ZenCodeType.Method
    public static AABB expandTowards(AABB internal, Vec3 vec) {
        
        return internal.expandTowards(vec);
    }
    
    @ZenCodeType.Method
    public static AABB expandTowards(AABB internal, double x, double y, double z) {
        
        return internal.expandTowards(x, y, z);
    }
    
    @ZenCodeType.Method
    public static AABB inflate(AABB internal, double x, double y, double z) {
        
        return internal.inflate(x, y, z);
    }
    
    @ZenCodeType.Method
    public static AABB inflate(AABB internal, double scalar) {
        
        return internal.inflate(scalar);
    }
    
    @ZenCodeType.Method
    public static AABB intersect(AABB internal, AABB other) {
        
        return internal.intersect(other);
    }
    
    @ZenCodeType.Method
    public static AABB minmax(AABB internal, AABB other) {
        
        return internal.minmax(other);
    }
    
    @ZenCodeType.Method
    public static AABB move(AABB internal, double x, double y, double z) {
        
        return internal.move(x, y, z);
    }
    
    @ZenCodeType.Method
    public static AABB move(AABB internal, BlockPos pos) {
        
        return internal.move(pos);
    }
    
    @ZenCodeType.Method
    public static AABB move(AABB internal, Vec3 vec) {
        
        return internal.move(vec);
    }
    
    @ZenCodeType.Method
    public static boolean intersects(AABB internal, AABB other) {
        
        return internal.intersects(other);
    }
    
    @ZenCodeType.Method
    public static boolean intersects(AABB internal, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        
        return internal.intersects(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    @ZenCodeType.Method
    public static boolean intersects(AABB internal, Vec3 minVec, Vec3 maxVec) {
        
        return internal.intersects(minVec, maxVec);
    }
    
    @ZenCodeType.Method
    public static boolean contains(AABB internal, Vec3 other) {
        
        return internal.contains(other);
    }
    
    @ZenCodeType.Method
    public static boolean contains(AABB internal, double x, double y, double z) {
        
        return internal.contains(x, y, z);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("size")
    public static double getSize(AABB internal) {
        
        return internal.getSize();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("xSize")
    public static double getXsize(AABB internal) {
        
        return internal.getXsize();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ySize")
    public static double getYsize(AABB internal) {
        
        return internal.getYsize();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("zSize")
    public static double getZsize(AABB internal) {
        
        return internal.getZsize();
    }
    
    @ZenCodeType.Method
    public static AABB deflate(AABB internal, double x, double y, double z) {
        
        return internal.deflate(x, y, z);
    }
    
    @ZenCodeType.Method
    public static AABB deflate(AABB internal, double scalar) {
        
        return internal.deflate(scalar);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @Nullable
    public static Vec3 clip(AABB internal, Vec3 minVec, Vec3 maxVec) {
        
        return internal.clip(minVec, maxVec).orElse(null);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasNaN")
    public static boolean hasNaN(AABB internal) {
        
        return internal.hasNaN();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("center")
    public static Vec3 getCenter(AABB internal) {
        
        return internal.getCenter();
    }
    
}
