/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc19.formatting;

import minetweaker.api.formatting.IFormattedText;
import minetweaker.api.formatting.IFormatter;
import net.minecraft.util.text.TextFormatting;

/**
 * @author Stan
 */
public class MCFormatter implements IFormatter{

    @Override
    public IFormattedText string(String text){
        return new FormattedString(text);
    }

    @Override
    public IFormattedText black(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.BLACK, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkBlue(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.DARK_BLUE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkGreen(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.DARK_GREEN, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkAqua(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.DARK_AQUA, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkRed(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.DARK_RED, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkPurple(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.DARK_PURPLE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText gold(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.GOLD, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText gray(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.GRAY, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkGray(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.DARK_GRAY, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText blue(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.BLUE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText green(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.GREEN, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText aqua(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.AQUA, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText red(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.RED, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText lightPurple(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.LIGHT_PURPLE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText yellow(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.YELLOW, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText white(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.WHITE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText obfuscated(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.OBFUSCATED, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText bold(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.BOLD, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText strikethrough(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.STRIKETHROUGH, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText underline(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.UNDERLINE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText italic(IFormattedText text){
        return new FormattedMarkupString(TextFormatting.ITALIC, (IMCFormattedString) text);
    }
}
