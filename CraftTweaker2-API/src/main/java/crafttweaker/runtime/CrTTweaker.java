package crafttweaker.runtime;

import crafttweaker.*;
import crafttweaker.api.network.NetworkSide;
import crafttweaker.preprocessor.*;
import crafttweaker.runtime.events.*;
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
    }
    
    @Override
    public void apply(IAction action) {
        CraftTweakerAPI.logInfo(action.describe());
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
        CraftTweakerAPI.logInfo("Loading scripts");
        CRT_LOADING_STARTED_EVENT_EVENT_LIST.publish(new CrTLoadingStartedEvent(loaderName, isSyntaxCommand, networkSide));
        preprocessorManager.clean();
        
        Set<String> executed = new HashSet<>();
        boolean loadSuccessful = true;
        
        List<ScriptFile> scriptFiles = collectScriptFiles(isSyntaxCommand);
        
        // preprocessor magic
        for(ScriptFile scriptFile : scriptFiles) {
            scriptFile.addAll(preprocessorManager.checkFileForPreprocessors(scriptFile));
        }
        
        scriptFiles.sort(PreprocessorManager.SCRIPT_FILE_COMPARATOR);
        
        Map<String, byte[]> classes = new HashMap<>();
        IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);
        
        // ZS magic
        long totalTime = System.currentTimeMillis();
        for(ScriptFile scriptFile : scriptFiles) {
            if(!scriptFile.getLoaderName().equals(loaderName) && !isSyntaxCommand) {
                CraftTweakerAPI.logInfo(getTweakerDescriptor(loaderName) + ": Skipping file " + scriptFile + " as we are currently loading with a different loader");
                continue;
            }
            
            if(!scriptFile.shouldBeLoadedOn(networkSide)) {
                CraftTweakerAPI.logInfo(getTweakerDescriptor(loaderName) + ": Skipping file " + scriptFile + " as we are on the wrong side of the Network");
                continue;
            }
            
            if(!executed.contains(scriptFile.getEffectiveName())) {
//                long time = System.currentTimeMillis();
                
                executed.add(scriptFile.getEffectiveName());
                
                CraftTweakerAPI.logInfo(getTweakerDescriptor(loaderName) + ": Loading Script: " + scriptFile);
                
                ZenParsedFile zenParsedFile = null;
                String filename = scriptFile.getEffectiveName();
                String className = extractClassName(filename);
    
                CRT_LOADING_SCRIPT_PRE_EVENT_LIST.publish(new CrTLoadingScriptEventPre(filename));
                Reader reader = null;
                try {
                    reader = new InputStreamReader(new BufferedInputStream(scriptFile.open()), "UTF-8");
                    
                    
                    CrTScriptLoadEvent loadEvent = new CrTScriptLoadEvent(scriptFile);
                    preprocessorManager.postLoadEvent(loadEvent);
                    
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
                } catch(Exception ex) {
                    CraftTweakerAPI.logError(getTweakerDescriptor(loaderName) + ": Error loading " + scriptFile + ": " + ex.toString(), ex);
                    loadSuccessful = false;
                } finally {
                    if(reader != null) {
                        try {
                            reader.close();
                        } catch(IOException ignored) {
                        }
                    }
                }
                
                
                try {
                    // Stops if the compile is disabled
                    if(zenParsedFile == null || scriptFile.isCompileBlocked())
                        continue;
                    compileScripts(className, Collections.singletonList(zenParsedFile), environmentGlobal, scriptFile.isDebugEnabled() || DEBUG);
                    
                    // stops if the execution is disabled
                    if(scriptFile.isExecutionBlocked() || isSyntaxCommand)
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
