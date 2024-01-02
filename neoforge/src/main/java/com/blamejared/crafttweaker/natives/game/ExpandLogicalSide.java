package com.blamejared.crafttweaker.natives.game;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.fml.LogicalSide;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/game/LogicalSide")
@NativeTypeRegistration(value = LogicalSide.class, zenCodeName = "crafttweaker.neoforge.game.LogicalSide")
@BracketEnum("neoforge:game/logical_side")
public class ExpandLogicalSide {
    
    @ZenCodeType.Getter("isServer")
    public static boolean isServer(LogicalSide internal) {
        
        return internal.isServer();
    }
    
    @ZenCodeType.Getter("isClient")
    public static boolean isClient(LogicalSide internal) {
        
        return internal.isClient();
    }
    
}
