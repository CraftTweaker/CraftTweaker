package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.context.ParsedArgument;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCParsedArgument")
@Document("vanilla/api/commands/custom/MCParsedArgument")
public class MCParsedArgument {
    
    private final ParsedArgument<CommandSource, ?> internal;
    
    public MCParsedArgument(ParsedArgument<CommandSource, ?> internal) {
        this.internal = internal;
    }
    
    public ParsedArgument<CommandSource, ?> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCStringRange getRange() {
        return new MCStringRange(getInternal().getRange());
    }

    /*
    public T getResult() {
        return result;
    }
     */
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCParsedArgument && getInternal().equals(((MCParsedArgument) o).getInternal());
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
