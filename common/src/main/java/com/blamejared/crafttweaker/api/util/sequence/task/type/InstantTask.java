package com.blamejared.crafttweaker.api.util.sequence.task.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.SequenceContext;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A task that will run immediately.
 *
 * @docParam this new InstantTask((level) => level.setRainLevel(0.5))
 */
@ZenRegister
@Document("vanilla/api/sequence/task/type/InstantTask")
@ZenCodeType.Name("crafttweaker.api.sequence.task.type.InstantTask")
public class InstantTask<T, U> implements ISequenceTask<T, U> {
    
    private final BiConsumer<T, SequenceContext<T, U>> actorConsumer;
    private boolean complete = false;
    
    @ZenCodeType.Constructor
    public InstantTask(Consumer<T> actorConsumer) {
        
        this.actorConsumer = (actor, context) -> actorConsumer.accept(actor);
    }
    
    @ZenCodeType.Constructor
    public InstantTask(BiConsumer<T, SequenceContext<T, U>> actorConsumer) {
        
        this.actorConsumer = actorConsumer;
    }
    
    @Override
    public void tick(T actor, SequenceContext<T, U> data) {
        
        actorConsumer.accept(actor, data);
        complete = true;
    }
    
    @Override
    public boolean isComplete(T actor, SequenceContext<T, U> data) {
        
        return complete;
    }
    
}
