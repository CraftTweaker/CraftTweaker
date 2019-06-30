package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CTCommands {
    
    public static LiteralArgumentBuilder<CommandSource> root;
    
    private static final List<CommandImpl> COMMANDS = new ArrayList<>();
    
    public static void init(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> root = Commands.literal("ct");
        
        registerCommand(new CommandImpl("hand", (CommandCallerPlayer) (player, stack) -> {
            StringBuilder builder = new StringBuilder();
            Collection<ResourceLocation> tags = ItemTags.getCollection().getOwningTags(stack.getItem());
            tags.forEach(resourceLocation -> builder.append(color("\t- ", TextFormatting.YELLOW)).append(color(resourceLocation.toString(), TextFormatting.AQUA)).append("\n"));
            FormattedTextComponent text = new FormattedTextComponent("Item: %s\n%s:\n%s", color(new MCItemStackMutable(stack).getCommandString(), TextFormatting.GREEN), color("Tag Entries", TextFormatting.DARK_AQUA), stripNewLine(builder));
            send(text, player);
            return 0;
        }));
        //TODO maybe post an event to collect sub commands from other addons?
        
        COMMANDS.forEach(command -> root.then(Commands.literal(command.getName()).executes(context -> command.getCaller().executeCommand(context))));
        
        
        LiteralCommandNode<CommandSource> rootNode = dispatcher.register(root);
        dispatcher.register(Commands.literal("crafttweaker").redirect(rootNode));
        
        /*
         * For anyone about to make a PR adding /minetweaker or /mt aliases, keep in mind:
         * for all intents and purposes, CraftTweaker is no longer MineTweaker, things have changed, scripts are not 1:1 with previous versions.
         * Not adding these aliases is a simple way to say "Don't expect previous things to work".
         * Saying that, feel free to try and convince me to add the aliases, if you can give a good argument for them, I may add them back.
         */
    }
    
    public static void registerCommand(CommandImpl command) {
        COMMANDS.add(command);
    }
    
    
    private static void send(TextComponent component, CommandSource source) {
        source.sendFeedback(component, true);
        CraftTweakerAPI.logInfo(component.getFormattedText());
    }
    
    private static void send(TextComponent component, PlayerEntity player) {
        player.sendMessage(component);
        CraftTweakerAPI.logInfo(component.getFormattedText());
    }
    
    public static class CommandImpl {
        
        private final String name;
        private final CommandCaller caller;
        
        public CommandImpl(String name, CommandCaller caller) {
            this.name = name;
            this.caller = caller;
        }
        
        public String getName() {
            return name;
        }
        
        public CommandCaller getCaller() {
            return caller;
        }
    }
    
    public static interface CommandCaller {
        
        int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException;
    }
    
    public static interface CommandCallerPlayer extends CommandCaller {
        
        default int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException {
            return executeCommand(context.getSource().asPlayer(), context.getSource().asPlayer().getHeldItemMainhand());
        }
        
        int executeCommand(PlayerEntity player, ItemStack stack);
        
    }
    
    private static String color(String str, TextFormatting formatting) {
        return formatting + str + TextFormatting.RESET;
    }
    
    private static String stripNewLine(String string) {
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    private static String stripNewLine(StringBuilder string) {
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
}
