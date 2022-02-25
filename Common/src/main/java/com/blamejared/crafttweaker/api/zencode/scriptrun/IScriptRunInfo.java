package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

import java.util.List;

public interface IScriptRunInfo {
    
    ScriptRunConfiguration configuration();
    
    List<IAction> appliedActions();
    
    List<IAction> invalidActions();
    
    boolean displayBranding();
    
    boolean dumpClasses();
    
    boolean isFirstRun();
    
    default IScriptLoader loader() {
        
        return this.configuration().loader();
    }
    
    default IScriptLoadSource loadSource() {
        
        return this.configuration().loadSource();
    }
    
}
