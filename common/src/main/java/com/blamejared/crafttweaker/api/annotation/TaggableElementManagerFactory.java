package com.blamejared.crafttweaker.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaggableElementManagerFactory {
    
    /**
     * The name of the registry that holds the element that this manager deals with.
     *
     * Examples:
     * - "minecraft:item" for Item
     * - "minecraft:worldgen/biome" for Biome
     *
     * @return The name of the registry that holds the element that this manager deals with.
     */
    String value();
    
}
