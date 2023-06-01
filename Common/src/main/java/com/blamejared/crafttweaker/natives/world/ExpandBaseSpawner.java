package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;

@ZenRegister
@Document("vanilla/api/world/BaseSpawner")
@NativeTypeRegistration(value = BaseSpawner.class, zenCodeName = "crafttweaker.api.world.BaseSpawner")
public class ExpandBaseSpawner {
    
    @ZenCodeType.Method
    public static void setEntityId(BaseSpawner internal, EntityType type, @Nullable Level level, RandomSource random, BlockPos position) {
        
        internal.setEntityId(type, level, random, position);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static Entity getOrCreateDisplayEntity(BaseSpawner internal, Level level, RandomSource random, BlockPos position) {
        
        return internal.getOrCreateDisplayEntity(level, random, position);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("spin")
    public static double getSpin(BaseSpawner internal) {
        
        return internal.getSpin();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("oSpin")
    public static double getoSpin(BaseSpawner internal) {
        
        return internal.getoSpin();
    }
    
}
