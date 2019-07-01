package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.network.messages.MessageCopy;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CTCommands {
    
    public static LiteralArgumentBuilder<CommandSource> root;
    
    private static final List<CommandImpl> COMMANDS = new ArrayList<>();
    
    
    public static void init(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> root = Commands.literal("ct");
        root.then(Commands.literal("copy").then(Commands.argument("toCopy", StringReader::readString).executes(context -> {
            String toCopy = context.getArgument("toCopy", String.class);
            ServerPlayerEntity entity = context.getSource().asPlayer();
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> entity), new MessageCopy(toCopy));
            return 0;
        })));
    
        
        registerCommand(new CommandImpl("hand", "Outputs the name and tags (if any) of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            
            String string = new MCItemStackMutable(stack).getCommandString();
            TextComponent copy = copy(new FormattedTextComponent("Item: %s", color(string, TextFormatting.GREEN)), string);
            send(copy, player);
            Collection<ResourceLocation> tags = ItemTags.getCollection().getOwningTags(stack.getItem());
            if(tags.isEmpty()) {
                return 0;
            }
            send(copy(new FormattedTextComponent(color("Tag Entries", TextFormatting.DARK_AQUA)), tags.stream().map(ResourceLocation::toString).collect(Collectors.joining(", "))), player);
            //TODO replace this with tag bep
            tags.forEach(resourceLocation -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(resourceLocation.toString(), TextFormatting.AQUA)), resourceLocation.toString()), player));
            
            return 0;
        }));
        
        
        //        registerCommand(new CommandImpl("help", (CommandCallerPlayer) (player, stack) -> {
        //            StringBuilder builder = new StringBuilder();
        //            ItemTags.getCollection().getOwningTags(stack.getItem()).forEach(resourceLocation -> builder.append(color("\t- ", TextFormatting.YELLOW)).append(color(resourceLocation.toString(), TextFormatting.AQUA)).append("\n"));
        //            FormattedTextComponent text = new FormattedTextComponent("Item: %s\n%s:\n%s", color(new MCItemStackMutable(stack).getCommandString(), TextFormatting.GREEN), color("Tag Entries", TextFormatting.DARK_AQUA), stripNewLine(builder));
        //            send(text, player);
        //            return 0;
        //        }));
        //TODO maybe post an event to collect sub commands from other addons?
        COMMANDS.sort(CommandImpl::compareTo);
        root.then(Commands.literal("help").executes(context -> executeHelp(context, 0)).then(Commands.argument("page", StringReader::readInt).executes(context -> executeHelp(context, context.getArgument("page", int.class)))));
        
        
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
    
    
    private static int executeHelp(CommandContext<CommandSource> context, int helpPage) {
        int commandsPerPage = 4;
        int page = MathHelper.clamp(helpPage, 0, (COMMANDS.size() / commandsPerPage) - 1);
        for(int i = page * commandsPerPage; i < Math.min((page * commandsPerPage) + commandsPerPage, COMMANDS.size()); i++) {
            FormattedTextComponent message = new FormattedTextComponent("/ct %s", COMMANDS.get(i).getName());
            context.getSource().sendFeedback(run(message, message.getUnformattedComponentText()), true);
            context.getSource().sendFeedback(new FormattedTextComponent("- %s", color(COMMANDS.get(i).getDescription(), TextFormatting.DARK_AQUA)), true);
        }
        context.getSource().sendFeedback(new FormattedTextComponent("Page %s of %s", page, (COMMANDS.size() / commandsPerPage) - 1), true);
        return 0;
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
    
    public static class CommandImpl implements Comparable<CommandImpl> {
        
        private final String name;
        private final String description;
        private final CommandCaller caller;
        
        public CommandImpl(String name, String description, CommandCaller caller) {
            this.name = name;
            this.description = description;
            this.caller = caller;
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
            style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ct copy \"" + toCopy + "\""));
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
}
