package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.blamejared.crafttweaker.impl.command.CommandUtilities;
import com.blamejared.crafttweaker.impl.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public final class ModCommands {
    
    public static void registerCommands() {
        
        CTCommands.registerCommand(new CommandImpl("mods", new TranslatableComponent("crafttweaker.command.description.mods"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                Services.PLATFORM.getMods().forEach(mod -> {
                    CraftTweakerAPI.LOGGER.info("- {}({})@{}", mod.displayName(), mod.id(), mod.version());
                });
                
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.mods")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                
                return Command.SINGLE_SUCCESS;
            });
        }));
    }
    
}
