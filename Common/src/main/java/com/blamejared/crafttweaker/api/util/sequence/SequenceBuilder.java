package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.sequence.task.ISequenceTask;
import com.blamejared.crafttweaker.api.util.sequence.task.type.InstantTask;
import com.blamejared.crafttweaker.api.util.sequence.task.type.SleepTask;
import com.blamejared.crafttweaker.api.util.sequence.task.type.SleepUntilTask;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A builder for {@link Sequence}.
 *
 * @docParam this level.sequence()
 */
@ZenRegister
@Document("vanilla/api/util/sequence/SequenceBuilder")
@ZenCodeType.Name("crafttweaker.api.util.sequence.SequenceBuilder")
public class SequenceBuilder<T, U> {
    
    
    private final SequenceType type;
    private final Supplier<T> actor;
    private final U data;
    private final Queue<ISequenceTask<T, U>> timeline;
    
    public SequenceBuilder(SequenceType type, Supplier<T> actor, U data) {
        
        this.type = type;
        this.actor = actor;
        this.data = data;
        this.timeline = new LinkedList<>();
    }
    
    /**
     * Adds a task to the sequence.
     *
     * @param task The task to add.
     *
     * @return This builder to chain calls.
     *
     * @docParam task new SleepTask(20)
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> addTask(ISequenceTask<T, U> task) {
        
        timeline.offer(task);
        return this;
    }
    
    /**
     * Sleep (wait) for the given amount of ticks.
     *
     * @param ticks The amount of ticks to wait.
     *
     * @return This builder to chain calls.
     *
     * @docParam ticks 20
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> sleep(long ticks) {
        
        return addTask(new SleepTask<>(ticks));
    }
    
    /**
     * Sleeps until the given condition is met.
     *
     * @param condition The condition to wait for.
     *
     * @return This builder to chain calls.
     *
     * @docParam condition (level) => level.isRaining
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> sleepUntil(Predicate<T> condition) {
        
        return this.addTask(new SleepUntilTask<>((t, u) -> condition.test(t)));
    }
    
    /**
     * Sleeps until the given condition is met.
     *
     * @param condition The condition to wait for.
     *
     * @return This builder to chain calls.
     *
     * @docParam condition (level, context) => level.isRaining
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> sleepUntil(BiPredicate<T, SequenceContext<T, U>> condition) {
        
        return this.addTask(new SleepUntilTask<>(condition));
    }
    
    /**
     * Runs the function as part of the sequence.
     *
     * @param function The function to run.
     *
     * @return This builder to chain calls.
     *
     * @docParam function (level) => level.setRainLevel(0.5)
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> run(Consumer<T> function) {
        
        return this.addTask(new InstantTask<>((t, u) -> function.accept(t)));
    }
    
    /**
     * Runs the function as part of the sequence.
     *
     * @param function The function to run.
     *
     * @return This builder to chain calls.
     *
     * @docParam function (level, context) => level.setRainLevel(0.5)
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> run(BiConsumer<T, SequenceContext<T, U>> function) {
        
        return this.addTask(new InstantTask<>(function));
    }
    
    /**
     * Runs the function as part of the sequence.
     *
     * <p>This method is an alias for {@code run}</p>
     *
     * @param function The function to run.
     *
     * @return This builder to chain calls.
     *
     * @docParam function (level) => level.setRainLevel(0.5)
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> then(Consumer<T> function) {
        
        return run(function);
    }
    
    /**
     * Runs the function as part of the sequence.
     *
     * <p>This method is an alias for {@code run}</p>
     *
     * @param function The function to run.
     *
     * @return This builder to chain calls.
     *
     * @docParam function (level, context) => level.setRainLevel(0.5)
     */
    @ZenCodeType.Method
    public SequenceBuilder<T, U> then(BiConsumer<T, SequenceContext<T, U>> function) {
        
        return run(function);
    }
    
    /**
     * Builds and starts the sequence.
     *
     * @return The sequence that was built.
     */
    @ZenCodeType.Method
    public Sequence<T, U> start() {
        
        Sequence<T, U> schedule = new Sequence<>(actor, data, timeline);
        SequenceManager.addSequence(type, schedule);
        return schedule;
    }
    
}
