package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
final class MutableRunInfo implements IMutableScriptRunInfo {
    
    private final RunInfo wrapped;
    
    MutableRunInfo(final RunInfo wrapped) {
        
        this.wrapped = wrapped;
    }
    
    @Override
    public void displayBranding(final boolean displayBranding) {
        
        this.wrapped.displayBranding(displayBranding);
    }
    
    @Override
    public void dumpClasses(final boolean dumpClasses) {
        
        this.wrapped.dumpClasses(dumpClasses);
    }
    
    @Override
    public ScriptRunConfiguration configuration() {
        
        return this.wrapped.configuration();
    }
    
    @Override
    public List<IAction> appliedActions() {
        
        return this.wrapped.appliedActions();
    }
    
    @Override
    public List<IAction> invalidActions() {
        
        return this.wrapped.invalidActions();
    }
    
    @Override
    public boolean displayBranding() {
        
        return this.wrapped.displayBranding();
    }
    
    @Override
    public boolean dumpClasses() {
        
        return this.wrapped.dumpClasses();
    }
    
    @Override
    public boolean isFirstRun() {
        
        return this.wrapped.isFirstRun();
    }
    
}
