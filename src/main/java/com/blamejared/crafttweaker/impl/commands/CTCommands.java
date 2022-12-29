package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.conflict.ConflictCommand;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.DumpCommands;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.HandCommands;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.HelpCommand;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.InventoryCommands;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.MiscCommands;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.RecipeCommands;
import com.blamejared.crafttweaker.impl.commands.crafttweaker.ScriptCommands;
import com.blamejared.crafttweaker.impl.commands.script_examples.ExamplesCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class CTCommands {
    
    private static final Map<String, com.blamejared.crafttweaker.impl.commands.CommandImpl> COMMANDS = new TreeMap<>(String::compareTo);
    public static LiteralArgumentBuilder<CommandSource> root = Commands.literal("ct");
    public static LiteralArgumentBuilder<CommandSource> rootAlternative = Commands.literal("crafttweaker");
    
    public static void initArgumentTypes() {
        
        ArgumentTypes.register("crafttweaker:item_argument", CTItemArgument.class, new ArgumentSerializer<>(() -> CTItemArgument.INSTANCE));
        ArgumentTypes.register("crafttweaker:recipe_type_argument", CTRecipeTypeArgument.class, new ArgumentSerializer<>(() -> CTRecipeTypeArgument.INSTANCE));
    }
    
    public static void init(CommandDispatcher<CommandSource> dispatcher) {
        
        ConflictCommand.registerConflictCommands(CTCommands::registerCustomCommand);
        DumpCommands.registerDumpCommands(() -> COMMANDS);
        ExamplesCommand.register();
        HandCommands.registerHandCommands();
        InventoryCommands.registerInventoryCommands();
        MiscCommands.registerMiscCommands(CTCommands::registerCustomCommand);
        RecipeCommands.registerRecipeCommands();
        ScriptCommands.registerScriptCommands();
        
        DumpCommands.registerDumpers();
        
        // Send an event to let others know that we are ready for SubCommands to be registered.
        // SubCommands registered earlier would throw a NPE (because the command itself would not exist yet)
        // SubCommands registered later would not be added to the Command system
        MinecraftForge.EVENT_BUS.post(new CTCommandCollectionEvent());
        
        // We'll add the dump subcommands after the event to find conflicts.
        final com.blamejared.crafttweaker.impl.commands.CommandImpl dump = COMMANDS.get("dump");
        for(BracketDumperInfo dumperInfo : CraftTweakerRegistry.getBracketDumpers().values()) {
            final String subCommandName = dumperInfo.getSubCommandName();
            
            // This means that a mod used .registerDump on a name for which a @BracketDumper exists as well
            // Let's log a warning because then the .registerDump will win
            if(dump.getChildCommands().containsKey(subCommandName)) {
                CraftTweakerAPI.logWarning("Found both an explicit Dumping command and a BracketDumper annotation for the name ' %s '. This is a (non-fatal) mod issue!", subCommandName);
            } else {
                registerDump(subCommandName, dumperInfo.getDescription(), dumperInfo);
            }
        }
        
        HelpCommand.registerHelpCommand(() -> COMMANDS, CTCommands::registerCustomCommand);
        
        COMMANDS.forEach((s, command) -> registerCommandInternal(root, command));
        COMMANDS.forEach((s, command) -> registerCommandInternal(rootAlternative, command));
        
        
        LiteralCommandNode<CommandSource> rootNode = dispatcher.register(root);
        LiteralCommandNode<CommandSource> rootAltNode = dispatcher.register(rootAlternative);
        
        /*
         * For anyone about to make a PR adding /minetweaker or /mt aliases, keep in mind:
         * for all intents and purposes, CraftTweaker is no longer MineTweaker, things have changed, scripts are not 1:1 with previous versions.
         * Not adding these aliases is a simple way to say "Don't expect previous things to work".
         * Saying that, feel free to try and convince me to add the aliases, if you can give a good argument for them, I may add them back.
         */
    }
    
    public static com.blamejared.crafttweaker.impl.commands.CommandImpl command(String name, String desc, com.blamejared.crafttweaker.impl.commands.CommandCaller caller) {
        
        return new com.blamejared.crafttweaker.impl.commands.CommandImpl(name, desc, caller);
    }
    
    public static com.blamejared.crafttweaker.impl.commands.CommandImpl playerCommand(String name, String desc, com.blamejared.crafttweaker.impl.commands.CommandCallerPlayer playerCaller) {
        
        return command(name, desc, playerCaller);
    }
    
    public static void registerPlayerDump(String name, String desc, com.blamejared.crafttweaker.impl.commands.CommandCallerPlayer playerCaller) {
        
        registerDump(name, desc, playerCaller);
    }
    
    public static void registerDump(String name, String desc, com.blamejared.crafttweaker.impl.commands.CommandCaller caller) {
        
        registerCommand("dump", new com.blamejared.crafttweaker.impl.commands.CommandImpl(name, desc, caller));
    }
    
    public static void registerCommand(com.blamejared.crafttweaker.impl.commands.CommandImpl command) {
        
        COMMANDS.put(command.getName(), command);
    }
    
    public static void registerCommand(String command, com.blamejared.crafttweaker.impl.commands.CommandImpl subCommand) {
        
        COMMANDS.get(command).getChildCommands().put(subCommand.getName(), subCommand);
    }
    
    public static void registerCommand(com.blamejared.crafttweaker.impl.commands.CommandImpl command, com.blamejared.crafttweaker.impl.commands.CommandImpl subCommand) {
        
        command.getChildCommands().put(subCommand.getName(), subCommand);
    }
    
    private static void registerCommandInternal(LiteralArgumentBuilder<CommandSource> root, com.blamejared.crafttweaker.impl.commands.CommandImpl command) {
        
        if (command.getCaller() == null) return;
        
        LiteralArgumentBuilder<CommandSource> literalCommand = Commands.literal(command.getName());
        final Map<String, com.blamejared.crafttweaker.impl.commands.CommandImpl> subCommands = command.getChildCommands();
        if(!subCommands.isEmpty()) {
            subCommands.forEach((name, subCommand) -> registerCommandInternal(literalCommand, subCommand));
        }
        root.then(literalCommand.requires(command.getRequirement()).executes(command.getCaller()::executeCommand));
    }
    
    private static void registerCustomCommand(LiteralArgumentBuilder<CommandSource> literal) {
        
        root.then(literal);
        rootAlternative.then(literal);
    }
    
    private static void registerCustomCommand(LiteralArgumentBuilder<CommandSource> literal, String name, String description) {
        
        registerCustomCommand(literal);
        if(name != null && description != null) {
            COMMANDS.put(name, new CommandImpl(name, description, null));
        }
    }
    
    //<editor-fold desc="Deprecation Central">
    @Deprecated
    private static void send(ITextComponent component, CommandSource source) {
        
        CommandUtilities.send(component, source);
    }
    
    @Deprecated
    static void send(ITextComponent component, PlayerEntity player) {
        
        CommandUtilities.send(component, player);
    }
    
    @Deprecated
    static String color(String str, TextFormatting formatting) {
        
        return CommandUtilities.color(str, formatting);
    }
    
    @Deprecated
    private static String stripNewLine(String string) {
        
        return CommandUtilities.stripNewLine(string);
    }
    
    @Deprecated
    private static String stripNewLine(StringBuilder string) {
        
        return CommandUtilities.stripNewLine(string);
    }
    
    @Deprecated
    public static ITextComponent copy(TextComponent base, String toCopy) {
        
        return CommandUtilities.copy(base, toCopy);
    }
    
    @Deprecated
    public static ITextComponent open(TextComponent base, String path) {
        
        return CommandUtilities.open(base, path);
    }
    
    @Deprecated
    public static TextComponent run(TextComponent base, String command) {
        
        return CommandUtilities.run(base, command);
    }
    
    @Deprecated
    public static void registerDump(String name, String desc, CommandCaller caller) {
        
        registerDump(name, desc, (com.blamejared.crafttweaker.impl.commands.CommandCaller) caller);
    }
    
    @Deprecated
    public static void registerCommand(CommandImpl command) {
        
        registerCommand((com.blamejared.crafttweaker.impl.commands.CommandImpl) command);
    }
    
    @Deprecated
    public static void registerCommand(String command, CommandImpl subCommand) {
        
        registerCommand(command, (com.blamejared.crafttweaker.impl.commands.CommandImpl) subCommand);
    }
    
    @Deprecated
    public static void registerCommand(CommandImpl command, CommandImpl subCommand) {
        
        registerCommand(command, (com.blamejared.crafttweaker.impl.commands.CommandImpl) subCommand);
    }
    
    @Deprecated
    public interface CommandCaller extends com.blamejared.crafttweaker.impl.commands.CommandCaller {}
    
    @Deprecated
    public interface CommandCallerPlayer extends com.blamejared.crafttweaker.impl.commands.CommandCallerPlayer, CommandCaller {}
    
    @Deprecated
    public static class CommandImpl extends com.blamejared.crafttweaker.impl.commands.CommandImpl {
        
        @Deprecated
        public CommandImpl(String name, String description, CommandCaller caller) {
            
            super(name, description, caller);
        }
        
        @Deprecated
        public CommandCaller getCaller() {
            
            final com.blamejared.crafttweaker.impl.commands.CommandCaller caller = super.getCaller();
            return (caller == null || caller instanceof CommandCaller)? (CommandCaller) caller : caller::executeCommand;
        }
        
        @Deprecated
        public void registerSubCommand(CommandImpl subCommand) {
            
            super.registerSubCommand(subCommand);
        }
        
        @Deprecated
        public Map<String, CommandImpl> getSubCommands() {
            
            return super.getChildCommands().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, it -> this.safeCast(it.getValue())));
        }
        
        private CommandImpl safeCast(final com.blamejared.crafttweaker.impl.commands.CommandImpl other) {
            
            return other instanceof CommandImpl ? (CommandImpl) other : new CommandImpl(other.getName(), other.getDescription(), other.getCaller()::executeCommand);
        }
        
    }
    // </editor-fold>
}
