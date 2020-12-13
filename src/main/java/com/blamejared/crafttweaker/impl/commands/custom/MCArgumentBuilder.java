package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCArgumentBuilder")
@Document("vanilla/api/commands/custom/MCArgumentBuilder")
public class MCArgumentBuilder {
    
    private final ArgumentBuilder<CommandSource, ?> internal;
    
    public MCArgumentBuilder(ArgumentBuilder<CommandSource, ?> internal) {
        this.internal = internal;
    }
    
    public static MCArgumentBuilder convert(ArgumentBuilder<CommandSource, ?> internal) {
        if(internal == null) {
            return null;
        }
        
        if(internal instanceof LiteralArgumentBuilder) {
            //noinspection unchecked
            return new MCLiteralArgumentBuilder((LiteralArgumentBuilder<CommandSource>) internal);
        }
        
        if(internal instanceof RequiredArgumentBuilder) {
            //noinspection unchecked
            return new MCRequiredArgumentBuilder((RequiredArgumentBuilder<CommandSource, ?>) internal);
        }
        
        return new MCArgumentBuilder(internal);
    }
    
    public ArgumentBuilder<CommandSource, ?> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder then(final MCArgumentBuilder argument) {
        final ArgumentBuilder<CommandSource, ?> then = internal.then(argument.getInternal());
        return this.internal == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder then(final MCCommandNode argument) {
        final ArgumentBuilder<CommandSource, ?> then = internal.then(argument.getInternal());
        return this.internal == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandNode> getArguments() {
        return internal.getArguments().stream().map(MCCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder executes(final MCCommand command) {
        final ArgumentBuilder<CommandSource, ?> then = internal.executes(command.getInternal());
        return this.internal == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public MCCommand getCommand() {
        return new MCCommand(internal.getCommand());
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder requires(final Predicate<MCCommandSource> requirement) {
        final ArgumentBuilder<CommandSource, ?> then = internal.requires(commandSource -> requirement.test(new MCCommandSource(commandSource)));
        return this.internal == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        return mcCommandSource -> internal.getRequirement().test(mcCommandSource.getInternal());
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder redirect(final MCCommandNode target) {
        final ArgumentBuilder<CommandSource, ?> redirect = internal.redirect(target.getInternal());
        return redirect == this.internal ? this : MCArgumentBuilder.convert(redirect);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder redirect(final MCCommandNode target, final MCSingleRedirectModifier modifier) {
        final ArgumentBuilder<CommandSource, ?> redirect = internal.redirect(target.getInternal(), modifier.getInternal());
        return redirect == this.internal ? this : MCArgumentBuilder.convert(redirect);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder fork(final MCCommandNode target, final MCRedirectModifier modifier) {
        final ArgumentBuilder<CommandSource, ?> fork = internal.fork(target.getInternal(), modifier.getInternal());
        return fork == this.internal ? this : MCArgumentBuilder.convert(fork);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder forward(final MCCommandNode target, final MCRedirectModifier modifier, final boolean fork) {
        final ArgumentBuilder<CommandSource, ?> forward = internal.forward(target.getInternal(), modifier.getInternal(), fork);
        return forward == this.internal ? this : MCArgumentBuilder.convert(forward);
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
    public boolean isFork() {
        return internal.isFork();
    }
    
    @ZenCodeType.Method
    public MCCommandNode build() {
        return MCCommandNode.convert(internal.build());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCArgumentBuilder && internal.equals(((MCArgumentBuilder) o).internal);
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
