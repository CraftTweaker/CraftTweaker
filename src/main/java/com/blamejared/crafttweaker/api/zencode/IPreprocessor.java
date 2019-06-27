package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.zencode.impl.*;

import javax.annotation.*;
import java.util.*;
import java.util.regex.*;

public interface IPreprocessor {
    
    Pattern preprocessorPattern = Pattern.compile("#\\w+");
    
    /**
     * The preprocessor's name.
     * May not contain whitespaces, and is what comes after the {@code #} in the scripts.
     * The {@code #} is not a part of the name!
     */
    String getName();
    
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
    boolean apply(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches);
}
