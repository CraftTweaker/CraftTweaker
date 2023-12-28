package com.blamejared.crafttweaker.natives.world;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.level.GameType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/GameType")
@NativeTypeRegistration(value = GameType.class, zenCodeName = "crafttweaker.api.world.GameType")
@BracketEnum("minecraft:world/gametype")
public class ExpandGameType {
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static int getId(GameType internal) {
        
        return internal.getId();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(GameType internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("longDisplayName")
    public static Component getLongDisplayName(GameType internal) {
        
        return internal.getLongDisplayName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shortDisplayName")
    public static Component getShortDisplayName(GameType internal) {
        
        return internal.getShortDisplayName();
    }
    
    @ZenCodeType.Method
    public static void updatePlayerAbilities(GameType internal, Abilities abilities) {
        
        internal.updatePlayerAbilities(abilities);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isBlockPlacingRestricted")
    public static boolean isBlockPlacingRestricted(GameType internal) {
        
        return internal.isBlockPlacingRestricted();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCreative")
    public static boolean isCreative(GameType internal) {
        
        return internal.isCreative();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSurvival")
    public static boolean isSurvival(GameType internal) {
        
        return internal.isSurvival();
    }
    
}
