package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.impl.brackets.util.*;
import com.blamejared.crafttweaker.impl.tag.registry.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.lexer.*;
import org.openzen.zenscript.parser.*;
import org.openzen.zenscript.parser.expression.*;
import org.openzen.zenscript.parser.type.*;

import java.util.*;

public class TagManagerBracketHandler implements BracketExpressionParser {
    
    private final CrTTagRegistryData tagRegistry;
    
    public TagManagerBracketHandler(CrTTagRegistryData tagRegistry) {
        this.tagRegistry = tagRegistry;
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        final String tagFolder = readContent(tokens);
        if(!tagRegistry.hasTagManager(tagFolder)) {
            throw new IllegalArgumentException("Could not find tag manager with folder '" + tagFolder + "'. Make sure it exists!");
        }
        
        if(tagRegistry.isSynthetic(tagFolder)) {
            return createCallSynthetic(tagFolder, position);
        }
        return createCallImplementation(tagFolder, position);
    }
    
    private ParsedExpression createCallImplementation(String tagFolder, CodePosition position) {
        final ParsedExpressionVariable tags = new ParsedExpressionVariable(position, CrTTagRegistry.GLOBAL_NAME, null);
        final ParsedExpressionMember member = new ParsedExpressionMember(position, tags, "getByImplementation", null);
        
        
        final String implementationZCTypeFor = tagRegistry.getImplementationZCTypeFor(tagFolder);
        final List<IParsedType> typeArguments = Collections.singletonList(ParseUtil.readParsedType(implementationZCTypeFor, position));
        final ParsedCallArguments arguments = new ParsedCallArguments(typeArguments, Collections.emptyList());
        
        return new ParsedExpressionCall(position, member, arguments);
    }
    
    private ParsedExpression createCallSynthetic(String tagFolder, CodePosition position) {
        final ParsedExpressionVariable tags = new ParsedExpressionVariable(position, CrTTagRegistry.GLOBAL_NAME, null);
        final ParsedExpressionMember member = new ParsedExpressionMember(position, tags, "getForElementType", null);
        final String type = tagRegistry.getElementZCTypeFor(tagFolder);
        final IParsedType elementType = ParseUtil.readParsedType(type, position);
        final List<IParsedType> typeParam = Collections.singletonList(elementType);
        
        return new ParsedExpressionCall(position, member, new ParsedCallArguments(typeParam, Collections.emptyList()));
    }
    
    private String readContent(ZSTokenParser tokens) throws ParseException {
        StringBuilder builder = new StringBuilder();
        while(tokens.optional(ZSTokenType.T_GREATER) == null) {
            builder.append(tokens.next().content);
            builder.append(tokens.getLastWhitespace());
        }
        return builder.toString();
    }
}
