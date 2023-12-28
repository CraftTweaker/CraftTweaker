package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerLevelAccessor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/ServerLevelAccessor")
@NativeTypeRegistration(value = ServerLevelAccessor.class, zenCodeName = "crafttweaker.api.world.ServerLevelAccessor")
public class ExpandServerLevelAccessor {
    
    @ZenCodeType.Getter("level")
    public static ServerLevel getLevel(ServerLevelAccessor internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Method
    public static void addFreshEntityWithPassengers(ServerLevelAccessor internal, Entity entity) {
        
        internal.addFreshEntityWithPassengers(entity);
    }
    
}
