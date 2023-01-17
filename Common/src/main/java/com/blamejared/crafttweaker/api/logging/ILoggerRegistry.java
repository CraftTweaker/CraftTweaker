package com.blamejared.crafttweaker.api.logging;

import org.apache.logging.log4j.Logger;

/**
 * Manages the {@link Logger} instances for the various systems.
 *
 * <p>An instance of this class can be obtained through {@link com.blamejared.crafttweaker.api.CraftTweakerAPI}.</p>
 *
 * @since 10.1.0
 */
public interface ILoggerRegistry {
    
    /**
     * Obtains a {@link Logger} for logging messages related to the specified system.
     *
     * <p>The given logger is automatically configured to output messages to {@code crafttweaker.log} with the correct
     * format. It can be therefore used directly.</p>
     *
     * <p>The system name can be any string, but it usually suggested to use the mod name (e.g. {@code "CraftTweaker"}
     * or {@code "Mekanism"}). In case the mod name is not specific enough, because there are multiple integrations or
     * the developer wants to be more specific, an additional specifier can be appended to the mod name with a dash
     * (e.g. {@code "CraftTweaker-ZenCode"} or {@code "Mekanism-Content"}). Other formats are allowed, but discouraged
     * as a matter of conventions.</p>
     *
     * @param system The name of the system.
     *
     * @return A {@link Logger} instance tailored to the given system.
     *
     * @since 10.1.0
     */
    Logger getLoggerFor(final String system);
    
}
