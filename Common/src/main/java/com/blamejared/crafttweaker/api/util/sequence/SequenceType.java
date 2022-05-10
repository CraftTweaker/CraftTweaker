package com.blamejared.crafttweaker.api.util.sequence;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents the type of 'actor' a {@link Sequence} acts on and is used to ensure that {@link Sequence}s are ticked correctly.
 *
 * <p>Some examples are a {@link net.minecraft.world.level.Level} or a {@link net.minecraft.world.entity.LivingEntity} (which is not yet implemented).</p>
 *
 * <p>Mods adding custom sequenceable actors (really anything that can be ticked) should make their own SequenceType and expose it to ZenScript.</p>
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
