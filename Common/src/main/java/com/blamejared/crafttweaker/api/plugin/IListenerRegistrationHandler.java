package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Handles registration of additional listeners that will be invoked at certain points during CraftTweaker's lifecycle.
 *
 * <p>Each listener is essentially a {@link Runnable} that will be enqueued and will wait for the right conditions
 * before being called. This allows plugins to perform additional work when CraftTweaker reaches a certain stage of
 * loading.</p>
 *
 * <p>Listeners have no implied ordering guarantees.</p>
 *
 * @since 9.1.0
 */
public interface IListenerRegistrationHandler {
    
    /**
     * Registers the given {@link Runnable} as a listener for when CraftTweaker has finished managing integration with
     * the ZenCode environment.
     *
     * <p>The listener will be called as soon as all script-related data has been successfully registered. This includes
     * bracket handlers, loaders, load sources, native types, and zen types.</p>
     *
     * @param runnable The runnable that will be invoked.
     *
     * @since 9.1.0
     */
    void onZenDataRegistrationCompletion(final Runnable runnable);
    
    /**
     * Registers the given {@link Runnable} as a listener for when CraftTweaker has completed its initialization cycle.
     *
     * <p>The listener will thus be called last after all internal CraftTweaker state has been successfully built. Minor
     * operations may have also been carried out by CraftTweaker to set up additional context. It is nevertheless
     * guaranteed that this listener will be called before the loader with the name indicated by
     * {@link com.blamejared.crafttweaker.api.CraftTweakerConstants#INIT_LOADER_NAME} is called.</p>
     *
     * @param runnable The runnable that will be invoked.
     *
     * @since 9.1.0
     */
    void onCraftTweakerLoadCompletion(final Runnable runnable);
    
    void onExecuteRun(final Consumer<ScriptRunConfiguration> executionConsumer);
}
