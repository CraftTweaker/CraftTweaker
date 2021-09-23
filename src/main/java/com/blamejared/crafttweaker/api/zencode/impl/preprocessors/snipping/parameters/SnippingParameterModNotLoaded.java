package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.parameters;

import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.SnippingParameter;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.SnippingParameterHit;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;

public class SnippingParameterModNotLoaded extends SnippingParameter {
    
    public SnippingParameterModNotLoaded() {
        
        super("modnotloaded");
    }
    
    @Override
    public SnippingParameterHit isHit(String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return SnippingParameterHit.invalid();
        }
        
        final boolean shouldSnip = Arrays.stream(additionalArguments).noneMatch(ModList.get()::isLoaded);
        if(shouldSnip) {
            return SnippingParameterHit.snip(additionalArguments.length);
        } else {
            return SnippingParameterHit.noSnip(additionalArguments.length);
        }
    }
    
}
