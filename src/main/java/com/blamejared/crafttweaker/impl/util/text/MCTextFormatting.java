package com.blamejared.crafttweaker.impl.util.text;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.text.TextFormatting;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/text/TextFormatting")
@ZenCodeType.Name("crafttweaker.api.text.TextFormatting")
public class MCTextFormatting implements CommandStringDisplayable {
    
    private final TextFormatting internal;
    
    public MCTextFormatting(TextFormatting internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Getter("colorIndex")
    public int getColorIndex() {
        return getInternal().getColorIndex();
    }
    
    @ZenCodeType.Getter("fancyStyling")
    public boolean fancyStyling() {
        return getInternal().isFancyStyling();
    }
    
    @ZenCodeType.Getter("isColor")
    public boolean isColor() {
        return getInternal().isColor();
    }
    
    @ZenCodeType.Getter("friendlyName")
    public String getFriendlyName() {
        return getInternal().getFriendlyName();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        return getInternal().toString();
    }
    
    public TextFormatting getInternal() {
        return internal;
    }
    
    @Override
    public String getCommandString() {
        return "<formatting:" + getInternal().getFriendlyName() + ">";
    }
}
