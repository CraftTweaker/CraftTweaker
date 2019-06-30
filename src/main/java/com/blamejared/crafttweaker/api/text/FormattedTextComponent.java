package com.blamejared.crafttweaker.api.text;


import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;

public class FormattedTextComponent extends TextComponent {
    
    private final String text;
    private final Object[] objects;
    
    
    public FormattedTextComponent(String msg, Object... objects) {
        this.text = String.format(msg, objects).replaceAll("\t", "    ");
        this.objects = objects;
    }
    
    @Override
    public String getUnformattedComponentText() {
        return text;
    }
    
    @Override
    public ITextComponent shallowCopy() {
        return new FormattedTextComponent(text, objects);
    }
}
