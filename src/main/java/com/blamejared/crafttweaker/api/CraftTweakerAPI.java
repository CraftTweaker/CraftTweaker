package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.actions.IAction;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.annotations.BracketResolver;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.logger.LogLevel;
import com.blamejared.crafttweaker.api.mods.MCMods;
import com.blamejared.crafttweaker.api.zencode.expands.IDataRewrites;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.logger.FileLogger;
import com.blamejared.crafttweaker.impl.logger.GroupLogger;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import org.openzen.zencode.java.JavaNativeModule;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.ScriptBlock;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.formatter.FileFormatter;
import org.openzen.zenscript.formatter.ScriptFormattingSettings;
import org.openzen.zenscript.parser.PrefixedBracketParser;
import org.openzen.zenscript.parser.SimpleBracketParser;
import org.openzen.zenscript.parser.expression.ParsedExpressionArray;
import org.openzen.zenscript.parser.expression.ParsedExpressionMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.*;

@ZenRegister
public class CraftTweakerAPI {
    
    public static final File SCRIPT_DIR = new File("scripts");
    
    @ZenCodeGlobals.Global
    public static ILogger logger;
    
    @ZenCodeGlobals.Global
    public static MCMods loadedMods = new MCMods();
    
    private static final List<IAction> ACTION_LIST = new ArrayList<>();
    private static final List<IAction> ACTION_LIST_INVALID = new ArrayList<>();
    
    private static ScriptingEngine SCRIPTING_ENGINE;
    
    public static boolean DEBUG_MODE = false;
    public static boolean NO_BRAND = false;
    private static boolean firstRun = true;
    
    static {
        ParsedExpressionMap.compileOverrides.add(IDataRewrites::rewriteMap);
        ParsedExpressionArray.compileOverrides.add(IDataRewrites::rewriteArray);
    }
    
    
    public static void apply(IAction action) {
        if(!(action instanceof IRuntimeAction)) {
            if(!isFirstRun()) {
                return;
            }
        }
        try {
            if(action.shouldApplyOn(EffectiveSide.get()) && action.validate(logger)) {
                String describe = action.describe();
                if(describe != null && !describe.isEmpty()) {
                    logInfo(describe);
                }
                action.apply();
                ACTION_LIST.add(action);
            } else {
                ACTION_LIST_INVALID.add(action);
            }
        } catch(Exception e) {
            logThrowing("Error running action", e);
        }
    }
    
    public static void reload() {
        ACTION_LIST.stream().filter(iAction -> iAction instanceof IUndoableAction).filter(iAction -> iAction.shouldApplyOn(EffectiveSide.get())).map(iAction -> (IUndoableAction) iAction).forEach(iUndoableAction -> {
            CraftTweakerAPI.logInfo(iUndoableAction.describeUndo());
            iUndoableAction.undo();
        });
        
        //ACTION_LIST.clear();
        ACTION_LIST.removeIf(iAction -> iAction.shouldApplyOn(EffectiveSide.get()));
        ACTION_LIST_INVALID.clear();
        ((GroupLogger) logger).getPreviousMessages().clear();
    }
    
    
    private static void initEngine() {
        SCRIPTING_ENGINE = new ScriptingEngine(logger);
        SCRIPTING_ENGINE.debug = DEBUG_MODE;
    }
    
    
    public static List<File> getScriptFiles() {
        List<File> fileList = new ArrayList<>();
        findScriptFiles(CraftTweakerAPI.SCRIPT_DIR, fileList);
        return fileList;
    }

    public static void loadScripts(ScriptLoadingOptions scriptLoadingOptions) {
        NO_BRAND = false;
        List<File> fileList = getScriptFiles();
        logInfo("Started loading Scripts for Loader '%s'!", scriptLoadingOptions.getLoaderName());
        if(!scriptLoadingOptions.isExecute()) {
            logInfo("This is only a syntax check. Script changes will not be applied.");
        }

        final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(CraftTweakerRegistry.getPreprocessors());
        SourceFile[] sourceFiles = fileList.stream()
                .map(file -> new FileAccessSingle(SCRIPT_DIR, file, scriptLoadingOptions, CraftTweakerRegistry.getPreprocessors()))
                .filter(FileAccessSingle::shouldBeLoaded)
                .sorted(comparator)
                .map(FileAccessSingle::getSourceFile)
                .toArray(SourceFile[]::new);

        loadScripts(sourceFiles, scriptLoadingOptions);
        logInfo("Finished loading Scripts!");
    }

