package com.blamejared.crafttweaker.api.command.boilerplate;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CommandImpl implements Comparable<CommandImpl> {
    
    private final String name;
    private final MutableComponent description;
    private final Consumer<LiteralArgumentBuilder<CommandSourceStack>> callback;
    private final Map<String, CommandImpl> subCommands;
    
    // Set after the callback has been consumed.
    private Predicate<CommandSourceStack> requirement;
    
    public CommandImpl(String name, MutableComponent description, Consumer<LiteralArgumentBuilder<CommandSourceStack>> callback) {
        
        this.name = name;
        this.description = description;
        this.callback = callback;
        this.subCommands = new TreeMap<>();
    }
    
    public void register(LiteralArgumentBuilder<CommandSourceStack> literalCommand) {
        
        getCallback().accept(literalCommand);
        this.requirement = literalCommand.getRequirement();
    }
    
    public String getName() {
        
        return name;
    }
    
    public Consumer<LiteralArgumentBuilder<CommandSourceStack>> getCallback() {
        
        return callback;
    }
    
    public Map<String, CommandImpl> getSubCommands() {
        
        return subCommands;
    }
    
    public MutableComponent getDescription() {
        
        return description;
    }
    
    public void registerSubCommand(CommandImpl subCommand) {
        
        this.subCommands.put(subCommand.getName(), subCommand);
    }
    
    @Override
    public int compareTo(CommandImpl o) {
        
        return getName().compareTo(o.getName());
    }
    
    
    public Predicate<CommandSourceStack> getRequirement() {
        
        if(requirement == null) {
            throw new RuntimeException("Cannot get requirements before command has been registered!");
        }
        
        return requirement;
    }
    
}
