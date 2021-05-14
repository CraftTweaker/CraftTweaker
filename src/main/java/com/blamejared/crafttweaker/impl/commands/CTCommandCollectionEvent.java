package com.blamejared.crafttweaker.impl.commands;

import net.minecraftforge.eventbus.api.*;

/**
 * This event is fired by {@link CTCommands} when it has created all CT commands and is about to register them to the Dispatcher
 * Use this if you want to register subcommands.
 * The methods here are simple delegates to the static methods in {@link CTCommands} to make it easier
 */
public class CTCommandCollectionEvent extends Event {
    
    public void registerCommand(CommandImpl command) {
        CTCommands.registerCommand(command);
    }
    
    public void registerCommand(String command, CommandImpl subCommand) {
        CTCommands.registerCommand(command, subCommand);
    }
    
    public void registerCommand(CommandImpl command, CommandImpl subCommand) {
        CTCommands.registerCommand(command, subCommand);
    }

    public void registerDump(String name, String desc, CommandCaller caller) {
        CTCommands.registerDump(name, desc, caller);
    }
    
    @Deprecated
    public void registerCommand(CTCommands.CommandImpl command) {
        CTCommands.registerCommand(command);
    }
    
    @Deprecated
    public void registerCommand(String command, CTCommands.CommandImpl subCommand) {
        CTCommands.registerCommand(command, subCommand);
    }
    
    @Deprecated
    public void registerCommand(CTCommands.CommandImpl command, CTCommands.CommandImpl subCommand) {
        CTCommands.registerCommand(command, subCommand);
    }
    
    @Deprecated
    public void registerDump(String name, String desc, CTCommands.CommandCaller caller) {
        CTCommands.registerDump(name, desc, caller);
    }
}
