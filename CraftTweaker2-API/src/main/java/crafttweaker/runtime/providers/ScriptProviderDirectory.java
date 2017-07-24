package crafttweaker.runtime.providers;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.runtime.*;

import java.io.*;
import java.util.*;

/**
 * @author Stan
 */
public class ScriptProviderDirectory implements IScriptProvider {
    
    private final File directory;
    
    public ScriptProviderDirectory(File directory) {
        if(directory == null)
            throw new IllegalArgumentException("directory cannot be null");
        
        this.directory = directory;
    }
    
    @Override
    public Iterator<IScriptIterator> getScripts() {
        List<IScriptIterator> scripts = new ArrayList<>();
        if(directory.exists()) {
            iterate(directory, scripts);
        }
        if(scripts.size() > 1)
            scripts.sort((sc, sc1) -> {
                if(sc == null || sc1 == null) {
                    return -1;
                }
                return sc.getName().compareTo(sc1.getName());
            });
        return scripts.iterator();
    }
    
    private void iterate(File directoryIterated, List<IScriptIterator> scripts) {
        File[] files = directoryIterated.listFiles();
        if (files == null) return;
        for(File file : files) {
            if(file.isDirectory()) {
                iterate(file, scripts);
            } else if(file.isFile() && file.getName().endsWith(".zs")) {
                scripts.add(new ScriptIteratorSingle(file, directory));
            } else if(file.isFile() && file.getName().endsWith(".zip")) {
                try {
                    scripts.add(new ScriptIteratorZip(file, directory));
                } catch(IOException ex) {
                    CraftTweakerAPI.logError("Could not load " + file.getName() + ": " + ex.getMessage());
                }
            }
        }
    }
}