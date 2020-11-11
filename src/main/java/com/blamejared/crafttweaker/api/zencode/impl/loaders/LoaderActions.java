package com.blamejared.crafttweaker.api.zencode.impl.loaders;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.google.common.collect.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.thread.*;

import java.util.*;

public class LoaderActions {
    
    private static final Map<String, LoaderActions> allActionsByLoaderName = new HashMap<>();
    
    private final String loaderName;
    private final List<IAction> actionListServer = new ArrayList<>();
    private final List<IAction> actionListClient = new ArrayList<>();
    
    private final List<IAction> actionListInvalidClient = new ArrayList<>();
    private final List<IAction> actionListInvalidServer = new ArrayList<>();
    private int runCount = 0;
    
    private LoaderActions(String loaderName) {
        this.loaderName = loaderName;
    }
    
    public static LoaderActions getActionForLoader(String loaderName) {
        return allActionsByLoaderName.computeIfAbsent(loaderName, LoaderActions::new);
    }
    
    public static Set<String> getKnownLoaderNames() {
        return ImmutableSet.copyOf(allActionsByLoaderName.keySet());
    }
    
    public String getLoaderName() {
        return loaderName;
    }
    
    public List<IAction> getActionList() {
        return EffectiveSide.get() == LogicalSide.CLIENT ? actionListClient : actionListServer;
    }
    
    public List<IAction> getActionListInvalid() {
        return EffectiveSide.get() == LogicalSide.CLIENT ? actionListInvalidClient : actionListInvalidServer;
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
                .filter(iAction -> iAction.shouldApplyOn(EffectiveSide.get()))
                .map(iAction -> (IUndoableAction) iAction)
                .forEach(iUndoableAction -> {
                    CraftTweakerAPI.logInfo(iUndoableAction.describeUndo());
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
