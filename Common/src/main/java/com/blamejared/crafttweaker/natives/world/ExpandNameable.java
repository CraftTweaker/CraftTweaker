package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/Nameable")
@NativeTypeRegistration(value = Nameable.class, zenCodeName = "crafttweaker.api.world.Nameable")
public class ExpandNameable {
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static Component getName(Nameable internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasCustomName")
    public static boolean hasCustomName(Nameable internal) {
        
        return internal.hasCustomName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(Nameable internal) {
        
        return internal.getDisplayName();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("customName")
    public static Component getCustomName(Nameable internal) {
        
        return internal.getCustomName();
    }
    
}
