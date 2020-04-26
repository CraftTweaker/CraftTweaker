package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.context.SuggestionContext;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSuggestionContext")
@Document("crafttweaker/api/commands/custom/MCSuggestionContext")
public class MCSuggestionContext {
    
    private final SuggestionContext<CommandSource> internal;
    
    public MCSuggestionContext(SuggestionContext<CommandSource> internal) {
        this.internal = internal;
    }
    
    public SuggestionContext<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCCommandNode getParent() {
        return MCCommandNode.convert(internal.parent);
    }
    
    @ZenCodeType.Method
    public int getStartPos() {
        return internal.startPos;
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCSuggestionContext && internal.equals(((MCSuggestionContext) o).internal);
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
