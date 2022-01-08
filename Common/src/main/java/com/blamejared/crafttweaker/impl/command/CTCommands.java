package com.blamejared.crafttweaker.impl.command;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.impl.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.impl.command.type.BracketDumperInfo;
import com.blamejared.crafttweaker.impl.command.type.DumpCommands;
import com.blamejared.crafttweaker.impl.command.type.HandCommands;
import com.blamejared.crafttweaker.impl.command.type.HelpCommand;
import com.blamejared.crafttweaker.impl.command.type.InventoryCommands;
import com.blamejared.crafttweaker.impl.command.type.MiscCommands;
import com.blamejared.crafttweaker.impl.command.type.ModCommands;
import com.blamejared.crafttweaker.impl.command.type.RecipeCommands;
import com.blamejared.crafttweaker.impl.command.type.conflict.ConflictCommand;
import com.blamejared.crafttweaker.impl.command.type.script.ScriptCommands;
import com.blamejared.crafttweaker.impl.command.type.script.example.ExamplesCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.Map;
import java.util.TreeMap;

public final class CTCommands {
    
    private static final Map<String, CommandImpl> COMMANDS = new TreeMap<>(String::compareTo);
    private static final LiteralArgumentBuilder<CommandSourceStack> rootLiteral = Commands.literal("ct");
    
    private CTCommands() {}
    
    public static void init(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
        
        LiteralCommandNode<CommandSourceStack> root = rootLiteral.build();
        LiteralArgumentBuilder<CommandSourceStack> rootAlternative = Commands.literal("crafttweaker");
        
        //TODO determine what permission to use
        ConflictCommand.registerCommands();
        DumpCommands.registerCommands();
        InventoryCommands.registerCommands();
        HandCommands.registerCommands();
        ScriptCommands.registerCommands();
        MiscCommands.registerCommands();
        DumpCommands.registerDumpers();
        ModCommands.registerCommands();
        RecipeCommands.registerCommands();
        CTCommands.registerCommand(new ExamplesCommand());
        
        
        dispatcher.getRoot().addChild(root);
        dispatcher.getRoot().addChild(rootAlternative.redirect(root).build());
        
        final CommandImpl dump = COMMANDS.get("dump");
        for(BracketDumperInfo dumperInfo : CraftTweakerRegistry.getBracketDumpers().values()) {
            final String subCommandName = dumperInfo.getSubCommandName();
            
            // This means that a mod used .registerDump on a name for which a @BracketDumper exists as well
            // Let's log a warning because then the .registerDump will win
            if(dump.getSubCommands().containsKey(subCommandName)) {
                CraftTweakerAPI.LOGGER.warn("Found both an explicit Dumping command and a BracketDumper annotation for the name '{}'. This is a (non-fatal) mod issue!", subCommandName);
            } else {
                registerDump(new CommandImpl(subCommandName, dumperInfo.getDescription(), commandSourceStackLiteralArgumentBuilder -> commandSourceStackLiteralArgumentBuilder.executes(dumperInfo)));
            }
        }
        
        HelpCommand.registerCommands();
        COMMANDS.forEach((s, command) -> registerCommandInternal(root, command));
        
    }
    
    
    public static void registerCommand(CommandImpl command) {
        
        COMMANDS.put(command.getName(), command);
    }
    
    public static void registerCommand(String command, CommandImpl subCommand) {
        
        COMMANDS.get(command)
                .getSubCommands()
                .put(subCommand.getName(), subCommand);
    }
    
    public static void registerDump(CommandImpl subCommand) {
        
        registerCommand("dump", subCommand);
    }
    
    public static void registerCommand(CommandImpl command, CommandImpl subCommand) {
        
        command.getSubCommands().put(subCommand.getName(), subCommand);
    }
    
    private static void registerCommandInternal(LiteralArgumentBuilder<CommandSourceStack> root, CommandImpl command) {
        
        LiteralArgumentBuilder<CommandSourceStack> literalCommand = Commands.literal(command.getName());
        final Map<String, CommandImpl> subCommands = command.getSubCommands();
        if(!subCommands.isEmpty()) {
            subCommands.forEach((name, subCommand) -> registerCommandInternal(literalCommand, subCommand));
        }
        command.register(literalCommand);
        root.then(literalCommand);
    }
    
    private static void registerCommandInternal(LiteralCommandNode<CommandSourceStack> root, CommandImpl command) {
        
        LiteralArgumentBuilder<CommandSourceStack> literalCommand = Commands.literal(command.getName());
        final Map<String, CommandImpl> subCommands = command.getSubCommands();
        if(!subCommands.isEmpty()) {
            subCommands.forEach((name, subCommand) -> registerCommandInternal(literalCommand, subCommand));
        }
        command.register(literalCommand);
        
        root.addChild(literalCommand.build());
    }
    
    
    public static Map<String, CommandImpl> getCommands() {
        
        return CTCommands.COMMANDS;
    }
    
    
}
