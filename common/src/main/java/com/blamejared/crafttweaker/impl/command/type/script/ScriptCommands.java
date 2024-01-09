package com.blamejared.crafttweaker.impl.command.type.script;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.util.PathUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.mojang.brigadier.Command;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public final class ScriptCommands {
    
    private ScriptCommands() {}
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "log",
                Component.translatable("crafttweaker.command.description.log"),
                builder -> builder.executes(context -> {
                    CommandUtilities.open(context.getSource(), PathUtil.makeRelativeToGameDirectory(CraftTweakerConstants.LOG_PATH));
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "scripts",
                Component.translatable("crafttweaker.command.description.script"),
                builder -> builder.executes(context -> {
                    CommandUtilities.open(context.getSource(), PathUtil.makeRelativeToGameDirectory(CraftTweakerConstants.SCRIPTS_DIRECTORY));
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "syntax",
                Component.translatable("crafttweaker.command.description.syntax"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    //TODO: get loader name from '/ct syntax loaderName'?
                    // TODO: Use a custom load source?
                    CraftTweakerAPI.getRegistry().getAllLoaders()
                            .stream()
                            .peek(loader -> CommandUtilities.send(source, Component.translatable("crafttweaker.script.load.start", CommandUtilities.makeNoticeable(loader.name()))))
                            .map(it -> new ScriptRunConfiguration(it, IScriptLoadSource.find(CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID), ScriptRunConfiguration.RunKind.SYNTAX_CHECK))
                            .map(it -> CraftTweakerAPI.getScriptRunManager().createScriptRun(it))
                            .forEach(it -> {
                                try {
                                    it.execute();
                                    CommandUtilities.send(source, Component.translatable("crafttweaker.script.load.end.noerror"));
                                } catch(final Throwable e) {
                                    CommandUtilities.COMMAND_LOGGER.error("Unable to check for syntax due to an error", e);
                                    CommandUtilities.send(source, Component.translatable("crafttweaker.script.load.end.error"));
                                }
                            });
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
}
