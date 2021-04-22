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
        return new MCCommandContext(getInternal().copyFor(source.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCCommandContext getChild() {
        final CommandContext<CommandSource> child = getInternal().getChild();
        return child == null ? null : new MCCommandContext(child);
    }
    
    @ZenCodeType.Method
    public MCCommandContext getLastChild() {
        final CommandContext<CommandSource> child = getInternal().getLastChild();
        return child == null ? null : new MCCommandContext(child);
    }
    
    @ZenCodeType.Method
    public MCCommand getCommand() {
        return new MCCommand(getInternal().getCommand());
    }
    
    @ZenCodeType.Method
    public MCCommandSource getSource() {
        return new MCCommandSource(getInternal().getSource());
    }
    
    @ZenCodeType.Method
    public String getArgument(final String name) {
        return getInternal().getArgument(name, String.class);
    }
    
    @ZenCodeType.Method
    public MCRedirectModifier getRedirectModifier() {
        return new MCRedirectModifier(getInternal().getRedirectModifier());
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        return new MCStringRange(getInternal().getRange());
    }
    
    @ZenCodeType.Method
    public String getInput() {
        return getInternal().getInput();
    }
    
    @ZenCodeType.Method
    public MCCommandNode getRootNode() {
        return MCCommandNode.convert(getInternal().getRootNode());
    }
    
    @ZenCodeType.Method
    public List<MCParsedCommandNode> getNodes() {
        return getInternal().getNodes().stream().map(MCParsedCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public boolean hasNodes() {
        return getInternal().hasNodes();
    }
    
    @ZenCodeType.Method
    public boolean isForked() {
        return getInternal().isForked();
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommandContext && getInternal().equals(((MCCommandContext) o).getInternal());
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
