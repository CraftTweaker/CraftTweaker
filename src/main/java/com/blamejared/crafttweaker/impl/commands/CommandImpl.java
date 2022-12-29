package com.blamejared.crafttweaker.impl.commands;

import net.minecraft.command.CommandSource;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

public class CommandImpl implements Comparable<CommandImpl> {
    
    private final String name;
    private final String description;
    private final com.blamejared.crafttweaker.impl.commands.CommandCaller caller;
    private final Map<String, CommandImpl> subCommands;
    
    private final Predicate<CommandSource> requirement;
    
    public CommandImpl(String name, String description, CommandCaller caller) {
        
        this.name = name;
        this.description = description;
        this.caller = caller;
        this.subCommands = new TreeMap<>();
        this.requirement = commandSource -> true;
    }
    
    public CommandImpl(String name, String description, CommandCaller caller,  Predicate<CommandSource> requirement) {
        
        this.name = name;
        this.description = description;
        this.caller = caller;
        this.subCommands = new TreeMap<>();
        this.requirement = requirement;
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
    
    public Predicate<CommandSource> getRequirement() {
        
        return requirement;
    }
    
    @Override
    public int compareTo(CommandImpl o) {
        
        return getName().compareTo(o.getName());
    }
    
}
