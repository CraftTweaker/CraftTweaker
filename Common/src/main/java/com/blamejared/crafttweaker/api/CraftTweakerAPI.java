package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.action.base.ActionApplier;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.game.Game;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.mod.Mods;
import com.blamejared.crafttweaker.api.zencode.expand.IDataRewrites;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.blamejared.crafttweaker.api.zencode.impl.loader.LoaderActions;
import com.blamejared.crafttweaker.api.zencode.impl.loader.ScriptRun;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.parser.expression.ParsedExpressionArray;
import org.openzen.zenscript.parser.expression.ParsedExpressionMap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.CraftTweakerAPI")
public class CraftTweakerAPI {
    
    private static final Supplier<ICraftTweakerRegistry> REGISTRY = Suppliers.memoize(Services.BRIDGE::registry);
    private static final Supplier<Path> SCRIPTS_DIRECTORY = Suppliers.memoize(() -> Services.PLATFORM.getPathFromGameDirectory(CraftTweakerConstants.SCRIPTS_DIRECTORY));
    
    // Do we want to make a log4j wrapper and expose it to a script...? 😬
    public static final Logger LOGGER = LogManager.getLogger(CraftTweakerLogger.LOGGER_NAME);
    
    @Deprecated(forRemoval = true)
    public static boolean DEBUG_MODE = false;
    @Deprecated(forRemoval = true)
    public static boolean NO_BRAND = false;
    
    @Deprecated(forRemoval = true)
    private static RecipeManager recipeManager;
    
    @ZenCodeGlobals.Global("game")
    public static final Game GAME = new Game();
    
    @ZenCodeGlobals.Global("loadedMods")
    public static final Mods MODS = new Mods();
    
    /**
     * This field is effectively final, it should never change in normal gameplay, but it is changed during testing,
     * which means that it cannot have the final modifier.
     */
    @Deprecated(forRemoval = true)
    private static ActionApplier ACTION_APPLIER = CraftTweakerAPI::applyActionInternal;
    
    /**
     * The last ScriptRun that was executed is regarded as "current" run.
     */
    @Deprecated(forRemoval = true)
    private static ScriptRun currentRun;
    
    static {
        ParsedExpressionMap.compileOverrides.add(IDataRewrites::rewriteMap);
        ParsedExpressionArray.compileOverrides.add(IDataRewrites::rewriteArray);
    }
    
    public static ICraftTweakerRegistry getRegistry() {
        
        return REGISTRY.get();
    }
    
    public static Path getScriptsDirectory() {
        
        return SCRIPTS_DIRECTORY.get();
    }
    
    // Down below there's old stuff that can and should be moved away
    
    /**
     * Loads the scripts with the given loadingOptions.
     * Will check CraftTweaker's default scripts directory for scripts.
     *
     * @param scriptLoadingOptions The options with which to load
     */
    public static void loadScripts(ScriptLoadingOptions scriptLoadingOptions) {
        
        NO_BRAND = false;
        final List<File> fileList = getScriptFiles();
        
        final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(CraftTweakerAPI.getRegistry()
                .getPreprocessors());
        SourceFile[] sourceFiles = fileList.stream()
                .map(file -> new FileAccessSingle(CraftTweakerConstants.SCRIPT_DIR, file, scriptLoadingOptions, CraftTweakerAPI.getRegistry()
                        .getPreprocessors()))
                .filter(FileAccessSingle::shouldBeLoaded)
                .sorted(comparator)
                .map(FileAccessSingle::getSourceFile)
                .toArray(SourceFile[]::new);
        
        loadScripts(sourceFiles, scriptLoadingOptions);
    }
    
    /**
     * Loads the given sourceFiles with the given loadingOptions.
     * It is recommended to use {@link #loadScripts(ScriptLoadingOptions)} instead, unless you know what you're doing.
     *
     * @param sourceFiles          The sourceFiles.
     * @param scriptLoadingOptions The options with which to load.
     */
    public static void loadScripts(SourceFile[] sourceFiles, ScriptLoadingOptions scriptLoadingOptions) {
        
        currentRun = new ScriptRun(scriptLoadingOptions, sourceFiles);
        LOGGER.info("Started loading Scripts for Loader '{}'!", scriptLoadingOptions.getLoaderName());
        
        try {
            currentRun.reload();
            currentRun.run();
        } catch(Exception e) {
            e.printStackTrace();
            LOGGER.error("Error running scripts", e);
        }
        LOGGER.info("Finished loading Scripts!");
    }
    
    public static void apply(IAction action) {
        
        ACTION_APPLIER.apply(action);
    }
    
