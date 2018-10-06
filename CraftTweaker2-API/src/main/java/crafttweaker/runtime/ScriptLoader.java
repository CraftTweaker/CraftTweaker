package crafttweaker.runtime;

import crafttweaker.CraftTweakerAPI;

import java.util.*;

public class ScriptLoader {
    
    private String mainName;
    private final Set<String> names = new HashSet<>();
    private final Set<String> delayUntil = new HashSet<>();
    private LoaderStage loaderStage = LoaderStage.NOT_LOADED;
    
    public ScriptLoader(String... nameAndAliases) {
        addAliases(nameAndAliases);
    }
    
    
    public void delayUntil(String... name) {
        delayUntil.addAll(Arrays.asList(name));
    }
    
    public boolean isDelayed() {
        return !delayUntil.isEmpty();
    }
    
    public ScriptLoader removeDelay(String... name) {
        delayUntil.removeAll(Arrays.asList(name));
        return this;
    }
    
    public Set<String> getNames() {
        return names;
    }
    
    public String getMainName() {
        if(mainName == null)
            mainName = names.iterator().next();
        return mainName;
    }
    
    public void setMainName(String mainName) {
        if(!canExecute(mainName))
            addAliases(mainName);
        this.mainName = mainName;
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
            this.names.add(name.trim().toLowerCase());
        if(this.names.isEmpty())
            throw new IllegalArgumentException("Loader is empty after all aliases have been added");
    }
    
    public boolean canExecute(String... loaderNames) {
        for(String loaderName : loaderNames) {
            if(names.contains(loaderName.trim().toLowerCase()))
                return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" | ", "[", "]");
    
        final String mainName = getMainName();
        joiner.add(mainName);
        for(String name : names) {
            if(!mainName.equals(name))
                joiner.add(name);
        }
        
        return joiner.toString();
    }
    
    
    public enum LoaderStage {
        NOT_LOADED, LOADING, LOADED_SUCCESSFUL, ERROR, INVALIDATED, UNKNOWN
    }
    
}
