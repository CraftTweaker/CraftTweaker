package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

import java.util.Locale;
import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
final class IgnorePrefixCasingBracketParser implements BracketExpressionParser {
    
    private final Map<String, BracketExpressionParser> parsers;
    
    IgnorePrefixCasingBracketParser(final Map<String, BracketExpressionParser> parsers) {
        
        this.parsers = Map.copyOf(parsers);
    }
    
    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        
        final StringBuilder name = new StringBuilder();
        while(tokens.peek().getType() != ZSTokenType.T_COLON) {
            name.append(tokens.next().getContent());
        }
        if(name.isEmpty()) {
            throw new ParseException(position.withLength(tokens.peek().getContent().length()), "identifier expected");
        }
        tokens.required(ZSTokenType.T_COLON, ": expected");
        
        final BracketExpressionParser parser = this.find(name.toString());
        if(parser == null) {
            throw new ParseException(position, "Invalid bracket expression: no prefix " + name);
        }
        
        return parser.parse(position, tokens);
    }
    
    private BracketExpressionParser find(final String name) {
        
        return this.parsers.get(name.toLowerCase(Locale.ENGLISH));
    }
    
}
