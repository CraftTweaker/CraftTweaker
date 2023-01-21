package com.blamejared.crafttweaker.api.logging;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.google.common.base.Suppliers;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * Provide a series of {@link Logger} instances for the most common systems.
 *
 * <p>Usage of these loggers is encouraged whenever possible to ensure consistency in the logging. Refer to the
 * documentation of the various methods for additional details relating to the specific system.</p>
 *
 * @see CraftTweakerAPI#getLogger(String)
 * @since 10.1.0
 */
public final class CommonLoggers {
    
    private static final Supplier<Logger> COMMANDS = loggerSupplier(ctSystem("Commands"));
    private static final Supplier<Logger> OWN = loggerSupplier(CraftTweakerConstants.MOD_NAME);
    private static final Supplier<Logger> ZEN_CODE = loggerSupplier(ctSystem("ZenCode"));
    
    private CommonLoggers() {}
    
    /**
     * Obtains the logger for the command system.
     *
     * <p>Usage of this logger is suggested for actions concerning user commands that are executed through one of the
     * subcommands of {@code /ct}. Example of such actions include confirmation of actions, responses, and dumps of
     * various objects.</p>
     *
     * <p>Commands should prefer referencing
     * {@link com.blamejared.crafttweaker.api.command.CommandUtilities#COMMAND_LOGGER} over using this method directly
     * for more API compatibility.</p>
     *
     * @return The logger for the command system.
     *
     * @since 10.1.0
     */
    public static Logger commands() {
        
        return COMMANDS.get();
    }
    
    /**
     * Obtains the logger used by the CraftTweaker API.
     *
     * <p>Integration writers should <strong><em>NEVER</em></strong> need usage of this logger: it is merely provided as
     * a way for the CraftTweaker API to log messages safely. Integration writers should instead prefer their own
     * logger, for ease of system tracking.</p>
     *
     * @return The CraftTweaker API's own logger.
     *
     * @since 10.1.0
     */
    public static Logger own() {
        
        return OWN.get();
    }
    
    /**
     * Obtains the logger used by the ZenCode system.
     *
     * <p>Usages of this logger is suggested for actions related to script execution and discovery, along with warning
     * and error messages emitted by
     * {@linkplain com.blamejared.crafttweaker.api.annotation.BracketValidator bracket validators} and
     * {@linkplain com.blamejared.crafttweaker.api.annotation.BracketResolver bracket resolvers}.</p>
     *
     * @return The logger for the ZenCode system.
     *
     * @since 10.1.0
     */
    public static Logger zenCode() {
        
        return ZEN_CODE.get();
    }
    
    private static Supplier<Logger> loggerSupplier(final String system) {
        
        return Suppliers.memoize(() -> CraftTweakerAPI.getLogger(system));
    }
    
    private static String ctSystem(final String system) {
        
        return CraftTweakerConstants.MOD_NAME + '-' + system;
    }
    
}
