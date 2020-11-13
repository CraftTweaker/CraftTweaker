package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.text.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.network.*;
import com.blamejared.crafttweaker.impl.network.messages.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.mojang.brigadier.*;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.*;
import com.mojang.brigadier.context.*;
import com.mojang.brigadier.exceptions.*;
import com.mojang.brigadier.tree.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.network.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@SuppressWarnings("unused")
public class CTCommands {
    
    private static final Map<String, CommandImpl> COMMANDS = new TreeMap<>(String::compareTo);
    public static LiteralArgumentBuilder<CommandSource> root = Commands.literal("ct");
    private static final int commandsPerPage = 4;
    
    public static void init(CommandDispatcher<CommandSource> dispatcher) {
        root.then(Commands.literal("copy")
                .then(Commands.argument("toCopy", StringArgumentType.string()).executes(context -> {
                    String toCopy = context.getArgument("toCopy", String.class);
                    ServerPlayerEntity entity = context.getSource().asPlayer();
                    PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> entity), new MessageCopy(toCopy));
                    send(new StringTextComponent("Copied!"), entity);
                    return 0;
                })));
        
        registerCommand(new CommandImpl("hand", "Outputs the name and tags (if any) of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            
            String string = new MCItemStackMutable(stack).getCommandString();
            ITextComponent copy = copy(new FormattedTextComponent("Item: %s", color(string, TextFormatting.GREEN)), string);
            send(copy, player);
            if(player instanceof ServerPlayerEntity) {
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(string));
            }
            Collection<ResourceLocation> tags = ItemTags.getCollection()
                    .getOwningTags(stack.getItem());
            if(tags.isEmpty()) {
                return 0;
            }
            
            send(copy(new FormattedTextComponent(color("Tag Entries", TextFormatting.DARK_AQUA)), tags
                    .stream()
                    .map(ResourceLocation::toString)
                    .collect(Collectors.joining(", "))), player);
            
            tags.stream()
                    .map(resourceLocation -> new MCTag(resourceLocation).getCommandString())
                    .forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(commandString, TextFormatting.AQUA)), commandString), player));
            
            return 0;
        }));
        registerCommand("hand", new CommandImpl("registryName", "Outputs the registry name of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            //It cannot be null because we can't get unregistered items here
            //noinspection ConstantConditions
            String registryName = stack.getItem().getRegistryName().toString();
            ITextComponent copy = copy(new FormattedTextComponent("Item: %s", color(registryName, TextFormatting.GREEN)), registryName);
            send(copy, player);
            if(player instanceof ServerPlayerEntity) {
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(registryName));
            }
            return 0;
        }));
        registerCommand("hand", new CommandImpl("tags", "Outputs the tags of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            
            Collection<ResourceLocation> tags = ItemTags.getCollection()
                    .getOwningTags(stack.getItem());
            if(tags.isEmpty()) {
                send(new StringTextComponent("Item has no tags"), player);
                return 0;
            }
            
            
            send(copy(new FormattedTextComponent(color("Tag Entries", TextFormatting.DARK_AQUA)), tags
                    .stream()
                    .map(ResourceLocation::toString)
                    .collect(Collectors.joining(", "))), player);
            
            tags.stream()
                    .map(resourceLocation -> new MCTag(resourceLocation).getCommandString())
                    .forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(commandString, TextFormatting.AQUA)), commandString), player));
            
            if(player instanceof ServerPlayerEntity) {
                //noinspection OptionalGetWithoutIsPresent
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(tags
                        .stream()
                        .map(MCTag::new)
                        .findFirst()
                        .get()
                        .getCommandString()));
            }
            return 0;
        }));
        
        registerCommand(new CommandImpl("inventory", "Outputs the names of the item in your inventory", (CommandCallerPlayer) (player, stack) -> {
            StringBuilder builder = new StringBuilder("Inventory items").append("\n");
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if(slot.isEmpty()) {
                    continue;
                }
                builder.append(new MCItemStackMutable(slot).getCommandString()).append("\n");
            }
            CraftTweakerAPI.logDump(builder.toString());
            send(new StringTextComponent(color("Inventory list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        }));
        
        registerCommand("inventory", new CommandImpl("tags", "Outputs the tags of the item in your inventory", (CommandCallerPlayer) (player, stack) -> {
            StringBuilder builder = new StringBuilder("Inventory item tags").append("\n");
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if(slot.isEmpty()) {
                    continue;
                }
                builder.append(new MCItemStackMutable(slot).getCommandString()).append("\n");
                
                Collection<ResourceLocation> tags = ItemTags.getCollection()
                        .getOwningTags(slot.getItem());
                if(tags.isEmpty()) {
                    builder.append("- No tags").append("\n");
                    continue;
                }
                tags.stream()
                        .map(resourceLocation -> new MCTag(resourceLocation).getCommandString())
                        .forEach(s -> builder.append("-").append(s).append("\n"));
            }
            CraftTweakerAPI.logDump(builder.toString());
            send(new StringTextComponent(color("Inventory tag list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        }));
        
        registerCommand(new CommandImpl("log", "Opens the log file", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen(new File("logs/crafttweaker.log")
                    .toURI()
                    .toString()));
            return 0;
        }));
        registerCommand(new CommandImpl("scripts", "Opens the scripts folder", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen(new File("scripts/")
                    .toURI()
                    .toString()));
            return 0;
        }));
        
        registerCommand(new CommandImpl("syntax", "Checks the syntax of the scripts", (CommandCallerPlayer) (player, stack) -> {
            //TODO: get loader name from '/ct syntax loaderName'?
            //final String loaderName = CraftTweakerAPI.getDefaultLoaderName();
            //CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().setLoaderName(loaderName));
            CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().setWildcardLoaderName());
            return 0;
        }));
        
        registerCommand(new CommandImpl("format", "Checks the syntax of the scripts and formats them into another folder.", (CommandCallerPlayer) (player, stack) -> {
            CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().setWildcardLoaderName()
                    .format());
            return 0;
        }));
        
        
        registerCommand(new CommandImpl("dumpBrackets", "Dumps available Bracket Expressions into the /ct_dumps folder", source -> {
            final File folder = new File("ct_dumps");
            if(!folder.exists() && !folder.mkdir()) {
                CraftTweakerAPI.logError("Could not create output folder %s", folder);
            }
            
            CraftTweakerRegistry.getBracketDumpers().forEach((name, dumpSupplier) -> {
                final String dumpedFileName = dumpSupplier.getDumpedFileName() + ".txt";
                try(final PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, dumpedFileName), false))) {
                    dumpSupplier.getDumpedValuesStream().sorted().forEach(writer::println);
                } catch(IOException e) {
                    CraftTweakerAPI.logThrowing("Error writing to file '%s'", e, dumpedFileName);
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
            COMMANDS.get("dump")
                    .getSubCommands()
                    .forEach((s, command) -> send(run(new FormattedTextComponent("- " + color(s, TextFormatting.GREEN)), "/ct dump " + s), player));
            return 0;
        }));
        
        
        registerDump("recipes", "Outputs the names of all registered recipes", (CommandCallerPlayer) (player, stack) -> {
            for(IRecipeType<?> type : Registry.RECIPE_TYPE) {
                CraftTweakerAPI.logDump(type.toString());
                for(ResourceLocation location : player.world.getRecipeManager().recipes.getOrDefault(type, new HashMap<>())
                        .keySet()) {
                    CraftTweakerAPI.logDump("- " + location.toString());
                }
            }
            send(new StringTextComponent(color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        });
        
        registerDump("tags", "Outputs the names of all registered tags (vanilla tag types)", (CommandCallerPlayer) (player, stack) -> {
            CraftTweakerAPI.logDump("Item Tags:\n");
            ItemTags.getCollection()
                    .getIDTagMap()
                    .keySet()
                    .forEach(resourceLocation -> CraftTweakerAPI.logDump("-" + resourceLocation.toString()));
            
            CraftTweakerAPI.logDump("Block Tags:\n");
            BlockTags.getCollection()
                    .getIDTagMap()
                    .keySet()
                    .forEach(resourceLocation -> CraftTweakerAPI.logDump("-" + resourceLocation.toString()));
            
            CraftTweakerAPI.logDump("Fluid Tags:\n");
            FluidTags.getCollection()
                    .getIDTagMap()
                    .keySet()
                    .forEach(resourceLocation -> CraftTweakerAPI.logDump("-" + resourceLocation.toString()));
            
            CraftTweakerAPI.logDump("Entity Type Tags:\n");
            EntityTypeTags.getCollection()
                    .getIDTagMap()
                    .keySet()
                    .forEach(resourceLocation -> CraftTweakerAPI.logDump("-" + resourceLocation.toString()));
            
            send(new StringTextComponent(color("Tag list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        });
        
        
        // Send an event to let others know that we are ready for SubCommands to be registered.
        // SubCommands registered earlier would throw a NPE (because the command itself would not exist yet)
        // SubCommands registered later would not be added to the Command system
        MinecraftForge.EVENT_BUS.post(new CTCommandCollectionEvent());
        
        // We'll add the dump subcommands after the event to find conflicts.
        final CommandImpl dump = COMMANDS.get("dump");
        for(BracketDumperInfo dumperInfo : CraftTweakerRegistry.getBracketDumpers().values()) {
            final String subCommandName = dumperInfo.getSubCommandName();
            
            // This means that a mod used .registerDump on a name for which a @BracketDumper exists as well
            // Let's log a warning because then the .registerDump will win
            if(dump.getSubCommands().containsKey(subCommandName)) {
                CraftTweakerAPI.logWarning("Found both an explicit Dumping command and a BracketDumper annotation for the name ' %s '. This is a (non-fatal) mod issue!", subCommandName);
            } else {
                registerDump(subCommandName, dumperInfo.getDescription(), dumperInfo);
            }
        }
    
        root.then(Commands.literal("help")
                .executes(context -> executeHelp(context, 1))
                .then(Commands.argument("page", IntegerArgumentType.integer(1, (COMMANDS.size() / commandsPerPage) + 1))
                        .executes(context -> executeHelp(context, context.getArgument("page", int.class)))));
        
        
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
        LiteralArgumentBuilder<CommandSource> literalCommand = Commands.literal(command.getName());
        final Map<String, CommandImpl> subCommands = command.getSubCommands();
        if(!subCommands.isEmpty()) {
            subCommands.forEach((name, subCommand) -> registerCommandInternal(literalCommand, subCommand));
        }
        root.then(literalCommand.executes(command.getCaller()::executeCommand));
    }
    
    
    //helpPageNumber is 1 based, so we reduce it by 1 in clamping
    private static int executeHelp(CommandContext<CommandSource> context, int helpPageNumber) {
        final CommandSource source = context.getSource();
        final List<String> keys = new ArrayList<>(COMMANDS.keySet());
        
        final int highestPageIndex = (keys.size() / commandsPerPage);
        
        //The page we are on (0 based)
        //helpPageNumber is 1 based, so we reduce it by 1 in clamping
        final int shownPageIndex = MathHelper.clamp(helpPageNumber - 1, 0, highestPageIndex);
        
        //The range of commands we show on this page, end exclusive
        final int startCommandIndex = shownPageIndex * commandsPerPage;
        final int endCommandIndex = Math.min(startCommandIndex + commandsPerPage, keys.size());
        
        //Actually show the commands
        for(int i = startCommandIndex; i < endCommandIndex; i++) {
            final CommandImpl command = COMMANDS.get(keys.get(i));
            
            final FormattedTextComponent message = new FormattedTextComponent("/ct %s", command.getName());
            source.sendFeedback(run(message, message.getUnformattedComponentText()), true);
            source.sendFeedback(new FormattedTextComponent("- %s", color(command.getDescription(), TextFormatting.DARK_AQUA)), true);
        }
        
        //Which page are we on?
        //We show it 1 based again, so we add 1 to the pages
        source.sendFeedback(new FormattedTextComponent("Page %s of %d", shownPageIndex + 1, highestPageIndex + 1), true);
        return 0;
    }
    
    
    private static void send(ITextComponent component, CommandSource source) {
        source.sendFeedback(component, true);
        CraftTweakerAPI.logDump(component.getString());
    }
    
    static void send(ITextComponent component, PlayerEntity player) {
        player.sendMessage(component, CraftTweaker.CRAFTTWEAKER_UUID);
        CraftTweakerAPI.logDump(component.getUnformattedComponentText());
    }
    
    static String color(String str, TextFormatting formatting) {
        return formatting + str + TextFormatting.RESET;
    }
    
    private static String stripNewLine(String string) {
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    private static String stripNewLine(StringBuilder string) {
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static ITextComponent copy(TextComponent base, String toCopy) {
        Style style = base.getStyle();
        style = style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to copy [%s]", color(toCopy, TextFormatting.GOLD))));
        style = style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ct copy " + quoteAndEscape(toCopy) + ""));
        return base.setStyle(style);
    }
    
    public static ITextComponent open(TextComponent base, String path) {
        Style style = base.getStyle();
        style = style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to open [%s]", color(path, TextFormatting.GOLD))));
        style = style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
        return base.setStyle(style);
    }
    
    public static TextComponent run(TextComponent base, String command) {
        Style style = Style.EMPTY;
        style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to run [%s]", color(command, TextFormatting.GOLD))));
        style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        base.setStyle(style);
        
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
    
    public interface CommandCaller {
        
        int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException;
        
    }
    
    
    public interface CommandCallerPlayer extends CommandCaller {
        
        default int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException {
            return executeCommand(context.getSource().asPlayer(), context.getSource()
                    .asPlayer()
                    .getHeldItemMainhand());
        }
        
        int executeCommand(PlayerEntity player, ItemStack stack);
        
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
}
