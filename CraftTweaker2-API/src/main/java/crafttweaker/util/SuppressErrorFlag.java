package crafttweaker.util;

/**
 * @author youyihj
 */
public enum SuppressErrorFlag {

    /**
     * All errors and warnings sent to log will be printed  to players' chat.
     */
    DEFAULT,

    /**
     * Only errors sent to log will be printed to players' chat. In other words, suppress warnings.
     * Used for #nowarn preprocessor
     */
    ONLY_WARNINGS,

    /**
     * Warnings and errors sent to log won't be printed to players' chat
     * Used for #ikwid preprocessor
     */
    ALL,

    /**
     * Force all warnings and errors to be printed to players' chat, ignoring two preprocessors above
     * Used for /ct syntax command
     */
    FORCED;

    public boolean isForced() {
        return this.equals(FORCED);
    }

    public boolean isSuppressingWarnings() {
        return this.equals(ONLY_WARNINGS) || this.equals(ALL);
    }

    public boolean isSuppressingErrors() {
        return this.equals(ALL);
    }
}
