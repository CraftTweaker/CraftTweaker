package com.blamejared.crafttweaker.api.zencode.util;

import org.openzen.zenscript.lexer.ZSTokenType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class ZenKeywordUtil {
    
    private static final Set<String> KEYWORDS = Arrays.stream(ZSTokenType.values())
            .filter(zsTokenType -> zsTokenType.isKeyword)
            .map(zsTokenType -> zsTokenType.flyweight.content)
            .collect(Collectors.toSet());
    
    private ZenKeywordUtil() {}
    
    /**
     * Checks if the given String is a reserved ZenCode keyword.
     *
     * @param string String to check.
     *
     * @return True if it is a keyword, false otherwise.
     */
    public static boolean isKeyword(String string) {
        
        return KEYWORDS.contains(string);
    }
    
    /**
     * Ensures that if the given String is a keyword that it is enclosed in quotation marks.
     *
     * @param string The string to sanitize
     *
     * @return The provided string unless it is a keyword, in which case it will be in quotation marks.
     */
    public static String sanitize(String string) {
        
        if(isKeyword(string)) {
            string = "\"" + string + "\"";
        }
        return string;
    }
    
}
