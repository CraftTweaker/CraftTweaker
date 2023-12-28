package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.platform.Services;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Configures the list of {@link JavaNativeModule}s for a particular {@link IScriptRun}.
 *
 * <p>Each {@link com.blamejared.crafttweaker.api.zencode.IScriptLoader} requires an instance of this class to ensure
 * proper creation of the environment for a script run.</p>
 *
 * <p>A script run module configurator is responsible for gathering the exposed classes and expansions from the
 * {@link ICraftTweakerRegistry} and using the information exposed by {@link ScriptRunConfiguration} to create the
 * various ZenCode modules that will then be used to build the ZenCode scripting environment and run scripts.</p>
 *
 * <p>There are no restrictions on the amount of modules that should be created or on the relationship between them: it
 * is up to the implementation of this class to ensure that the configuration created is valid and will not cause errors
 * at runtime.</p>
 *
 * <p>This method is a {@linkplain FunctionalInterface functional interface} whose functional method is
 * {@link #populateModules(ICraftTweakerRegistry, ScriptRunConfiguration, ModuleCreator)}.</p>
 *
 * @since 9.1.0
 */
@FunctionalInterface
public interface IScriptRunModuleConfigurator {
    
    /**
     * Creates a {@link JavaNativeModule} from the given information.
     *
     * <p>This method is a {@linkplain FunctionalInterface functional interface} whose functional method is
     * {@link #createNativeModule(String, String, List, Consumer)}.</p>
     *
     * @since 9.1.0
     */
    @FunctionalInterface
    interface ModuleCreator {
        
        /**
         * Creates a {@link JavaNativeModule} from the given information.
         *
         * <p>The module is also automatically initialized so that the ZenCode scripting environment has all the
         * required information to proceed. Any special module configuration that must be ran between the module
         * creation and any preparatory work executed by ZenCode, such as adding classes or globals,
         * <strong>must</strong> be executed in the {@code configurator} {@link Consumer}. Additional non-critical
         * configuration can be executed in the consumer or deferred after the return of this method.</p>
         *
         * @param name         The name of the module to create.
         * @param rootPackage  The root package that identifies this module.
         * @param dependencies A list of {@link JavaNativeModule}s representing the dependencies of the module.
         * @param configurator A {@link Consumer} responsible for performing additional configuration work on the module
         *                     between its creation and its preparation for the ZenCode scripting environment. Critical
         *                     tasks such as adding classes and expansions <strong>must</strong> be performed here.
         *                     Non-critical tasks do not have to be performed here.
         *
         * @return A newly created and prepared module according to the given information.
         *
         * @throws CompileException If compilation of the module during the preparation phase failed.
         * @since 9.1.0
         */
        JavaNativeModule createNativeModule(
                final String name,
                final String rootPackage,
                final List<JavaNativeModule> dependencies,
                final Consumer<JavaNativeModule> configurator
        ) throws CompileException;
        
    }
    
    /**
     * Creates a default {@link IScriptRunModuleConfigurator} for the given base package.
     *
     * <p>The default configurator automatically sets up a hierarchy of modules, with the module for {@code basePackage}
     * sitting at the root of the hierarchy and all expansions being gathered in a special module.</p>
     *
     * <p>To be more precise, the {@code basePackage} is used to gather all classes contained in it and all subpackages
     * and package them into a module with the same name. All the other packages are then scanned and grouped based on
     * their root package. Each root package gives birth to a module that depends uniquely on the {@code basePackage}
     * module. At the end, all expansions are then gathered into an {@code "expansions"} module, which depends on all
     * previously created modules.</p>
     *
     * <p>As an example, if the {@code basePackage} is {@code foo} and there are classes in both the {@code bar} and
     * {@code baz} root packages, four modules will be created. The {@code foo} module will sit at the root of the
     * hierarchy. Two modules, {@code bar} and {@code baz}, will then be created and both will depend on {@code foo}.
     * Lastly, a fourth module named {@code "expansions"} will be created and it will depend on {@code foo},
     * {@code bar}, and {@code baz}.</p>
     *
     * @param basePackage The base package which will be queried to create the base module.
     *
     * @return A script run module configurator carrying out the operations specified above.
     *
     * @since 9.1.0
     */
    static IScriptRunModuleConfigurator createDefault(final String basePackage) {
        
        return Services.BRIDGE.defaultScriptRunModuleConfigurator(basePackage);
    }
    
    /**
     * Creates all necessary modules for a particular script run.
     *
     * @param registry      An instance of {@link ICraftTweakerRegistry} from which to grab classes and other data for
     *                      the creation and configuration of the modules.
     * @param configuration The {@link ScriptRunConfiguration} for which the script run should be created.
     * @param creator       A {@link ModuleCreator} which is responsible for the actual creation of the modules. Refer
     *                      to {@link ModuleCreator#createNativeModule(String, String, List, Consumer)} for more
     *                      information.
     *
     * @return A {@link Collection} with all {@link JavaNativeModule}s that have been created. The list need not be
     * ordered.
     *
     * @throws CompileException If the creation of any of the module fails for any reason.
     * @since 9.1.0
     */
    Collection<JavaNativeModule> populateModules(
            final ICraftTweakerRegistry registry,
            final ScriptRunConfiguration configuration,
            final ModuleCreator creator
    ) throws CompileException;
    
}
