package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCAmbiguityConsumer")
@FunctionalInterface
public interface MCAmbiguityConsumer {
    
    @ZenCodeType.Method
    void ambiguous(final MCCommandNode parent, final MCCommandNode child, final MCCommandNode sibling, final Collection<String> inputs);
}
