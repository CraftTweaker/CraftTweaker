package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/BaseSpawner")
@NativeTypeRegistration(value = BaseSpawner.class, zenCodeName = "crafttweaker.api.world.BaseSpawner")
public class ExpandBaseSpawner {
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("entityId")
    public static void setEntityId(BaseSpawner internal, EntityType param0) {
        
        internal.setEntityId(param0);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static Entity getOrCreateDisplayEntity(BaseSpawner internal, Level param0) {
        
        return internal.getOrCreateDisplayEntity(param0);
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
