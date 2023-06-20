package com.blamejared.crafttweaker.natives.game;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraftforge.api.distmarker.Dist;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/game/Distribution")
@NativeTypeRegistration(value = Dist.class, zenCodeName = "crafttweaker.forge.game.Distribution")
@BracketEnum("forge:game/distribution")
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
