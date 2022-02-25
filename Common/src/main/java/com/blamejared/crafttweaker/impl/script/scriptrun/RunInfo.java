package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class RunInfo implements IScriptRunInfo {
    
    private record Actions(List<IAction> validActions, List<IAction> invalidActions) {
        
        Actions() {
            
            this(new ArrayList<>(), new ArrayList<>());
        }
        
        @SuppressWarnings("CopyConstructorMissesField")
            // Still not a copy constructor
        Actions(final Actions other) {
            
            this(Collections.unmodifiableList(other.validActions()), Collections.unmodifiableList(other.invalidActions()));
        }
        
    }
    
    private final ScriptRunConfiguration configuration;
    private final Actions actions;
    private final Actions view;
    private boolean displayBranding;
    private boolean dumpClasses;
    private Boolean firstRun;
    
    private RunInfo(final ScriptRunConfiguration configuration) {
        
        this.configuration = configuration;
        this.actions = new Actions();
        this.view = new Actions(this.actions);
        this.displayBranding = true;
        this.dumpClasses = false;
        this.firstRun = null;
    }
    
    static RunInfo create(final ScriptRunConfiguration configuration) {
        
        return new RunInfo(configuration);
    }
    
    @Override
    public ScriptRunConfiguration configuration() {
        
        return this.configuration;
    }
    
    @Override
    public List<IAction> appliedActions() {
        
        return this.view.validActions();
    }
    
    @Override
    public List<IAction> invalidActions() {
        
        return this.view.invalidActions();
    }
    
    @Override
    public boolean displayBranding() {
        
        return this.displayBranding;
    }
    
    @Override
    public boolean dumpClasses() {
        
        return this.dumpClasses;
    }
    
    @Override
    public boolean isFirstRun() {
        
        return Objects.requireNonNull(this.firstRun, "Unable to determine at this stage if this is a first run");
    }
    
    void displayBranding(final boolean displayBranding) {
        
        this.displayBranding = displayBranding;
    }
    
    void dumpClasses(final boolean dumpClasses) {
        
        this.dumpClasses = dumpClasses;
    }
    
    void enqueueAction(final IAction action, final boolean valid) {
        
        (valid ? this.actions.validActions() : this.actions.invalidActions()).add(action);
    }
    
    void isFirstRun(final boolean isFirstRun) {
        
        this.firstRun = isFirstRun;
    }
    
}
