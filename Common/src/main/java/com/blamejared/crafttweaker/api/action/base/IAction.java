package com.blamejared.crafttweaker.api.action.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.CodePosition;

import javax.annotation.Nonnull;

/**
 * Represents an action that is to be executed through a ZenCode script.
 *
 * <p>All methods that are exposed to ZenCode should use classes that implement this interface or one of its
 * sub-interfaces as required by semantics to ensure proper logging and rollback if necessary.</p>
 *
 * <p>If an action should be executed on every game reload and not only during the first run of a
 * {@linkplain IScriptLoader loader}, refer to {@link IRuntimeAction} instead. If the action requires some additional
 * code to be run to correctly rollback changes, refer to {@link IUndoableAction}.</p>
 *
 * <!-- TODO("Add documentation for IStagedAction") -->
 *
 * @since 9.1.0
 */
// TODO("IStagedAction")
public interface IAction {
    
    /**
     * Applies the action, executing all code necessary.
     *
     * @since 9.1.0
     */
    void apply();
    
    /**
     * Gets a human-readable description of the action.
     *
     * <p>This message is used for logging and to surface information to the user when something goes wrong. It is thus
     * customary to describe the action as accurately as possible without being too verbose.</p>
     *
     * <p>It is not allowed to return a {@code null} or otherwise empty description for the action: doing so will raise
     * an error at runtime.</p>
     *
     * @return A description of the current action.
     *
     * @since 9.1.0
     */
    String describe();
    
    /**
     * Gets the name of the system that is responsible for the execution of the action.
     *
     * <p>The system usually corresponds to the name of the mod that created the action and is thus attempting to apply
     * it. For example, adding a recipe is done through CraftTweaker, so the system name is {@code "CraftTweaker"}. Mods
     * that have various components or would like to separate various integrations from each other can return more
     * complex strings. In other words, if the class that implements this interface belongs to mod <em>Foo</em>, then
     * the system name should be {@code "Foo"} (or {@code "Foo-Bar"} where <em>Bar</em> is the name of the subsystem) in
     * almost all cases.</p>
     *
     * <p>Note that the system name will be used for logging, therefore it is suggested to avoid overly verbose names or
     * acronyms that are not widely known. Returning {@code null} or an empty name is disallowed.</p>
     *
     * @return The name of the system that is responsible for this action.
     *
     * @since 11.0.0
     */
    String systemName();
    
    /**
     * Validates the action, ensuring no erroneous information is present.
     *
     * <p>Implementations should validate all action information and log errors using the provided {@link Logger} if
     * anything is incorrect. It is highly suggested to specify exactly what is wrong in the most precise yet brief way
     * possible, to ensure script writers know why their actions are not being applied.</p>
     *
     * <p>If validation fails for whatever reason, {@link #apply()} will not be called.</p>
     *
     * @param logger Logger object on which to log errors or warnings.
     *
     * @return Whether the action is valid ({@code true}) or not ({@code false}).
     *
     * @implSpec The default implementation assumes that the action is always valid, and logs no errors or warnings.
     * @since 9.1.0
     */
    default boolean validate(final Logger logger) {
        
        return true;
    }
    
    /**
     * Determines whether an action should be applied for scripts loading in the given {@link IScriptLoadSource}.
     *
     * @param source The {@link IScriptLoadSource} responsible for loading the scripts.
     *
     * @return If the action should be applied.
     *
     * @apiNote CraftTweaker provides two load sources by default:
     * {@link CraftTweakerConstants#RELOAD_LISTENER_SOURCE_ID} allows to identify scripts being loaded on the server
     * thread, {@link CraftTweakerConstants#CLIENT_RECIPES_UPDATED_SOURCE_ID} allows identification of scripts loaded on
     * the client thread when joining a server.
     * @implSpec By default, scripts are applied if the load source's ID matches
     * {@link CraftTweakerConstants#RELOAD_LISTENER_SOURCE_ID}.
     * @since 9.1.0
     * @deprecated Override {@link #shouldApplyOn(IScriptLoadSource, Logger)} instead.
     */
    @Deprecated
    default boolean shouldApplyOn(final IScriptLoadSource source) {
        
        return source.id().equals(CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID);
    }
    
