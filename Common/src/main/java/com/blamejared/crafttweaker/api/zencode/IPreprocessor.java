package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Identifies a preprocessor for an {@link IScriptFile}.
 *
 * <p>A preprocessor is a piece of code that reads the contents of the file and modifies them according to various
 * instructions present in the file itself. Each preprocessor instruction starts with a pound sign ({@code #}), followed
 * by the name of the preprocessor and optional arguments. The line on which the pound sign is located, along with the
 * name and the arguments identify a {@linkplain IPreprocessor.Match preprocessor match}.</p>
 *
 * <p>A preprocessor is allowed to modify the contents of the file, edit the information for the current run, change the
 * order in which script files will be evaluated, and prevent a script from loading.</p>
 *
 * @since 9.1.0
 */
public interface IPreprocessor extends Comparator<IScriptFile> {
    
    /**
     * Represents a preprocessor match.
     *
     * <p>A preprocessor match is found whenever a line with a pound sign mentioning a valid preprocessor is found when
     * reading the file.</p>
     *
     * @param preprocessor The {@link IPreprocessor} mentioned by the instruction.
     * @param line         The line in the file at which the instruction was identified.
     * @param content      The arguments given to the preprocessor in this specific match.
     *
     * @since 9.1.0
     */
    record Match(IPreprocessor preprocessor, int line, String content) {}
    
    /**
     * Pattern that identifies the structure of a preprocessor instruction.
     *
     * @since 9.1.0
     */
    Pattern PREPROCESSOR_PATTERN = Pattern.compile("#\\w+ ?");
    
    /**
     * Logger that can be used within a preprocessor to log messages.
     *
     * @since 11.0.0
     */
    Logger PREPROCESSOR_LOGGER = CommonLoggers.zenCode();
    
    /**
     * Gets the name of the preprocessor.
     *
     * <p>It is illegal for a preprocessor name to contain spaces. It is also customary, although not required, for the
     * preprocessor name to be all lowercase.</p>
     *
     * <p>It is <strong>not</strong> allowed to specify the pound symbol as part of the name.</p>
     *
     * @return The name of the preprocessor.
     *
     * @since 9.1.0
     */
    String name();
    
    /**
     * Gets the name of the preprocessor that marks the end of a preprocessor block.
     *
     * <p>A preprocessor block is identified as a group of file lines, which can be either other preprocessor
     * instructions or lines of code, contained between a "begin" marker and an "end" marker. It is up to the
     * preprocessor to determine the begin and end markers. It is also up to the preprocessor to manage nesting of
     * blocks if such a behavior is desired.</p>
     *
     * @return The name of the instruction that closes the block, or {@link #name()} if no block is desired.
     *
     * @since 9.1.0
     */
    default String preprocessorEndMarker() {
        
        return this.name();
    }
    
    /**
     * Gets the priority of the preprocessor.
     *
     * <p>The higher the priority is, the earlier the preprocessor will be queried.</p>
     *
     * @return The priority of the preprocessor.
     *
     * @since 9.1.0
     */
    default int priority() {
        
        return 10;
    }
    
    /**
     * Gets the default value of a preprocessor.
     *
     * <p>{@code null} is allowed and means that no default value is provided.</p>
     *
     * @return The default value of the preprocessor.
     *
     * @since 9.1.0
     */
    @Nullable
    String defaultValue();
    
    /**
     * Applies the preprocessor to the {@link IScriptFile} and the {@link IMutableScriptRunInfo}.
     *
     * <p>Each preprocessor for which at least one {@linkplain Match match} has been identified or that defines a
     * {@linkplain #defaultValue() non-null default value} will be called. This method will regardless be called at most
     * once for every file in a single script run.</p>
     *
     * <p>It is possible for a preprocessor to modify the file contents by acting directly on the
     * {@code preprocessedContents} list, whereas the run information can be altered through the {@code runInfo}
     * parameter. All other information is read-only.</p>
     *
     * <p>A preprocessor can also prevent loading of the file and further pre-processing by returning {@code false} in
     * this method.</p>
     *
     * @param file                 The file on which the preprocessor is being invoked on.
     * @param preprocessedContents The contents of the file, as preprocessed by other preprocessors. If this
     *                             preprocessor wants to modify the file contents, it is allowed to act directly on this
     *                             list.
     * @param runInfo              an {@link IMutableScriptRunInfo} representing information related to the run and
     *                             allowing modification if desired.
     * @param matches              A read-only list containing all preprocessor matches in the current file. The matches
     *                             are guaranteed to be ordered from top to bottom.
     *
     * @return Whether the file is still eligible for loading ({@code true}) or if it should be discarded
     * ({@code false}).
     *
     * @since 9.1.0
     */
    boolean apply(final IScriptFile file, final List<String> preprocessedContents, final IMutableScriptRunInfo runInfo, final List<Match> matches);
    
    /**
     * Compares the two script files, allowing the preprocessor to alter their ordering.
     *
     * <p>This method might get called even if the current preprocessor has no matches in the file. It is thus suggested
     * to verify the presence of the preprocessor through {@link IScriptFile#hasMatchesFor(IPreprocessor)} instead of
     * assuming its presence.</p>
     *
     * @param a The first script file to compare.
     * @param b The second script file to compare.
     *
     * @return {@code 0} if the files are to be considered equal, {@code -1} if {@code a} comes before {@code b},
     * {@code 1} otherwise.
     *
     * @implSpec By default, all files are considered equal.
     * @since 9.1.0
     */
    @Override
    default int compare(final IScriptFile a, final IScriptFile b) {
        
        return 0;
    }
    
}
