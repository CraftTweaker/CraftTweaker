package com.blamejared.crafttweaker.api.plugin;

/**
 * Identifies a CraftTweaker Plugin.
 *
 * <p>Classes that implement this interface <strong>must</strong> be annotated with {@link CraftTweakerPlugin} to
 * enable automatic discovery by CraftTweaker. Refer to the documentation of the annotation for more details on class
 * structure.</p>
 *
 * <p>All the methods will be invoked automatically by CraftTweaker as needed. Methods should not rely on global state
 * when performing their tasks.</p>
 *
 * @since 9.1.0
 */
public interface ICraftTweakerPlugin {
    
    /**
     * Manages plugin initialization tasks that might be required prior to the plugin lifecycle.
     *
     * <p>This method is guaranteed to be called before any other registration methods, but after all plugins have
     * been discovered.</p>
     *
     * @since 10.0
     */
    default void initialize() {}
    
    /**
     * Manages the registration of {@link com.blamejared.crafttweaker.api.zencode.IScriptLoader}s.
     *
     * @param handler The handler responsible for registration.
     *
     * @see ILoaderRegistrationHandler
     * @see com.blamejared.crafttweaker.api.zencode.IScriptLoader
     * @since 9.1.0
     */
    default void registerLoaders(final ILoaderRegistrationHandler handler) {}
    
    /**
     * Manages the registration of {@link com.blamejared.crafttweaker.api.zencode.IScriptLoadSource}s.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IScriptLoadSourceRegistrationHandler
     * @see com.blamejared.crafttweaker.api.zencode.IScriptLoadSource
     * @since 9.1.0
     */
    default void registerLoadSource(final IScriptLoadSourceRegistrationHandler handler) {}
    
    /**
     * Manages the registration of
     * {@link com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator}s.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IScriptRunModuleConfiguratorRegistrationHandler
     * @see com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator
     * @since 9.1.0
     */
    default void registerModuleConfigurators(final IScriptRunModuleConfiguratorRegistrationHandler handler) {}
    
    /**
     * Manages the registration of bracket expression parsers.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IBracketParserRegistrationHandler
     * @since 9.1.0
     */
    default void registerBracketParsers(final IBracketParserRegistrationHandler handler) {}
    
    /**
     * Manages the registration of {@link com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent}s.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IRecipeComponentRegistrationHandler
     * @see com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent
     * @since 10.0
     */
    default void registerRecipeComponents(final IRecipeComponentRegistrationHandler handler) {}
    
    /**
     * Manages the registration of {@link com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler}s.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IRecipeHandlerRegistrationHandler
     * @see com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler
     * @since 9.1.0
     */
    default void registerRecipeHandlers(final IRecipeHandlerRegistrationHandler handler) {}
    
    /**
     * Manages the registration of all components required to integrate ZenCode scripts and Java.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IJavaNativeIntegrationRegistrationHandler
     * @since 9.1.0
     */
    default void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {}
    
    /**
     * Manages the registration of additional listeners invoked at specific points during CraftTweaker's lifecycle.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IListenerRegistrationHandler
     * @since 9.1.0
     */
    default void registerListeners(final IListenerRegistrationHandler handler) {}
    
    /**
     * Manages the registration of elements necessary to manipulate villager trades.
     *
     * @param handler The handler responsible for registration.
     *
     * @see IVillagerTradeRegistrationHandler
     * @since 9.1.0
     */
    default void registerVillagerTradeConverters(final IVillagerTradeRegistrationHandler handler) {}
    
    /**
     * Manages the registration of commands for the {@code /ct} main command.
     *
     * @param handler The handler responsible for registration.
     *
     * @see ICommandRegistrationHandler
     * @since 9.1.0
     */
    default void registerCommands(final ICommandRegistrationHandler handler) {}
    
    /**
     * Manages the registration of taggable elements and their tag managers.
     *
     * @param handler The handler responsible for registration.
     *
     * @see ITaggableElementRegistrationHandler
     * @since 9.1.0
     */
    default void registerTaggableElements(final ITaggableElementRegistrationHandler handler) {}
    
    /**
     * Manages the registration of components tied to the {@link Replacer} system in CraftTweaker.
     *
     * @param handler The handler responsible for registration
     *
     * @see IReplacerComponentRegistrationHandler
     * @since 10.0
     */
    default void registerReplacerComponents(final IReplacerComponentRegistrationHandler handler) {}
    
}
