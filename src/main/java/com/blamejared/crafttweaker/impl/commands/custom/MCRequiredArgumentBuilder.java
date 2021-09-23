package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCRequiredArgumentBuilder")
@Document("vanilla/api/commands/custom/MCRequiredArgumentBuilder")
public class MCRequiredArgumentBuilder extends MCArgumentBuilder {
    
    private final RequiredArgumentBuilder<CommandSource, ?> internal;
    
    public MCRequiredArgumentBuilder(RequiredArgumentBuilder<CommandSource, ?> internal) {
        
        super(internal);
        this.internal = internal;
    }
    
    @Override
    public RequiredArgumentBuilder<CommandSource, ?> getInternal() {
        
        return internal;
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder suggests(final MCSuggestionProvider provider) {
        
        final RequiredArgumentBuilder<CommandSource, ?> suggests = getInternal().suggests(provider.getInternal());
        return getInternal() == suggests ? this : new MCRequiredArgumentBuilder(getInternal());
    }
    
    @ZenCodeType.Method
    public MCSuggestionProvider getSuggestionsProvider() {
        
        final SuggestionProvider<CommandSource> suggestionsProvider = getInternal().getSuggestionsProvider();
        return suggestionsProvider == null ? null : new MCSuggestionProvider(suggestionsProvider);
    }
    
    @ZenCodeType.Method
    public String getName() {
        
        return getInternal().getName();
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder then(final MCRequiredArgumentBuilder argument) {
        
        final RequiredArgumentBuilder<CommandSource, ?> then = getInternal().then(argument.getInternal());
        return getInternal() == then ? this : new MCRequiredArgumentBuilder(then);
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder then(final MCCommandNode argument) {
        
        final RequiredArgumentBuilder<CommandSource, ?> then = getInternal().then(argument.getInternal());
        return getInternal() == then ? this : new MCRequiredArgumentBuilder(then);
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandNode> getArguments() {
        
        return getInternal().getArguments().stream().map(MCCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder executes(final MCCommand command) {
        
        final RequiredArgumentBuilder<CommandSource, ?> then = getInternal().executes(command.getInternal());
        return getInternal() == then ? this : new MCRequiredArgumentBuilder(then);
    }
    
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder requires(final Predicate<MCCommandSource> requirement) {
        
        final RequiredArgumentBuilder<CommandSource, ?> then = getInternal().requires(commandSource -> requirement.test(new MCCommandSource(commandSource)));
        return getInternal() == then ? this : new MCRequiredArgumentBuilder(then);
    }
    
    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        
        return mcCommandSource -> getInternal().getRequirement().test(mcCommandSource.getInternal());
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder redirect(final MCCommandNode target) {
        
        final RequiredArgumentBuilder<CommandSource, ?> redirect = getInternal().redirect(target.getInternal());
        return redirect == getInternal() ? this : new MCRequiredArgumentBuilder(redirect);
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder redirect(final MCCommandNode target, final MCSingleRedirectModifier modifier) {
        
        final RequiredArgumentBuilder<CommandSource, ?> redirect = getInternal().redirect(target.getInternal(), modifier.getInternal());
        return redirect == getInternal() ? this : new MCRequiredArgumentBuilder(redirect);
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder fork(final MCCommandNode target, final MCRedirectModifier modifier) {
        
        final RequiredArgumentBuilder<CommandSource, ?> fork = getInternal().fork(target.getInternal(), modifier.getInternal());
        return fork == getInternal() ? this : new MCRequiredArgumentBuilder(fork);
    }
    
    @ZenCodeType.Method
    public MCRequiredArgumentBuilder forward(final MCCommandNode target, final MCRedirectModifier modifier, final boolean fork) {
        
        final RequiredArgumentBuilder<CommandSource, ?> forward = getInternal().forward(target.getInternal(), modifier.getInternal(), fork);
        return forward == getInternal() ? this : new MCRequiredArgumentBuilder(forward);
    }
    
    
    @ZenCodeType.Method
    public MCArgumentCommandNode build() {
        
        return new MCArgumentCommandNode(getInternal().build());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCRequiredArgumentBuilder && getInternal().equals(((MCRequiredArgumentBuilder) o).getInternal());
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
