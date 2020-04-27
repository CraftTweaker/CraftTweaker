package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommandContext")
@Document("vanilla/api/commands/custom/MCCommandContext")
public class MCCommandContext {
    
    private final CommandContext<CommandSource> internal;
    
    public MCCommandContext(CommandContext<CommandSource> internal) {
        this.internal = internal;
    }
    
    public CommandContext<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCCommandContext copyFor(final MCCommandSource source) {
        if(getSource() == source) {
            return this;
        }
        return new MCCommandContext(internal.copyFor(source.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCCommandContext getChild() {
        final CommandContext<CommandSource> child = internal.getChild();
        return child == null ? null : new MCCommandContext(child);
    }
    
    @ZenCodeType.Method
    public MCCommandContext getLastChild() {
        final CommandContext<CommandSource> child = internal.getLastChild();
        return child == null ? null : new MCCommandContext(child);
    }
    
    @ZenCodeType.Method
    public MCCommand getCommand() {
        return new MCCommand(internal.getCommand());
    }
    
    @ZenCodeType.Method
    public MCCommandSource getSource() {
        return new MCCommandSource(internal.getSource());
    }
    
    @ZenCodeType.Method
    public String getArgument(final String name) {
        return internal.getArgument(name, String.class);
    }
    
    @ZenCodeType.Method
    public MCRedirectModifier getRedirectModifier() {
        return new MCRedirectModifier(internal.getRedirectModifier());
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        return new MCStringRange(internal.getRange());
    }
    
    @ZenCodeType.Method
    public String getInput() {
        return internal.getInput();
    }
    
    @ZenCodeType.Method
    public MCCommandNode getRootNode() {
        return MCCommandNode.convert(internal.getRootNode());
    }
    
    @ZenCodeType.Method
    public List<MCParsedCommandNode> getNodes() {
        return internal.getNodes().stream().map(MCParsedCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public boolean hasNodes() {
        return internal.hasNodes();
    }
    
    @ZenCodeType.Method
    public boolean isForked() {
        return internal.isForked();
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommandContext && internal.equals(((MCCommandContext) o).internal);
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
