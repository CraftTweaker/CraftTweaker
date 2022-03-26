package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import org.openzen.zencode.shared.SourceFile;

import java.nio.file.Path;
import java.util.List;

/**
 * Manages the creation and lifecycle of {@link IScriptRun}s, while also dealing with {@link IAction}s.
 *
 * @since 9.1.0
 */
public interface IScriptRunManager {
    
    /**
     * Creates a {@link IScriptRun} with the specified {@link ScriptRunConfiguration}.
     *
     * <p>The script run will execute all scripts that are present in the default scripts directory. In this instance, a
     * valid script file is identified by a {@link Path} whose {@link java.nio.file.attribute.BasicFileAttributes}
     * report a {@linkplain java.nio.file.attribute.BasicFileAttributes#isRegularFile() regular file} and whose
     * {@linkplain Path#getFileName() file name} ends with {@code .zs}.</p>
     *
     * @param configuration The configuration of the script run.
     *
     * @return A script run that carries out the specified operations.
     *
     * @since 9.1.0
     */
    IScriptRun createScriptRun(final ScriptRunConfiguration configuration);
    
    /**
     * Creates a {@link IScriptRun} with the specified {@link ScriptRunConfiguration} executing all valid scripts from
     * the given {@code root}.
     *
     * <p>In this instance, a valid script file is identified by a {@link Path} whose
     * {@link java.nio.file.attribute.BasicFileAttributes} report a
     * {@linkplain java.nio.file.attribute.BasicFileAttributes#isRegularFile() regular file} and whose
     * {@linkplain Path#getFileName() file name} ends with {@code .zs}.</p>
     *
     * @param root          The root {@link Path} from which CraftTweaker should look for scripts.
     * @param configuration The configuration of the script run.
     *
     * @return A script run that carries out the specified operations.
     *
     * @since 9.1.0
     */
    IScriptRun createScriptRun(final Path root, final ScriptRunConfiguration configuration);
    
    /**
     * Creates a {@link IScriptRun} with the specified {@link ScriptRunConfiguration} executing the script files in
     * the {@code files} list.
     *
     * <p>In this instance, all {@link Path}s given in {@code files} are assumed to be valid files. The path given as
     * root, on the other hand, is used to properly identify the name of the script file for the ZenCode scripting
     * environment. For this reason, it is <strong>mandatory</strong> for {@code root} to be a valid parent of all files
     * listed in the {@code files} list.</p>
     *
     * @param root          The root which contains all files listed in {@code files}.
     * @param files         The files that CraftTweaker should execute in the script run.
     * @param configuration The configuration of the script run.
     *
     * @return A script run that carries out the specified operations.
     *
     * @since 9.1.0
     */
    IScriptRun createScriptRun(final Path root, final List<Path> files, final ScriptRunConfiguration configuration);
    
    /**
     * Creates a {@link IScriptRun} with the specified {@link ScriptRunConfiguration} executing the given ZenCode
     * {@link SourceFile}s.
     *
     * <p>No restrictions are imposed on the type of the source files given.</p>
     *
     * @param sources       The source files to execute in the script run.
     * @param configuration The configuration of the script run.
     *
     * @return A script run that carries out the specified operations.
     *
     * @since 9.1.0
     */
    IScriptRun createScriptRun(final List<SourceFile> sources, final ScriptRunConfiguration configuration);
    
    /**
     * Obtains the {@link IScriptRunInfo} of the currently executing {@link IScriptRun}, if any.
     *
     * <p>If no script run is currently executing, a {@link NullPointerException} is thrown instead. It is the caller's
     * responsibility to verify that this method can only ever be called during a script run execution.</p>
     *
     * <p>A script run is considered executing only when its {@link IScriptRun#execute()} method is operating.</p>
     *
     * <p>To get information for a specific script run, consider using {@link IScriptRun#specificRunInfo()} instead.</p>
     *
     * @return The information for the currently executing script run, if any.
     *
     * @since 9.1.0
     */
    IScriptRunInfo currentRunInfo();
    
    /**
     * Applies the given action.
     *
     * <p>Application of the action requires a {@link IScriptRun} to be currently executing. Attempting to apply an
     * action outside of a script run will result in an {@link IllegalStateException} being thrown.</p>
     *
     * @param action The action to apply.
     *
     * @since 9.1.0
     */
    void applyAction(final IAction action);
    
}
