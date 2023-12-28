package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.MobCategory;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/MobCategory")
@NativeTypeRegistration(value = MobCategory.class, zenCodeName = "crafttweaker.api.entity.MobCategory")
@BracketEnum("minecraft:mobcategory")
public class ExpandMobCategory {
    
    @ZenCodeType.Method
    public static String getName(MobCategory internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxInstancesPerChunk")
    public static int getMaxInstancesPerChunk(MobCategory internal) {
        
        return internal.getMaxInstancesPerChunk();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("friendly")
    public static boolean isFriendly(MobCategory internal) {
        
        return internal.isFriendly();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("persistent")
    public static boolean isPersistent(MobCategory internal) {
        
        return internal.isPersistent();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("despawnDistance")
    public static int getDespawnDistance(MobCategory internal) {
        
        return internal.getDespawnDistance();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("noDespawnDistance")
    public static int getNoDespawnDistance(MobCategory internal) {
        
        return internal.getNoDespawnDistance();
    }
    
}
