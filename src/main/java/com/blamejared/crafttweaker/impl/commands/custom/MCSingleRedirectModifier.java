package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.SingleRedirectModifier;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCSingleRedirectModifier")
@Document("vanilla/api/commands/custom/MCSingleRedirectModifier")
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
        
        return new MCCommandSource(getInternal().apply(context.getInternal()));
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        
        return o instanceof MCSingleRedirectModifier && getInternal().equals(((MCSingleRedirectModifier) o).getInternal());
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
