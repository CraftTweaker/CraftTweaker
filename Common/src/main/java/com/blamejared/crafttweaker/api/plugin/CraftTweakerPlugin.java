package com.blamejared.crafttweaker.api.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the class as a CraftTweaker plugin, which needs to be detected and loaded by CraftTweaker.
 *
 * <p>Classes annotated by this annotation will be automatically discovered and class-loaded when required by
 * CraftTweaker: no additional registration is necessary for the consumer of the API.</p>
 *
 * <p>Classes annotated with this annotation <strong>must</strong> implement the {@link ICraftTweakerPlugin} interface
 * and have a single parameter-less public constructor. If any of these conditions are not followed, the plugin will
 * not be loaded and a runtime error will occur.</p>
 *
 * <p>Refer to the methods available in {@link ICraftTweakerPlugin} for more information on what actions are exposed to
 * plugins.</p>
 *
 * @since 9.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CraftTweakerPlugin {
    
    /**
     * Returns the ID of the plugin.
     *
     * <p>The ID must be unique among plugins, as it is used to identify each plugin during the various initialization
     * procedures of CraftTweaker.</p>
     *
     * <p>It is <strong>mandatory</strong> for the plugin's ID to be a valid resource location, i.e. it must follow the
     * form {@code "mod:name"}. It is illegal for a plugin to omit the namespace or to specify {@code minecraft} as its
     * namespace.</p>
     *
     * @return The ID of the plugin.
     *
     * @since 9.1.0
     */
    String value();
    
}
