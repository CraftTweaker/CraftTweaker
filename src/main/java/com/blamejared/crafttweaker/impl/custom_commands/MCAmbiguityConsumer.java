package com.blamejared.crafttweaker.impl.custom_commands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.custom_commands.MCAmbiguityConsumer")
@FunctionalInterface
public interface MCAmbiguityConsumer {
    @ZenCodeType.Method
    void ambiguous(final MCCommandNode parent, final MCCommandNode child, final MCCommandNode sibling, final Collection<String> inputs);
}
