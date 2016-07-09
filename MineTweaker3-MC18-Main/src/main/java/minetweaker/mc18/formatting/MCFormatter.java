/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18.formatting;

import com.mojang.realmsclient.gui.ChatFormatting;
import minetweaker.api.formatting.IFormattedText;
import minetweaker.api.formatting.IFormatter;

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
        return new FormattedMarkupString(ChatFormatting.BLACK, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkBlue(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.DARK_BLUE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkGreen(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.DARK_GREEN, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkAqua(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.DARK_AQUA, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkRed(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.DARK_RED, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkPurple(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.DARK_PURPLE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText gold(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.GOLD, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText gray(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.GRAY, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText darkGray(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.DARK_GRAY, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText blue(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.BLUE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText green(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.GREEN, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText aqua(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.AQUA, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText red(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.RED, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText lightPurple(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.LIGHT_PURPLE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText yellow(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.YELLOW, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText white(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.WHITE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText obfuscated(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.OBFUSCATED, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText bold(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.BOLD, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText strikethrough(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.STRIKETHROUGH, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText underline(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.UNDERLINE, (IMCFormattedString) text);
    }

    @Override
    public IFormattedText italic(IFormattedText text){
        return new FormattedMarkupString(ChatFormatting.ITALIC, (IMCFormattedString) text);
    }
}
