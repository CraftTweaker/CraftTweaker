package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.util.text.StringTextComponent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSuggestionsBuilder")
@Document("vanilla/api/commands/custom/MCSuggestionsBuilder")
public class MCSuggestionsBuilder {
    
    private final SuggestionsBuilder internal;
    
    public MCSuggestionsBuilder(SuggestionsBuilder internal) {
        this.internal = internal;
    }
    
    public SuggestionsBuilder getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public String getInput() {
        return getInternal().getInput();
    }
    
    @ZenCodeType.Method
    public int getStart() {
        return getInternal().getStart();
    }
    
    @ZenCodeType.Method
    public String getRemaining() {
        return getInternal().getRemaining();
    }
    
    @ZenCodeType.Method
    public MCSuggestions build() {
        return new MCSuggestions(getInternal().build());
    }
    
    @ZenCodeType.Method
    public MCSuggestionsBuilder suggest(final String text) {
        final SuggestionsBuilder builder = getInternal().suggest(text);
        return getInternal() == builder ? this : new MCSuggestionsBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCSuggestionsBuilder suggest(final String text, final String tooltip) {
        final SuggestionsBuilder builder = getInternal().suggest(text, new StringTextComponent(tooltip));
        return getInternal() == builder ? this : new MCSuggestionsBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCSuggestionsBuilder suggest(final int value) {
        final SuggestionsBuilder builder = getInternal().suggest(value);
        return getInternal() == builder ? this : new MCSuggestionsBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCSuggestionsBuilder suggest(final int value, final String tooltip) {
        final SuggestionsBuilder builder = getInternal().suggest(value, new StringTextComponent(tooltip));
        return getInternal() == builder ? this : new MCSuggestionsBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCSuggestionsBuilder add(final MCSuggestionsBuilder other) {
        final SuggestionsBuilder builder = getInternal().add(other.getInternal());
        return getInternal() == builder ? this : new MCSuggestionsBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCSuggestionsBuilder createOffset(final int start) {
        final SuggestionsBuilder builder = getInternal().createOffset(start);
        return getInternal() == builder ? this : new MCSuggestionsBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCSuggestionsBuilder restart() {
        final SuggestionsBuilder builder = getInternal().restart();
        return getInternal() == builder ? this : new MCSuggestionsBuilder(builder);
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCSuggestionsBuilder && getInternal().equals(((MCSuggestionsBuilder) o).getInternal());
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
