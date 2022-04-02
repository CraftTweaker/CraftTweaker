package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A taggable element is a class which can be used in a vanilla Tag.
 *
 * <p>Examples are:</p>
 * <ul>
 * <li>Item</li>
 * <li>Block</li>
 * <li>EntityType</li>
 * </ul>
 *
 * <p>By marking a class as taggable, it lets ZenCode know that it has special tag handling (such as being able to add an instance of the element to a tag).</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaggableElement {
    
    /**
     * The name of the registry that holds this element.
     *
     * Examples:
     * - "minecraft:item" for Item
     * - "minecraft:worldgen/biome" for Biome
     *
     * @return The name of the registry that holds this element.
     */
    String value();
    
}
