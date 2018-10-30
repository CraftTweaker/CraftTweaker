package crafttweaker.runtime;

import crafttweaker.*;
import crafttweaker.api.network.NetworkSide;
import crafttweaker.preprocessor.*;
import crafttweaker.runtime.events.*;
import crafttweaker.socket.SingleError;
import crafttweaker.util.*;
import crafttweaker.zenscript.*;
import stanhebben.zenscript.*;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static stanhebben.zenscript.ZenModule.*;

/**
 * @author Stan Hebben
 */
public class CrTTweaker implements ITweaker {
    
    private static final String defaultLoaderName = "crafttweaker";
    private static boolean DEBUG = false;
    private final List<IAction> actions = new ArrayList<>();
    
    private final List<ScriptLoader> loaders = new ArrayList<>();
    
    /**
     * List of all event subscribers
     */
    private final EventList<CrTLoaderLoadingEvent.Started> CRT_LOADING_STARTED_EVENT_EVENT_LIST = new EventList<>();
    private final EventList<CrTLoaderLoadingEvent.Finished> CRT_LOADING_FINISHED_EVENT_EVENT_LIST = new EventList<>();
    private final EventList<CrTLoaderLoadingEvent.Aborted> CRT_LOADING_ABORTED_EVENT_EVENT_LIST = new EventList<>();
    private final EventList<CrTScriptLoadingEvent.Pre> CRT_LOADING_SCRIPT_PRE_EVENT_LIST = new EventList<>();
    private final EventList<CrTScriptLoadingEvent.Post> CRT_LOADING_SCRIPT_POST_EVENT_LIST = new EventList<>();
    private NetworkSide networkSide = NetworkSide.INVALID_SIDE;
    /**
     * PreprocessorManager, deals with all preprocessor Actions
     */
    private PreprocessorManager preprocessorManager = new PreprocessorManager();
    private IScriptProvider scriptProvider;
    
    
    public CrTTweaker() {
        PreprocessorManager.registerOwnPreprocessors(preprocessorManager);
    }
    
    @Override
    public void apply(IAction action) {
        String describe = action.describe();
        if(describe != null && !describe.isEmpty()) {
            CraftTweakerAPI.logInfo(describe);
        }
        action.apply();
        actions.add(action);
    }
    
    @Override
    public void setScriptProvider(IScriptProvider provider) {
        scriptProvider = provider;
    }
    
    @Override
    public void load() {
        loadScript(false, defaultLoaderName);
    }
    
    
    @Override
    public boolean loadScript(boolean isSyntaxCommand, String loaderName) {
        return loadScript(isSyntaxCommand, loaderName, null, false);
    }
    
    private List<ScriptFile> loadPreprocessor(boolean isSyntaxCommand) {
        List<ScriptFile> scriptFiles = collectScriptFiles(isSyntaxCommand);
        
        // preprocessor magic
        for(ScriptFile scriptFile : scriptFiles) {
            scriptFile.addAll(preprocessorManager.checkFileForPreprocessors(scriptFile));
        }
        
        scriptFiles.sort(PreprocessorManager.SCRIPT_FILE_COMPARATOR);
        
        return scriptFiles;
    }
    
    
    public boolean loadScript(boolean isSyntaxCommand, List<SingleError> parseExceptions, boolean isLinter, String... loaderNames) {
        return loadScript(isSyntaxCommand, getOrAddLoader(loaderNames).removeDelay(loaderNames), parseExceptions, isLinter);
    }
    
    public boolean loadScript(boolean isSyntaxCommand, String loaderName, List<SingleError> parseExceptions, boolean isLinter) {
        return loadScript(isSyntaxCommand, parseExceptions, isLinter, loaderName);
    }
    
    @Override
    public void loadScript(boolean isSyntaxCommand, ScriptLoader loader) {
        loadScript(isSyntaxCommand, loader, null, false);
    }
    
    private boolean loadScript(boolean isSyntaxCommand, ScriptLoader loader, List<SingleError> parseExceptions, boolean isLinter) {
        if(loader == null) {
            CraftTweakerAPI.logError("Error when trying to load with a null loader");
            return false;
        }
        
        CraftTweakerAPI.logInfo("Loading scripts for loader with names " + loader.toString());
        if(loader.isLoaded() && !isSyntaxCommand) {
            CraftTweakerAPI.logDefault("Skipping loading for loader " + loader + " since it's already been loaded");
            return false;
        }
        
        if(loader.isDelayed() && !isSyntaxCommand) {
            CraftTweakerAPI.logDefault("Skipping loading for loader " + loader + " since its execution is being delayed by another mod.");
            return false;
        }
        
        if(loader.getLoaderStage() == ScriptLoader.LoaderStage.INVALIDATED) {
            CraftTweakerAPI.logWarning("Skipping loading for loader " + loader + " since it's become invalidated");
            return false;
        }
        
        
        loader.setLoaderStage(ScriptLoader.LoaderStage.LOADING);
        if(!isLinter)
            CRT_LOADING_STARTED_EVENT_EVENT_LIST.publish(new CrTLoadingStartedEvent(loader, isSyntaxCommand, networkSide));
        
        preprocessorManager.clean();
        
        Set<String> executed = new HashSet<>();
        boolean loadSuccessful = true;
        
        List<ScriptFile> scriptFiles = loadPreprocessor(isSyntaxCommand);
        
        // prepare logger
        ((CrtStoringErrorLogger) GlobalRegistry.getErrors()).clear();
        
        
        Map<String, byte[]> classes = new HashMap<>();
        IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes, loader.getMainName());
        
