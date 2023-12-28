package com.blamejared.crafttweaker.api.util.sequence.task;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.SequenceContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A task in a {@link com.blamejared.crafttweaker.api.util.sequence.Sequence}.
 *
 * @docParam this new SleepTask(20)
 */
@ZenRegister
@Document("vanilla/api/sequence/task/ISequenceTask")
@ZenCodeType.Name("crafttweaker.api.sequence.task.ISequenceTask")
public interface ISequenceTask<T, U> {
    
    /**
     * Ticks this task.
     *
     * <p>A check to {@code isComplete} should be done before ticking!.</p>
     *
     * @param actor   The actor that is being sequenced.
     * @param context The context for the sequence.
     *
     * @docParam actor level
     * @docParam context new crafttweaker.api.util.sequence.SequenceContext(sequence, new crafttweaker.api.data.MapData())
     */
    @ZenCodeType.Method
    void tick(T actor, SequenceContext<T, U> context);
    
    /**
     * Checks if this task is complete.
     *
     * @param actor   The actor that is being sequenced.
     * @param context The context for the sequence.
     *
     * @docParam actor level
     * @docParam data new crafttweaker.api.util.sequence.SequenceContext(sequence, new crafttweaker.api.data.MapData())
     */
    @ZenCodeType.Method
    boolean isComplete(T actor, SequenceContext<T, U> context);
    
}
