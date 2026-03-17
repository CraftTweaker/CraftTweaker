package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;

import java.util.Arrays;

public final class OnlyIfParameterGameVersion extends OnlyIfParameter {
    
    public OnlyIfParameterGameVersion() {
        
        super("gameversion");
    }
    
    @Override
    public OnlyIfParameterHit isHit(final String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return OnlyIfParameterHit.invalid();
        }
        
        final String gameVersion = SharedConstants.getCurrentVersion().getName();
        final boolean conditionMet = Arrays.asList(additionalArguments).contains(gameVersion);
        return OnlyIfParameterHit.basedOn(conditionMet, additionalArguments.length);
    }
    
}
