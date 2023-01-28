package com.blamejared.crafttweaker.api.zencode.scriptrun;

import java.nio.file.Path;
import java.util.List;

/**
 * Holds the configuration that should be followed during automatic script discovery for the creation of a
 * {@link IScriptRun}.
 *
 * @param suspiciousNamesBehavior The behavior to use whenever a file with a
 *                                {@linkplain SuspiciousNamesBehavior suspicious name} is encountered during discovery.
 * @param retainer                The {@link DiscoveryRetainer} to invoke to store the list of discovered files.
 *
 * @since 10.1.0
 */
public record ScriptDiscoveryConfiguration(SuspiciousNamesBehavior suspiciousNamesBehavior,
                                           DiscoveryRetainer retainer) {
    
    /**
     * Creates a {@link ScriptDiscoveryConfiguration} with the given behavior and a no-op {@link DiscoveryRetainer}.
     *
     * @param suspiciousNamesBehavior The behavior to use whenever a file with a
     *                                {@linkplain SuspiciousNamesBehavior suspicious name} is encountered during
     *                                discovery.
     *
     * @since 10.1.0
     */
    public ScriptDiscoveryConfiguration(final SuspiciousNamesBehavior suspiciousNamesBehavior) {
        
        this(suspiciousNamesBehavior, (root, files) -> {});
    }
    
    /**
     * Specifies the behavior to be used when a file with a suspicious name is encountered.
     *
     * <p>A file is deemed to be with a suspicious name if its name ends with {@code .zs.txt}, as it indicates that the
     * user has likely made a mistake when creating the file.</p>
     *
     * @since 10.1.0
     */
    public enum SuspiciousNamesBehavior {
        /**
         * Indicates that any file with a suspicious name should be ignored.
         *
         * @since 10.1.0
         */
        IGNORE,
        /**
         * Indicates that any file with a suspicious name should raise a warning in the logs, but should otherwise be
         * ignored for the purposes of loading.
         *
         * @since 10.1.0
         */
        WARN
    }
    
    /**
     * Represents a callback that can be used to retain the results of the discovery process.
     *
     * <p>This callback will be invoked once at the end of the discovery phase, allowing for further operations to be
     * performed with the script files that the automatic discovery process has identified. In particular, this callback
     * is guaranteed to be invoked before a {@link IScriptRun} instance will be created.</p>
     *
     * <p>This is a {@linkplain FunctionalInterface functional interface} whose functional method is
     * {@link #retain(Path, List)}.</p>
     *
     * @since 10.1.0
     */
    @FunctionalInterface
    public interface DiscoveryRetainer {
        
        /**
         * Performs the retaining operations that are deemed necessary.
         *
         * @param root             The path from which script discovery has begun.
         * @param discoveryResults The paths that have been discovered and deemed valid scripts.
         *
         * @since 10.1.0
         */
        void retain(final Path root, final List<Path> discoveryResults);
        
    }
    
}
