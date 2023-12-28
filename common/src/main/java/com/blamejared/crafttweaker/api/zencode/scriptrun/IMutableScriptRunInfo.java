package com.blamejared.crafttweaker.api.zencode.scriptrun;

/**
 * Mutable version of {@link IScriptRunInfo} that allows changing certain information.
 *
 * @since 9.1.0
 */
public interface IMutableScriptRunInfo extends IScriptRunInfo {
    
    /**
     * Sets whether the mod should print its branding message in the log.
     *
     * @param displayBranding Whether the branding message should be displayed.
     *
     * @see #displayBranding()
     * @since 9.1.0
     */
    void displayBranding(final boolean displayBranding);
    
    /**
     * Sets whether the generated classes should be dumped for debugging purposes.
     *
     * @param dumpClasses Whether the generated classes should be dumped.
     *
     * @see #dumpClasses()
     * @since 9.1.0
     */
    void dumpClasses(final boolean dumpClasses);
    
}
