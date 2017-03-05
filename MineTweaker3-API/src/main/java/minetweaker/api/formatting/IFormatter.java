package minetweaker.api.formatting;

import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Stan
 */
public interface IFormatter {
    
    IFormattedText string(String text);
    
    @ZenMethod
    IFormattedText black(IFormattedText text);
    
    @ZenMethod
    IFormattedText darkBlue(IFormattedText text);
    
    @ZenMethod
    IFormattedText darkGreen(IFormattedText text);
    
    @ZenMethod
    IFormattedText darkAqua(IFormattedText text);
    
    @ZenMethod
    IFormattedText darkRed(IFormattedText text);
    
    @ZenMethod
    IFormattedText darkPurple(IFormattedText text);
    
    @ZenMethod
    IFormattedText gold(IFormattedText text);
    
    @ZenMethod
    IFormattedText gray(IFormattedText text);
    
    @ZenMethod
    IFormattedText darkGray(IFormattedText text);
    
    @ZenMethod
    IFormattedText blue(IFormattedText text);
    
    @ZenMethod
    IFormattedText green(IFormattedText text);
    
    @ZenMethod
    IFormattedText aqua(IFormattedText text);
    
    @ZenMethod
    IFormattedText red(IFormattedText text);
    
    @ZenMethod
    IFormattedText lightPurple(IFormattedText text);
    
    @ZenMethod
    IFormattedText yellow(IFormattedText text);
    
    @ZenMethod
    IFormattedText white(IFormattedText text);
    
    @ZenMethod
    IFormattedText obfuscated(IFormattedText text);
    
    @ZenMethod
    IFormattedText bold(IFormattedText text);
    
    @ZenMethod
    IFormattedText strikethrough(IFormattedText text);
    
    @ZenMethod
    IFormattedText underline(IFormattedText text);
    
    @ZenMethod
    IFormattedText italic(IFormattedText text);
}
