package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.regex.Pattern;

/**
 * Basic String utils exposed to ZenScript and for use in Java code.
 */
@ZenRegister
@ZenCodeType.Expansion("string")
public class StringUtils {
    
    /**
     * Checks if the given string matches the given regular expression
     *
     * @param string String to check
     * @param regex  Regex to check against
     *
     * @return True if the string matches. False otherwise
     */
    @ZenCodeType.Method
    public static boolean matchesRegex(String string, String regex) {
        
        return Pattern.compile(regex).matcher(string).matches();
    }
    
    /**
     * Quotes the given ResourceLocation in double quotes (") and escapes any control character.
     *
     * @param location ResourceLocation to quote and escape.
     *
     * @return a new String with the ResourceLocation quoted and escaped.
     */
    public static String quoteAndEscape(ResourceLocation location) {
        
        return quoteAndEscape(location.toString());
    }
    
    /**
     * Quotes the given String in double quotes (") and escapes any control character.
     *
     * @param str String to quote and escape.
     *
     * @return a new String with the String quoted and escaped.
     */
    @ZenCodeType.Method
    public static String quoteAndEscape(String str) {
        
        return wrap(str, "\"", true);
    }
    
    /**
     * Wraps the given String in another String and also optionally escapes control characters in the String.
     *
     * @param str    String to wrap
     * @param with   String to wrap with
     * @param escape Should control characters be escaped
     *
     * @return a new String that is wrapped with the given String and optioanlly escaped.
     */
    @ZenCodeType.Method
    public static String wrap(String str, String with, @ZenCodeType.OptionalBoolean boolean escape) {
        
        StringBuilder stringbuilder = new StringBuilder(with);
        
        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            
            if(escape) {
                if(c == '\\' || c == '"') {
                    stringbuilder.append('\\');
                }
            }
            
            stringbuilder.append(c);
        }
        
        return stringbuilder.append(with).toString();
    }
    
}
