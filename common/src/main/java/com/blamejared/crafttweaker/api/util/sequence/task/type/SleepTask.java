package com.blamejared.crafttweaker.api.util.sequence.task.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.SequenceContext;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A task that will sleep for the given amount of ticks.
 *
 * @docParam this new SleepTask(20)
 */
@ZenRegister
@Document("vanilla/api/sequence/task/type/SleepTask")
@ZenCodeType.Name("crafttweaker.api.sequence.task.type.SleepTask")
public class SleepTask<T, U> implements ISequenceTask<T, U> {
    
    private final long sleepTime;
    private long timeSlept;
    
    @ZenCodeType.Constructor
    public SleepTask(long sleepTime) {
        
        this.sleepTime = sleepTime;
        this.timeSlept = 0;
    }
    
    @Override
    public void tick(T actor, SequenceContext<T, U> data) {
        
        timeSlept++;
    }
    
    @Override
    public boolean isComplete(T actor, SequenceContext<T, U> data) {
        
        return timeSlept >= sleepTime;
    }
    
}
