package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.command.argument.IItemStackArgument;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.blamejared.crafttweaker.impl.plugin.core.PluginManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CraftTweakerCommon {
    
    public static final Logger LOG = LogManager.getLogger(CraftTweakerConstants.MOD_NAME);
    private static Set<String> PATRON_LIST = new HashSet<>();
    
    private static final AtomicReference<PluginManager> PLUGIN_MANAGER = new AtomicReference<>(null);
    
    public static void init() {
        
        try {
            Files.createDirectories(CraftTweakerConstants.SCRIPT_DIR.toPath());
        } catch(IOException e) {
            final String path = CraftTweakerConstants.SCRIPT_DIR.getAbsolutePath();
            throw new IllegalStateException("Could not create Directory " + path);
        }
        CraftTweakerLogger.init();
        
        CraftTweakerAPI.LOGGER.info("Starting building internal Registries");
        //CraftTweakerRegistry.addAdvancedBEPName("recipemanager");
        //CraftTweakerRegistry.findClasses();
        CraftTweakerAPI.LOGGER.info("Completed building internal Registries");
        
        CraftTweakerRegistries.init();
        
        new Thread(() -> {
            try {
                URL url = new URL("https://blamejared.com/patrons.txt");
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                urlConnection.setRequestProperty("User-Agent", "CraftTweaker|1.18.1");
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    PATRON_LIST = reader.lines().filter(s -> !s.isEmpty()).collect(Collectors.toSet());
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static void handlePlugins(final Stream<Class<? extends ICraftTweakerPlugin>> pluginClasses) {
        
        PLUGIN_MANAGER.set(PluginManager.of(pluginClasses));
        PLUGIN_MANAGER.get().loadPlugins();
    }
    
    public static void handleFullSetupEnd() {
        
        PLUGIN_MANAGER.get().broadcastEnd();
    }
    
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
        
        CTCommands.init(dispatcher, environment);
    }
    
    public static void registerCommandArguments() {
        
        ArgumentTypes.register(CraftTweakerConstants.MOD_ID + ":recipe_type", RecipeTypeArgument.class, new EmptyArgumentSerializer<>(RecipeTypeArgument::get));
        ArgumentTypes.register(CraftTweakerConstants.MOD_ID + ":item", IItemStackArgument.class, new EmptyArgumentSerializer<>(IItemStackArgument::get));
    }
    
    public static Set<String> getPatronList() {
        
        return PATRON_LIST;
    }
    
    public static void loadInitScripts() {
        
        final ScriptLoadingOptions setupCommon = new ScriptLoadingOptions().setLoaderName(CraftTweakerConstants.INIT_LOADER_NAME)
                .execute();
        CraftTweakerAPI.loadScripts(setupCommon);
    }
    
}
