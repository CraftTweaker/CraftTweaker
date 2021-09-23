package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.LoaderActions;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import net.minecraft.util.text.TextFormatting;

import java.io.File;

public final class ScriptCommands {
    
    private ScriptCommands() {}
    
    public static void registerScriptCommands() {
        
        CTCommands.registerCommand(CTCommands.playerCommand("log", "Opens the log file", (player, stack) -> {
            CommandUtilities.open(player, new File("logs/crafttweaker.log"));
            return 0;
        }));
        
        CTCommands.registerCommand(CTCommands.playerCommand("scripts", "Opens the scripts folder", (player, stack) -> {
            CommandUtilities.open(player, CraftTweakerAPI.SCRIPT_DIR);
            return 0;
        }));
        
        CTCommands.registerCommand(CTCommands.playerCommand("syntax", "Checks the syntax of the scripts", (player, stack) -> {
            //TODO: get loader name from '/ct syntax loaderName'?
            LoaderActions.getKnownLoaderNames().forEach(loader -> {
                CommandUtilities.send(CommandUtilities.color("Starting loading scripts for loader '" + loader + "'", TextFormatting.YELLOW), player);
                CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().setLoaderName(loader));
            });
            
            return 0;
        }));
        
        CTCommands.registerCommand(CTCommands.playerCommand("format", "Checks the syntax of the scripts and formats them into another folder.", (player, stack) -> {
            LoaderActions.getKnownLoaderNames().forEach(loader -> {
                CommandUtilities.send(CommandUtilities.color("Loading and formatting scripts for loader '" + loader + "'", TextFormatting.YELLOW), player);
                CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().setLoaderName(loader).format());
            });
            
            return 0;
        }));
    }
    
}
