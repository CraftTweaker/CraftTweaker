package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;

/**
 * Manages the registration of {@link IScriptRunModuleConfigurator}s for the various loaders.
 *
 * <p>Refer to their documentation for more information on what they represent.</p>
 *
 * <p>Each loader <strong>MUST</strong> have an associated script run module configurator, otherwise an error will be
 * raised at runtime.</p>
 *
 * @since 9.1.0
 */
public interface IScriptRunModuleConfiguratorRegistrationHandler {
    
    /**
     * Registers the provided configurator as the configurator of the given loader.
     *
     * @param loader       The name of the loader for which the configurator is for.
     * @param configurator The configurator to register.
     *
     * @since 9.1.0
     */
    void registerConfigurator(final String loader, final IScriptRunModuleConfigurator configurator);
    
}
