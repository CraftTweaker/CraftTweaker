package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommandDispatcher")
@Document("vanilla/api/commands/custom/MCCommandDispatcher")
public class MCCommandDispatcher {
    
    private final CommandDispatcher<CommandSource> internal;
    
    public MCCommandDispatcher(CommandDispatcher<CommandSource> internal) {
        this.internal = internal;
    }
    
    public CommandDispatcher<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCLiteralCommandNode register(final MCLiteralArgumentBuilder command) {
        return new MCLiteralCommandNode(internal.register(command.getInternal()));
    }
    
    @ZenCodeType.Method
    public void setConsumer(final MCResultConsumer consumer) {
        internal.setConsumer((context, success, result) -> consumer.onCommandComplete(new MCCommandContext(context), success, result));
    }
    
    @ZenCodeType.Method
    public int execute(final String input, final MCCommandSource source) throws Exception {
        return internal.execute(input, source.getInternal());
    }
    
    @ZenCodeType.Method
    public int execute(final StringReader input, final MCCommandSource source) throws Exception {
        return internal.execute(input, source.getInternal());
    }
    
    @ZenCodeType.Method
    public int execute(final MCParseResults parse) throws Exception {
        return internal.execute(parse.getInternal());
    }
    
    @ZenCodeType.Method
    public MCParseResults parse(final String command, final MCCommandSource source) {
        return new MCParseResults(internal.parse(command, source.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCParseResults parse(final StringReader command, final MCCommandSource source) {
        return new MCParseResults(internal.parse(command, source.getInternal()));
    }
    
    @ZenCodeType.Method
    public String[] getAllUsage(final MCCommandNode node, final MCCommandSource source, final boolean restricted) {
        return internal.getAllUsage(node.getInternal(), source.getInternal(), restricted);
    }
    
    @ZenCodeType.Method
    public Map<MCCommandNode, String> getSmartUsage(final MCCommandNode node, final MCCommandSource source) {
        return internal.getSmartUsage(node.getInternal(), source.getInternal()).entrySet().stream().collect(Collectors.toMap(e -> MCCommandNode.convert(e.getKey()), Map.Entry::getValue));
    }

    /*
    public CompletableFuture<Suggestions> getCompletionSuggestions(final ParseResults<S> parse) {
    }

    public CompletableFuture<Suggestions> getCompletionSuggestions(final ParseResults<S> parse, int cursor) {
    }
     */
    
    @ZenCodeType.Method
    public MCSuggestions getCompletionSuggestions(final MCParseResults parse) throws Exception {
        return new MCSuggestions(internal.getCompletionSuggestions(parse.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCSuggestions getCompletionSuggestions(final MCParseResults parse, int cursor) throws Exception {
        return new MCSuggestions(internal.getCompletionSuggestions(parse.getInternal(), cursor));
    }
    
    @ZenCodeType.Method
    public MCRootCommandNode getRoot() {
        return new MCRootCommandNode(internal.getRoot());
    }
    
    @ZenCodeType.Method
    public Collection<String> getPath(final MCCommandNode target) {
        return internal.getPath(target.getInternal());
    }
    
    @ZenCodeType.Method
    public MCCommandNode findNode(final Collection<String> path) {
        return MCCommandNode.convert(internal.findNode(path));
    }
    
    @ZenCodeType.Method
    public void findAmbiguities(final MCAmbiguityConsumer consumer) {
        internal.findAmbiguities((parent, child, sibling, inputs) -> consumer.ambiguous(MCCommandNode.convert(parent), MCCommandNode.convert(child), MCCommandNode.convert(sibling), inputs));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommandDispatcher && internal.equals(((MCCommandDispatcher) o).internal);
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
