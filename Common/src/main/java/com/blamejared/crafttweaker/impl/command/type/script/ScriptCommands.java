package com.blamejared.crafttweaker.impl.command.type.script;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.api.zencode.impl.loader.LoaderActions;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.mojang.brigadier.Command;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;

public final class ScriptCommands {
    
    private ScriptCommands() {}
    
    public static void registerCommands() {
        
        CTCommands.registerCommand(new CommandImpl("log", new TranslatableComponent("crafttweaker.command.description.log"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CommandUtilities.open(player, new File(CraftTweakerConstants.LOG_PATH));
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("scripts", new TranslatableComponent("crafttweaker.command.description.script"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CommandUtilities.open(player, CraftTweakerConstants.SCRIPT_DIR);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("syntax", new TranslatableComponent("crafttweaker.command.description.syntax"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                //TODO: get loader name from '/ct syntax loaderName'?
                LoaderActions.getKnownLoaderNames().forEach(loader -> {
                    CommandUtilities.send(new TranslatableComponent("crafttweaker.script.load.start", CommandUtilities.makeNoticeable(loader)), player);
                    CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().setLoaderName(loader));
                });
                
                return Command.SINGLE_SUCCESS;
            });
        }));
        
    }
    
}
