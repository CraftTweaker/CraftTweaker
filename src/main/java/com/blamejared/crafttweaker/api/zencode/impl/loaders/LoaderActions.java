package com.blamejared.crafttweaker.api.zencode.impl.loaders;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.google.common.collect.*;
import net.minecraftforge.fml.common.thread.*;

import java.util.*;

public class LoaderActions {
    private static final Map<String, LoaderActions> allActionsByLoaderName = new HashMap<>();
    
    private final String loaderName;
    private final List<IAction> actionList = new ArrayList<>();
    private final List<IAction> actionListInvalid = new ArrayList<>();
    private int runCount = 0;
    
    public static LoaderActions getActionForLoader(String loaderName) {
        return allActionsByLoaderName.computeIfAbsent(loaderName, LoaderActions::new);
    }
    
    public static Set<String> getKnownLoaderNames() {
        return ImmutableSet.copyOf(allActionsByLoaderName.keySet());
    }
    
    private LoaderActions(String loaderName) {
        this.loaderName = loaderName;
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
        actionList.add(action);
    }
    
    public void addInvalidAction(IAction action) {
        actionListInvalid.add(action);
    }
    
    public void reload() {
        actionList.stream()
                .filter(iAction -> iAction instanceof IUndoableAction)
                .filter(iAction -> iAction.shouldApplyOn(EffectiveSide.get()))
                .map(iAction -> (IUndoableAction) iAction)
                .forEach(iUndoableAction -> {
                    CraftTweakerAPI.logInfo(iUndoableAction.describeUndo());
                    iUndoableAction.undo();
                });
    
        //We use the removeIf for SinglePlayer, where there are two runs with the different Effective Sides.
        //If we used 'clear' then the 2nd side would not be able to undo the changes on their side.
        actionList.removeIf(iAction -> iAction.shouldApplyOn(EffectiveSide.get()));
        actionListInvalid.clear();
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
