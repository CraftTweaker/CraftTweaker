package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Consumer;

final class CommandRegistrationHandler implements ICommandRegistrationHandler {
    
    private CommandRegistrationHandler() {}
    
    public static void gather(final Consumer<ICommandRegistrationHandler> consumer) {
        
        final CommandRegistrationHandler handler = new CommandRegistrationHandler();
        consumer.accept(handler);
    }
    
    @Override
    public void registerRootCommand(final String commandId, final MutableComponent description, final CommandBuilder builder) {
        
        CtCommands.get().registerCommand(commandId, description, builder);
    }
    
    @Override
    public void registerSubCommand(final String parentCommand, final String commandId, final MutableComponent description, final CommandBuilder builder) {
        
        CtCommands.get().registerSubCommand(parentCommand, commandId, description, builder);
    }
    
    @Override
    public void registerDump(final String dumpId, final MutableComponent description, final CommandBuilder builder) {
        
        CtCommands.get().registerDump(dumpId, description, builder);
    }
    
}
