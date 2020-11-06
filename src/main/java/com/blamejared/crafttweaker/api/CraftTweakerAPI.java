package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.api.mods.*;
import com.blamejared.crafttweaker.api.zencode.expands.*;
import com.blamejared.crafttweaker.api.zencode.impl.*;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.*;
import com.blamejared.crafttweaker.impl.game.MCGame;
import com.blamejared.crafttweaker.impl.logger.*;
import com.blamejared.crafttweaker.impl.script.*;
import com.google.common.collect.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.thread.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.parser.expression.*;

import java.io.*;
import java.util.*;

@ZenRegister
public class CraftTweakerAPI {
    
    public static final File SCRIPT_DIR = new File("scripts");
    
    @ZenCodeGlobals.Global
    public static ILogger logger;
    
    @ZenCodeGlobals.Global
    public static MCMods loadedMods = new MCMods();

    @ZenCodeGlobals.Global
    public static MCGame game = new MCGame();
    
    public static boolean DEBUG_MODE = false;
    public static boolean NO_BRAND = false;
    
    /**
     * The last ScriptRun that was executed is regarded as "current" run.
     */
    private static ScriptRun currentRun;
    
    static {
        ParsedExpressionMap.compileOverrides.add(IDataRewrites::rewriteMap);
        ParsedExpressionArray.compileOverrides.add(IDataRewrites::rewriteArray);
    }
    
    
    public static void apply(IAction action) {
        final ScriptRun currentRun = getCurrentRun();
        if(!(action instanceof IRuntimeAction) && !currentRun.isFirstRun()) {
            return;
        }
        
        final LoaderActions currentLoaderActions = currentRun.getLoaderActions();
        try {
            if(action.shouldApplyOn(EffectiveSide.get()) && action.validate(logger)) {
                String describe = action.describe();
                if(describe != null && !describe.isEmpty()) {
                    logInfo(describe);
                }
                action.apply();
                currentLoaderActions.addValidAction(action);
            } else {
                currentLoaderActions.addInvalidAction(action);
            }
        } catch(Exception e) {
            logThrowing("Error running action", e);
        }
    }
    
    
    public static List<File> getScriptFiles() {
        List<File> fileList = new ArrayList<>();
        findScriptFiles(CraftTweakerAPI.SCRIPT_DIR, fileList);
        return fileList;
    }
    
    /**
     * Loads the scripts with the given loadingOptions.
     * Will check CraftTweaker's default scripts directory for scripts.
     *
     * @param scriptLoadingOptions The options with which to load
     */
    public static void loadScripts(ScriptLoadingOptions scriptLoadingOptions) {
        NO_BRAND = false;
        final List<File> fileList = getScriptFiles();
        
        final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(CraftTweakerRegistry
                .getPreprocessors());
        SourceFile[] sourceFiles = fileList.stream()
                .map(file -> new FileAccessSingle(SCRIPT_DIR, file, scriptLoadingOptions, CraftTweakerRegistry
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
        //currentLoadingOptions = scriptLoadingOptions;
        currentRun = new ScriptRun(scriptLoadingOptions, sourceFiles);
        logInfo("Started loading Scripts for Loader '%s'!", scriptLoadingOptions.getLoaderName());
        
        try {
            currentRun.reload();
            currentRun.run();
        } catch(Exception e) {
            e.printStackTrace();
            CraftTweakerAPI.logger.throwingErr("Error running scripts", e);
        }
        
        logInfo("Finished loading Scripts!");
    }
    
    /**
     *
     * Gets the source files that were sent to the client as IRecipes and executes them with the given loadingOptions
     * CrT uses this method during the RecipesUpdatedEvent on the Client to get the serverside scripts.
     *
     * @param recipeManager The world's RecipeManager.
     * @param scriptLoadingOptions The loadingOptions, used for the Preprocessors
     */
    public static void loadScriptsFromRecipeManager(RecipeManager recipeManager, ScriptLoadingOptions scriptLoadingOptions) {
        Map<ResourceLocation, IRecipe<?>> map = recipeManager.recipes.getOrDefault(CraftTweaker.RECIPE_TYPE_SCRIPTS, new HashMap<>());
        Collection<IRecipe<?>> recipes = map.values();
        CraftTweakerAPI.NO_BRAND = false;
        
        final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(CraftTweakerRegistry.getPreprocessors());
        final SourceFile[] sourceFiles = recipes.stream()
                .map(iRecipe -> (ScriptRecipe) iRecipe)
                .map(recipe -> new FileAccessSingle(recipe.getFileName(), new StringReader(recipe.getContent()), scriptLoadingOptions, CraftTweakerRegistry
                        .getPreprocessors()))
                .filter(FileAccessSingle::shouldBeLoaded)
                .sorted(comparator)
                .map(FileAccessSingle::getSourceFile)
                .toArray(SourceFile[]::new);
        loadScripts(sourceFiles, scriptLoadingOptions);
    }
    
    /**
     * Finds all files in the given path that end with `.zs` and adds them to the files list.
     * Traverses the file tree recursively.
     *
     * @param path  The path to traverse.
     * @param files The list where the found files will be stored in.
     */
    public static void findScriptFiles(File path, List<File> files) {
        if(path.isDirectory()) {
            for(File file : path.listFiles()) {
                if(file.isDirectory()) {
                    findScriptFiles(file, files);
                } else {
                    if(file.getName().toLowerCase().endsWith(".zs")) {
                        files.add(file);
                    }
                }
            }
        }
    }
    
    public static void setupLoggers() {
        logger = new GroupLogger();
        ((GroupLogger) logger).addLogger(new FileLogger(new File("logs/crafttweaker.log")));
        //TODO maybe post an event to collect a bunch of loggers? not sure if it will be used much
    }
    
    public static void logDump(String message, Object... formats) {
        logger.log(LogLevel.INFO, String.format(message, formats), false);
    }
    
    public static void logInfo(String message, Object... formats) {
        logger.info(String.format(message, formats));
    }
    
    public static void logDebug(String message, Object... formats) {
        logger.debug(String.format(message, formats));
    }
    
    public static void logWarning(String message, Object... formats) {
        logger.warning(String.format(message, formats));
    }
    
    public static void logError(String message, Object... formats) {
        logger.error(String.format(message, formats));
    }
    
    public static void logThrowing(String message, Throwable e, Object... formats) {
        logger.throwingErr(String.format(message, formats), e);
    }
    
    public static void log(LogLevel level, String filename, int lineNumber, String message, Object... formats) {
        logger.log(level, String.format("[%s:%d%s]", filename, lineNumber, String.format(message, formats)));
    }
    
    
    public static List<IAction> getActionList() {
        final LoaderActions loaderActions = getCurrentRun().getLoaderActions();
        return ImmutableList.copyOf(loaderActions.getActionList());
    }
    
    public static List<IAction> getActionListInvalid() {
        final LoaderActions loaderActions = getCurrentRun().getLoaderActions();
        return ImmutableList.copyOf(loaderActions.getActionListInvalid());
    }
    
    
    public static ScriptRun getCurrentRun() {
        if(currentRun == null) {
            throw new IllegalStateException("Invalid current run!");
        }
        return currentRun;
    }
    
    
    public static ScriptingEngine getEngine() {
        //I don't see why one would want an engine without a run being present
        return getCurrentRun().getEngine();
    }
    
    public static String getDefaultLoaderName() {
        return "crafttweaker";
    }
}
