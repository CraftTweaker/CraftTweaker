package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.zencode.expand.IDataRewrites;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.helper.IAccessibleElementsProvider;
import com.google.common.base.Suppliers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openzen.zenscript.parser.expression.ParsedExpressionArray;
import org.openzen.zenscript.parser.expression.ParsedExpressionMap;

import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * Provides access to the main CraftTweaker API.
 *
 * @since 9.1.0
 */
public final class CraftTweakerAPI {
    
    private static final Supplier<IAccessibleElementsProvider> ACCESSIBLE_ELEMENTS = Suppliers.memoize(Services.BRIDGE::accessibleElementsProvider);
    private static final Supplier<ICraftTweakerRegistry> REGISTRY = Suppliers.memoize(Services.BRIDGE::registry);
    private static final Supplier<IScriptRunManager> SCRIPT_RUN_MANAGER = Suppliers.memoize(Services.BRIDGE::scriptRunManager);
    private static final Supplier<Path> SCRIPTS_DIRECTORY = Suppliers.memoize(() -> Services.PLATFORM.getPathFromGameDirectory(CraftTweakerConstants.SCRIPTS_DIRECTORY));
    
    // Do we want to make a log4j wrapper and expose it to a script...? 😬
    /**
     * Logger used to log CraftTweaker specific messages.
     *
     * <p>This logger is also wired to the {@code crafttweaker.log} file.</p>
     *
     * @since 9.1.0
     */
    public static final Logger LOGGER = LogManager.getLogger(CraftTweakerLogger.LOGGER_NAME);
    
    static {
        ParsedExpressionMap.compileOverrides.add(IDataRewrites::rewriteMap);
        ParsedExpressionArray.compileOverrides.add(IDataRewrites::rewriteArray);
    }
    
    /**
     * Provides access to the {@link IAccessibleElementsProvider}, allowing for more internal access.
     *
     * @return The {@link IAccessibleElementsProvider}.
     *
     * @since 9.1.0
     */
    public static IAccessibleElementsProvider getAccessibleElementsProvider() {
        
        return ACCESSIBLE_ELEMENTS.get();
    }
    
    /**
     * Provides access to the main CraftTweaker set of registries.
     *
     * @return An instance of {@link ICraftTweakerRegistry}.
     *
     * @since 9.1.0
     */
    public static ICraftTweakerRegistry getRegistry() {
        
        return REGISTRY.get();
    }
    
    /**
     * Obtains the {@link IScriptRunManager} used to create and execute script runs.
     *
     * @return The script run manager.
     *
     * @since 9.1.0
     */
    public static IScriptRunManager getScriptRunManager() {
        
        return SCRIPT_RUN_MANAGER.get();
    }
    
    /**
     * Obtains a {@link Path} to the default {@code scripts} directory used by CraftTweaker.
     *
     * @return The default scripts directory.
     *
     * @since 9.1.0
     */
    public static Path getScriptsDirectory() {
        
        return SCRIPTS_DIRECTORY.get();
    }
    
    /**
     * Applies the given {@link IAction}.
     *
     * @param action The action to apply.
     *
     * @see IScriptRunManager#applyAction(IAction)
     * @since 9.1.0
     */
    public static void apply(final IAction action) {
        
        getScriptRunManager().applyAction(action);
    }
    
}
