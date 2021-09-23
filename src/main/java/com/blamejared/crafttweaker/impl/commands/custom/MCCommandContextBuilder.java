package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommandContextBuilder")
@Document("vanilla/api/commands/custom/MCCommandContextBuilder")
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
        
        final CommandContextBuilder<CommandSource> builder = getInternal().withSource(source.getInternal());
        return getInternal() == builder ? this : new MCCommandContextBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCCommandSource getSource() {
        
        return new MCCommandSource(getInternal().getSource());
    }
    
    @ZenCodeType.Method
    public MCCommandNode getRootNode() {
        
        return MCCommandNode.convert(getInternal().getRootNode());
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withArgument(final String name, final MCParsedArgument argument) {
        
        final CommandContextBuilder<CommandSource> builder = getInternal().withArgument(name, argument.getInternal());
        return getInternal() == builder ? this : new MCCommandContextBuilder(builder);
    }
    
    @ZenCodeType.Method
    public Map<String, MCParsedArgument> getArguments() {
        
        return getInternal().getArguments()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new MCParsedArgument(e.getValue())));
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withCommand(final MCCommand command) {
        
        final CommandContextBuilder<CommandSource> builder = getInternal().withCommand(command.getInternal());
        return getInternal() == builder ? this : new MCCommandContextBuilder(builder);
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withNode(final MCCommandNode node, final MCStringRange range) {
        
        final CommandContextBuilder<CommandSource> bui = getInternal().withNode(node.getInternal(), range.getInternal());
        return getInternal() == bui ? this : new MCCommandContextBuilder(bui);
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder copy() {
        
        return new MCCommandContextBuilder(getInternal().copy());
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder withChild(final MCCommandContextBuilder child) {
        
        final CommandContextBuilder<CommandSource> bui = getInternal().withChild(child.getInternal());
        return getInternal() == bui ? this : new MCCommandContextBuilder(bui);
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder getChild() {
        
        return new MCCommandContextBuilder(getInternal().getChild());
    }
    
    @ZenCodeType.Method
    public MCCommandContextBuilder getLastChild() {
        
        return new MCCommandContextBuilder(getInternal().getChild());
    }
    
    @ZenCodeType.Method
    public MCCommand getCommand() {
        
        return new MCCommand(getInternal().getCommand());
    }
    
    @ZenCodeType.Method
    public List<MCParsedCommandNode> getNodes() {
        
        return getInternal().getNodes().stream().map(MCParsedCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCCommandContext build(final String input) {
        
        return new MCCommandContext(getInternal().build(input));
    }
    
    @ZenCodeType.Method
    public MCCommandDispatcher getDispatcher() {
        
        return new MCCommandDispatcher(getInternal().getDispatcher());
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        
        return new MCStringRange(getInternal().getRange());
    }
    
    @ZenCodeType.Method
    public MCSuggestionContext findSuggestionContext(final int cursor) {
        
        return new MCSuggestionContext(getInternal().findSuggestionContext(cursor));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCCommandContextBuilder && getInternal().equals(((MCCommandContextBuilder) o).getInternal());
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
