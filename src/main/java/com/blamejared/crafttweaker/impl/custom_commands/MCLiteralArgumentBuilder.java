package com.blamejared.crafttweaker.impl.custom_commands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Predicate;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.custom_commands.MCLiteralArgumentBuilder")
public class MCLiteralArgumentBuilder extends MCArgumentBuilder {
    private final LiteralArgumentBuilder<CommandSource> internal;

    public MCLiteralArgumentBuilder(LiteralArgumentBuilder<CommandSource> internal) {
        super(internal);
        this.internal = internal;
    }

    public LiteralArgumentBuilder<CommandSource> getInternal() {
        return internal;
    }

    @ZenCodeType.Method
    public String getLiteral() {
        return internal.getLiteral();
    }

    @ZenCodeType.Method
    public MCLiteralCommandNode build() {
        return new MCLiteralCommandNode(internal.build());
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder then(final MCArgumentBuilder argument) {
        final LiteralArgumentBuilder<CommandSource> then = internal.then(argument.getInternal());
        return this.internal == then ? this : new MCLiteralArgumentBuilder(then);
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder then(final MCCommandNode argument) {
        final LiteralArgumentBuilder<CommandSource> then = internal.then(argument.getInternal());
        return this.internal == then ? this : new MCLiteralArgumentBuilder(then);
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder executes(final MCCommand command) {
        final LiteralArgumentBuilder<CommandSource> then = internal.executes(command.getInternal());
        return this.internal == then ? this : new MCLiteralArgumentBuilder(then);
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder requires(final Predicate<MCCommandSource> requirement) {
        final LiteralArgumentBuilder<CommandSource> then = internal.requires(commandSource -> requirement.test(new MCCommandSource(commandSource)));
        return this.internal == then ? this : new MCLiteralArgumentBuilder(then);
    }

    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        return mcCommandSource -> internal.getRequirement().test(mcCommandSource.getInternal());
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder redirect(final MCCommandNode target) {
        final LiteralArgumentBuilder<CommandSource> redirect = internal.redirect(target.getInternal());
        return redirect == this.internal ? this : new MCLiteralArgumentBuilder(redirect);
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder redirect(final MCCommandNode target, final MCSingleRedirectModifier modifier) {
        final LiteralArgumentBuilder<CommandSource> redirect = internal.redirect(target.getInternal(), modifier.getInternal());
        return redirect == this.internal ? this : new MCLiteralArgumentBuilder(redirect);
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder fork(final MCCommandNode target, final MCRedirectModifier modifier) {
        final LiteralArgumentBuilder<CommandSource> fork = internal.fork(target.getInternal(), modifier.getInternal());
        return fork == this.internal ? this : new MCLiteralArgumentBuilder(fork);
    }

    @ZenCodeType.Method
    public MCLiteralArgumentBuilder forward(final MCCommandNode target, final MCRedirectModifier modifier, final boolean fork) {
        final LiteralArgumentBuilder<CommandSource> forward = internal.forward(target.getInternal(), modifier.getInternal(), fork);
        return forward == this.internal ? this : new MCLiteralArgumentBuilder(forward);
    }

    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCLiteralArgumentBuilder && internal.equals(((MCLiteralArgumentBuilder) o).internal);
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
