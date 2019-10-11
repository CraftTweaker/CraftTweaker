package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.network.messages.MessageCopy;
import com.blamejared.crafttweaker.impl.network.messages.MessageOpen;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class CTCommands {
    
    public static LiteralArgumentBuilder<CommandSource> root = Commands.literal("ct");
    
    private static final Map<String, CommandImpl> COMMANDS = new TreeMap<>(String::compareTo);
    
    
    public static void init(CommandDispatcher<CommandSource> dispatcher) {
        root.then(Commands.literal("copy").then(Commands.argument("toCopy", StringReader::readString).executes(context -> {
            String toCopy = context.getArgument("toCopy", String.class);
            ServerPlayerEntity entity = context.getSource().asPlayer();
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> entity), new MessageCopy(toCopy));
            send(new StringTextComponent("Copied!"), entity);
            return 0;
        })));
        
        registerCommand(new CommandImpl("hand", "Outputs the name and tags (if any) of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            
            String string = new MCItemStackMutable(stack).getCommandString();
            TextComponent copy = copy(new FormattedTextComponent("Item: %s", color(string, TextFormatting.GREEN)), string);
            send(copy, player);
            if(player instanceof ServerPlayerEntity) {
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(string));
            }
            Collection<ResourceLocation> tags = ItemTags.getCollection().getOwningTags(stack.getItem());
            if(tags.isEmpty()) {
                return 0;
            }
            send(copy(new FormattedTextComponent(color("Tag Entries", TextFormatting.DARK_AQUA)), tags.stream().map(ResourceLocation::toString).collect(Collectors.joining(", "))), player);
            
            tags.stream().map(resourceLocation -> new MCTag(resourceLocation).getCommandString()).forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(commandString, TextFormatting.AQUA)), commandString), player));
            
            return 0;
        }));
        registerCommand(new CommandImpl("log", "Opens the log file", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen(new File("logs/crafttweaker.log").toURI().toString()));
            return 0;
        }));
        registerCommand(new CommandImpl("scripts", "Opens the scripts folder", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen(new File("scripts/").toURI().toString()));
            return 0;
        }));
        

        registerCommand(new CommandImpl("dumpBrackets", "Dumps available Bracket Expressions into the /ct_dumps folder", source -> {
            final File folder = new File("ct_dumps");
            if(!folder.exists() && !folder.mkdir()) {
                CraftTweakerAPI.logError("Could not create output folder %s", folder);
            }

            CraftTweakerRegistry.getBracketDumpers().forEach((name, dumpSupplier) -> {
                try(final PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, name + ".txt"), false))) {
                    dumpSupplier.get().forEach(writer::println);
                } catch(IOException e) {
                    CraftTweakerAPI.logThrowing("Error writing to file '%s.txt'", e, name);
                }
            });

            send(new StringTextComponent("Files Created"), source.getSource());

            return 0;
        }));


        registerCommand(new CommandImpl("discord", "Opens a link to discord", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://discord.blamejared.com"));
            return 0;
        }));

        registerCommand(new CommandImpl("issues", "Opens a link to the issue tracker", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://github.com/CraftTweaker/CraftTweaker/issues"));
            return 0;
        }));

        registerCommand(new CommandImpl("patreon", "Opens a link to patreon", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://patreon.com/jaredlll08"));
            return 0;
        }));


        registerCommand(new CommandImpl("dump", "Dumps available sub commands for the dump command", (CommandCallerPlayer) (player, stack) -> {
            send(new StringTextComponent("Dump types: "), player);
            COMMANDS.get("dump").getSubCommands().forEach((s, command) -> send(run(new StringTextComponent("- " + color(s, TextFormatting.GREEN)), "/ct dump " + s), player));
            return 0;
        }));
        
        
        registerDump("recipes", "Outputs the names of all registered recipes", (CommandCallerPlayer) (player, stack) -> {
            for(IRecipeType<?> type : Registry.RECIPE_TYPE) {
                CraftTweakerAPI.logInfo(type.toString());
                for(ResourceLocation location : player.world.getRecipeManager().recipes.get(type).keySet()) {
                    CraftTweakerAPI.logInfo("- " + location.toString());
                }
            }
            send(new StringTextComponent(color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        });
        
        registerDump("recipeTypes", "Outputs the names of all Recipe Types", (CommandCallerPlayer) (player, stack) -> {
            for(IRecipeType<?> type : Registry.RECIPE_TYPE) {
                CraftTweakerAPI.logInfo("- " + new ResourceLocation(type.toString()).toString());
            }
            send(new StringTextComponent(color("Recipe Type list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        });
        
        //        registerCommand(new CommandImpl("help", (CommandCallerPlayer) (player, stack) -> {
        //            StringBuilder builder = new StringBuilder();
        //            ItemTags.getCollection().getOwningTags(stack.getItem()).forEach(resourceLocation -> builder.append(color("\t- ", TextFormatting.YELLOW)).append(color(resourceLocation.toString(), TextFormatting.AQUA)).append("\n"));
        //            FormattedTextComponent text = new FormattedTextComponent("Item: %s\n%s:\n%s", color(new MCItemStackMutable(stack).getCommandString(), TextFormatting.GREEN), color("Tag Entries", TextFormatting.DARK_AQUA), stripNewLine(builder));
        //            send(text, player);
        //            return 0;
        //        }));
        //TODO maybe post an event to collect sub commands from other addons?
        root.then(Commands.literal("help").executes(context -> executeHelp(context, 0)).then(Commands.argument("page", StringReader::readInt).executes(context -> executeHelp(context, context.getArgument("page", int.class)))));
        
        COMMANDS.forEach((s, command) -> registerCommandInternal(root, command));
        LiteralCommandNode<CommandSource> rootNode = dispatcher.register(root);
        dispatcher.register(Commands.literal("crafttweaker").redirect(rootNode));
        
        /*
         * For anyone about to make a PR adding /minetweaker or /mt aliases, keep in mind:
         * for all intents and purposes, CraftTweaker is no longer MineTweaker, things have changed, scripts are not 1:1 with previous versions.
         * Not adding these aliases is a simple way to say "Don't expect previous things to work".
         * Saying that, feel free to try and convince me to add the aliases, if you can give a good argument for them, I may add them back.
         */
    }
    
    
    public static void registerDump(String name, String desc, CommandCaller caller) {
        registerCommand("dump", new CommandImpl(name, desc, caller));
    }
    
    public static void registerCommand(CommandImpl command) {
        COMMANDS.put(command.getName(), command);
    }
    
    public static void registerCommand(String command, CommandImpl subCommand) {
        COMMANDS.get(command).getSubCommands().put(subCommand.getName(), subCommand);
    }
    
    public static void registerCommand(CommandImpl command, CommandImpl subCommand) {
        command.getSubCommands().put(subCommand.getName(), subCommand);
    }
    
    private static void registerCommandInternal(LiteralArgumentBuilder<CommandSource> root, CommandImpl command) {
        LiteralArgumentBuilder<CommandSource> litCommand = Commands.literal(command.getName());
        if(!command.getSubCommands().isEmpty()) {
            command.getSubCommands().forEach((s, command1) -> registerCommandInternal(litCommand, command1));
        }
        root.then(litCommand.executes(command.getCaller()::executeCommand));
        
    }
    
    
    private static int executeHelp(CommandContext<CommandSource> context, int helpPage) {
        double commandsPerPage = 4;
        List<String> keys = new ArrayList<>(COMMANDS.keySet());
        int page = (int) MathHelper.clamp(helpPage, 0, Math.ceil(keys.size() / commandsPerPage) - 1);
        for(int i = (int) (page * commandsPerPage); i < Math.min((page * commandsPerPage) + commandsPerPage, keys.size()); i++) {
            FormattedTextComponent message = new FormattedTextComponent("/ct %s", COMMANDS.get(keys.get(i)).getName());
            context.getSource().sendFeedback(run(message, message.getUnformattedComponentText()), true);
            context.getSource().sendFeedback(new FormattedTextComponent("- %s", color(COMMANDS.get(keys.get(i)).getDescription(), TextFormatting.DARK_AQUA)), true);
        }
        context.getSource().sendFeedback(new FormattedTextComponent("Page %s of %s", page, (int)Math.ceil(keys.size() / commandsPerPage) - 1), true);
        return 0;
    }
    
    
    private static void send(TextComponent component, CommandSource source) {
        source.sendFeedback(component, true);
        CraftTweakerAPI.logInfo(component.getFormattedText());
    }
    
    private static void send(TextComponent component, PlayerEntity player) {
        player.sendMessage(component);
        CraftTweakerAPI.logInfo(component.getFormattedText());
    }
    
    public static class CommandImpl implements Comparable<CommandImpl> {
        
        private final String name;
        private final String description;
        private final CommandCaller caller;
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
        
        public Map<String, CommandImpl> getSubCommands() {
            return subCommands;
        }
        
        @Override
        public int compareTo(CommandImpl o) {
            return getName().compareTo(o.getName());
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
    
    public static TextComponent copy(TextComponent base, String toCopy) {
        base.applyTextStyle(style -> {
            style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to copy [%s]", color(toCopy, TextFormatting.GOLD))));
            style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ct copy " + quoteAndEscape(toCopy) + ""));
        });
        
        return base;
    }
    
    public static TextComponent open(TextComponent base, String path) {
        base.applyTextStyle(style -> {
            style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to open [%s]", color(path, TextFormatting.GOLD))));
            style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
        });
        
        return base;
    }
    
    
    public static TextComponent run(TextComponent base, String command) {
        base.applyTextStyle(style -> {
            style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to run [%s]", color(command, TextFormatting.GOLD))));
            style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        });
        
        return base;
    }
    
    
    private static String quoteAndEscape(String p_193588_0_) {
        StringBuilder stringbuilder = new StringBuilder("\"");
        
        for(int i = 0; i < p_193588_0_.length(); ++i) {
            char c0 = p_193588_0_.charAt(i);
            
            if(c0 == '\\' || c0 == '"') {
                stringbuilder.append('\\');
            }
            
            stringbuilder.append(c0);
        }
        
        return stringbuilder.append('"').toString();
    }
}
