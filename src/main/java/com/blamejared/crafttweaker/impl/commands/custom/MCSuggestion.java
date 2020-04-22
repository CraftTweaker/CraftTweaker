package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.mojang.brigadier.suggestion.Suggestion;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSuggestion")
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
        return new MCStringRange(internal.getRange());
    }
    
    @ZenCodeType.Method
    public String getText() {
        return internal.getText();
    }
    
    @ZenCodeType.Method
    public String getTooltip() {
        return internal.getTooltip().getString();
    }
    
    @ZenCodeType.Method
    public String apply(final String input) {
        return internal.apply(input);
    }
    
    @ZenCodeType.Method
    public int compareTo(final MCSuggestion o) {
        return this.internal.compareTo(o.internal);
    }
    
    @ZenCodeType.Method
    public int compareToIgnoreCase(final MCSuggestion b) {
        return this.internal.compareToIgnoreCase(b.internal);
    }
    
    @ZenCodeType.Method
    public MCSuggestion expand(final String command, final MCStringRange range) {
        return new MCSuggestion(internal.expand(command, range.getInternal()));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCSuggestion && internal.equals(((MCSuggestion) o).internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean opEquals(final Object o) {
        return equals(o);
    }
    
    @ZenCodeType.Method
    public int hashCode() {
        return internal.hashCode();
    }
    
    @ZenCodeType.Method
    @Override
    public String toString() {
        return internal.toString();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public String asString() {
        return toString();
    }
}
