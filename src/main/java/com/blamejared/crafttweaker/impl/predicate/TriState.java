package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister()
@ZenCodeType.Name("crafttweaker.api.predicate.TriState")
@Document("vanilla/api/predicate/TriState")
public enum TriState {
    TRUE(true),
    FALSE(false),
    UNSET(null);

    private final Boolean bool;

    TriState(final Boolean bool) {
        this.bool = bool;
    }

    public Boolean toBoolean() {
        return this.bool;
    }
}
