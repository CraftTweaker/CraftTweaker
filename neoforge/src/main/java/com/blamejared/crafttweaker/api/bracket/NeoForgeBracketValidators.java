package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.NeoForgeBracketValidators")
@Document("neoforge/api/NeoForgeBracketValidators")
public final class NeoForgeBracketValidators {

    private NeoForgeBracketValidators() {
    }

    @ZenCodeType.StaticExpansionMethod
    @BracketValidator("toolaction")
    public static boolean validateToolActionBracket(String tokens) {

        return tokens.chars().allMatch(c -> ('a' <= c && c <= 'z') || c == '_');
    }

}
