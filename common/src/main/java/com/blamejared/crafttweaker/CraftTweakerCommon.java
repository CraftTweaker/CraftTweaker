package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.CraftTweakerEarlyInit;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import com.blamejared.crafttweaker.impl.plugin.core.PluginManager;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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
    
    private static Set<String> PATRON_LIST = new HashSet<>();
    
    private static final Supplier<PluginManager> PLUGIN_MANAGER = Suppliers.memoize(PluginManager::of);
    
    public static void init() {
        
        try {
            Files.createDirectories(CraftTweakerAPI.getScriptsDirectory());
        } catch(IOException e) {
            final String path = CraftTweakerAPI.getScriptsDirectory().toAbsolutePath().toString();
            throw new IllegalStateException("Could not create Directory " + path);
        }
        CraftTweakerEarlyInit.run();
        
        Services.REGISTRY.init();
        
        final Thread patronThread = new Thread(() -> {
            try {
                final String ua = CraftTweakerConstants.MOD_NAME + '|' + SharedConstants.getCurrentVersion().getName();
                final URL url = new URL("https://blamejared.com/patrons.txt");
                final URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                urlConnection.setRequestProperty("User-Agent", ua);
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    PATRON_LIST = reader.lines().filter(s -> !s.isEmpty()).collect(Collectors.toSet());
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
        patronThread.setName(CraftTweakerConstants.MOD_NAME + "-Patron-List-Downloader");
        patronThread.setDaemon(true); // Just in case MC crashes while we're doing this, it makes no sense to stall
        patronThread.start();
    }
    
    public static PluginManager getPluginManager() {
        
        return PLUGIN_MANAGER.get();
    }
    
    public static Logger logger() {
        
        return CommonLoggers.api();
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
            logger().error("Unable to run init scripts due to an error", e);
        }
    }
    
}
