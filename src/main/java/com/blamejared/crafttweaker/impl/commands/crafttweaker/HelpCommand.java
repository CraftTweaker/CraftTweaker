package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.impl.commands.CommandImpl;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class HelpCommand {
    private static final int COMMANDS_PER_PAGE = 4;
    
    private HelpCommand() {}
    
    public static void registerHelpCommand(final Supplier<Map<String, CommandImpl>> commandsGetter, final Consumer<LiteralArgumentBuilder<CommandSource>> registerCustomCommand) {
        final Map<String, CommandImpl> commands = commandsGetter.get();
        registerCustomCommand.accept(Commands.literal("help")
                .executes(context -> executeHelp(commands, context, 1))
                .then(Commands.argument("page", IntegerArgumentType.integer(1, (commands.size() / COMMANDS_PER_PAGE) + 1))
                        .executes(context -> executeHelp(commands, context, context.getArgument("page", int.class)))));
    }
    
    //helpPageNumber is 1 based, so we reduce it by 1 in clamping
    private static int executeHelp(final Map<String, CommandImpl> commands, final CommandContext<CommandSource> context, int helpPageNumber) {
        
        final CommandSource source = context.getSource();
        final List<String> keys = new ArrayList<>(commands.keySet());
        
        final int highestPageIndex = (keys.size() / COMMANDS_PER_PAGE);
        
        //The page we are on (0 based)
        //helpPageNumber is 1 based, so we reduce it by 1 in clamping
        final int shownPageIndex = MathHelper.clamp(helpPageNumber - 1, 0, highestPageIndex);
        
        //The range of commands we show on this page, end exclusive
        final int startCommandIndex = shownPageIndex * COMMANDS_PER_PAGE;
        final int endCommandIndex = Math.min(startCommandIndex + COMMANDS_PER_PAGE, keys.size());
        
        //Actually show the commands
        for(int i = startCommandIndex; i < endCommandIndex; i++) {
            final com.blamejared.crafttweaker.impl.commands.CommandImpl command = commands.get(keys.get(i));
            
            final FormattedTextComponent message = new FormattedTextComponent("/ct %s", command.getName());
            source.sendFeedback(CommandUtilities.run(message, message.getUnformattedComponentText()), true);
            source.sendFeedback(new FormattedTextComponent("- %s", CommandUtilities.color(command.getDescription(), TextFormatting.DARK_AQUA)), true);
        }
        
        //Which page are we on?
        //We show it 1 based again, so we add 1 to the pages
        source.sendFeedback(new FormattedTextComponent("Page %s of %d", shownPageIndex + 1, highestPageIndex + 1), true);
        return 0;
    }
    
}
