/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18.formatting;

import com.mojang.realmsclient.gui.ChatFormatting;
import minetweaker.api.formatting.IFormattedText;

/**
 * @author Stan
 */
public class FormattedMarkupString implements IMCFormattedString{
    private final ChatFormatting markup;
    private final IMCFormattedString contents;

    public FormattedMarkupString(ChatFormatting markup, IMCFormattedString contents){
        this.markup = markup;
        this.contents = contents;
    }

    @Override
    public String getTooltipString(){
        return markup + contents.getTooltipString(markup.toString());
    }

    @Override
    public String getTooltipString(String context){
        return markup + contents.getTooltipString(context + markup);
    }

    @Override
    public IFormattedText add(IFormattedText other){
        return cat(other);
    }

    @Override
    public IFormattedText cat(IFormattedText other){
        return new FormattedStringJoin(this, (IMCFormattedString) other);
    }
}
