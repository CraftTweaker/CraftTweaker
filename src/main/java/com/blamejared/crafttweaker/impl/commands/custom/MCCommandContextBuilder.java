package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommandContextBuilder")
@Document("crafttweaker/api/commands/custom/MCCommandContextBuilder")
public class MCCommandContextBuilder {
    
    private final CommandContextBuilder<CommandSource> internal;
    
    public MCCommandContextBuilder(CommandContextBuilder<CommandSource> internal) {
        this.internal = internal;
    }
    
    public CommandContextBuilder<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withSource(final MCCommandSource source) {
        final CommandContextBuilder<CommandSource> builder = internal.withSource(source.getInternal());
        return this.internal == builder ? this : new MCCommandContextBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCCommandSource getSource() {
        return new MCCommandSource(internal.getSource());
    }
    
    @ZenCodeType.Method
    public MCCommandNode getRootNode() {
        return MCCommandNode.convert(internal.getRootNode());
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withArgument(final String name, final MCParsedArgument argument) {
        final CommandContextBuilder<CommandSource> builder = internal.withArgument(name, argument.getInternal());
        return this.internal == builder ? this : new MCCommandContextBuilder(builder);
    }
    
    @ZenCodeType.Method
    public Map<String, MCParsedArgument> getArguments() {
        return internal.getArguments().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new MCParsedArgument(e.getValue())));
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withCommand(final MCCommand command) {
        final CommandContextBuilder<CommandSource> builder = this.internal.withCommand(command.getInternal());
        return this.internal == builder ? this : new MCCommandContextBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withNode(final MCCommandNode node, final MCStringRange range) {
        final CommandContextBuilder<CommandSource> bui = internal.withNode(node.getInternal(), range.getInternal());
        return this.internal == bui ? this : new MCCommandContextBuilder(bui);
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder copy() {
        return new MCCommandContextBuilder(this.internal.copy());
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withChild(final MCCommandContextBuilder child) {
        final CommandContextBuilder<CommandSource> bui = internal.withChild(child.getInternal());
        return this.internal == bui ? this : new MCCommandContextBuilder(bui);
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder getChild() {
        return new MCCommandContextBuilder(internal.getChild());
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder getLastChild() {
        return new MCCommandContextBuilder(internal.getChild());
    }
    
    @ZenCodeType.Method
    public MCCommand getCommand() {
        return new MCCommand(internal.getCommand());
    }
    
    @ZenCodeType.Method
    public List<MCParsedCommandNode> getNodes() {
        return internal.getNodes().stream().map(MCParsedCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCCommandContext build(final String input) {
        return new MCCommandContext(internal.build(input));
    }
    
    @ZenCodeType.Method
    public MCCommandDispatcher getDispatcher() {
        return new MCCommandDispatcher(internal.getDispatcher());
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        return new MCStringRange(internal.getRange());
    }
    
    @ZenCodeType.Method
    public MCSuggestionContext findSuggestionContext(final int cursor) {
        return new MCSuggestionContext(internal.findSuggestionContext(cursor));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommandContextBuilder && internal.equals(((MCCommandContextBuilder) o).internal);
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
