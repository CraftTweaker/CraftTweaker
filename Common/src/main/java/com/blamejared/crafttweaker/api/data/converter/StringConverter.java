package com.blamejared.crafttweaker.api.data.converter;

import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.visitor.DataToJsonStringVisitor;
import org.openzen.zencode.shared.LiteralSourceFile;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSToken;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openzen.zenscript.lexer.ZSTokenType.K_AS;
import static org.openzen.zenscript.lexer.ZSTokenType.T_ACLOSE;
import static org.openzen.zenscript.lexer.ZSTokenType.T_COLON;
import static org.openzen.zenscript.lexer.ZSTokenType.T_COMMA;
import static org.openzen.zenscript.lexer.ZSTokenType.T_IDENTIFIER;
import static org.openzen.zenscript.lexer.ZSTokenType.T_SQCLOSE;

public class StringConverter {
    
    public static IData convert(String expression) throws ParseException {
        
        try {
            ZSTokenParser parser = ZSTokenParser.create(new LiteralSourceFile("", expression), null);
            return parse(parser);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static IData parse(ZSTokenParser parser) throws ParseException {
        
        ZSToken next = parser.next();
        final IData base = switch(next.getType()) {
            case T_AOPEN -> parseMap(parser);
            case T_SQOPEN -> parseList(parser);
            case K_TRUE -> new BoolData(true);
            case K_FALSE -> new BoolData(false);
            case T_INT -> new LongData(Long.parseLong(next.getContent(), 10));
            case T_FLOAT -> new DoubleData(Double.parseDouble(next.getContent()));
            case T_STRING_DQ, T_STRING_DQ_WYSIWYG -> new StringData(next.getContent());
            default -> throw new ParseException(parser.getPosition()
                    .withLength(next.getContent()
                            .length()), "Could not resolve Data near " + next.getContent());
        };
        
        if(parser.optional(K_AS) != null) {
            final ZSToken token = parser.next();
            switch(token.getType()) {
                case K_BOOL:
                    return new BoolData(base.asBool());
                case K_BYTE:
                    return new ByteData(base.asByte());
                case K_SHORT:
                    return new ShortData(base.asShort());
                case K_INT:
                    return new IntData(base.asInt());
                case K_LONG:
                    return new LongData(base.asLong());
                case K_FLOAT:
                    return new FloatData(base.asFloat());
                case K_DOUBLE:
                    return new DoubleData(base.asDouble());
                case K_STRING:
                    if(base instanceof StringData) {
                        return base;
                    }
                    return new StringData(base.accept(DataToJsonStringVisitor.INSTANCE));
                case T_IDENTIFIER:
                    return base;
            }
        }
        return base;
    }
    
    private static IData parseList(ZSTokenParser parser) throws ParseException {
        
        final List<IData> result = new ArrayList<>();
        while(parser.optional(T_SQCLOSE) == null) {
            result.add(parse(parser));
            if(parser.optional(T_COMMA) == null) {
                parser.required(T_SQCLOSE, "] expected");
                break;
            }
        }
        return new ListData(result);
    }
    
    private static IData parseMap(ZSTokenParser parser) throws ParseException {
        
        final Map<String, IData> result = new HashMap<>();
        while(parser.optional(T_ACLOSE) == null) {
            ZSTokenType tokenType = parser.peek().getType();
            final String key;
            switch(tokenType) {
                case T_STRING_DQ, T_STRING_DQ_WYSIWYG -> {
                    String content = parser.required(tokenType, "String expected").getContent();
                    key = content.substring(1, content.length() - 1); // remove quotation mark wraps fast
                }
                default -> key = parser.required(T_IDENTIFIER, "Identifier expected").getContent();
            }
            parser.required(T_COLON, ": expected");
            result.put(key, parse(parser));
            
            if(parser.optional(T_COMMA) == null) {
                parser.required(T_ACLOSE, "} expected");
                break;
            }
        }
        return new MapData(result);
    }
    
}
