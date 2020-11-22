package com.blamejared.crafttweaker.impl.brackets.util;

import org.openzen.zencode.shared.*;
import org.openzen.zenscript.lexer.*;
import org.openzen.zenscript.parser.type.*;

import java.util.*;
import java.util.stream.*;

public class ParseUtil {
    private ParseUtil(){}
    
    public static IParsedType readParsedType(String name, CodePosition position) {
        final List<ParsedNamedType.ParsedNamePart> collect = Arrays.stream(name.split("[.]"))
                .map(s -> new ParsedNamedType.ParsedNamePart(s, null))
                .collect(Collectors.toList());
    
        return new ParsedNamedType(position, collect);
    }
    
    public static String readContent(ZSTokenParser tokens) throws ParseException {
        StringBuilder builder = new StringBuilder();
        while(tokens.optional(ZSTokenType.T_GREATER) == null) {
            builder.append(tokens.next().content);
            builder.append(tokens.getLastWhitespace());
        }
        return builder.toString();
    }
}
