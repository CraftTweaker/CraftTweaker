package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

final class RunInfoQueue {
    
    private final Queue<RunInfo> queue;
    private RunInfo previous;
    private boolean firstRun;
    
    RunInfoQueue() {
        
        this.queue = new ArrayDeque<>();
        this.firstRun = true;
    }
    
    boolean isFirstRun() {
        
        return this.firstRun;
    }
    
    void offer(final RunInfo info) {
        
        this.queue.offer(Objects.requireNonNull(info, "info"));
        this.previous = info;
        this.firstRun = false;
    }
    
    RunInfo previous() {
        
        return this.previous;
    }
    
    void undoActions() {
        
        if(this.queue.isEmpty()) {
            return;
        }
        
        CraftTweakerAPI.LOGGER.info("Undoing previous actions");
        while(!this.queue.isEmpty()) {
            final RunInfo previous = this.queue.poll();
            previous.appliedActions()
                    .stream()
                    .filter(IUndoableAction.class::isInstance)
                    .filter(it -> it.shouldApplyOn(previous.loadSource()))
                    .map(IUndoableAction.class::cast)
                    .peek(it -> CraftTweakerAPI.LOGGER.info(it.describeUndo()))
                    .forEach(IUndoableAction::undo);
        }
    }
    
}
