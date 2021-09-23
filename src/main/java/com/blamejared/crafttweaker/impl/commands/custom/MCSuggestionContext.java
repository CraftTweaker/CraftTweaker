package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.context.SuggestionContext;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSuggestionContext")
@Document("vanilla/api/commands/custom/MCSuggestionContext")
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
        
        return MCCommandNode.convert(getInternal().parent);
    }
    
    @ZenCodeType.Method
    public int getStartPos() {
        
        return getInternal().startPos;
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCSuggestionContext && getInternal().equals(((MCSuggestionContext) o).getInternal());
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