    /**
     * Determines whether an action should be applied for scripts loading in the given {@link IScriptLoadSource}.
     *
     * @param source The {@link IScriptLoadSource} responsible for loading the scripts.
     * @param logger The {@link Logger} instance that should be used to log error messages if needed.
     *
     * @return If the action should be applied.
     *
     * @apiNote CraftTweaker provides two load sources by default:
     * {@link CraftTweakerConstants#RELOAD_LISTENER_SOURCE_ID} allows to identify scripts being loaded on the server
     * thread, {@link CraftTweakerConstants#CLIENT_RECIPES_UPDATED_SOURCE_ID} allows identification of scripts loaded on
     * the client thread when joining a server.
     * @implSpec By default, scripts are applied if the load source's ID matches
     * {@link CraftTweakerConstants#RELOAD_LISTENER_SOURCE_ID}.
     * @since 11.0.0
     */
    default boolean shouldApplyOn(final IScriptLoadSource source, final Logger logger) {
        
        return this.shouldApplyOn(source);
    }
    
    /**
     * Retrieves the position in the script file where the action was created.
     *
     * <p>The created {@link CodePosition} will always be a virtual position, meaning that its contents cannot be
     * accessed.</p>
     *
     * @return The {@link CodePosition} where the action was created on, or {@link CodePosition#UNKNOWN} if the
     * information cannot be retrieved.
     *
     * @implNote The method inspects the stacktrace to identify the target position. This means that by default, any
     * staged actions or out-of-context action creation might lead to invalid positions being returned. Staged actions
     * should thus consider overriding this method to report an accurate position.
     * @since 9.1.0
     */
    @Nonnull
    default CodePosition getDeclaredScriptPosition() {
        
        return PositionUtil.getZCScriptPositionFromStackTrace();
    }
    
    /**
     * Ensures that an action is only applied on a certain loader.
     *
     * <p>This method is <strong>not meant to be overridden</strong>, but rather used as an additional check in
     * {@link IAction#shouldApplyOn(IScriptLoadSource)} if needed.</p>
     *
     * <p>If the check fails, this method will also log a warning stating the script position where the error occurred
     * if possible (see {@link #getDeclaredScriptPosition()}) and which loader is the one the action is supposed to be
     * ran on.</p>
     *
     * @param loader The {@link IScriptLoader} the action should be only applied on.
     *
     * @return If this loader matches the one specified as a parameter.
     *
     * @since 9.1.0
     * @deprecated Use {@link #assertLoader(IScriptLoader, Logger)} instead.
     */
    @Deprecated
    default boolean assertLoader(final IScriptLoader loader) {
        
        return this.assertLoader(loader, this.logger());
    }
    
    /**
     * Ensures that an action is only applied on a certain loader.
     *
     * <p>This method is <strong>not meant to be overridden</strong>, but rather used as an additional check in
     * {@link IAction#shouldApplyOn(IScriptLoadSource)} if needed.</p>
     *
     * <p>If the check fails, this method will also log a warning stating the script position where the error occurred
     * if possible (see {@link #getDeclaredScriptPosition()}) and which loader is the one the action is supposed to be
     * ran on.</p>
     *
     * @param loader The {@link IScriptLoader} the action should be only applied on.
     * @param logger The {@link Logger} instance that should be used to log a warning in case the loader does not match.
     *
     * @return If this loader matches the one specified as a parameter.
     *
     * @since 11.0.0
     */
    default boolean assertLoader(final IScriptLoader loader, final Logger logger) {
        
        final IScriptLoader currentLoader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
        if(currentLoader.equals(loader)) {
            
            return true;
        }
        
        logger.warn(
                "Action '{}' ({}) can only be invoked on loader '{}'. You tried to run it on loader '{}'.",
                this.getClass().getName(),
                this.getDeclaredScriptPosition(),
                loader,
                currentLoader
        );
        return false;
    }
    
    /**
     * Obtains the {@link Logger} instance that can be used to log messages for this action.
     *
     * <p>This method is <strong>not meant to be overridden</strong>, but rather used in methods such as
     * {@link #apply()} if needed. Overriding this method will have no effect as CraftTweaker ignores the return value
     * of it.</p>
     *
     * @return The logger used to log messages for this action.
     *
     * @implNote Invoking this method is equivalent to calling {@link CraftTweakerAPI#getLogger(String)} with the
     * system name of {@link #systemName()}.
     * @since 11.0.0
     */
    default Logger logger() {
        
        return CraftTweakerAPI.getLogger(this.systemName());
    }
    
}
