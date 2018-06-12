package crafttweaker.runtime;

import java.util.*;

public class ScriptLoader {
    
    private final Set<String> names = new HashSet<>();
    private boolean loaded = false;
    
    public ScriptLoader(String... nameAndAliases) {
        addAliases(nameAndAliases);
    }
    
    
    public Set<String> getNames() {
        return names;
    }
    
    public boolean isLoaded() {
        return loaded;
    }
    
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
    
    public void addAliases(String... names) {
        this.names.addAll(Arrays.asList(names));
    }
    
    public boolean canExecute(String... loaderNames) {
        for(String loaderName : loaderNames) {
            if(names.contains(loaderName))
                return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "[" + String.join(" | ", names) + "]";
    }
    
}
