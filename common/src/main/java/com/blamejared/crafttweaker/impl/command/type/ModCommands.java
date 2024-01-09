package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public final class ModCommands {
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "mods",
                Component.translatable("crafttweaker.command.description.mods"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    Services.PLATFORM.getMods()
                            .forEach(mod -> CommandUtilities.COMMAND_LOGGER.info("- {}({})@{}", mod.displayName(), mod.id(), mod.version()));
                    
                    CommandUtilities.send(source, CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.mods")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN)));
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
}
