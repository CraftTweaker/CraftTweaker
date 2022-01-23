package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.bracket.custom.EnumConstantBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagManagerBracketHandler;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistryData;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class CraftTweakerCommon {
    
    public static final Logger LOG = LogManager.getLogger(CraftTweakerConstants.MOD_NAME);
    private static Set<String> PATRON_LIST = new HashSet<>();
    
    public static void init() {
        
        try {
            Files.createDirectories(CraftTweakerConstants.SCRIPT_DIR.toPath());
        } catch(IOException e) {
            final String path = CraftTweakerConstants.SCRIPT_DIR.getAbsolutePath();
            throw new IllegalStateException("Could not create Directory " + path);
        }
        CraftTweakerLogger.init();
        
        CraftTweakerAPI.LOGGER.info("Starting building internal Registries");
        CraftTweakerRegistry.addAdvancedBEPName("recipemanager");
        CraftTweakerRegistry.findClasses();
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
    
    public static void registerCraftTweakerBracketHandlers(final BiConsumer<String, BracketExpressionParser> reg) {
        
        final TagManagerBracketHandler tagManagerBep = new TagManagerBracketHandler(CrTTagRegistryData.INSTANCE);
        
        reg.accept("recipetype", new RecipeTypeBracketHandler(CraftTweakerRegistry.getRecipeManagers()));
        reg.accept("constant", new EnumConstantBracketHandler());
        reg.accept("tagmanager", tagManagerBep);
        reg.accept("tag", new TagBracketHandler(tagManagerBep));
    }
    
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
        
        CTCommands.init(dispatcher, environment);
    }
    
    public static Set<String> getPatronList() {
        
        return PATRON_LIST;
    }
    
    public static void loadInitScripts() {
        
        final ScriptLoadingOptions setupCommon = new ScriptLoadingOptions().setLoaderName("initialize").execute();
        CraftTweakerAPI.loadScripts(setupCommon);
    }
    
}
