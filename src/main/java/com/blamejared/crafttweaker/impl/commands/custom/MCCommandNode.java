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
        return new MCCommand(internal.getCommand());
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandNode> getChildren() {
        return internal.getChildren().stream().map(MCCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCCommandNode getChild(final String name) {
        return MCCommandNode.convert(internal.getChild(name));
    }
    
    @ZenCodeType.Method
    public MCCommandNode getRedirect() {
        return MCCommandNode.convert(internal.getRedirect());
    }
    
    @ZenCodeType.Method
    public MCRedirectModifier getRedirectModifier() {
        return new MCRedirectModifier(internal.getRedirectModifier());
    }
    
    @ZenCodeType.Method
    public boolean canUse(final MCCommandSource source) {
        return internal.canUse(source.getInternal());
    }
    
    @ZenCodeType.Method
    public void addChild(final MCCommandNode node) {
        internal.addChild(node.internal);
    }
    
    @ZenCodeType.Method
    public void findAmbiguities(final MCAmbiguityConsumer consumer) {
        internal.findAmbiguities((parent, child, sibling, inputs) -> consumer.ambiguous(MCCommandNode.convert(parent), MCCommandNode.convert(child), MCCommandNode.convert(sibling), inputs));
    }
    
    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        return mcCommandSource -> internal.getRequirement().test(mcCommandSource.getInternal());
    }
    
    @ZenCodeType.Method
    public String getName() {
        return internal.getName();
    }
    
    @ZenCodeType.Method
    public String getUsageText() {
        return internal.getUsageText();
    }
    
    @ZenCodeType.Method
    public void parse(String input, MCCommandContextBuilder contextBuilder) throws Exception {
        internal.parse(new StringReader(input), contextBuilder.getInternal());
    }
    
    @ZenCodeType.Method
    public MCSuggestions listSuggestions(MCCommandContext context, MCSuggestionsBuilder builder) throws Exception {
        return new MCSuggestions(internal.listSuggestions(context.getInternal(), builder.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder createBuilder() {
        return MCArgumentBuilder.convert(internal.createBuilder());
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandNode> getRelevantNodes(final String input) {
        return internal.getRelevantNodes(new StringReader(input)).stream().map(MCCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.COMPARE)
    public int compareTo(final MCCommandNode o) {
        return this.internal.compareTo(o.internal);
    }
    
    @ZenCodeType.Method
    public boolean isFork() {
        return internal.isFork();
    }
    
    @ZenCodeType.Method
    public Collection<String> getExamples() {
        return internal.getExamples();
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommandNode && internal.equals(((MCCommandNode) o).internal);
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
