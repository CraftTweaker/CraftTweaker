/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime.providers;

import minetweaker.MineTweakerAPI;
import minetweaker.runtime.IScriptIterator;
import minetweaker.runtime.IScriptProvider;

import java.io.File;
import java.io.IOException;
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
            for(File file : directory.listFiles()) {
                if(file.isDirectory()) {
                    scripts.add(new ScriptIteratorDirectory(file));
                } else if(file.getName().endsWith(".zs")) {
                    scripts.add(new ScriptIteratorSingle(file));
                } else if(file.getName().endsWith(".zip")) {
                    try {
                        scripts.add(new ScriptIteratorZip(file));
                    } catch(IOException ex) {
                        MineTweakerAPI.logError("Could not load " + file.getName() + ": " + ex.getMessage());
                    }
                }
            }
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
}
