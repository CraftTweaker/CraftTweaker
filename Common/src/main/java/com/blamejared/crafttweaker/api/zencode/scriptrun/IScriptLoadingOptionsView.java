package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;

import java.util.Collection;
import java.util.stream.Stream;

public interface IScriptLoadingOptionsView {
    
    boolean isFormat();
    
    boolean isExecute();
    
    String getLoaderName();
    
    Collection<String> getInheritedLoaders();
    
    ScriptLoadingOptions.ScriptLoadSource getSource();
    
    default Collection<String> getTargetedLoaders() {
        
        return Stream.concat(this.getInheritedLoaders().stream(), Stream.of(this.getLoaderName())).toList();
    }
    
    default boolean appliesLoader(final String loader) {
        
        return this.getLoaderName().equals(loader) || this.getInheritedLoaders().contains(loader);
    }
    
}
