package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import org.openzen.zencode.shared.SourceFile;

import java.util.List;
import java.util.Optional;

/**
 * Represents a script file which will be read during an {@link IScriptRun}.
 *
 * <p>The file might refer to a real file stored on disk, a fake file generated on the fly, or any other source of
 * ZenCode code. No assumptions are made on the source of the file.</p>
 *
 * @since 9.1.0
 */
public interface IScriptFile {
    
    /**
     * The name of the script file.
     *
     * @return The name of the script file.
     *
     * @since 9.1.0
     */
    String name();
    
    /**
     * Gets the unaltered contents of the file.
     *
     * <p>The returned list is read only and is supposed to represent the effective state of the file prior to any
     * preprocessing operations that might be carried out.</p>
     *
     * @return The original file contents.
     *
     * @since 9.1.0
     */
    List<String> fileContents();
    
    /**
     * Gets the contents of the file after preprocessing.
     *
     * <p>The returned list is read only.</p>
     *
     * @return The content of the file after preprocessing.
     *
     * @since 9.1.0
     */
    List<String> preprocessedContents();
    
    /**
     * Attempts to convert this script file into a ZenCode {@link SourceFile}.
     *
     * <p>If conversion fails for any reason, either due to an error or because the file should not be exposed to
     * ZenCode, then an {@linkplain Optional#empty() empty optional} will be returned.</p>
     *
     * @return An {@link Optional} wrapping a {@link SourceFile} representing this file, if possible; an empty optional
     * otherwise.
     *
     * @since 9.1.0
     */
    Optional<SourceFile> toSourceFile();
    
    /**
     * Gets all preprocessor matches for the given preprocessor in the current file.
     *
     * <p>Refer to {@link IPreprocessor} and {@link IPreprocessor.Match} for more information.</p>
     *
     * @param preprocessor The preprocessor whose matches should be queried.
     *
     * @return A read-only list containing all matches for that specific preprocessor.
     *
     * @since 9.1.0
     */
    List<IPreprocessor.Match> matchesFor(final IPreprocessor preprocessor);
    
    /**
     * Gets whether the file has any matches for the specified preprocessor.
     *
     * <p>Refer to {@link IPreprocessor} and {@link IPreprocessor.Match} for more information.</p>
     *
     * @param preprocessor The preprocessor whose matches should be queried.
     *
     * @return Whether there is at least one match for the given preprocessor.
     *
     * @since 9.1.0
     */
    default boolean hasMatchesFor(final IPreprocessor preprocessor) {
        
        return !this.matchesFor(preprocessor).isEmpty();
    }
    
}
