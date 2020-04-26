package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.context.ParsedCommandNode;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCParsedCommandNode")
@Document("crafttweaker/api/commands/custom/MCParsedCommandNode")
public class MCParsedCommandNode {
    
    private final ParsedCommandNode<CommandSource> internal;
    
    public MCParsedCommandNode(ParsedCommandNode<CommandSource> internal) {
        this.internal = internal;
    }
    
    public ParsedCommandNode<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCCommandNode getNode() {
        return MCCommandNode.convert(internal.getNode());
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        return new MCStringRange(internal.getRange());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCParsedCommandNode && internal.equals(((MCParsedCommandNode) o).internal);
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
