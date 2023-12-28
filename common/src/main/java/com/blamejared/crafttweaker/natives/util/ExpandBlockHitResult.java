package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/BlockHitResult")
@NativeTypeRegistration(value = BlockHitResult.class, zenCodeName = "crafttweaker.api.util.BlockHitResult")
public class ExpandBlockHitResult {
    
    @ZenCodeType.Method
    public static BlockHitResult withDirection(BlockHitResult internal, Direction param0) {
        
        return internal.withDirection(param0);
    }
    
    @ZenCodeType.Method
    public static BlockHitResult withPosition(BlockHitResult internal, BlockPos param0) {
        
        return internal.withPosition(param0);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockPos")
    public static BlockPos getBlockPos(BlockHitResult internal) {
        
        return internal.getBlockPos();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("direction")
    public static Direction getDirection(BlockHitResult internal) {
        
        return internal.getDirection();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static HitResult.Type getType(BlockHitResult internal) {
        
        return internal.getType();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInside")
    public static boolean isInside(BlockHitResult internal) {
        
        return internal.isInside();
    }
    
}
