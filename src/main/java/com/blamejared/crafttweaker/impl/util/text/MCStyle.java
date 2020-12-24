package com.blamejared.crafttweaker.impl.util.text;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import org.openzen.zencode.java.ZenCodeType;

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
        return internal.getBold();
    }
    
    @ZenCodeType.Method
    public MCStyle setBold(Boolean boldIn) {
        return new MCStyle(internal.setBold(boldIn));
    }
    
    @ZenCodeType.Method
    public boolean getItalic() {
        return internal.getItalic();
    }
    
    @ZenCodeType.Method
    public MCStyle setItalic(Boolean italic) {
        return new MCStyle(internal.setItalic(italic));
    }
    
    @ZenCodeType.Method
    public boolean getStrikethrough() {
        return internal.getStrikethrough();
    }
    
    @ZenCodeType.Method
    public MCStyle setStrikethrough(Boolean strikethrough) {
        return new MCStyle(internal.setStrikethrough(strikethrough));
    }
    
    @ZenCodeType.Method
    public boolean getObfuscated() {
        return internal.getObfuscated();
    }
    
    @ZenCodeType.Method
    public MCStyle setObfuscated(Boolean obfuscated) {
        return new MCStyle(internal.setObfuscated(obfuscated));
    }
    
    /**
     * Whether or not this style is empty (inherits everything from the parent).
     */
    @ZenCodeType.Method
    public boolean isEmpty() {
        return internal.isEmpty();
    }
    
    @ZenCodeType.Method
    public String getInsertion() {
        return internal.getInsertion();
    }
    
    /**
     * Set a text to be inserted into Chat when the component is shift-clicked
     */
    @ZenCodeType.Method
    public MCStyle setInsertion(String insertion) {
        Style set = internal.setInsertion(insertion);
        return set == internal ? this : new MCStyle(set);
    }
    
    @ZenCodeType.Method
    public ResourceLocation getFontId() {
        return getInternal().getFontId();
    }
    
    @ZenCodeType.Method
    public MCStyle setFontId(ResourceLocation location) {
        return new MCStyle(getInternal().setFontId(location));
    }
    
    @ZenCodeType.Method
    public MCStyle setFormatting(MCTextFormatting formatting) {
        return new MCStyle(internal.setFormatting(formatting.getInternal()));
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
    public boolean getUnderlined() {
        return internal.getUnderlined();
    }
    
    @ZenCodeType.Method
    public MCStyle setUnderlined(Boolean underlined) {
        return new MCStyle(internal.setUnderlined(underlined));
    }
    
    @ZenCodeType.Method
    public MCStyle setUnderlined(boolean underlined) {
        return new MCStyle(getInternal().setUnderlined(underlined));
    }
    
    @ZenCodeType.Method
    public boolean equals(Object other) {
        return other instanceof MCStyle && internal.equals(other);
    }
    
    @ZenCodeType.Method
    public MCStyle mergeStyle(MCStyle style) {
        return new MCStyle(getInternal().mergeStyle(style.getInternal()));
    }
    
    @ZenCodeType.Method
    public int hashCode() {
        return internal.hashCode();
    }
    
    @ZenCodeType.Method
    public String toString() {
        return internal.toString();
    }
    
    public Style getInternal() {
        return this.internal;
    }
    
}
