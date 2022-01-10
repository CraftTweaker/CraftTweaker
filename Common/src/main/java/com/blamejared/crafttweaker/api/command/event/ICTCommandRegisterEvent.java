package com.blamejared.crafttweaker.api.command.event;

import com.blamejared.crafttweaker.api.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.impl.command.CTCommands;

public interface ICTCommandRegisterEvent {
    
    default void registerCommand(CommandImpl command) {
        
        CTCommands.registerCommand(command);
    }
    
    default void registerCommand(String command, CommandImpl subCommand) {
        
        CTCommands.registerCommand(command, subCommand);
    }
    
    default void registerCommand(CommandImpl command, CommandImpl subCommand) {
        
        CTCommands.registerCommand(command, subCommand);
    }
    
    default void registerDump(CommandImpl command) {
        
        CTCommands.registerDump(command);
    }
    
}
