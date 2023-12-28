package com.blamejared.crafttweaker.impl.command;

import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.impl.command.type.HelpCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.TreeMap;

public final class CtCommands {
    
    private record ChildData(
            String parent,
            String id,
            MutableComponent desc,
            ICommandRegistrationHandler.CommandBuilder builder
    ) {}
    
    private static final CtCommands INSTANCE = new CtCommands();
    
    private final Map<String, CommandImpl> commands;
    private final Map<String, CommandImpl> commandsView;
    private final Queue<ChildData> lazyData;
    
    private CtCommands() {
        
        this.commands = new TreeMap<>(String::compareTo);
        this.commandsView = Collections.unmodifiableMap(this.commands);
        this.lazyData = new LinkedList<>();
    }
    
    public static CtCommands get() {
        
        return INSTANCE;
    }
    
    public void registerCommand(final String id, final MutableComponent desc, final ICommandRegistrationHandler.CommandBuilder builder) {
        
        if(this.commands.containsKey(id)) {
            throw new IllegalStateException("Duplicate root command " + id);
        }
        this.commands.put(id, new CommandImpl(id, desc, builder));
    }
    
    public void registerSubCommand(final String parent, final String id, final MutableComponent desc, final ICommandRegistrationHandler.CommandBuilder builder) {
        
        // TODO("Allow recursion?")
        final CommandImpl parentCommand = this.commands.get(parent);
        if(parentCommand == null) {
            this.lazyData.add(new ChildData(parent, id, desc, builder));
            return;
        }
        parentCommand.registerSubCommand(new CommandImpl(id, desc, builder));
    }
    
    public void registerDump(final String dumpId, final MutableComponent description, final ICommandRegistrationHandler.CommandBuilder builder) {
        
        this.lazyData.add(new ChildData("dump", dumpId, description, builder));
    }
    
    public void finalizeCommands() {
        
        this.computeLazyData(this.lazyData);
    }
    
    public void registerCommandsTo(final CommandDispatcher<CommandSourceStack> dispatcher, @SuppressWarnings("unused") final Commands.CommandSelection environment) {
        
        final LiteralCommandNode<CommandSourceStack> root = Commands.literal("ct").build();
        final LiteralArgumentBuilder<CommandSourceStack> aliasedRoot = Commands.literal("crafttweaker");
        
        dispatcher.getRoot().addChild(root);
        dispatcher.getRoot().addChild(aliasedRoot.redirect(root).build());
        
        HelpCommand.registerCommandIfRequired(this);
        this.commands.forEach((s, command) -> this.registerCommandTo(root, command));
    }
    
    private void computeLazyData(final Queue<ChildData> dataQueue) {
        
        while(!dataQueue.isEmpty()) {
            final ChildData data = dataQueue.remove();
            final CommandImpl parent = Objects.requireNonNull(this.commands.get(data.parent()), "Unknown parent command " + data.parent());
            if(parent.subCommands().containsKey(data.id())) {
                throw new IllegalArgumentException("Duplicated subcommand for parent " + parent.name() + ": " + data.id());
            }
            parent.registerSubCommand(new CommandImpl(data.id(), data.desc(), data.builder()));
        }
    }
    
    private void registerCommandTo(final LiteralCommandNode<CommandSourceStack> root, final CommandImpl command) {
        
        final LiteralArgumentBuilder<CommandSourceStack> literalCommand = Commands.literal(command.name());
        command.subCommands().forEach((name, subCommand) -> this.registerSubCommandTo(literalCommand, subCommand));
        command.register(literalCommand);
        root.addChild(literalCommand.build());
    }
    
    private void registerSubCommandTo(final LiteralArgumentBuilder<CommandSourceStack> root, final CommandImpl command) {
        
        final LiteralArgumentBuilder<CommandSourceStack> literalCommand = Commands.literal(command.name());
        command.subCommands().forEach((name, subCommand) -> this.registerSubCommandTo(literalCommand, subCommand));
        command.register(literalCommand);
        root.then(literalCommand);
    }
    
    public Map<String, CommandImpl> commands() {
        
        return this.commandsView;
    }
    
}
