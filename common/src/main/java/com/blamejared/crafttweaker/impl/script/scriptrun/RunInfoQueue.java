package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;

final class RunInfoQueue {
    
    private record UndoQueueElement(IUndoableAction action, Logger logger) {
        
        UndoQueueElement(final IUndoableAction action, final Function<IAction, Logger> loggerObtainingFunction) {
            
            this(action, loggerObtainingFunction.apply(action));
        }
        
    }
    
    private final Queue<RunInfo> queue;
    private final Runnable undoActionMessage;
    private final Function<IAction, Logger> loggerObtainingFunction;
    private RunInfo previous;
    private boolean firstRun;
    
    RunInfoQueue(final Runnable undoActionMessage, final Function<IAction, Logger> loggerObtainingFunction) {
        
        this.queue = new ArrayDeque<>();
        this.undoActionMessage = undoActionMessage;
        this.loggerObtainingFunction = loggerObtainingFunction;
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
        
        this.undoActionMessage.run();
        while(!this.queue.isEmpty()) {
            final RunInfo previous = this.queue.poll();
            previous.appliedActions()
                    .stream()
                    .filter(IUndoableAction.class::isInstance)
                    .map(IUndoableAction.class::cast)
                    .map(it -> new UndoQueueElement(it, this.loggerObtainingFunction))
                    .filter(it -> it.action().shouldApplyOn(previous.loadSource(), it.logger()))
                    .peek(it -> it.logger().info(it.action().describeUndo()))
                    .map(UndoQueueElement::action)
                    .forEach(IUndoableAction::undo);
        }
    }
    
}
