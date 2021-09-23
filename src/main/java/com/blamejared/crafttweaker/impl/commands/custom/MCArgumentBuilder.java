package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
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
        
        final ArgumentBuilder<CommandSource, ?> then = getInternal().then(argument.getInternal());
        return getInternal() == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder then(final MCCommandNode argument) {
        
        final ArgumentBuilder<CommandSource, ?> then = getInternal().then(argument.getInternal());
        return getInternal() == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandNode> getArguments() {
        
        return getInternal().getArguments().stream().map(MCCommandNode::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder executes(final MCCommand command) {
        
        final ArgumentBuilder<CommandSource, ?> then = getInternal().executes(command.getInternal());
        return getInternal() == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public MCCommand getCommand() {
        
        return new MCCommand(getInternal().getCommand());
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder requires(final Predicate<MCCommandSource> requirement) {
        
        final ArgumentBuilder<CommandSource, ?> then = getInternal().requires(commandSource -> requirement.test(new MCCommandSource(commandSource)));
        return getInternal() == then ? this : MCArgumentBuilder.convert(then);
    }
    
    @ZenCodeType.Method
    public Predicate<MCCommandSource> getRequirement() {
        
        return mcCommandSource -> getInternal().getRequirement().test(mcCommandSource.getInternal());
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder redirect(final MCCommandNode target) {
        
        final ArgumentBuilder<CommandSource, ?> redirect = getInternal().redirect(target.getInternal());
        return redirect == getInternal() ? this : MCArgumentBuilder.convert(redirect);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder redirect(final MCCommandNode target, final MCSingleRedirectModifier modifier) {
        
        final ArgumentBuilder<CommandSource, ?> redirect = getInternal().redirect(target.getInternal(), modifier.getInternal());
        return redirect == getInternal() ? this : MCArgumentBuilder.convert(redirect);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder fork(final MCCommandNode target, final MCRedirectModifier modifier) {
        
        final ArgumentBuilder<CommandSource, ?> fork = getInternal().fork(target.getInternal(), modifier.getInternal());
        return fork == getInternal() ? this : MCArgumentBuilder.convert(fork);
    }
    
    @ZenCodeType.Method
    public MCArgumentBuilder forward(final MCCommandNode target, final MCRedirectModifier modifier, final boolean fork) {
        
        final ArgumentBuilder<CommandSource, ?> forward = getInternal().forward(target.getInternal(), modifier.getInternal(), fork);
        return forward == getInternal() ? this : MCArgumentBuilder.convert(forward);
    }
    
    @ZenCodeType.Method
    public MCCommandNode getRedirect() {
        
        return MCCommandNode.convert(getInternal().getRedirect());
    }
    
    @ZenCodeType.Method
    public MCRedirectModifier getRedirectModifier() {
        
        return new MCRedirectModifier(getInternal().getRedirectModifier());
    }
    
    @ZenCodeType.Method
    public boolean isFork() {
        
        return getInternal().isFork();
    }
    
    @ZenCodeType.Method
    public MCCommandNode build() {
        
        return MCCommandNode.convert(getInternal().build());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCArgumentBuilder && getInternal().equals(((MCArgumentBuilder) o).getInternal());
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