    public static void loadScripts(SourceFile[] sourceFiles, ScriptLoadingOptions scriptLoadingOptions) {
        try {
            CraftTweakerAPI.reload();
            initEngine();
            //Register crafttweaker module first to assign deps
            JavaNativeModule crafttweakerModule = SCRIPTING_ENGINE.createNativeModule(CraftTweaker.MODID, "crafttweaker");
            List<JavaNativeModule> modules = new LinkedList<>();
            CraftTweakerRegistry.getClassesInPackage("crafttweaker").forEach(crafttweakerModule::addClass);
            CraftTweakerRegistry.getZenGlobals().forEach(crafttweakerModule::addGlobals);
            modules.add(crafttweakerModule);
            PrefixedBracketParser bep = new PrefixedBracketParser(null);
            for(Method method : CraftTweakerRegistry.getBracketResolvers()) {
                String name = method.getAnnotation(BracketResolver.class).value();
                FunctionalMemberRef memberRef = crafttweakerModule.loadStaticMethod(method);
                bep.register(name, new SimpleBracketParser(SCRIPTING_ENGINE.registry, memberRef));
            }
            bep.register("recipetype", new RecipeTypeBracketHandler());
            crafttweakerModule.registerBEP(bep);
            SCRIPTING_ENGINE.registerNativeProvided(crafttweakerModule);
            for(String key : CraftTweakerRegistry.getRootPackages()) {
                //module already registered
                if(key.equals("crafttweaker")) {
                    continue;
                }
                JavaNativeModule module = SCRIPTING_ENGINE.createNativeModule(key, key, crafttweakerModule);
                CraftTweakerRegistry.getClassesInPackage(key).forEach(module::addClass);
                SCRIPTING_ENGINE.registerNativeProvided(module);
                modules.add(module);
            }

            // For expansions on ZenScript types (I.E. any[any], string, int) and just anything else that fails
            JavaNativeModule expansions = SCRIPTING_ENGINE.createNativeModule("expansions", "", modules.toArray(new JavaNativeModule[0]));
            CraftTweakerRegistry.getExpansions().values().stream().flatMap(Collection::stream).forEach(expansions::addClass);
            SCRIPTING_ENGINE.registerNativeProvided(expansions);


            SemanticModule scripts = SCRIPTING_ENGINE.createScriptedModule("scripts", sourceFiles, bep, FunctionParameter.NONE, compileError -> CraftTweakerAPI.logger.error(compileError.toString()), validationLogEntry -> CraftTweakerAPI.logger.error(validationLogEntry.toString()), sourceFile -> CraftTweakerAPI.logger.info("Loading " + sourceFile.getFilename()));

            if(!scripts.isValid()) {
                CraftTweakerAPI.logger.error("Scripts are invalid!");
                CraftTweaker.LOG.info("Scripts are invalid!");
                return;
            }

            //  toggle this to format scripts, ideally this should be a command
            if(scriptLoadingOptions.isFormat()) {
                List<HighLevelDefinition> all = scripts.definitions.getAll();
                ScriptFormattingSettings.Builder builder = new ScriptFormattingSettings.Builder();
                FileFormatter formatter = new FileFormatter(builder.build());
                List<ScriptBlock> blocks = scripts.scripts;
                for(ScriptBlock block : blocks) {
                    String format = formatter.format(scripts.rootPackage, block, all);
                    File parent = new File("scriptsFormatted");
                    File file = new File(parent, block.file.getFilename());

                    if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                        CraftTweakerAPI.logError("Could not find or create folder %s, aborting formatting task!", file.getParent());
                    }
                    file.createNewFile();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(format);
                    writer.close();
                }
            }

            if(!scriptLoadingOptions.isExecute()) {
                return;
            }

            SCRIPTING_ENGINE.registerCompiled(scripts);
            SCRIPTING_ENGINE.run(Collections.emptyMap(), CraftTweaker.class.getClassLoader());
        } catch(Exception e) {
            e.printStackTrace();
            CraftTweakerAPI.logger.throwingErr("Error running scripts", e);
        }

        CraftTweakerAPI.endFirstRun();
    }
    
    private static String getClassName(Class<?> cls) {
        return cls.isAnnotationPresent(ZenCodeType.Name.class) ? cls.getAnnotation(ZenCodeType.Name.class).value() : cls.getName();
    }
    
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
        return ImmutableList.copyOf(ACTION_LIST);
    }
    
    public static List<IAction> getActionListInvalid() {
        return ImmutableList.copyOf(ACTION_LIST_INVALID);
    }
    
    
    public static void endFirstRun() {
        firstRun = false;
    }
    
    public static boolean isFirstRun() {
        return firstRun;
    }
    
    public static ScriptingEngine getEngine() {
        if(SCRIPTING_ENGINE == null) {
            initEngine();
        }
        
        return SCRIPTING_ENGINE;
    }

    public static String getDefaultLoaderName() {
        return "crafttweaker";
    }
}
