package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A SequenceContext provides more data to {@link Sequence}s and allows a sequence to be stopped from inside a task.
 *
 * @param <T>
 * @param <U>
 */
@ZenRegister
@Document("vanilla/api/util/sequence/SequenceContext")
@ZenCodeType.Name("crafttweaker.api.util.sequence.SequenceContext")
public class SequenceContext<T, U> {
    
    private final Sequence<T, U> sequence;
    private final U data;
    
    @ZenCodeType.Constructor
    public SequenceContext(Sequence<T, U> sequence, U data) {
        
        this.sequence = sequence;
        this.data = data;
    }
    
    /**
     * Stop the sequence.
     */
    @ZenCodeType.Method
    public void stop() {
        
        this.sequence.stop();
    }
    
    /**
     * Gets the data provided to the sequence.
     *
     * <p>Note, this can never return null, if the data is null then it will throw an error.</p>
     *
     * @return The data provided to the sequence or an error if null was provided.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("data")
    public U getData() {
        
        if(this.data == null) {
            throw new IllegalStateException("Unable to call 'getData' as data is null!");
        }
        return this.data;
    }
    
}
