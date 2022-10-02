package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;

import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * Holds various utilities related to {@link Path} manipulation.
 *
 * <p><em>Game directory</em> in this context refers to the directory in which the game is set to be loaded. This means
 * the directory containing mods, logs, and scripts.</p>
 *
 * @since 10.0.0
 */
public final class PathUtil {
    
    private static final Supplier<Path> GAME_DIRECTORY = Suppliers.memoize(Services.PLATFORM::getGameDirectory);
    
    private PathUtil() {}
    
    /**
     * Resolves the given path from the current game directory.
     *
     * <p>The given string is automatically converted into a path with the same {@link java.nio.file.FileSystem} as the
     * game directory.</p>
     *
     * @param other The path to resolve
     *
     * @return A new {@link Path} resolving according to the given rules.
     *
     * @see Path#resolve(Path)
     * @since 10.0.0
     */
    public static Path findFromGameDirectory(final String other) {
        
        return findFromGameDirectory(makeSameFileSystemPath(GAME_DIRECTORY.get(), other));
    }
    
    /**
     * Resolves the given path from the current game directory.
     *
     * @param other The path to resolve
     *
     * @return A new {@link Path} resolving according to the given rules.
     *
     * @see Path#resolve(Path)
     * @since 10.0.0
     */
    public static Path findFromGameDirectory(final Path other) {
        
        return GAME_DIRECTORY.get().resolve(other);
    }
    
    /**
     * Changes the given path to be relative to the current game directory.
     *
     * <p>The given string is automatically converted into a path with the same {@link java.nio.file.FileSystem} as the
     * game directory.</p>
     *
     * @param other The path to make relative.
     *
     * @return A new relative {@link Path}.
     *
     * @see Path#relativize(Path)
     * @since 10.0.0
     */
    public static Path makeRelativeToGameDirectory(final String other) {
        
        return makeRelativeToGameDirectory(makeSameFileSystemPath(GAME_DIRECTORY.get(), other));
    }
    
    /**
     * Changes the given path to be relative to the current game directory.
     *
     * @param other The path to make relative.
     *
     * @return A new relative {@link Path}.
     *
     * @see Path#relativize(Path)
     * @since 10.0.0
     */
    public static Path makeRelativeToGameDirectory(final Path other) {
        
        return GAME_DIRECTORY.get().toAbsolutePath().relativize(other.toAbsolutePath());
    }
    
    /**
     * Creates a new path with the same underlying {@link java.nio.file.FileSystem} as the specified one.
     *
     * @param original The path of which the file system should be obtained.
     * @param other    The path to create in the file system.
     *
     * @return A new {@link Path} pointing to {@code other} with the same FS as {@code original}.
     *
     * @see java.nio.file.FileSystem#getPath(String, String...)
     * @since 10.0.0
     */
    public static Path makeSameFileSystemPath(final Path original, final String other) {
        
        return original.getFileSystem().getPath(other);
    }
    
}
