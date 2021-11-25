package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a value that can assume three values, instead of a simple boolean that can only assume two values.
 */
@ZenRegister()
@ZenCodeType.Name("crafttweaker.api.predicate.TriState")
@Document("vanilla/api/predicate/TriState")
public enum TriState {
    /**
     * The true value of the TriState.
     *
     * It can be considered the same as a boolean's 'true' value.
     */
    TRUE(true),
    /**
     * The false value of the TriState.
     *
     * It can be considered the same as a boolean's 'false' value.
     */
    FALSE(false),
    /**
     * The unset value of the TriState.
     *
     * It represents the lack of a state or a decision that hasn't been made yet.
     */
    UNSET(null);
    
    private final Boolean bool;
    
    TriState(final Boolean bool) {
        
        this.bool = bool;
    }
    
    public Boolean toBoolean() {
        
        return this.bool;
    }
    
    public boolean matchOrUnset(final boolean toMatch) {
        
        return this.isUnset() || this.match(toMatch);
    }
    
    public boolean match(final boolean toMatch) {
        
        return (toMatch && this == TRUE) || (!toMatch && this == FALSE);
    }
    
    public boolean isUnset() {
        
        return this == UNSET;
    }
}
