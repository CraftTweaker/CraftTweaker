package com.blamejared.crafttweaker.api.zencode.scriptrun;

/**
 * Represents an execution of one or more {@link IScriptFile}s.
 *
 * <p>A script run can be created through the {@link IScriptRunManager} and is bound to a specific
 * {@linkplain IScriptRunInfo set of information}, which dictates parameters that must be followed by the run. When a
 * script run is executed, its run information is also globally available for usage by scripts and actions through the
 * {@code IScriptRunManager}.</p>
 *
 * <p>A run can be created but does not have to be executed immediately: it is in fact possible to create a run and then
 * store it for later use. Note that this kind of behavior is not suggested as it might lead to weird interactions, but
 * is nevertheless a valid use case.</p>
 *
 * <p>It is <strong>illegal</strong> at all times to attempt to execute two script runs at the same time: such attempts
 * will be blocked by the script run manager.</p>
 *
 * @since 9.1.0
 */
public interface IScriptRun {
    
    /**
     * Gets the {@link IScriptRunInfo} tied to this specific run.
     *
     * @return This run's information.
     *
     * @since 9.1.0
     */
    IScriptRunInfo specificRunInfo();
    
    /**
     * Executes the script run.
     *
     * @throws Throwable If any errors occur during execution.
     * @since 9.1.0
     */
    void execute() throws Throwable;
    
}
