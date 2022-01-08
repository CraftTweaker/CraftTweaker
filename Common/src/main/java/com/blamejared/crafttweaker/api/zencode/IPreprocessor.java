package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public interface IPreprocessor extends Comparator<FileAccessSingle> {
    
    Pattern preprocessorPattern = Pattern.compile("#\\w+ ?");
    
    /**
     * The preprocessor's name.
     * May not contain whitespaces, and is what comes after the {@code #} in the scripts.
     * The {@code #} is not a part of the name!
     */
    String getName();
    
    /**
     * Gets the name of the preprocessor that acts as the "end" of a preprocessor block.
     *
     * This is primarily used for the "onlyif" preprocessor.
     *
     * @return The name of the preprocessor that acts as the "end" of a preprocessor block.
     */
    default String getMatchEnder() {
        
        return getName();
    }
    
    /**
     * The Preprocessor's priority, the higher the earlier it will be checked
     */
    default int getPriority() {
        
        return 10;
    }
    
    /**
     * Returns the default value, or {@code null} if none is present
     */
    @Nullable
    String getDefaultValue();
    
    /**
     * Perform any changes to the script's content that you need to
     * Will be called once per preprocessor that either has a match in the script or whose {@link #getDefaultValue()} is not {@code null}
     * Once a preprocessor returns {@code false} the script is discarded and longer sent to subsequent preprocessors
     *
     * @return {@code false} if the script is no longer eligible to be loaded
     */
    boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches);
    
    
    /**
     * If your Preprocessor changes the order of script execution, then you can override this method.
     *
     * Careful, this will be called for every preprocessor, regardless of whether it is actually in the compared files or not
     * You can use {@link FileAccessSingle#hasMatchFor(IPreprocessor)} to see if the preprocessor is in the files
     */
    @Override
    default int compare(FileAccessSingle o1, FileAccessSingle o2) {
        
        return 0;
    }
    
}
