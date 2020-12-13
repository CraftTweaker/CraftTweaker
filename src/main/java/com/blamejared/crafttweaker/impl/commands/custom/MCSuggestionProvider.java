package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSuggestionProvider")
@Document("vanilla/api/commands/custom/MCSuggestionProvider")
public class MCSuggestionProvider {
    
    private final SuggestionProvider<CommandSource> internal;
    
    public MCSuggestionProvider(SuggestionProvider<CommandSource> internal) {
        this.internal = internal;
    }
    
    public SuggestionProvider<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Constructor
    public MCSuggestionProvider(BiFunction<MCCommandContext, MCSuggestionsBuilder, MCSuggestions> fun) {
        this.internal = (context, builder) -> CompletableFuture.completedFuture(fun.apply(new MCCommandContext(context), new MCSuggestionsBuilder(builder)).getInternal());
    }
    
    @ZenCodeType.Method
    public MCSuggestions getSuggestions(final MCCommandContext context, final MCSuggestionsBuilder builder) throws Exception {
        return new MCSuggestions(internal.getSuggestions(context.getInternal(), builder.getInternal()));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCSuggestionProvider && internal.equals(((MCSuggestionProvider) o).internal);
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
