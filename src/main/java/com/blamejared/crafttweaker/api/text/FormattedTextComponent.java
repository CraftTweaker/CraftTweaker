package com.blamejared.crafttweaker.api.text;


import net.minecraft.util.text.StringTextComponent;

public class FormattedTextComponent extends StringTextComponent {
    
    public FormattedTextComponent(String msg, Object... objects) {
        
        super(String.format(msg, objects).replaceAll("\t", "    "));
    }
    
}
