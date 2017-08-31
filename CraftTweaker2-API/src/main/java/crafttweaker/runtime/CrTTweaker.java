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
    
    public static HashSet<String> scriptsToIgnoreBracketErrors = new HashSet<>();
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
        loadScript(true, "crafttweaker");
    }
    
    @Override
    public boolean loadScript(boolean executeScripts, String loaderName) {
        System.out.println("Loading scripts");
        Set<String> executed = new HashSet<>();
        boolean loadSuccessful = true;
        
        List<ScriptFile> scriptFiles = collectScriptFiles();
        
        // preprocessor magic
        for(ScriptFile scriptFile : scriptFiles) {
            scriptFile.addAll(preprocessorManager.checkFileForPreprocessors(scriptFile));
        }
        
        for(ScriptFile scriptFile : scriptFiles) {
            
            if(!executed.contains(scriptFile.getGroupName())) {
                executed.add(scriptFile.getGroupName());
                
                Map<String, byte[]> classes = new HashMap<>();
                IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);
                
                List<ZenParsedFile> files = new ArrayList<>();
                
                
                Reader reader = null;
                try {
                    reader = new InputStreamReader(new BufferedInputStream(scriptFile.open()), "UTF-8");
                    
                    String filename = scriptFile.getName();
                    String className = extractClassName(filename);
                    
                    CrTScriptLoadEvent loadEvent = new CrTScriptLoadEvent(executeScripts, filename, className, this, preprocessorManager.preprocessorActionsPerFile.get(filename));
                    preprocessorManager.postLoadEvent(loadEvent);
                    preprocessorManager.handleLoadEvents(loadEvent);
                    
                    if(loadEvent.isParsingCanceled)
                        continue;
                    
                    ZenTokener parser = new ZenTokener(reader, environmentGlobal.getEnvironment(), filename, scriptsToIgnoreBracketErrors.contains(filename));
                    ZenParsedFile pfile = new ZenParsedFile(filename, className, parser, environmentGlobal);
                    files.add(pfile);
                } catch(IOException ex) {
                    CraftTweakerAPI.logError("Could not load script " + scriptFile.getName() + ": " + ex.getMessage());
                    loadSuccessful = false;
                } catch(ParseException ex) {
                    CraftTweakerAPI.logError("Error parsing " + ex.getFile().getFileName() + ":" + ex.getLine() + " -- " + ex.getExplanation());
                    loadSuccessful = false;
                } catch(Exception ex) {
                    CraftTweakerAPI.logError("Error loading " + scriptFile.getName() + ": " + ex.toString(), ex);
                    loadSuccessful = false;
                }
                
                if(reader != null) {
                    try {
                        reader.close();
                    } catch(IOException ignored) {
                    }
                }
                
                
                try {
                    String filename = scriptFile.getGroupName();
                    if(filename.toLowerCase().endsWith(".zs")) {
                        CraftTweakerAPI.logInfo("CraftTweaker: Loading file " + filename);
                    } else if(filename.toLowerCase().endsWith(".zip")) {
                        CraftTweakerAPI.logInfo("CraftTweaker: Loading zip " + filename);
                    } else {
                        CraftTweakerAPI.logInfo("CraftTweaker: Loading group " + filename);
                    }
                    
                    List<ZenParsedFile> filesToExecute = new ArrayList<>();
                    for(ZenParsedFile file : files) {
                        if(!preprocessorManager.fileIgnoreExecuteList.contains(file.getFileName())) {
                            filesToExecute.add(file);
                        }
                    }
                    
                    compileScripts(filename, filesToExecute, environmentGlobal, DEBUG);
                    
                    if(executeScripts) {
                        // execute scripts
                        
                        Map<String, byte[]> classesToExecute = new HashMap<>();
                        classes.forEach((s, bytes) -> {
                            if(!preprocessorManager.classIgnoreExecuteList.contains(s)) {
                                classesToExecute.put(s, bytes);
                            }
                        });
                        
                        
                        ZenModule module = new ZenModule(classesToExecute, CraftTweakerAPI.class.getClassLoader());
                        Runnable runnable = module.getMain();
                        if(runnable != null)
                            runnable.run();
                    }
                    
                } catch(Throwable ex) {
                    CraftTweakerAPI.logError("Error executing " + scriptFile.getGroupName() + ": " + ex.getMessage(), ex);
                }
            }
        }
        
        return loadSuccessful;
    }
    
    protected List<ScriptFile> collectScriptFiles() {
        List<ScriptFile> fileList = new ArrayList<>();
        HashSet<String> collected = new HashSet<>();
        
        // Collecting all scripts
        Iterator<IScriptIterator> scripts = scriptProvider.getScripts();
        while(scripts.hasNext()) {
            IScriptIterator script = scripts.next();
            
            if(!collected.contains(script.getGroupName())) {
                collected.add(script.getGroupName());
                
                while(script.next()) {
                    fileList.add(new ScriptFile(this, script));
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
    public void addFileToIgnoreBracketErrors(String filename) {
        scriptsToIgnoreBracketErrors.add(filename);
    }
    
    @Override
    public PreprocessorManager getPreprocessorManager() {
        return preprocessorManager;
    }
}
