package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.bracket.ValidatedEscapableBracketParser;
import org.openzen.zenscript.parser.BracketExpressionParser;

@FunctionalInterface
public interface IBepRegistrationHandler {
    
    void registerBracketHandler(final String bepName, final BracketExpressionParser parser);
    
    // TODO("Public API?")
    default void registerBracketHandler(final ValidatedEscapableBracketParser parser) {
        
        this.registerBracketHandler(parser.getName(), parser);
    }
    
}
