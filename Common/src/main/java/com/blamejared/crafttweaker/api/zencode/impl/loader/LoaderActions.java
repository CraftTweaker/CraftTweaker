package com.blamejared.crafttweaker.api.zencode.impl.loader;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoaderActions {
    
    private static final Map<String, LoaderActions> allActionsByLoaderName = new HashMap<>();
    
    private final String loaderName;
    private final ScriptLoadingOptions.ScriptLoadSource source;
    private final List<IAction> actionList = new ArrayList<>();
    
    private final List<IAction> actionListInvalid = new ArrayList<>();
    private int runCount = 0;
    
    private LoaderActions(String loaderName, ScriptLoadingOptions.ScriptLoadSource source) {
        
        this.loaderName = loaderName;
        this.source = source;
    }
    
    public static LoaderActions getActionForLoader(String loaderName, ScriptLoadingOptions.ScriptLoadSource source) {
        
        return allActionsByLoaderName.computeIfAbsent(loaderName, key -> new LoaderActions(key, source));
    }
    
    public static Set<String> getKnownLoaderNames() {
        
        return ImmutableSet.copyOf(allActionsByLoaderName.keySet());
    }
    
    public String getLoaderName() {
        
        return loaderName;
    }
    
    public List<IAction> getActionList() {
        
        return actionList;
    }
    
    public List<IAction> getActionListInvalid() {
        
        return actionListInvalid;
    }
    
    public void addValidAction(IAction action) {
        
        getActionList().add(action);
    }
    
    public void addInvalidAction(IAction action) {
        
        getActionListInvalid().add(action);
    }
    
    public void reload() {
        
        final List<IAction> actionList = getActionList();
        actionList.stream()
                .filter(iAction -> iAction instanceof IUndoableAction)
                .filter(iAction -> iAction.shouldApplyOn(source))
                .map(iAction -> (IUndoableAction) iAction)
                .forEach(iUndoableAction -> {
                    CraftTweakerAPI.LOGGER.info(iUndoableAction.describeUndo());
                    iUndoableAction.undo();
                });
        actionList.clear();
        getActionListInvalid().clear();
    }
    
    public boolean isFirstRun() {
        
        return getRunCount() == 0;
    }
    
    public int getRunCount() {
        
        return runCount;
    }
    
    public void incrementRunCount() {
        
        runCount++;
    }
    
}
