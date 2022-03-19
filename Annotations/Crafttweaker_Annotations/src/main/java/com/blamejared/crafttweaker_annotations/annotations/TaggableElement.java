package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaggableElement {
    
    /**
     * The ResourceKey location of the registry of this element
     *
     * Examples:
     * - "minecraft:item" for Item
     * - "minecraft:worldgen/biome" for Biome
     *
     * @return The ResourceKey location of the registry of this element.
     */
    String value();
    
    /**
     * The TagManagerFactory class to make the custom ITagManager class for this element.
     *
     * @return The TagManagerFactory class for this element.
     */
    Class<?> managerFactoryClass() default Object.class;
    
}
