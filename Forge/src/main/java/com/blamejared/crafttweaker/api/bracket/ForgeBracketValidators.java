package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.ForgeBracketValidators")
@Document("forge/api/ForgeBracketValidators")
public final class ForgeBracketValidators {
    
    private ForgeBracketValidators() {
    
    }
    
    @ZenCodeType.StaticExpansionMethod
    @BracketValidator("toolaction")
    public static boolean validateToolActionBracket(String tokens) {
        
        return tokens.chars().allMatch(c -> ('a' <= c && c <= 'z') || c == '_');
    }

}
