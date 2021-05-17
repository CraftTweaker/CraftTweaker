package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommandNode")
@Document("vanilla/api/commands/custom/MCCommandNode")
public class MCCommandNode {
    
    public static MCCommandNode convert(CommandNode<CommandSource> internal) {
        if(internal == null) {
            return null;
        }
        
        if(internal instanceof RootCommandNode) {
            return new MCRootCommandNode((RootCommandNode<CommandSource>) internal);
        }
        if(internal instanceof LiteralCommandNode) {
            return new MCLiteralCommandNode((LiteralCommandNode<CommandSource>) internal);
        }
        
        return new MCCommandNode(internal);
    }
    
    private final CommandNode<CommandSource> internal;
    
    public MCCommandNode(CommandNode<CommandSource> internal) {
        this.internal = internal;
    }
    
    public CommandNode<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCCommand getCommand() {
        return new MCCommand(getInternal().getCommand());
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandNode> getChildren() {
        return getInternal().getChildren().stream().map(MCCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCCommandNode getChild(final String name) {
        return MCCommandNode.convert(getInternal().getChild(name));
    }
    
    @ZenCodeType.Method
    public MCCommandNode getRedirect() {
        return MCCommandNode.convert(getInternal().getRedirect());
    }
    
    @ZenCodeType.Method
    public MCRedirectModifier getRedirectModifier() {
        return new MCRedirectModifier(getInternal().getRedirectModifier());
    }
    
    @ZenCodeType.Method
    public boolean canUse(final MCCommandSource source) {
        return getInternal().canUse(source.getInternal());
    }
    
    @ZenCodeType.Method
    public void addChild(final MCCommandNode node) {
        getInternal().addChild(node.getInternal());
    }
    
    @ZenCodeType.Method
    public void findAmbiguities(final MCAmbiguityConsumer consumer) {
        getInternal().findAmbiguities((parent, child, sibling, inputs) -> consumer.ambiguous(MCCommandNode.convert(parent), MCCommandNode.convert(child), MCCommandNode.convert(sibling), inputs));
    }
    
    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        return mcCommandSource -> getInternal().getRequirement().test(mcCommandSource.getInternal());
    }
    
    @ZenCodeType.Method
    public String getName() {
        return getInternal().getName();
    }
    
    @ZenCodeType.Method
    public String getUsageText() {
        return getInternal().getUsageText();
    }
    
    @ZenCodeType.Method
    public void parse(String input, MCCommandContextBuilder contextBuilder) throws Exception {
        getInternal().parse(new StringReader(input), contextBuilder.getInternal());
    }
    
    @ZenCodeType.Method
    public MCSuggestions listSuggestions(MCCommandContext context, MCSuggestionsBuilder builder) throws Exception {
        return new MCSuggestions(getInternal().listSuggestions(context.getInternal(), builder.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder createBuilder() {
        return MCArgumentBuilder.convert(getInternal().createBuilder());
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandNode> getRelevantNodes(final String input) {
        return getInternal().getRelevantNodes(new StringReader(input)).stream().map(MCCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.COMPARE)
    public int compareTo(final MCCommandNode o) {
        return getInternal().compareTo(o.getInternal());
    }
    
    @ZenCodeType.Method
    public boolean isFork() {
        return getInternal().isFork();
    }
    
    @ZenCodeType.Method
    public Collection<String> getExamples() {
        return getInternal().getExamples();
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommandNode && getInternal().equals(((MCCommandNode) o).getInternal());
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
