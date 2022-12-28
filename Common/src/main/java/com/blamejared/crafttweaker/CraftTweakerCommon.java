package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import com.blamejared.crafttweaker.impl.plugin.core.PluginManager;
import com.blamejared.crafttweaker.impl.script.recipefs.RecipeFileSystemProviderInjector;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CraftTweakerCommon {
    
    public static final Logger LOG = LogManager.getLogger(CraftTweakerConstants.MOD_NAME);
    private static Set<String> PATRON_LIST = new HashSet<>();
    
    private static final Supplier<PluginManager> PLUGIN_MANAGER = Suppliers.memoize(PluginManager::of);
    
    public static void init() {
        
        try {
            Files.createDirectories(CraftTweakerAPI.getScriptsDirectory());
        } catch(IOException e) {
            final String path = CraftTweakerAPI.getScriptsDirectory().toAbsolutePath().toString();
            throw new IllegalStateException("Could not create Directory " + path);
        }
        CraftTweakerLog4jEditor.edit();
        RecipeFileSystemProviderInjector.inject();
        
        Services.REGISTRY.init();
        
        new Thread(() -> {
            try {
                URL url = new URL("https://blamejared.com/patrons.txt");
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                urlConnection.setRequestProperty("User-Agent", "CraftTweaker|1.19.2");
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    PATRON_LIST = reader.lines().filter(s -> !s.isEmpty()).collect(Collectors.toSet());
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static PluginManager getPluginManager() {
        
        return PLUGIN_MANAGER.get();
    }
    
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
        
        CtCommands.get().registerCommandsTo(dispatcher, environment);
    }
    
    
    public static Set<String> getPatronList() {
        
        return PATRON_LIST;
    }
    
    public static void loadInitScripts() {
        
        final ScriptRunConfiguration configuration = new ScriptRunConfiguration(
                CraftTweakerConstants.INIT_LOADER_NAME,
                CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID, // TODO("Custom load source?")
                ScriptRunConfiguration.RunKind.EXECUTE
        );
        
        try {
            CraftTweakerAPI.getScriptRunManager()
                    .createScriptRun(configuration)
                    .execute();
        } catch(final Throwable e) {
            CraftTweakerAPI.LOGGER.error("Unable to run init scripts due to an error", e);
        }
    }
    
}
