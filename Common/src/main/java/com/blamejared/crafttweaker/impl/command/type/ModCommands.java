package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public final class ModCommands {
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "mods",
                new TranslatableComponent("crafttweaker.command.description.mods"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    Services.PLATFORM.getMods()
                            .forEach(mod -> CraftTweakerAPI.LOGGER.info("- {}({})@{}", mod.displayName(), mod.id(), mod.version()));
                    
                    CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.mods")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
}
