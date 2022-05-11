package com.blamejared.crafttweaker.api.util.sequence.task.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.SequenceContext;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiPredicate;

/**
 * A task that will sleep until its condition is met.
 *
 * @docParam this new SleepUntilTask((level) => level.isRaining)
 */
@ZenRegister
@Document("vanilla/api/sequence/task/type/SleepUntilTask")
@ZenCodeType.Name("crafttweaker.api.sequence.task.type.SleepUntilTask")
public class SleepUntilTask<T, U> implements ISequenceTask<T, U> {
    
    private final BiPredicate<T, SequenceContext<T, U>> condition;
    private boolean complete = false;
    
    @ZenCodeType.Constructor
    public SleepUntilTask(BiPredicate<T, SequenceContext<T, U>> actorConsumer) {
        
        this.condition = actorConsumer;
    }
    
    @Override
    public void tick(T actor, SequenceContext<T, U> data) {
        
        if(!complete) {
            complete = condition.test(actor, data);
        }
    }
    
    @Override
    public boolean isComplete(T actor, SequenceContext<T, U> data) {
        
        return complete;
    }
    
}
