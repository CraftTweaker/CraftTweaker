package crafttweaker.mc1120.data;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.data.*;
import crafttweaker.mc1120.CraftTweaker;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.parser.Token;

import java.io.IOException;
import java.util.*;

import static stanhebben.zenscript.ZenTokener.*;

public class StringIDataParser {
    
    public static IData parse(String expression) {
        try {
            final ZenTokener zenTokener = new ZenTokener(expression, null, "", false);
            return parse(zenTokener);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static IData parse(ZenTokener tokener) {
        final Token next = tokener.next();
        final IData base;
        switch(next.getType()) {
            case T_AOPEN:
                base = parseMap(tokener);
                break;
            case T_SQBROPEN:
                base = parseList(tokener);
                break;
            case T_TRUE:
                base = new DataBool(true);
                break;
            case T_FALSE:
                base = new DataBool(false);
                break;
            case T_INTVALUE:
                base = new DataLong(Long.parseLong(next.getValue(), 10));
                break;
            case T_FLOATVALUE:
                base = new DataDouble(Double.parseDouble(next.getValue()));
                break;
            case T_STRINGVALUE:
                base = new DataString(next.getValue());
                break;
            default:
                CraftTweakerAPI.logError("Could not completely resolve Data near " + next.toString());
                return new DataMap(Collections.emptyMap(), true);
        }
        
        if(tokener.optional(T_AS) != null) {
            final Token token = tokener.next();
            switch(token.getType()) {
                case T_BOOL:
                    return new DataBool(base.asBool());
                case T_BYTE:
                    return new DataByte(base.asByte());
                case T_SHORT:
                    return new DataShort(base.asShort());
                case T_INT:
                    return new DataInt(base.asInt());
                case T_LONG:
                    return new DataLong(base.asLong());
                case T_FLOAT:
                    return new DataFloat(base.asFloat());
                case T_STRING:
                    return new DataString(base.asString());
                case T_ID:
                    return base;
            }
        }
        return base;
    }
    
    private static IData parseList(ZenTokener tokener) {
        final List<IData> result = new ArrayList<>();
        while(tokener.optional(T_SQBRCLOSE) == null) {
            result.add(parse(tokener));
            if(tokener.optional(T_COMMA) == null) {
                tokener.required(T_SQBRCLOSE, "] expected");
                break;
            }
        }
        return new DataList(result, false);
    }
    
    private static IData parseMap(ZenTokener tokener) {
        final Map<String, IData> result = new HashMap<>();
        while(tokener.optional(T_ACLOSE) == null) {
            final String key = tokener.required(T_ID, "Identifier expected").getValue();
            tokener.required(T_COLON, ": expected");
            result.put(key, parse(tokener));
            
            if(tokener.optional(T_COMMA) == null) {
                tokener.required(T_ACLOSE, "} expected");
                break;
            }
        }
        return new DataMap(result, false);
    }
}
