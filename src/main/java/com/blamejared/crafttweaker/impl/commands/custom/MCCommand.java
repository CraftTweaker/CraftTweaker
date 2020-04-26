package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.Command;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommand")
@Document("crafttweaker/api/commands/custom/MCCommand")
public class MCCommand {
    
    private final Command<CommandSource> internal;
    
    public MCCommand(Command<CommandSource> internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MCCommand(Function<MCCommandContext, Integer> fun) {
        internal = context -> fun.apply(new MCCommandContext(context));
    }
    
    public Command<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public int run(MCCommandContext context) throws Exception {
        return internal.run(context.getInternal());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCCommand && internal.equals(((MCCommand) o).internal);
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
