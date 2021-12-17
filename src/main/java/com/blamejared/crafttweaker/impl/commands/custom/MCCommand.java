package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.Command;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;
import java.util.function.ToIntFunction;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommand")
@Document("vanilla/api/commands/custom/MCCommand")
public class MCCommand {
    
    private final Command<CommandSource> internal;
    
    public MCCommand(Command<CommandSource> internal) {
        
        this.internal = internal;
    }
    
    @Deprecated
    public MCCommand(Function<MCCommandContext, Integer> fun) {
        
        internal = context -> fun.apply(new MCCommandContext(context));
    }

    @ZenCodeType.Constructor
    public MCCommand(ToIntFunction<MCCommandContext> fun) {

        internal = context -> fun.applyAsInt(new MCCommandContext(context));
    }
    
    public Command<CommandSource> getInternal() {
        
        return internal;
    }
    
    @ZenCodeType.Method
    public int run(MCCommandContext context) throws Exception {
        
        return getInternal().run(context.getInternal());
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCCommand && getInternal().equals(((MCCommand) o).getInternal());
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
