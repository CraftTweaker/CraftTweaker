package com.blamejared.crafttweaker.api;

import javax.annotation.Nonnull;

public class ScriptLoadingOptions {
    
    private boolean format;
    private boolean execute;
    private String loaderName = CraftTweakerAPI.getDefaultLoaderName();
    
    public ScriptLoadingOptions() {
    }
    
    /**
     * Causes the scripts to be formatted and outputted in the "formatted" folder
     * Used e.g by {@code /ct format}
     */
    public ScriptLoadingOptions format() {
        this.format = true;
        return this;
    }
    
    /**
     * Causes the scripts to be executed
     * If not set, then this will stop after checking the syntax
     */
    public ScriptLoadingOptions execute() {
        return setExecute(true);
    }
    
    /**
     * @see #format()
     */
    public boolean isFormat() {
        return format;
    }
    
    /**
     * @see #format()
     */
    public ScriptLoadingOptions setFormat(boolean format) {
        this.format = format;
        return this;
    }
    
    /**
     * @see #execute()
     */
    public boolean isExecute() {
        return execute;
    }
    
    /**
     * @see #execute()
     */
    public ScriptLoadingOptions setExecute(boolean execute) {
        this.execute = execute;
        return this;
    }
    
    /**
     * The current loader name
     */
    public String getLoaderName() {
        return loaderName;
    }
    
    /**
     * Sets the loader name.
     * Loader names are case insensitive, but are <strong>not</strong> Regular Expressions
     */
    public ScriptLoadingOptions setLoaderName(@Nonnull String loaderName) {
        this.loaderName = loaderName;
        return this;
    }
}
