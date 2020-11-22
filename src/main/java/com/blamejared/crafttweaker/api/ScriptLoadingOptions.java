package com.blamejared.crafttweaker.api;

import javax.annotation.Nonnull;

public class ScriptLoadingOptions {
    private boolean format;
    private boolean execute;
    private boolean firstRun;
    private String loaderName = CraftTweakerAPI.getDefaultLoaderName();

    public ScriptLoadingOptions() {
    }

    public ScriptLoadingOptions firstRun() {
        this.firstRun = true;
        return this;
    }
    
    public ScriptLoadingOptions format() {
        this.format = true;
        return this;
    }

    public ScriptLoadingOptions execute() {
        this.execute = true;
        return this;
    }
    
    public boolean isFirstRun() {
        return firstRun;
    }
    
    public ScriptLoadingOptions setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
        return this;
    }
    
    public boolean isFormat() {
        return format;
    }

    public ScriptLoadingOptions setFormat(boolean format) {
        this.format = format;
        return this;
    }

    public boolean isExecute() {
        return execute;
    }

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
