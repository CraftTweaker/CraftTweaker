package com.blamejared.crafttweaker.api.logger;

import java.util.Arrays;

public enum LogLevel {
    
    DEBUG, INFO, WARNING, ERROR;

    public boolean canLog(LogLevel other) {
        return this.compareTo(other) <= 0;
    }

    /**
     * The maximum character length of any enum name
     * Used to make print formats easier
     */
    public static final int MAX_LENGTH = Arrays.stream(LogLevel.values())
            .map(Enum::name)
            .mapToInt(String::length)
            .max()
            .orElse(7);

}
