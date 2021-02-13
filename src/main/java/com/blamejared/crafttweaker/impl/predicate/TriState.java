package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister()
@ZenCodeType.Name("crafttweaker.api.predicate.TriState")
@Document("vanilla/api/predicate/TriState")
public enum TriState {
    @ZenCodeType.Field TRUE(true),
    @ZenCodeType.Field FALSE(false),
    @ZenCodeType.Field UNSET(null);

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
