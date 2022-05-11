package com.blamejared.crafttweaker.api.util.sequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Manages {@link Sequence}s, handles adding, removing and ticking.
 */
public class SequenceManager {
    
    private static final Map<SequenceType, List<Sequence<?, ?>>> sequences = new HashMap<>();
    private static final Map<SequenceType, List<Sequence<?, ?>>> sequencesView = Collections.unmodifiableMap(sequences);
    
    /**
     * Adds a new Sequence.
     *
     * @param type     The type of Sequence to add.
     * @param sequence The sequence to add.
     */
    public static void addSequence(SequenceType type, Sequence<?, ?> sequence) {
        
        sequences.computeIfAbsent(type, sequenceType -> new LinkedList<>())
                .add(sequence);
    }
    
    /**
     * Removes a sequence.
     *
     * @param type     The type of Sequence to remove.
     * @param sequence The sequence to remove.
     */
    public static void removeSequence(SequenceType type, Sequence<?, ?> sequence) {
        
        sequences.getOrDefault(type, Collections.emptyList())
                .remove(sequence);
    }
    
    /**
     * Tick all sequences for the given type on the given side.
     *
     * @param type The type to tick for.
     */
    public static void tick(SequenceType type) {
        
        if(!sequences.containsKey(type)) {
            return;
        }
        
        Iterator<Sequence<?, ?>> tasks = sequences.get(type).iterator();
        while(tasks.hasNext()) {
            Sequence<?, ?> sequence = tasks.next();
            sequence.tick();
            if(sequence.isComplete() || sequence.isStopped()) {
                tasks.remove();
            }
        }
    }
    
    /**
     * Gets a view of the client side Sequences.
     *
     * @return A map of client side sequences,
     */
    public static Map<SequenceType, List<Sequence<?, ?>>> getSequences() {
        
        return sequencesView;
    }
    
    
    /**
     * Clears the sequences.
     */
    public static void clearSequences() {
        
        sequences.clear();
    }
    
    
    /**
     * Clears the sequences for the given {@link SequenceType}.
     */
    public static void clearSequences(SequenceType type) {
        
        sequences.getOrDefault(type, Collections.emptyList()).clear();
    }
    
}
