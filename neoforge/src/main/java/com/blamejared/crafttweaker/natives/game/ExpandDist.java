package com.blamejared.crafttweaker.natives.game;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.api.distmarker.Dist;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/game/Distribution")
@NativeTypeRegistration(value = Dist.class, zenCodeName = "crafttweaker.neoforge.game.Distribution")
@BracketEnum("neoforge:game/distribution")
public class ExpandDist {
    
    @ZenCodeType.Getter("isDedicatedServer")
    public static boolean isDedicatedServer(Dist internal) {
        
        return internal.isDedicatedServer();
    }
    
    @ZenCodeType.Getter("isClient")
    public static boolean isClient(Dist internal) {
        
        return internal.isClient();
    }
    
}