        // ZS magic
        long totalTime = System.currentTimeMillis();
        for(ScriptFile scriptFile : scriptFiles) {
            // check for loader
            final String loaderName = loader.getMainName();
            
            
            if(!loader.canExecute(scriptFile.getLoaderName())) {
                if(!isSyntaxCommand)
                    CraftTweakerAPI.logDefault(getTweakerDescriptor(loaderName) + ": Skipping file " + scriptFile + " as we are currently loading with a different loader");
                continue;
            }
            
            // check for network side
            if(!scriptFile.shouldBeLoadedOn(networkSide)) {
                CraftTweakerAPI.logDefault(getTweakerDescriptor(loaderName) + ": Skipping file " + scriptFile + " as we are on the wrong side of the Network");
                continue;
            }
            
            // prevent double execution
            if(!executed.contains(scriptFile.getEffectiveName())) {
                executed.add(scriptFile.getEffectiveName());
                
                //TODO: Only print if not syntax command?
                //if(!isSyntaxCommand)
                CraftTweakerAPI.logDefault(getTweakerDescriptor(loaderName) + ": Loading Script: " + scriptFile);
                
                ZenParsedFile zenParsedFile = null;
                String filename = scriptFile.getEffectiveName();
                String className = extractClassName(filename);
                
                if(!isLinter)
                    CRT_LOADING_SCRIPT_PRE_EVENT_LIST.publish(new CrTLoadingScriptEventPre(filename));
                
                // start reading of the scripts
                ZenTokener parser = null;
                try(Reader reader = new InputStreamReader(new BufferedInputStream(scriptFile.open()), StandardCharsets.UTF_8)) {
                    preprocessorManager.postLoadEvent(new CrTScriptLoadEvent(scriptFile));
                    
                    // blocks the parsing of the script
                    if(scriptFile.isParsingBlocked())
                        continue;
                    
                    parser = new ZenTokener(reader, environmentGlobal.getEnvironment(), filename, scriptFile.areBracketErrorsIgnored());
                    zenParsedFile = new ZenParsedFile(filename, className, parser, environmentGlobal);
                    
                } catch(IOException ex) {
                    CraftTweakerAPI.logError(getTweakerDescriptor(loaderName) + ": Could not load script " + scriptFile + ": " + ex.getMessage());
                    loadSuccessful = false;
                } catch(ParseException ex) {
                    CraftTweakerAPI.logError(getTweakerDescriptor(loaderName) + ": Error parsing " + ex.getFile().getFileName() + ":" + ex.getLine() + " -- " + ex.getExplanation());
                    loadSuccessful = false;
                    if(parseExceptions != null)
                        parseExceptions.add(new SingleError(ex.getFile().getFileName(), ex.getLine(), ex.getLineOffset(), ex.getExplanation(), SingleError.Level.ERROR));
                } catch(Exception ex) {
                    CraftTweakerAPI.logError(getTweakerDescriptor(loaderName) + ": Error loading " + scriptFile + ": " + ex.toString(), ex);
                    if(parser != null && parseExceptions != null)
                        parseExceptions.add(new SingleError(parser.getFile().getFileName(), parser.getLine(), parser.getLineOffset(), "Generic ERROR", SingleError.Level.ERROR));
                    loadSuccessful = false;
                }
                
                
                try {
                    // Stops if the compile is disabled
                    if(zenParsedFile == null || scriptFile.isCompileBlocked() || !loadSuccessful)
                        continue;
                    compileScripts(className, Collections.singletonList(zenParsedFile), environmentGlobal, scriptFile.isDebugEnabled() || DEBUG);
                    
                    // stops if the execution is disabled
                    if(scriptFile.isExecutionBlocked() || isSyntaxCommand || isLinter)
                        continue;
                    
                    
                    ZenModule module = new ZenModule(classes, CraftTweakerAPI.class.getClassLoader());
                    Runnable runnable = module.getMain();
                    if(runnable != null)
                        runnable.run();
                    
                    
                } catch(Throwable ex) {
                    CraftTweakerAPI.logError("[" + loaderName + "]: Error executing " + scriptFile + ": " + ex.getMessage(), ex);
                }
                
                if(!isLinter)
                    CRT_LOADING_SCRIPT_POST_EVENT_LIST.publish(new CrTLoadingScriptEventPost(filename));
                    //CraftTweakerAPI.logDefault("Completed file: " + filename +" in: " + (System.currentTimeMillis() - time) + "ms");
            }
            
        }
        
