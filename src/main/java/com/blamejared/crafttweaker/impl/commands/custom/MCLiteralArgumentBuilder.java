package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Predicate;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCLiteralArgumentBuilder")
@Document("vanilla/api/commands/custom/MCLiteralArgumentBuilder")
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
        return getInternal().getLiteral();
    }
    
    @ZenCodeType.Method
    public MCLiteralCommandNode build() {
        return new MCLiteralCommandNode(getInternal().build());
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder then(final MCArgumentBuilder argument) {
        final LiteralArgumentBuilder<CommandSource> then = getInternal().then(argument.getInternal());
        return getInternal() == then ? this : new MCLiteralArgumentBuilder(then);
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder then(final MCCommandNode argument) {
        final LiteralArgumentBuilder<CommandSource> then = getInternal().then(argument.getInternal());
        return getInternal() == then ? this : new MCLiteralArgumentBuilder(then);
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder executes(final MCCommand command) {
        final LiteralArgumentBuilder<CommandSource> then = getInternal().executes(command.getInternal());
        return getInternal() == then ? this : new MCLiteralArgumentBuilder(then);
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder requires(final Predicate<MCCommandSource> requirement) {
        final LiteralArgumentBuilder<CommandSource> then = getInternal().requires(commandSource -> requirement.test(new MCCommandSource(commandSource)));
        return getInternal() == then ? this : new MCLiteralArgumentBuilder(then);
    }
    
    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        return mcCommandSource -> getInternal().getRequirement().test(mcCommandSource.getInternal());
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder redirect(final MCCommandNode target) {
        final LiteralArgumentBuilder<CommandSource> redirect = getInternal().redirect(target.getInternal());
        return redirect == getInternal() ? this : new MCLiteralArgumentBuilder(redirect);
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder redirect(final MCCommandNode target, final MCSingleRedirectModifier modifier) {
        final LiteralArgumentBuilder<CommandSource> redirect = getInternal().redirect(target.getInternal(), modifier.getInternal());
        return redirect == getInternal() ? this : new MCLiteralArgumentBuilder(redirect);
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder fork(final MCCommandNode target, final MCRedirectModifier modifier) {
        final LiteralArgumentBuilder<CommandSource> fork = getInternal().fork(target.getInternal(), modifier.getInternal());
        return fork == getInternal() ? this : new MCLiteralArgumentBuilder(fork);
    }
    
    @ZenCodeType.Method
    public MCLiteralArgumentBuilder forward(final MCCommandNode target, final MCRedirectModifier modifier, final boolean fork) {
        final LiteralArgumentBuilder<CommandSource> forward = getInternal().forward(target.getInternal(), modifier.getInternal(), fork);
        return forward == getInternal() ? this : new MCLiteralArgumentBuilder(forward);
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCLiteralArgumentBuilder && getInternal().equals(((MCLiteralArgumentBuilder) o).getInternal());
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
