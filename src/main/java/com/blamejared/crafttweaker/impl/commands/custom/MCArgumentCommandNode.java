package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCArgumentCommandNode")
@Document("vanilla/api/commands/custom/MCArgumentCommandNode")
public class MCArgumentCommandNode extends MCCommandNode {
    
    private final ArgumentCommandNode<CommandSource, ?> internal;
    
    public MCArgumentCommandNode(ArgumentCommandNode<CommandSource, ?> internal) {
        
        super(internal);
        this.internal = internal;
    }
    
    @Override
    public ArgumentCommandNode<CommandSource, ?> getInternal() {
        
        return internal;
    }
    
    @ZenCodeType.Method
    public MCSuggestionProvider getCustomSuggestions() {
        
        return new MCSuggestionProvider(getInternal().getCustomSuggestions());
    }
    
    @ZenCodeType.Method
    @Override
    public MCRequiredArgumentBuilder createBuilder() {
        
        return new MCRequiredArgumentBuilder(getInternal().createBuilder());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCArgumentCommandNode && getInternal().equals(((MCArgumentCommandNode) o).getInternal());
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
