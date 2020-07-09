package com.blamejared.crafttweaker.impl.util.text;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.text.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.text.MCStyle")
@Document("vanilla/api/util/text/MCStyle")
@ZenWrapper(wrappedClass = "net.minecraft.util.text.Style", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCStyle {
    
    private final Style internal;
    
    public MCStyle(Style internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MCStyle() {
        this(Style.EMPTY);
    }
    
    public Style getInternal() {
        return this.internal;
    }
    
    /**
     * Whether or not this style is empty (inherits everything from the parent).
     */
    @ZenCodeType.Method
    public boolean isEmpty() {
        return internal.isEmpty();
    }
    
    @ZenCodeType.Method
    public boolean getBold() {
        return internal.getBold();
    }
    
    @ZenCodeType.Method
    public MCStyle setBold(Boolean boldIn) {
        Style set = internal.setBold(boldIn);
        return set == internal ? this : new MCStyle(set);
    }
    
    @ZenCodeType.Method
    public int hashCode() {
        return internal.hashCode();
    }
    
    @ZenCodeType.Method
    public boolean getObfuscated() {
        return internal.getObfuscated();
    }
    
    @ZenCodeType.Method
    public MCStyle setObfuscated(Boolean obfuscated) {
        Style set = internal.setObfuscated(obfuscated);
        return set == internal ? this : new MCStyle(set);
    }
    
    @ZenCodeType.Method
    public boolean equals(Object other) {
        return other instanceof MCStyle && internal.equals(other);
    }
    
    @ZenCodeType.Method
    public boolean getItalic() {
        return internal.getItalic();
    }
    
    @ZenCodeType.Method
    public MCStyle setItalic(Boolean italic) {
        Style set = internal.setItalic(italic);
        return set == internal ? this : new MCStyle(set);
    }
    
    @ZenCodeType.Method
    public String toString() {
        return internal.toString();
    }
    
    @ZenCodeType.Method
    public boolean getStrikethrough() {
        return internal.getStrikethrough();
    }
    
    @ZenCodeType.Method
    public MCStyle setStrikethrough(Boolean strikethrough) {
        Style set = internal.setStrikethrough(strikethrough);
        return set == internal ? this : new MCStyle(set);
    }
    
    @ZenCodeType.Method
    public boolean getUnderlined() {
        return internal.getUnderlined();
    }
    
    @ZenCodeType.Method
    public MCStyle setUnderlined(Boolean underlined) {
        Style set = internal.setUnderlined(underlined);
        return set == internal ? this : new MCStyle(set);
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
    
    
}
