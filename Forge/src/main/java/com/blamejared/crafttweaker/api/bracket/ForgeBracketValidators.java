package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.BracketValidators")
@Document("forge/api/ForgeBracketValidators")
public final class ForgeBracketValidators {
    
    private ForgeBracketValidators() {
    
    }
    
    @ZenCodeType.StaticExpansionMethod
    @BracketValidator("toolaction")
    public static boolean validateToolActionBracket(String tokens) {
        
        return tokens.chars().allMatch(c -> ('a' <= c && c <= 'z') || c == '_');
    }
    
    @ZenCodeType.StaticExpansionMethod
    @BracketValidator("fluid")
    public static boolean validateFluidStack(String tokens) {
        
        final ResourceLocation resourceLocation = ResourceLocation.tryParse(tokens);
        if(resourceLocation == null) {
            CraftTweakerAPI.LOGGER.error("Could not get BEP <fluid:{}>. Syntax is <fluid:modid:fluidname>", tokens);
            return false;
        }
        
        if(Services.REGISTRY.fluids().containsKey(resourceLocation)) {
            return true;
        }
        
        CraftTweakerAPI.LOGGER.error("Could not get fluid for <fluid:{}>. Fluid does not appear to exist!", tokens);
        return false;
        
    }
    
}
