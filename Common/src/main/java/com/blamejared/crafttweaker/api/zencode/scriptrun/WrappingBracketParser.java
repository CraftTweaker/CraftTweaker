package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

import java.util.Collections;
import java.util.Set;

@Deprecated(forRemoval = true)
public final class WrappingBracketParser extends IgnorePrefixCasingBracketParser {
    
    private final IBepRegistrationHandler handler;
    
    public WrappingBracketParser(final IBepRegistrationHandler handler) {
        
        this.handler = handler;
    }
    
    @Override
    public void register(final String name, final BracketExpressionParser parser) {
        
        this.handler.registerBracketHandler(name, parser);
    }
    
    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) {
        
        throw new UnsupportedOperationException("Wrapping only");
    }
    
    @Override
    public Set<String> getSubParsersName() {
        
        return Collections.emptySet();
    }
    
}
