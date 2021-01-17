package com.blamejared.crafttweaker.impl.loot.conditions;

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
