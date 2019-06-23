package com.blamejared.crafttweaker.api.logger;

import java.util.Arrays;

public enum LogLevel {
    
    DEBUG, INFO, WARNING, ERROR;
    
    /**
     * The maximum character length of any enum name
     * Used to make print formats easier
     */
    public static final int MAX_LENGTH = Arrays.stream(LogLevel.values()).map(Enum::name).mapToInt(String::length).max().orElse(7);
    
    public boolean canLog(LogLevel other) {
        return this.compareTo(other) <= 0;
    }
    
    @Override
    public String toString() {
        return String.format("%" + MAX_LENGTH + "s", super.toString());
    }
}
