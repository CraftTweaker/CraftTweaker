package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.CommandDispatcher;
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
        return new MCLiteralCommandNode(getInternal().register(command.getInternal()));
    }
    
    @ZenCodeType.Method
    public void setConsumer(final MCResultConsumer consumer) {
        getInternal().setConsumer((context, success, result) -> consumer.onCommandComplete(new MCCommandContext(context), success, result));
    }
    
    @ZenCodeType.Method
    public int execute(final String input, final MCCommandSource source) throws Exception {
        return getInternal().execute(input, source.getInternal());
    }
    
    @ZenCodeType.Method
    public int execute(final MCParseResults parse) throws Exception {
        return getInternal().execute(parse.getInternal());
    }
    
    @ZenCodeType.Method
    public MCParseResults parse(final String command, final MCCommandSource source) {
        return new MCParseResults(getInternal().parse(command, source.getInternal()));
    }
    
    @ZenCodeType.Method
    public String[] getAllUsage(final MCCommandNode node, final MCCommandSource source, final boolean restricted) {
        return getInternal().getAllUsage(node.getInternal(), source.getInternal(), restricted);
    }
    
    @ZenCodeType.Method
    public Map<MCCommandNode, String> getSmartUsage(final MCCommandNode node, final MCCommandSource source) {
        return getInternal().getSmartUsage(node.getInternal(), source.getInternal()).entrySet().stream().collect(Collectors.toMap(e -> MCCommandNode.convert(e.getKey()), Map.Entry::getValue));
    }

    /*
    public CompletableFuture<Suggestions> getCompletionSuggestions(final ParseResults<S> parse) {
    }

    public CompletableFuture<Suggestions> getCompletionSuggestions(final ParseResults<S> parse, int cursor) {
    }
     */
    
    @ZenCodeType.Method
    public MCSuggestions getCompletionSuggestions(final MCParseResults parse) throws Exception {
        return new MCSuggestions(getInternal().getCompletionSuggestions(parse.getInternal()));
    }
    
    @ZenCodeType.Method
    public MCSuggestions getCompletionSuggestions(final MCParseResults parse, int cursor) throws Exception {
        return new MCSuggestions(getInternal().getCompletionSuggestions(parse.getInternal(), cursor));
    }
    
    @ZenCodeType.Method
    public MCRootCommandNode getRoot() {
        return new MCRootCommandNode(getInternal().getRoot());
    }
    
    @ZenCodeType.Method
    public Collection<String> getPath(final MCCommandNode target) {
        return getInternal().getPath(target.getInternal());
    }
    
    @ZenCodeType.Method
    public MCCommandNode findNode(final Collection<String> path) {
        return MCCommandNode.convert(getInternal().findNode(path));
    }
    
    @ZenCodeType.Method
    public void findAmbiguities(final MCAmbiguityConsumer consumer) {
        getInternal().findAmbiguities((parent, child, sibling, inputs) -> consumer.ambiguous(MCCommandNode.convert(parent), MCCommandNode.convert(child), MCCommandNode.convert(sibling), inputs));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommandDispatcher && getInternal().equals(((MCCommandDispatcher) o).getInternal());
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
