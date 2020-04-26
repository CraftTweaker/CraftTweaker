package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.RedirectModifier;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;


@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCRedirectModifier")
@Document("crafttweaker/api/commands/custom/MCRedirectModifier")
public class MCRedirectModifier {
    
    private final RedirectModifier<CommandSource> internal;
    
    public MCRedirectModifier(RedirectModifier<CommandSource> internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MCRedirectModifier(Function<MCCommandContext, Collection<MCCommandSource>> fun) {
        internal = context -> fun.apply(new MCCommandContext(context)).stream().map(MCCommandSource::getInternal).collect(Collectors.toList());
    }
    
    public RedirectModifier<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public Collection<MCCommandSource> apply(final MCCommandContext context) throws Exception {
        return internal.apply(context.getInternal()).stream().map(MCCommandSource::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCRedirectModifier && internal.equals(((MCRedirectModifier) o).internal);
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
