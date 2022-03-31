package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.tag.manager.factory.EntityTypeTagManagerFactory;
import com.blamejared.crafttweaker.api.tag.manager.type.EntityTypeTagManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Manages the registration of taggable elements and their managers used for tags.
 *
 * <p>Refer to the documentation of the various methods for more information.</p>
 *
 * @since 9.1.0
 */
public interface ITaggableElementRegistrationHandler {
    
    /**
     * Registers a new taggable element with the given {@link ResourceKey} of the {@link Registry} that holds the given element class.
     *
     * <p>The key is used to map {@code <}{@code tag:>} calls to the specific element.</p>
     *
     * @param key          The {@link ResourceKey} of the Registry that holds the element is being registered.
     * @param elementClass A {@link Class} of the element that is being registered.
     * @param <T>          The type of element being registered.
     *
     * @since 9.1.0
     */
    <T> void registerTaggableElement(final ResourceKey<Registry<T>> key, final Class<T> elementClass);
    
    /**
     * Registers a new {@link TagManagerFactory} for the given {@link ResourceKey}.
     *
     * <p>A tag manager factory allows for a custom class to be used instead of the
     * default {@link com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager}.
     *
     * A custom tag manager allows you to easily add new methods to your manager without having to expand it.</p>
     *
     * <p>Note: Taggable elements with type parameters require a custom factory, see {@link EntityTypeTagManagerFactory} and {@link EntityTypeTagManager}.</p>
     *
     * @param key     The {@link ResourceKey} of the Registry that holds the element is being registered.
     * @param factory The {@link TagManagerFactory} that is being registered.
     * @param <T>     The type of element the tagmanager the factory makes deals with.
     *
     * @since 9.1.0
     */
    <T, U extends ITagManager<?>> void registerManager(final ResourceKey<Registry<T>> key, final TagManagerFactory<T, U> factory);
    
    
}
