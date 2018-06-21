package crafttweaker.runtime;

import crafttweaker.CraftTweakerAPI;

import java.util.*;

public class ScriptLoader {
    
    private final Set<String> names = new HashSet<>();
    private LoaderStage loaderStage = LoaderStage.NOT_LOADED;
    
    public ScriptLoader(String... nameAndAliases) {
        addAliases(nameAndAliases);
    }
    
    
    public Set<String> getNames() {
        return names;
    }
    
    public boolean isLoaded() {
        return loaderStage != LoaderStage.NOT_LOADED;
    }
    
    public LoaderStage getLoaderStage() {
        return loaderStage;
    }
    
    public void setLoaderStage(LoaderStage loaderStage) {
        this.loaderStage = loaderStage;
    }
    
    public void addAliases(String... names) {
        if(isLoaded())
            CraftTweakerAPI.logInfo("Trying to add loader aliases [" + String.join(" | ", names) + "] to already loaded ScriptLoader " + this.toString());
        
        for(String name : names)
            this.names.add(name.toLowerCase());
    }
    
    public boolean canExecute(String... loaderNames) {
        for(String loaderName : loaderNames) {
            if(names.contains(loaderName.toLowerCase()))
                return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "[" + String.join(" | ", names) + "]";
    }
    
    
    public enum LoaderStage {
        NOT_LOADED, LOADING, LOADED_SUCCESSFUL, ERROR, UNKNOWN
    }
    
}
