package crafttweaker.runtime;

import crafttweaker.*;
import crafttweaker.api.network.NetworkSide;
import crafttweaker.preprocessor.*;
import crafttweaker.runtime.events.*;
import crafttweaker.socket.CrTSocketHandler;
import crafttweaker.util.*;
import crafttweaker.zenscript.GlobalRegistry;
import stanhebben.zenscript.*;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.ParseException;

import java.io.*;
import java.util.*;

import static stanhebben.zenscript.ZenModule.*;

/**
 * @author Stan Hebben
 */
public class CrTTweaker implements ITweaker {
    
    private static String defaultLoaderName = "crafttweaker";
    private NetworkSide networkSide = NetworkSide.INVALID_SIDE;
    
    private static boolean DEBUG = false;
    private final List<IAction> actions = new ArrayList<>();
    /**
     * PreprocessorManager, deals with all preprocessor Actions
     */
    private PreprocessorManager preprocessorManager = new PreprocessorManager();
    private IScriptProvider scriptProvider;
    
    /**
     * List of all event subscribers
     */
    private final EventList<CrTLoadingStartedEvent> CRT_LOADING_STARTED_EVENT_EVENT_LIST = new EventList<>();
    private final EventList<CrTLoadingScriptEventPre> CRT_LOADING_SCRIPT_PRE_EVENT_LIST = new EventList<>();
    private final EventList<CrTLoadingScriptEventPost> CRT_LOADING_SCRIPT_POST_EVENT_LIST = new EventList<>();
    
    
    
    public CrTTweaker() {
        PreprocessorManager.registerOwnPreprocessors(preprocessorManager);
        CrTSocketHandler socketHandler = new CrTSocketHandler();
    }
    
    @Override
    public void apply(IAction action) {
        String describe = action.describe();
        if(describe !=null && !describe.isEmpty()) {
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
    
    /**
     * TODO: Keep this at all cost -----------------------
     *
     */
    @Override
    public boolean loadScript(boolean isSyntaxCommand, String loaderName) {
        return loadScript(isSyntaxCommand, loaderName, null, false);
    }
    
    private List<ScriptFile> loadPreprocessor(boolean isSyntaxCommand){
        List<ScriptFile> scriptFiles = collectScriptFiles(isSyntaxCommand);
    
        // preprocessor magic
        for(ScriptFile scriptFile : scriptFiles) {
            scriptFile.addAll(preprocessorManager.checkFileForPreprocessors(scriptFile));
        }
    
        scriptFiles.sort(PreprocessorManager.SCRIPT_FILE_COMPARATOR);
        
        return scriptFiles;
    }
    
    
    public boolean loadScript(boolean isSyntaxCommand, String loaderName, List<ParseException> parseExceptions, boolean isLinter) {
        CraftTweakerAPI.logInfo("Loading scripts");
        if (!isLinter)
            CRT_LOADING_STARTED_EVENT_EVENT_LIST.publish(new CrTLoadingStartedEvent(loaderName, isSyntaxCommand, networkSide));
        
        preprocessorManager.clean();
        
        Set<String> executed = new HashSet<>();
        boolean loadSuccessful = true;
        
        List<ScriptFile> scriptFiles = loadPreprocessor(isSyntaxCommand);
        
        

        Map<String, byte[]> classes = new HashMap<>();
        IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);
        
        // ZS magic
        long totalTime = System.currentTimeMillis();
        for(ScriptFile scriptFile : scriptFiles) {
            // check for loader
            if(!scriptFile.getLoaderName().equals(loaderName) && !isSyntaxCommand) {
                CraftTweakerAPI.logInfo(getTweakerDescriptor(loaderName) + ": Skipping file " + scriptFile + " as we are currently loading with a different loader");
                continue;
            }
            
            // check for network side
            if(!scriptFile.shouldBeLoadedOn(networkSide)) {
                CraftTweakerAPI.logInfo(getTweakerDescriptor(loaderName) + ": Skipping file " + scriptFile + " as we are on the wrong side of the Network");
                continue;
            }
            
            // prevent double execution
            if(!executed.contains(scriptFile.getEffectiveName())) {
                executed.add(scriptFile.getEffectiveName());
                
                CraftTweakerAPI.logInfo(getTweakerDescriptor(loaderName) + ": Loading Script: " + scriptFile);
                
                ZenParsedFile zenParsedFile = null;
                String filename = scriptFile.getEffectiveName();
                String className = extractClassName(filename);
                
                if (!isLinter)
                    CRT_LOADING_SCRIPT_PRE_EVENT_LIST.publish(new CrTLoadingScriptEventPre(filename));
                
                // start reading of the scripts
                try(Reader reader = new InputStreamReader(new BufferedInputStream(scriptFile.open()), "UTF-8")) {
                    preprocessorManager.postLoadEvent(new CrTScriptLoadEvent(scriptFile));
        
                    // blocks the parsing of the script
                    if(scriptFile.isParsingBlocked())
                        continue;
        
                    ZenTokener parser = new ZenTokener(reader, environmentGlobal.getEnvironment(), filename, scriptFile.areBracketErrorsIgnored());
                    zenParsedFile = new ZenParsedFile(filename, className, parser, environmentGlobal);
        
                } catch(IOException ex) {
                    CraftTweakerAPI.logError(getTweakerDescriptor(loaderName) + ": Could not load script " + scriptFile + ": " + ex.getMessage());
                    loadSuccessful = false;
                } catch(ParseException ex) {
                    CraftTweakerAPI.logError(getTweakerDescriptor(loaderName) + ": Error parsing " + ex.getFile().getFileName() + ":" + ex.getLine() + " -- " + ex.getExplanation());
                    loadSuccessful = false;
                    if (parseExceptions != null)
                        parseExceptions.add(ex);
                } catch(Exception ex) {
                    CraftTweakerAPI.logError(getTweakerDescriptor(loaderName) + ": Error loading " + scriptFile + ": " + ex.toString(), ex);
                    loadSuccessful = false;
                }
                
                
                try {
                    // Stops if the compile is disabled
                    if(zenParsedFile == null || scriptFile.isCompileBlocked())
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
                CRT_LOADING_SCRIPT_POST_EVENT_LIST.publish(new CrTLoadingScriptEventPost(filename));
//                CraftTweakerAPI.logInfo("Completed file: " + filename +" in: " + (System.currentTimeMillis() - time) + "ms");
            }
            
        }
        CraftTweakerAPI.logInfo("Completed script loading in: " + (System.currentTimeMillis() - totalTime) + "ms");
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
    
    public void registerLoadStartedEvent(IEventHandler<CrTLoadingStartedEvent> eventHandler) {
        CRT_LOADING_STARTED_EVENT_EVENT_LIST.add(eventHandler);
    }
    
    public void registerScriptLoadPreEvent(IEventHandler<CrTLoadingScriptEventPre> eventHandler) {
        CRT_LOADING_SCRIPT_PRE_EVENT_LIST.add(eventHandler);
    }
    
    public void registerScriptLoadPostEvent(IEventHandler<CrTLoadingScriptEventPost> eventHandler) {
        CRT_LOADING_SCRIPT_POST_EVENT_LIST.add(eventHandler);
    }
}
