package com.blamejared.crafttweaker.impl.custom_commands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.custom_commands.MCRequiredArgumentBuilder")
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
        final RequiredArgumentBuilder<CommandSource, ?> suggests = internal.suggests(provider.getInternal());
        return internal == suggests ? this : new MCRequiredArgumentBuilder(internal);
    }

    @ZenCodeType.Method
    public MCSuggestionProvider getSuggestionsProvider() {
        final SuggestionProvider<CommandSource> suggestionsProvider = internal.getSuggestionsProvider();
        return suggestionsProvider == null ? null : new MCSuggestionProvider(suggestionsProvider);
    }

    @ZenCodeType.Method
    public String getName() {
        return internal.getName();
    }

    @ZenCodeType.Method
    public MCRequiredArgumentBuilder then(final MCRequiredArgumentBuilder argument) {
        final RequiredArgumentBuilder<CommandSource, ?> then = internal.then(argument.getInternal());
        return this.internal == then ? this : new MCRequiredArgumentBuilder(then);
    }

    @ZenCodeType.Method
    public MCRequiredArgumentBuilder then(final MCCommandNode argument) {
        final RequiredArgumentBuilder<CommandSource, ?> then = internal.then(argument.getInternal());
        return this.internal == then ? this : new MCRequiredArgumentBuilder(then);
    }

    @ZenCodeType.Method
    public Collection<MCCommandNode> getArguments() {
        return internal.getArguments().stream().map(MCCommandNode::new).collect(Collectors.toList());
    }

    @ZenCodeType.Method
    public MCRequiredArgumentBuilder executes(final MCCommand command) {
        final RequiredArgumentBuilder<CommandSource, ?> then = internal.executes(command.getInternal());
        return this.internal == then ? this : new MCRequiredArgumentBuilder(then);
    }


    @ZenCodeType.Method
    public MCRequiredArgumentBuilder requires(final Predicate<MCCommandSource> requirement) {
        final RequiredArgumentBuilder<CommandSource, ?> then = internal.requires(commandSource -> requirement.test(new MCCommandSource(commandSource)));
        return this.internal == then ? this : new MCRequiredArgumentBuilder(then);
    }

    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        return mcCommandSource -> internal.getRequirement().test(mcCommandSource.getInternal());
    }

    @ZenCodeType.Method
    public MCRequiredArgumentBuilder redirect(final MCCommandNode target) {
        final RequiredArgumentBuilder<CommandSource, ?> redirect = internal.redirect(target.getInternal());
        return redirect == this.internal ? this : new MCRequiredArgumentBuilder(redirect);
    }

    @ZenCodeType.Method
    public MCRequiredArgumentBuilder redirect(final MCCommandNode target, final MCSingleRedirectModifier modifier) {
        final RequiredArgumentBuilder<CommandSource, ?> redirect = internal.redirect(target.getInternal(), modifier.getInternal());
        return redirect == this.internal ? this : new MCRequiredArgumentBuilder(redirect);
    }

    @ZenCodeType.Method
    public MCRequiredArgumentBuilder fork(final MCCommandNode target, final MCRedirectModifier modifier) {
        final RequiredArgumentBuilder<CommandSource, ?> fork = internal.fork(target.getInternal(), modifier.getInternal());
        return fork == this.internal ? this : new MCRequiredArgumentBuilder(fork);
    }

    @ZenCodeType.Method
    public MCRequiredArgumentBuilder forward(final MCCommandNode target, final MCRedirectModifier modifier, final boolean fork) {
        final RequiredArgumentBuilder<CommandSource, ?> forward = internal.forward(target.getInternal(), modifier.getInternal(), fork);
        return forward == this.internal ? this : new MCRequiredArgumentBuilder(forward);
    }


    @ZenCodeType.Method
    public MCArgumentCommandNode build() {
        return new MCArgumentCommandNode(internal.build());
    }

    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCRequiredArgumentBuilder && internal.equals(((MCRequiredArgumentBuilder) o).internal);
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
