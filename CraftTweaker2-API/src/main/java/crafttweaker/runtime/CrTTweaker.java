package crafttweaker.runtime;

import crafttweaker.*;
import crafttweaker.preprocessor.*;
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
    public static String defaultLoaderName = "crafttweaker";
    
    private static boolean DEBUG = false;
    private final List<IAction> actions = new ArrayList<>();
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
        
        preprocessorManager.clean();
        
        Set<String> executed = new HashSet<>();
        boolean loadSuccessful = true;
    
        List<ScriptFile> scriptFiles = collectScriptFiles(isSyntaxCommand);
        
        // preprocessor magic
        for(ScriptFile scriptFile : scriptFiles) {
            scriptFile.addAll(preprocessorManager.checkFileForPreprocessors(scriptFile));
        }
        
        scriptFiles.sort(PreprocessorManager.SCRIPT_FILE_COMPARATOR);
        
        // ZS magic
        for(ScriptFile scriptFile : scriptFiles) {
            if (!scriptFile.getLoaderName().equals(loaderName) && !isSyntaxCommand) {
                CraftTweakerAPI.logInfo("[" +loaderName + "]: Skipping file " + scriptFile);
                continue;
            }
            
            if(!executed.contains(scriptFile.getEffectiveName())) {
                executed.add(scriptFile.getEffectiveName());
                
                CraftTweakerAPI.logInfo("[" +loaderName + "]: Loading Script: " + scriptFile);
                
                Map<String, byte[]> classes = new HashMap<>();
                IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);
                
                ZenParsedFile zenParsedFile = null;
                
                Reader reader = null;
                try {
                    reader = new InputStreamReader(new BufferedInputStream(scriptFile.open()), "UTF-8");
                    
                    String filename = scriptFile.getEffectiveName();
                    String className = extractClassName(filename);
                    
                    CrTScriptLoadEvent loadEvent = new CrTScriptLoadEvent(scriptFile);
                    preprocessorManager.postLoadEvent(loadEvent);
                    
                    // blocks the parsing of the script
                    if(scriptFile.isParsingBlocked()) continue;
                    
                    ZenTokener parser = new ZenTokener(reader, environmentGlobal.getEnvironment(), filename, scriptFile.areBracketErrorsIgnored());
                    zenParsedFile = new ZenParsedFile(filename, className, parser, environmentGlobal);
                    
                } catch(IOException ex) {
                    CraftTweakerAPI.logError("[" +loaderName + "]: Could not load script " + scriptFile + ": " + ex.getMessage());
                    loadSuccessful = false;
                } catch(ParseException ex) {
                    CraftTweakerAPI.logError("[" +loaderName + "]: Error parsing " + ex.getFile().getFileName() + ":" + ex.getLine() + " -- " + ex.getExplanation());
                    loadSuccessful = false;
                } catch(Exception ex) {
                    CraftTweakerAPI.logError("[" +loaderName + "]: Error loading " + scriptFile + ": " + ex.toString(), ex);
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
                    String filename = scriptFile.getEffectiveName();
                    
                    // Stops if the compile is disabled
                    if (zenParsedFile == null || scriptFile.isCompileBlocked()) continue;
                    compileScripts(filename, Collections.singletonList(zenParsedFile), environmentGlobal, scriptFile.isDebugEnabled() || DEBUG);
                    
                    // stops if the execution is disabled
                    if (scriptFile.isExecutionBlocked() || isSyntaxCommand) continue;
                    
                    
                    ZenModule module = new ZenModule(classes, CraftTweakerAPI.class.getClassLoader());
                    Runnable runnable = module.getMain();
                    if(runnable != null)
                        runnable.run();
                    
                    
                } catch(Throwable ex) {
                    CraftTweakerAPI.logError("[" +loaderName + "]: Error executing " + scriptFile + ": " + ex.getMessage(), ex);
                }
            }
        }
        
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
}
