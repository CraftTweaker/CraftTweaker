package com.blamejared.crafttweaker.api.util.sequence;

import java.util.*;

/**
 * Manages {@link Sequence}s, handles adding, removing and ticking.
 */
public class SequenceManager {
    
    private static final Map<SequenceType, List<Sequence<?, ?>>> clientSequences = new HashMap<>();
    private static final Map<SequenceType, List<Sequence<?, ?>>> clientSequencesView = Collections.unmodifiableMap(clientSequences);
    private static final Map<SequenceType, List<Sequence<?, ?>>> serverSequences = new HashMap<>();
    private static final Map<SequenceType, List<Sequence<?, ?>>> serverSequencesView = Collections.unmodifiableMap(serverSequences);
    
    /**
     * Adds a new Sequence.
     *
     * @param type         The type of Sequence to add.
     * @param sequence     The sequence to add.
     * @param isClientSide Is the sequence on the client side.
     */
    public static void addSequence(SequenceType type, Sequence<?, ?> sequence, boolean isClientSide) {
        
        (isClientSide ? clientSequences : serverSequences).computeIfAbsent(type, sequenceType -> new LinkedList<>())
                .add(sequence);
    }
    
    /**
     * Removes a sequence.
     *
     * @param type         The type of Sequence to remove.
     * @param sequence     The sequence to remove.
     * @param isClientSide Is the sequence on the client side.
     */
    public static void removeSequence(SequenceType type, Sequence<?, ?> sequence, boolean isClientSide) {
        
        (isClientSide ? clientSequences : serverSequences).getOrDefault(type, Collections.emptyList())
                .remove(sequence);
    }
    
    /**
     * Tick all sequences for the given type on the given side.
     *
     * @param type         The type to tick for.
     * @param isClientSide Are we ticking on the client thread.
     */
    public static void tick(SequenceType type, boolean isClientSide) {
        
        if(!getSequences(isClientSide).containsKey(type)) {
            return;
        }
        
        Iterator<Sequence<?, ?>> tasks = getSequences(isClientSide).get(type).iterator();
        while(tasks.hasNext()) {
            Sequence<?, ?> sequence = tasks.next();
            sequence.tick();
            if(sequence.isComplete() || sequence.isStopped()) {
                tasks.remove();
            }
        }
    }
    
    /**
     * Gets a view of the Sequences for the given side.
     *
     * @return A map of sequences for the given side,
     */
    private static Map<SequenceType, List<Sequence<?, ?>>> getSequences(boolean isClientSide) {
        
        return (isClientSide ? clientSequences : serverSequences);
    }
    
    /**
     * Gets a view of the client side Sequences.
     *
     * @return A map of client side sequences,
     */
    public static Map<SequenceType, List<Sequence<?, ?>>> getClientSequences() {
        
        return clientSequencesView;
    }
    
    /**
     * Gets a view of the server side Sequences.
     *
     * @return A map of server side sequences,
     */
    public static Map<SequenceType, List<Sequence<?, ?>>> getServerSequences() {
        
        return serverSequencesView;
    }
    
    /**
     * Clears the sequences.
     */
    public static void clearSequences() {
        
        clearSequences(true);
        clearSequences(false);
    }
    
    /**
     * Clears the sequences for the given side.
     */
    public static void clearSequences(boolean isClientSide) {
        
        (isClientSide ? clientSequences : serverSequences).clear();
    }
    
}
