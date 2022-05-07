package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The type of a {@link Sequence}.
 *
 * @docParam this SequenceType.LEVEL
 */
@ZenRegister
@Document("vanilla/api/util/sequence/SequenceType")
@ZenCodeType.Name("crafttweaker.api.util.sequence.SequenceType")
@SuppressWarnings("ClassCanBeRecord")
public final class SequenceType {
    
    /**
     * The type of the LEVEL sequence.
     */
    @ZenCodeType.Field
    public static final SequenceType LEVEL = new SequenceType("level");
    
    private final String name;
    
    /**
     * The name of the type.
     *
     * @param name The name of the type.
     */
    public SequenceType(String name) {
        
        this.name = name;
    }
    
    /**
     * Gets the name of the sequence type.
     *
     * @return The name of the sequence type.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter
    public String name() {
        
        return name;
    }
    
}