    public static List<File> getScriptFiles() {
        
        List<File> fileList = new ArrayList<>();
        findScriptFiles(CraftTweakerConstants.SCRIPT_DIR, fileList);
        return fileList;
    }
    
    public static ScriptingEngine getEngine() {
        //I don't see why one would want an engine without a run being present
        return getCurrentRun().getEngine();
    }
    
    public static ScriptRun getCurrentRun() {
        
        if(currentRun == null) {
            throw new IllegalStateException("Invalid current run!");
        }
        return currentRun;
    }
    
    public static List<IAction> getActionList() {
        
        final LoaderActions loaderActions = getCurrentRun().getLoaderActions();
        return ImmutableList.copyOf(loaderActions.getActionList());
    }
    
    public static List<IAction> getActionListInvalid() {
        
        final LoaderActions loaderActions = getCurrentRun().getLoaderActions();
        return ImmutableList.copyOf(loaderActions.getActionListInvalid());
    }
    
    private static void applyActionInternal(IAction action) {
        
        final ScriptRun currentRun = getCurrentRun();
        if(!(action instanceof IRuntimeAction) && !currentRun.isFirstRun()) {
            return;
        }
        
        final LoaderActions currentLoaderActions = currentRun.getLoaderActions();
        try {
            if(!action.shouldApplyOn(currentRun.getScriptLoadSource())) {
                return;
            }
            
            if(!action.validate(LOGGER)) {
                currentLoaderActions.addInvalidAction(action);
                return;
            }
            
            final String describe = action.describe();
            if(describe != null && !describe.isEmpty()) {
                LOGGER.info(describe);
            }
            action.apply();
            currentLoaderActions.addValidAction(action);
        } catch(Exception e) {
            LOGGER.error("Error running action", e);
        }
    }
    
    /**
     * Applies IActions in {@link #apply(IAction)}
     * Extracted as interface to be able to override this in tests.
     */
    @VisibleForTesting
    public static void setActionApplier(ActionApplier actionApplier) {
        
        CraftTweakerAPI.ACTION_APPLIER = actionApplier;
    }
    
    
    /**
     * Finds all files in the given path that end with `.zs` and adds them to the files list.
     * Traverses the file tree recursively.
     *
     * @param path       The path to traverse.
     * @param foundFiles The list where the found files will be stored in.
     */
    public static void findScriptFiles(File path, List<File> foundFiles) {
        
        if(path.isDirectory()) {
            File[] files = path.listFiles();
            if(files == null) {
                return;
            }
            for(File file : files) {
                if(file.isDirectory()) {
                    findScriptFiles(file, foundFiles);
                } else {
                    if(file.getName().toLowerCase().endsWith(".zs")) {
                        foundFiles.add(file);
                    }
                }
            }
        }
    }
    
    public static RecipeManager getRecipeManager() {
        
        Preconditions.checkNotNull(recipeManager, "Cannot get the recipe manager before it has been set!");
        
        return recipeManager;
    }
    
    public static AccessRecipeManager getAccessibleRecipeManager() {
        
        Preconditions.checkNotNull(recipeManager, "Cannot get the recipe manager before it has been set!");
        
        return (AccessRecipeManager) recipeManager;
    }
    
    
    public static void setRecipeManager(RecipeManager recipeManager) {
        
        CraftTweakerAPI.recipeManager = recipeManager;
    }
    
    public static void loadScriptsFromRecipeManager(RecipeManager recipeManager, ScriptLoadingOptions scriptLoadingOptions) {
        
        Map<ResourceLocation, Recipe<?>> map = ((AccessRecipeManager) recipeManager).getRecipes()
                .getOrDefault(CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS, new HashMap<>());
        Collection<Recipe<?>> recipes = map.values();
        CraftTweakerAPI.NO_BRAND = false;
        
        final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(CraftTweakerAPI.getRegistry()
                .getPreprocessors());
        final SourceFile[] sourceFiles = recipes.stream()
                .map(iRecipe -> (ScriptRecipe) iRecipe)
                .map(recipe -> new FileAccessSingle(recipe.getFileName(), new InputStreamReader(new ByteArrayInputStream(recipe.getContent()
                        .getBytes(StandardCharsets.UTF_8))), scriptLoadingOptions, CraftTweakerAPI.getRegistry()
                        .getPreprocessors()))
                .filter(FileAccessSingle::shouldBeLoaded)
                .sorted(comparator)
                .map(FileAccessSingle::getSourceFile)
                .toArray(SourceFile[]::new);
        loadScripts(sourceFiles, scriptLoadingOptions);
    }
    
}
