package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.suggestion.Suggestion;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSuggestion")
@Document("vanilla/api/commands/custom/MCSuggestion")
public class MCSuggestion {
    
    private final Suggestion internal;
    
    public MCSuggestion(Suggestion internal) {
        
        this.internal = internal;
    }
    
    public Suggestion getInternal() {
        
        return internal;
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        
        return new MCStringRange(getInternal().getRange());
    }
    
    @ZenCodeType.Method
    public String getText() {
        
        return getInternal().getText();
    }
    
    @ZenCodeType.Method
    public String getTooltip() {
        
        return getInternal().getTooltip().getString();
    }
    
    @ZenCodeType.Method
    public String apply(final String input) {
        
        return getInternal().apply(input);
    }
    
    @ZenCodeType.Method
    public int compareTo(final MCSuggestion o) {
        
        return getInternal().compareTo(o.getInternal());
    }
    
    @ZenCodeType.Method
    public int compareToIgnoreCase(final MCSuggestion b) {
        
        return getInternal().compareToIgnoreCase(b.getInternal());
    }
    
    @ZenCodeType.Method
    public MCSuggestion expandWith(final String command, final MCStringRange range) {
        
        return new MCSuggestion(getInternal().expand(command, range.getInternal()));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCSuggestion && getInternal().equals(((MCSuggestion) o).getInternal());
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean opEquals(final Object o) {
        
        return equals(o);
    }
    
    @ZenCodeType.Method
    public int hashCode() {
        
        return getInternal().hashCode();
    }
    
    @ZenCodeType.Method
    @Override
    public String toString() {
        
        return getInternal().toString();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public String asString() {
        
        return toString();
    }
    
}
