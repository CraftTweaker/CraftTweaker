package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.ForgeBracketValidators")
@Document("forge/api/ForgeBracketValidators")
public final class ForgeBracketValidators {
    
    private static final Logger LOGGER = CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME + "-ZenCode");
    
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
            LOGGER.error("Could not get BEP <fluid:{}>. Syntax is <fluid:modid:fluidname>", tokens);
            return false;
        }
        
        if(Registry.FLUID.containsKey(resourceLocation)) {
            return true;
        }
        
        LOGGER.error("Could not get fluid for <fluid:{}>. Fluid does not appear to exist!", tokens);
        return false;
        
    }
    
}
