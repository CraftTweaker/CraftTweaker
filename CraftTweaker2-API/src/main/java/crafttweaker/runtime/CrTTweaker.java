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
    
    /**
     * PreprocessorManager, deals with all preprocessor Actions
     */
    private PreprocessorManager preprocessorManager = new PreprocessorManager();
    
    private static boolean DEBUG = false;
    public static HashSet<String> scriptsToIgnoreBracketErrors = new HashSet<>();
    
    private final List<IAction> actions = new ArrayList<>();
    private IScriptProvider scriptProvider;
    
    public CrTTweaker(){
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
        loadScript(true);
    }

    @Override
    public boolean loadScript(boolean executeScripts) {
        System.out.println("Loading scripts");
        Set<String> executed = new HashSet<>();
        boolean loadSuccessful = true;
    
        // preprocessor magic
        Iterator<IScriptIterator> scriptsPreprocessor = scriptProvider.getScripts();
        while(scriptsPreprocessor.hasNext()) {
            IScriptIterator script = scriptsPreprocessor.next();
            while(script.next()) {
                try {
                    String filename = script.getName();
                    preprocessorManager.checkFileForPreprocessors(filename, script.open());
                    
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        
        // Doing ZS magic with the scripts
        Iterator<IScriptIterator> scripts = scriptProvider.getScripts();
        while(scripts.hasNext()) {
            IScriptIterator script = scripts.next();

            if(!executed.contains(script.getGroupName())) {
                executed.add(script.getGroupName());

                Map<String, byte[]> classes = new HashMap<>();
                IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);

                List<ZenParsedFile> files = new ArrayList<>();
                
                while(script.next()) {
                    Reader reader = null;
                    try {
                        reader = new InputStreamReader(new BufferedInputStream(script.open()), "UTF-8");
                        
                        String filename = script.getName();
                        String className = extractClassName(filename);

                        CrTScriptLoadEvent loadEvent = new CrTScriptLoadEvent(executeScripts, filename, className,this, preprocessorManager.preprocessorActionsPerFile.get(filename));
                        preprocessorManager.postLoadEvent(loadEvent);
                        preprocessorManager.handleLoadEvents(loadEvent);
                        
                        if (loadEvent.isParsingCanceled) continue;
                        
                        ZenTokener parser = new ZenTokener(reader, environmentGlobal.getEnvironment(), filename, scriptsToIgnoreBracketErrors.contains(filename));
                        ZenParsedFile pfile = new ZenParsedFile(filename, className, parser, environmentGlobal);
                        files.add(pfile);
                    } catch(IOException ex) {
                        CraftTweakerAPI.logError("Could not load script " + script.getName() + ": " + ex.getMessage());
                        loadSuccessful = false;
                    } catch(ParseException ex) {
                        CraftTweakerAPI.logError("Error parsing " + ex.getFile().getFileName() + ":" + ex.getLine() + " -- " + ex.getExplanation());
                        loadSuccessful = false;
                    } catch(Exception ex) {
                        CraftTweakerAPI.logError("Error loading " + script.getName() + ": " + ex.toString(), ex);
                        loadSuccessful = false;
                    }

                    if(reader != null) {
                        try {
                            reader.close();
                        } catch(IOException ignored) {
                        }
                    }
                }

                try {
                    String filename = script.getGroupName();
                    if(filename.toLowerCase().endsWith(".zs")) {
                        CraftTweakerAPI.logInfo("CraftTweaker: Loading file " + filename);
                    } else if(filename.toLowerCase().endsWith(".zip")) {
                        CraftTweakerAPI.logInfo("CraftTweaker: Loading zip " + filename);
                    } else {
                        CraftTweakerAPI.logInfo("CraftTweaker: Loading group " + filename);
                    }
    
                    List<ZenParsedFile> filesToExecute = new ArrayList<>();
                    for(ZenParsedFile file : files) {
                        if (!preprocessorManager.fileIgnoreExecuteList.contains(file.getFileName())){
                            filesToExecute.add(file);
                        }
                    }
    
                    compileScripts(filename, filesToExecute, environmentGlobal, DEBUG);

                    if (executeScripts){
                        // execute scripts
    
                        Map<String, byte[]> classesToExecute = new HashMap<>();
                        classes.forEach((s, bytes) -> {
                            if (!preprocessorManager.classIgnoreExecuteList.contains(s)){
                                classesToExecute.put(s, bytes);
                            }
                        });
                        
    
                        ZenModule module = new ZenModule(classesToExecute, CraftTweakerAPI.class.getClassLoader());
                        Runnable runnable = module.getMain();
                        if (runnable != null) runnable.run();
                    }

                } catch(Throwable ex) {
                    CraftTweakerAPI.logError("Error executing " + script.getGroupName() + ": " + ex.getMessage(), ex);
                }
            }
        }
        return loadSuccessful;
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
    public void addFileToIgnoreBracketErrors(String filename){
        scriptsToIgnoreBracketErrors.add(filename);
    }
    
    @Override
    public PreprocessorManager getPreprocessorManager() {
        return preprocessorManager;
    }
}
