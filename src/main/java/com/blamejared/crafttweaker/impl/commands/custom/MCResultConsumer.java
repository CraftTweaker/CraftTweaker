package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCResultConsumer")
@FunctionalInterface
public interface MCResultConsumer {
    
    @ZenCodeType.Method
    void onCommandComplete(MCCommandContext commandContext, boolean success, int result);
}
