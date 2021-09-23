package com.blamejared.crafttweaker.impl.commands;

import java.util.Map;
import java.util.TreeMap;

public class CommandImpl implements Comparable<CommandImpl> {
    
    private final String name;
    private final String description;
    private final com.blamejared.crafttweaker.impl.commands.CommandCaller caller;
    private final Map<String, CommandImpl> subCommands;
    
    public CommandImpl(String name, String description, CommandCaller caller) {
        
        this.name = name;
        this.description = description;
        this.caller = caller;
        this.subCommands = new TreeMap<>();
    }
    
    public String getName() {
        
        return name;
    }
    
    public CommandCaller getCaller() {
        
        return caller;
    }
    
    
    public String getDescription() {
        
        return description;
    }
    
    public void registerSubCommand(CommandImpl subCommand) {
        
        this.subCommands.put(subCommand.getName(), subCommand);
    }
    
    public Map<String, CommandImpl> getChildCommands() {
        
        return subCommands;
    }
    
    @Override
    public int compareTo(CommandImpl o) {
        
        return getName().compareTo(o.getName());
    }
    
}
