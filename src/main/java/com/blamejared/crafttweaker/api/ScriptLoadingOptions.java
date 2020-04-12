package com.blamejared.crafttweaker.api;

public class ScriptLoadingOptions {
    private boolean format;
    private boolean execute;

    public ScriptLoadingOptions() {
    }

    public ScriptLoadingOptions format() {
        this.format = true;
        return this;
    }

    public ScriptLoadingOptions execute() {
        this.execute = true;
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
}
