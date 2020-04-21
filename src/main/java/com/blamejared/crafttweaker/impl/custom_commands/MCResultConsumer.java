package com.blamejared.crafttweaker.impl.custom_commands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.custom_commands.MCResultConsumer")
@FunctionalInterface
public interface MCResultConsumer {
    @ZenCodeType.Method
    void onCommandComplete(MCCommandContext commandContext, boolean success, int result);
}
