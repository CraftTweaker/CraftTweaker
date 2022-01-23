package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;

public final class WrappingBracketParser extends IgnorePrefixCasingBracketParser {
    
    private final BiConsumer<String, BracketExpressionParser> registrationFunction;
    
    public WrappingBracketParser(final BiConsumer<String, BracketExpressionParser> registrationFunction) {
        
        this.registrationFunction = registrationFunction;
    }
    
    @Override
    public void register(final String name, final BracketExpressionParser parser) {
        
        this.registrationFunction.accept(name, parser);
    }
    
    @Override
    public Set<String> getSubParsersName() {
        
        return Collections.emptySet();
    }
    
}
