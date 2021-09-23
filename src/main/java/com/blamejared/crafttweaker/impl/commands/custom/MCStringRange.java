package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.mojang.brigadier.context.StringRange;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCStringRange")
@Document("vanilla/api/commands/custom/MCStringRange")
@ZenWrapper(wrappedClass = "com.mojang.brigadier.context.StringRange", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCStringRange {
    
    private final StringRange internal;
    
    public MCStringRange(StringRange internal) {
        
        this.internal = internal;
    }
    
    public StringRange getInternal() {
        
        return this.internal;
    }
    
    @ZenCodeType.Method
    public String getFrom(MCImmutableStringReader reader) {
        
        return getInternal().get((reader).getInternal());
    }
    
    
    @ZenCodeType.Method
    public int getStart() {
        
        return getInternal().getStart();
    }
    
    
    @ZenCodeType.Method
    public boolean equals(Object o) {
        
        return o instanceof MCStringRange && getInternal().equals(((MCStringRange) o).getInternal());
    }
    
    
    @ZenCodeType.Method
    public int getLength() {
        
        return getInternal().getLength();
    }
    
    
    @ZenCodeType.Method
    public int getEnd() {
        
        return getInternal().getEnd();
    }
    
    
    @ZenCodeType.Method
    public static MCStringRange encompassing(MCStringRange a, MCStringRange b) {
        
        return new MCStringRange(StringRange.encompassing((a).getInternal(), (b).getInternal()));
    }
    
    
    @ZenCodeType.Method
    public boolean isEmpty() {
        
        return getInternal().isEmpty();
    }
    
    
    @ZenCodeType.Method
    public String toString() {
        
        return getInternal().toString();
    }
    
    
    @ZenCodeType.Method
    public static MCStringRange between(int start, int end) {
        
        return new MCStringRange(StringRange.between(start, end));
    }
    
    
    @ZenCodeType.Method
    public static MCStringRange at(int pos) {
        
        return new MCStringRange(StringRange.at(pos));
    }
    
    
    @ZenCodeType.Method
    public String getFrom(String string) {
        
        return getInternal().get((string));
    }
    
    
    @ZenCodeType.Method
    public int hashCode() {
        
        return getInternal().hashCode();
    }
    
    
}
