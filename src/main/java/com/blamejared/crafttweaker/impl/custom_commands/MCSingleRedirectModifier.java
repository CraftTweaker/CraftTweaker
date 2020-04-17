package com.blamejared.crafttweaker.impl.custom_commands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.mojang.brigadier.SingleRedirectModifier;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.custom_commands.MCSingleRedirectModifier")
public class MCSingleRedirectModifier {
    private final SingleRedirectModifier<CommandSource> internal;

    public MCSingleRedirectModifier(SingleRedirectModifier<CommandSource> internal) {
        this.internal = internal;
    }

    @ZenCodeType.Constructor
    public MCSingleRedirectModifier(Function<MCCommandContext, MCCommandSource> fun) {
        internal = context -> fun.apply(new MCCommandContext(context)).getInternal();
    }

    public SingleRedirectModifier<CommandSource> getInternal() {
        return internal;
    }

    @ZenCodeType.Method
    public MCCommandSource apply(MCCommandContext context) throws Exception {
        return new MCCommandSource(internal.apply(context.getInternal()));
    }

    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCSingleRedirectModifier && internal.equals(((MCSingleRedirectModifier) o).internal);
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