        if(parseExceptions != null) {
            CrtStoringErrorLogger logger = (CrtStoringErrorLogger) GlobalRegistry.getErrors();
            parseExceptions.addAll(logger.getErrors());
            
            CrTGlobalEnvironment global = (CrTGlobalEnvironment) environmentGlobal;
            parseExceptions.addAll(global.getErrors());
        }
        
        
        loader.setLoaderStage(loadSuccessful ? ScriptLoader.LoaderStage.LOADED_SUCCESSFUL : ScriptLoader.LoaderStage.ERROR);
        if(!isLinter)
            CRT_LOADING_FINISHED_EVENT_EVENT_LIST.publish(new CrTLoaderLoadingEvent.Finished(loader, networkSide, isSyntaxCommand));
        CraftTweakerAPI.logDefault("Completed script loading in: " + (System.currentTimeMillis() - totalTime) + "ms");
        return loadSuccessful;
    }
    
    protected List<ScriptFile> collectScriptFiles(boolean isSyntaxCommand) {
        List<ScriptFile> fileList = new ArrayList<>();
        HashSet<String> collected = new HashSet<>();
        
        // Collecting all scripts
        Iterator<IScriptIterator> scripts = scriptProvider.getScripts();
        while(scripts.hasNext()) {
            IScriptIterator script = scripts.next();
            
            if(!collected.contains(script.getGroupName())) {
                collected.add(script.getGroupName());
                
                while(script.next()) {
                    fileList.add(new ScriptFile(this, script.copyCurrent(), isSyntaxCommand));
                }
            }
        }
        return fileList;
    }
    
    private String getTweakerDescriptor(String loaderName) {
        return "[" + loaderName + " | " + networkSide + "]";
    }
    
    @Override
    public List<IAction> getActions() {
        return actions;
    }
    
    @Override
    public void enableDebug() {
        DEBUG = true;
    }
    
    @Override
    public PreprocessorManager getPreprocessorManager() {
        return preprocessorManager;
    }
    
    public NetworkSide getNetworkSide() {
        return networkSide;
    }
    
    public void setNetworkSide(NetworkSide networkSide) {
        this.networkSide = networkSide;
    }
    
    public void registerLoadStartedEvent(IEventHandler<CrTLoaderLoadingEvent.Started> eventHandler) {
        CRT_LOADING_STARTED_EVENT_EVENT_LIST.add(eventHandler);
    }
    
    public void registerLoadFinishedEvent(IEventHandler<CrTLoaderLoadingEvent.Finished> eventHandler) {
        CRT_LOADING_FINISHED_EVENT_EVENT_LIST.add(eventHandler);
    }
    
    public void registerLoadAbortedEvent(IEventHandler<CrTLoaderLoadingEvent.Aborted> eventHandler) {
        CRT_LOADING_ABORTED_EVENT_EVENT_LIST.add(eventHandler);
    }
    
    public void registerScriptLoadPreEvent(IEventHandler<CrTScriptLoadingEvent.Pre> eventHandler) {
        CRT_LOADING_SCRIPT_PRE_EVENT_LIST.add(eventHandler);
    }
    
    public void registerScriptLoadPostEvent(IEventHandler<CrTScriptLoadingEvent.Post> eventHandler) {
        CRT_LOADING_SCRIPT_POST_EVENT_LIST.add(eventHandler);
    }
    
    private ScriptLoader getLoader(String... names) {
        for(ScriptLoader loader : loaders) {
            if(loader.canExecute(names))
                return loader;
        }
        return null;
    }
    
    private ScriptLoader getOrAddLoader(String... names) {
        ScriptLoader loader = getLoader(names);
        if(loader != null)
            return loader;
        return getOrCreateLoader(names);
    }
    
    @Override
    public List<ScriptLoader> getLoaders() {
        return loaders;
    }
    
    /**
     * Adds a loader, merges with other Lists if possible
     */
    @Override
    public ScriptLoader getOrCreateLoader(String... nameAndAliases) {
        Iterator<ScriptLoader> it = loaders.iterator();
        ScriptLoader mergeLoader = null;
        
        while(it.hasNext()) {
            ScriptLoader loader = it.next();
            if(loader.canExecute(nameAndAliases)) {
                loader.addAliases(nameAndAliases);
                
                if(mergeLoader == null) {
                    mergeLoader = loader;
                } else {
                    mergeLoader.addAliases(loader.getNames().toArray(new String[0]));
                    mergeLoader.setMainName(loader.getMainName());
                    if(loader.getLoaderStage() != ScriptLoader.LoaderStage.NOT_LOADED && loader.getLoaderStage() != ScriptLoader.LoaderStage.INVALIDATED)
                        mergeLoader.setLoaderStage(loader.getLoaderStage());
                    loader.setLoaderStage(ScriptLoader.LoaderStage.INVALIDATED);
                    it.remove();
                }
            }
        }
        
        if(mergeLoader == null) {
            mergeLoader = new ScriptLoader(nameAndAliases);
            loaders.add(mergeLoader);
        }
        
        CraftTweakerAPI.logDefault("Current loaders after merging: " + loaders);
        return mergeLoader;
    }
    
    public void resetLoaderStats() {
        for(ScriptLoader loader : loaders) {
            loader.setLoaderStage(ScriptLoader.LoaderStage.NOT_LOADED);
        }
    }
}
