package com.blamejared.crafttweaker.natives.world;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/Difficulty")
@NativeTypeRegistration(value = Difficulty.class, zenCodeName = "crafttweaker.api.world.Difficulty")
@BracketEnum("minecraft:world/difficulty")
public class ExpandDifficulty {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static int getId(Difficulty internal) {
        
        return internal.getId();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(Difficulty internal) {
        
        return internal.getDisplayName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("key")
    public static String getKey(Difficulty internal) {
        
        return internal.getKey();
    }
    
}
