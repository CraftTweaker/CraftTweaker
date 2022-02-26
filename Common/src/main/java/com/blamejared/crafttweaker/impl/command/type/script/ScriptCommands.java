package com.blamejared.crafttweaker.impl.command.type.script;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public final class ScriptCommands {
    
    private ScriptCommands() {}
    
    public static void registerCommands() {
        
        CTCommands.registerCommand(new CommandImpl("log", new TranslatableComponent("crafttweaker.command.description.log"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CommandUtilities.open(player, Services.PLATFORM.getPathFromGameDirectory(CraftTweakerConstants.LOG_PATH));
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("scripts", new TranslatableComponent("crafttweaker.command.description.script"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CommandUtilities.open(player, CraftTweakerAPI.getScriptsDirectory());
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("syntax", new TranslatableComponent("crafttweaker.command.description.syntax"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                //TODO: get loader name from '/ct syntax loaderName'?
                // TODO: Use a custom load source?
                CraftTweakerAPI.getRegistry().getAllLoaders()
                        .stream()
                        .peek(loader -> CommandUtilities.send(new TranslatableComponent("crafttweaker.script.load.start", CommandUtilities.makeNoticeable(loader.name())), player))
                        .map(it -> new ScriptRunConfiguration(it, IScriptLoadSource.find(CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID), ScriptRunConfiguration.RunKind.SYNTAX_CHECK))
                        .map(it -> CraftTweakerAPI.getScriptRunManager().createScriptRun(it))
                        .forEach(it -> {
                            try {
                                it.execute();
                            } catch(final Throwable e) {
                                CraftTweakerAPI.LOGGER.error("Unable to check for syntax due to an error", e);
                            }
                        });
                
                return Command.SINGLE_SUCCESS;
            });
        }));
        
    }
    
}
