package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.parameters;

import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.SnippingParameter;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.SnippingParameterHit;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;

public class SnippingParameterModLoaded extends SnippingParameter {
    
    public SnippingParameterModLoaded() {
        
        super("modloaded");
    }
    
    @Override
    public SnippingParameterHit isHit(String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return SnippingParameterHit.invalid();
        }
        
        final boolean shouldSnip = Arrays.stream(additionalArguments).allMatch(ModList.get()::isLoaded);
        if(shouldSnip) {
            return SnippingParameterHit.snip(additionalArguments.length);
        } else {
            return SnippingParameterHit.noSnip(additionalArguments.length);
        }
    }
    
}
