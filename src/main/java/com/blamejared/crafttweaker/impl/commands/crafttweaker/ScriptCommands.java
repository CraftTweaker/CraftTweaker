package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.LoaderActions;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public final class ScriptCommands {
    
    private ScriptCommands() {}
    
    public static void registerScriptCommands() {
        
        CTCommands.registerCommand(CTCommands.playerCommand("log", "Opens the log file", (player, stack) -> {
            String link = "logs/crafttweaker.log";
            CommandUtilities.send(CommandUtilities.openingUrl(new TranslationTextComponent("Click to open the logs folder: %s", CommandUtilities.makeNoticeable(link)).mergeStyle(TextFormatting.GREEN), link), player);
            return 0;
        }));
        
        CTCommands.registerCommand(CTCommands.playerCommand("scripts", "Opens the scripts folder", (player, stack) -> {
            String link = CraftTweakerAPI.SCRIPT_DIR.getPath();
            CommandUtilities.send(CommandUtilities.openingUrl(new TranslationTextComponent("Click to open the scripts folder: %s", CommandUtilities.makeNoticeable(link)).mergeStyle(TextFormatting.GREEN), link), player);
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
