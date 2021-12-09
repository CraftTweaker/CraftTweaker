package com.blamejared.crafttweaker.impl.util.text;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this new MCStyle()
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.text.MCStyle")
@Document("vanilla/api/util/text/MCStyle")
@ZenWrapper(wrappedClass = "net.minecraft.util.text.Style", displayStringFormat = "%s.toString()")
public class MCStyle {
    
    private final Style internal;
    
    public MCStyle(Style internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MCStyle() {
        
        this(Style.EMPTY);
    }
    
    @ZenCodeType.Method
    public boolean getBold() {
        
        return getInternal().getBold();
    }
    
    @ZenCodeType.Method
    public MCStyle setBold(Boolean boldIn) {
        
        return new MCStyle(getInternal().setBold(boldIn));
    }
    
    @ZenCodeType.Method
    public boolean getItalic() {
        
        return getInternal().getItalic();
    }
    
    @ZenCodeType.Method
    public MCStyle setItalic(Boolean italic) {
        
        return new MCStyle(getInternal().setItalic(italic));
    }
    
    @ZenCodeType.Method
    public boolean getStrikethrough() {
        
        return getInternal().getStrikethrough();
    }
    
    @ZenCodeType.Method
    public MCStyle setStrikethrough(Boolean strikethrough) {
        
        return new MCStyle(getInternal().setStrikethrough(strikethrough));
    }
    
    @ZenCodeType.Method
    public boolean getObfuscated() {
        
        return getInternal().getObfuscated();
    }
    
    @ZenCodeType.Method
    public MCStyle setObfuscated(Boolean obfuscated) {
        
        return new MCStyle(getInternal().setObfuscated(obfuscated));
    }
    
    /**
     * Whether or not this style is empty (inherits everything from the parent).
     */
    @ZenCodeType.Method
    public boolean isEmpty() {
        
        return getInternal().isEmpty();
    }
    
    @ZenCodeType.Method
    public String getInsertion() {
        
        return getInternal().getInsertion();
    }
    
    /**
     * Set a text to be inserted into Chat when the component is shift-clicked
     */
    @ZenCodeType.Method
    public MCStyle setInsertion(String insertion) {
        
        Style set = getInternal().setInsertion(insertion);
        return set == getInternal() ? this : new MCStyle(set);
    }
    
    @ZenCodeType.Method
    public ResourceLocation getFontId() {
        
        return getInternal().getFontId();
    }
    
    @ZenCodeType.Method
    public MCStyle setFontId(ResourceLocation location) {
        
        return new MCStyle(getInternal().setFontId(location));
    }
    
    /**
     * Sets the formatting of the style.
     *
     * This will only take the colour of the text formatting, so using `<formatting:obfuscated>` would not do anything as it does not have a colour.
     * If you want to take the actual characteristics of the MCTextFormatting, use {@link MCStyle#applyFormatting(MCTextFormatting)}
     *
     * @param formatting The formatting to set.
     *
     * @return A new MCStyle with the given Formatting.
     */
    @ZenCodeType.Method
    public MCStyle setFormatting(MCTextFormatting formatting) {
        
        return new MCStyle(getInternal().setFormatting(formatting.getInternal()));
    }
    
    /**
     * Applies the formatting characteristics (Bold, Italic, Obfucated, etc) of the given MCTExtFormatting as well as the colour of the formatting.
     *
     * @param formatting The formatting to set.
     *
     * @return A new MCStyle with the given Formatting.
     */
    @ZenCodeType.Method
    public MCStyle applyFormatting(MCTextFormatting formatting) {
        
        return new MCStyle(getInternal().applyFormatting(formatting.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCStyle setColor(int colour) {
        
        return new MCStyle(getInternal().setColor(Color.fromInt(colour)));
    }
    
    @ZenCodeType.Method
    public MCStyle setColor(MCTextFormatting formatting) {
        
        return new MCStyle(getInternal().setColor(Color.fromTextFormatting(formatting.getInternal())));
    }
    
    @ZenCodeType.Method
    public int getColor() {
        
        if(getInternal().getColor() == null) {
            return 0;
        }
        return getInternal().getColor().getColor();
    }
    
    @ZenCodeType.Method
    public boolean getUnderlined() {
        
        return getInternal().getUnderlined();
    }
    
    @ZenCodeType.Method
    public MCStyle setUnderlined(Boolean underlined) {
        
        return new MCStyle(getInternal().setUnderlined(underlined));
    }
    
    @ZenCodeType.Method
    public MCStyle setUnderlined(boolean underlined) {
        
        return new MCStyle(getInternal().setUnderlined(underlined));
    }
    
    @ZenCodeType.Method
    public boolean equals(Object other) {
        
        return other instanceof MCStyle && getInternal().equals(other);
    }
    
    @ZenCodeType.Method
    public MCStyle mergeStyle(MCStyle style) {
        
        return new MCStyle(getInternal().mergeStyle(style.getInternal()));
    }
    
    @ZenCodeType.Method
    public int hashCode() {
        
        return getInternal().hashCode();
    }
    
    @ZenCodeType.Method
    public String toString() {
        
        return getInternal().toString();
    }
    
    public Style getInternal() {
        
        return this.internal;
    }
    
}
