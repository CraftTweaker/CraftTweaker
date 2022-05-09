package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Queue;
import java.util.function.Supplier;

/**
 * A sequence is a series of tasks that run after each other when the actor ticks.
 *
 * <p>For example, when a level ticks.</p>
 *
 * @param <T>
 * @param <U>
 */
@ZenRegister
@Document("vanilla/api/util/sequence/Sequence")
@ZenCodeType.Name("crafttweaker.api.util.sequence.Sequence")
public class Sequence<T, U> {
    
    private final Supplier<T> actor;
    private final Queue<ISequenceTask<T, U>> timeline;
    private boolean stopped;
    private final SequenceContext<T, U> context;
    
    public Sequence(Supplier<T> actor, U data, Queue<ISequenceTask<T, U>> timeline) {
        
        this.actor = actor;
        this.timeline = timeline;
        this.stopped = false;
        this.context = new SequenceContext<>(this, data);
    }
    
    /**
     * Ticks this sequence.
     */
    @ZenCodeType.Method
    public void tick() {
        
        if(timeline.isEmpty()) {
            return;
        }
        ISequenceTask<T, U> task = timeline.peek();
        
        task.tick(actor.get(), context);
        if(task.isComplete(actor.get(), context)) {
            timeline.remove();
        }
    }
    
    /**
     * Stops this sequence, subsequent tasks will not be ran.
     */
    @ZenCodeType.Method
    public void stop() {
        
        this.stopped = true;
    }
    
    /**
     * Checks if this sequence is stopped or not.
     *
     * @return true if stopped, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("stopped")
    public boolean isStopped() {
        
        return this.stopped;
    }
    
    /**
     * Checks if this sequence is complete.
     *
     * @return true if complete, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isComplete")
    public boolean isComplete() {
        
        return timeline.isEmpty();
    }
    
    /**
     * Gets the context for this sequence.
     *
     * @return The context for this sequence.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("context")
    public SequenceContext<T, U> getContext() {
        
        return context;
    }
    
}
