package crafttweaker.runtime;

import crafttweaker.*;
import crafttweaker.runtime.providers.ScriptProviderMemory;
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
    
    private static boolean DEBUG = false;
    
    private final List<IAction> actions = new ArrayList<>();
    private IScriptProvider scriptProvider;
    
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
    public boolean loadScript(boolean executeScripts){
        System.out.println("Loading scripts");
        Set<String> executed = new HashSet<>();
        boolean loadSuccessful = true;

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

                        ZenTokener parser = new ZenTokener(reader, environmentGlobal.getEnvironment(), filename);
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
                        System.out.println("CraftTweaker: Loading file " + filename);
                    } else if(filename.toLowerCase().endsWith(".zip")) {
                        System.out.println("CraftTweaker: Loading zip " + filename);
                    } else {
                        System.out.println("CraftTweaker: Loading group " + filename);
                    }
                    compileScripts(filename, files, environmentGlobal, DEBUG);

                    if (executeScripts){
                        // execute scripts
                        ZenModule module = new ZenModule(classes, CraftTweakerAPI.class.getClassLoader());
                        module.getMain().run();
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
}
