package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.parameters;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.SnippingParameter;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping.SnippingParameterHit;
import net.minecraftforge.fml.LogicalSide;

public class SnippingParameterSide extends SnippingParameter {
    
    public SnippingParameterSide() {
        
        super("side");
    }
    
    @Override
    public SnippingParameterHit isHit(String[] additionalArguments) {
        
        if(additionalArguments.length == 0) {
            return SnippingParameterHit.invalid();
        }
        String side = additionalArguments[0];
        
        // If on the server, we snip
        boolean shouldSnip = false;
        if(side.equalsIgnoreCase(LogicalSide.CLIENT.name())) {
            shouldSnip = CraftTweakerAPI.isServer();
        } else if(side.equalsIgnoreCase(LogicalSide.SERVER.name())) {
            shouldSnip = !CraftTweakerAPI.isServer();
        }
        
        
        if(shouldSnip) {
            return SnippingParameterHit.snip(additionalArguments.length);
        } else {
            return SnippingParameterHit.noSnip(additionalArguments.length);
        }
    }
    
}
