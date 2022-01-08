package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/RemovalReason")
@NativeTypeRegistration(value = Entity.RemovalReason.class, zenCodeName = "crafttweaker.api.entity.RemovalReason")
@BracketEnum("minecraft:entity/removalreason")
public class ExpandEntityRemovalReason {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldDestroy")
    public static boolean shouldDestroy(Entity.RemovalReason internal) {
        
        return internal.shouldDestroy();
    }
    
}
