package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.impl.command.CommandImpl;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.Map;

public final class HelpCommand {
    
    private static final int COMMANDS_PER_PAGE = 4;
    
    private HelpCommand() {}
    
    public static void registerCommandIfRequired(final CtCommands instance) {
        
        final Map<String, CommandImpl> commands = instance.commands();
        
        if(commands.containsKey("help")) {
            return;
        }
        
        instance.registerCommand(
                "help",
                Component.translatable("crafttweaker.command.description.help"),
                builder -> {
                    builder.executes(context -> executeHelp(commands, context, 1));
                    builder.then(Commands.argument("page", IntegerArgumentType.integer(1))
                            .executes(context -> executeHelp(commands, context, context.getArgument("page", int.class))));
                }
        );
    }
    
    //helpPageNumber is 1 based, so we reduce it by 1 in clamping
    private static int executeHelp(final Map<String, CommandImpl> commands, final CommandContext<CommandSourceStack> context, int currentPage) throws CommandSyntaxException {
        
        final CommandSourceStack source = context.getSource();
        
        final List<String> allowedToUseCommands = commands.keySet()
                .stream()
                .filter(s -> commands.get(s).requirement().test(source))
                .toList();
        
        final int maxPages = (int) Math.ceil(((double) allowedToUseCommands.size()) / COMMANDS_PER_PAGE);
        
        int clampedPage = Mth.clamp(currentPage, 1, maxPages);
        
        int minIndex = (clampedPage - 1) * COMMANDS_PER_PAGE;
        
        int maxIndex = Math.min(commands.size(), minIndex + COMMANDS_PER_PAGE);
        
        for(int i = minIndex; i < maxIndex; i++) {
            final CommandImpl command = commands.get(allowedToUseCommands.get(i));
            
            String commandStr = "/ct " + command.name();
            source.sendSuccess(CommandUtilities.run(Component.literal(commandStr), commandStr), true);
            source.sendSuccess(Component.literal("- ").append(command.description()
                    .withStyle(ChatFormatting.DARK_AQUA)), true);
        }
        
        source.sendSuccess(Component.translatable("crafttweaker.command.help.page.info", clampedPage, maxPages), true);
        return Command.SINGLE_SUCCESS;
    }
    
}
