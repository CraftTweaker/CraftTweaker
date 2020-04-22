package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSuggestions")
public class MCSuggestions {
    
    private final Suggestions internal;
    
    public MCSuggestions(CompletableFuture<Suggestions> suggestionsCompletableFuture) throws ExecutionException, InterruptedException {
        this(suggestionsCompletableFuture.get());
    }
    
    public MCSuggestions(Suggestions internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Method
    public static MCSuggestions empty() throws Exception {
        return new MCSuggestions(Suggestions.empty());
    }
    
    @ZenCodeType.Method
    public static MCSuggestions merge(final String command, final Collection<MCSuggestions> input) {
        return new MCSuggestions(Suggestions.merge(command, input.stream().map(MCSuggestions::getInternal).collect(Collectors.toList())));
    }
    
    @ZenCodeType.Method
    public static MCSuggestions create(final String command, final Collection<MCSuggestion> suggestions) {
        return new MCSuggestions(Suggestions.create(command, suggestions.stream().map(MCSuggestion::getInternal).collect(Collectors.toList())));
    }
    
    public Suggestions getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        return new MCStringRange(internal.getRange());
    }
    
    @ZenCodeType.Method
    public List<Suggestion> getList() {
        return internal.getList();
    }
    
    @ZenCodeType.Method
    public boolean isEmpty() {
        return internal.isEmpty();
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCSuggestions && internal.equals(((MCSuggestions) o).internal);
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
