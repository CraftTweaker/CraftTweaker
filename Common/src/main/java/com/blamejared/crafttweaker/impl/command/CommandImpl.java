package com.blamejared.crafttweaker.impl.command;

import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Predicate;

public final class CommandImpl implements Comparable<CommandImpl> {
    
    private static final Comparator<CommandImpl> COMPARATOR = Comparator.comparing(CommandImpl::name);
    
    private final String name;
    private final MutableComponent description;
    private final ICommandRegistrationHandler.CommandBuilder callback;
    private final Map<String, CommandImpl> subCommands;
    private final Map<String, CommandImpl> subCommandsView;
    
    // Set after the callback has been consumed.
    private Predicate<CommandSourceStack> requirement;
    
    public CommandImpl(final String name, final MutableComponent description, ICommandRegistrationHandler.CommandBuilder callback) {
        
        this.name = name;
        this.description = description;
        this.callback = callback;
        this.subCommands = new TreeMap<>(String::compareTo);
        this.subCommandsView = Collections.unmodifiableMap(this.subCommands);
    }
    
    public void register(LiteralArgumentBuilder<CommandSourceStack> literalCommand) {
        
        this.callback.buildCommand(literalCommand);
        this.requirement = literalCommand.getRequirement();
    }
    
    public String name() {
        
        return this.name;
    }
    
    public ICommandRegistrationHandler.CommandBuilder callback() {
        
        return this.callback;
    }
    
    public Map<String, CommandImpl> subCommands() {
        
        return this.subCommandsView;
    }
    
    public MutableComponent description() {
        
        return this.description;
    }
    
    public Predicate<CommandSourceStack> requirement() {
        
        return Objects.requireNonNull(this.requirement, "Cannot get requirements before command registration");
    }
    
    public void registerSubCommand(final CommandImpl subCommand) {
        
        this.subCommands.put(subCommand.name(), subCommand);
    }
    
    @Override
    public int compareTo(final CommandImpl o) {
        
        return COMPARATOR.compare(this, o);
    }
    
}
