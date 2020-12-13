package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCAmbiguityConsumer")
@FunctionalInterface
@Document("vanilla/api/commands/custom/MCAmbiguityConsumer")
public interface MCAmbiguityConsumer {
    
    @ZenCodeType.Method
    void ambiguous(final MCCommandNode parent, final MCCommandNode child, final MCCommandNode sibling, final Collection<String> inputs);
}
