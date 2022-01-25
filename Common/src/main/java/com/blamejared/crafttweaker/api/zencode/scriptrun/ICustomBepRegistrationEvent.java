package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.bracket.ValidatedEscapableBracketParser;
import org.openzen.zenscript.parser.BracketExpressionParser;

public interface ICustomBepRegistrationEvent {
    
    IScriptLoadingOptionsView currentOptions();
    
    void registerBep(final String name, final BracketExpressionParser parser);
    
    // TODO("Public API?")
    default void registerBep(final ValidatedEscapableBracketParser parser) {
        
        this.registerBep(parser.getName(), parser);
    }
    
    default void registerBepIfLoader(final String loader, final String name, final BracketExpressionParser parser) {
        
        if(this.currentOptions().appliesLoader(loader)) {
            this.registerBep(name, parser);
        }
    }
    
    // TODO("Public API?")
    default void registerBepIfLoader(final String loader, final ValidatedEscapableBracketParser parser) {
        
        this.registerBepIfLoader(loader, parser.getName(), parser);
    }
    
    default boolean appliesLoader(final String loader) {
        
        return this.currentOptions().appliesLoader(loader);
    }
    
}
