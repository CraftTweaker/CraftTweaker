package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Represents a part of a recipe after it has been decomposed.
 *
 * <p>Recipe components in a recipe are associated with a list of data, whose type varies depending on the component.
 * The data indicates what the component is made up of, allowing for introspection and selective editing of any recipe
 * that can be decomposed and then recomposed, without requiring explicit support for every type of change.</p>
 *
 * <p>Obtaining an instance of a recipe component in a script can be done through the usage of the
 * {@link <recipecomponent>} bracket handler. Integration writers can instead refer to the {@code find} method.</p>
 *
 * <p>Every recipe component must be registered to the registry through a plugin to be able to be used effectively and
 * discovered by script writers. New recipe components can be created with either {@code simple} or {@code composite}. A
 * set of common components is already provided by CraftTweaker.</p>
 *
 * @param <T> The type of objects pointed to by the component.
 *
 * @see IDecomposedRecipe
 * @see #find(ResourceLocation)
 * @see #simple(ResourceLocation, TypeToken, BiPredicate)
 * @see #composite(ResourceLocation, TypeToken, BiPredicate, Function, Function)
 * @see com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin#registerRecipeComponents(com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler)
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/IRecipeComponent")
@ZenCodeType.Name("crafttweaker.api.recipe.IRecipeComponent")
@ZenRegister
public sealed interface IRecipeComponent<T> extends CommandStringDisplayable permits SimpleRecipeComponent, ListRecipeComponent {
    
    /**
     * Identifies the recipe component with the given id, if registered.
     *
     * <p>This method must be invoked only after all registries have been built. If such an action is performed, the
     * result is undefined behavior.</p>
     *
     * @param id  The id of the recipe component that should be found.
     * @param <T> The type of data pointed to by the recipe component. Note that no checks are performed on the validity
     *            of this value. It is the caller responsibility to ensure that {@link #objectType()} and {@code T}
     *            represent the same or compatible types.
     *
     * @return The recipe component with the given id, if it exists.
     *
     * @throws IllegalArgumentException If no recipe component with the given ID has been registered.
     * @since 10.0.0
     */
    static <T> IRecipeComponent<T> find(final ResourceLocation id) {
        
        return CraftTweakerAPI.getRegistry().findRecipeComponent(id);
    }
    
    /**
     * Builds a new {@link IRecipeComponent} that cannot be further decomposed into a list of lists.
     *
     * <p>In other words, this identifies a component whose data cannot be further decomposed into "atoms", as it
     * represents a base type directly. An example is {@code IItemStack}, which cannot be further decomposed into a list
     * of smaller stacks.</p>
     *
     * <p>Take note that the returned component <strong>must still</strong> be manually registered through a plugin to
     * be able to be used.</p>
     *
     * @param id         The ID of the component that needs to be created.
     * @param objectType A {@link TypeToken} representing the type of objects being pointed to by the component.
     * @param matcher    A {@link BiPredicate} used to determine whether two of the objects pointed to by the component
     *                   are the same. The first argument of the predicate is the oracle, meaning the fixed element that
     *                   is used to perform the comparison. The second argument is the element that needs to be
     *                   compared. Take note that the distinction is not necessarily meaningful as the general contract
     *                   of equality must be preserved, with reflectivity, symmetry, and transitivity.
     * @param <T>        The type of objects being pointed to by the component.
     *
     * @return A new recipe component constructed according to the given data.
     *
     * @since 10.0.0
     */
    static <T> IRecipeComponent<T> simple(final ResourceLocation id, final TypeToken<T> objectType, final BiPredicate<T, T> matcher) {
        
        return new SimpleRecipeComponent<>(Objects.requireNonNull(id), Objects.requireNonNull(objectType), Objects.requireNonNull(matcher));
    }
    
    /**
     * Builds a new {@link IRecipeComponent} whose data is a composite of multiple smaller units.
     *
     * <p>In other words, this identifies a component whose data can be further decomposed into "atoms" and then brought
     * back together into the same container. An example is {@code IIngredient}, which can be further decomposed into a
     * list of basic ingredients (items, tags, predicates...). The various parts can then be added back together again
     * through the {@code |} operator to build the initial ingredient back. Note how the type of the various atoms is
     * the same as the initial container: this is mandatory for composite recipe components.</p>
     *
     * <p>Take note that the returned component <strong>must still</strong> be manually registered through a plugin to
     * be able to be used.</p>
     *
     * @param id                 The ID of the component that needs to be created.
     * @param objectType         A {@link TypeToken} representing the type of objects being pointed to by the component.
     * @param matcher            A {@link BiPredicate} used to determine whether two of the objects pointed to by the
     *                           component are the same. The first argument of the predicate is the oracle, meaning the
     *                           fixed element that is used to perform the comparison. The second argument is the
     *                           element that needs to be compared. Take note that this distinction is not necessarily
     *                           meaningful as the general contract of equality must be preserved, with reflectivity,
     *                           symmetry, and transitivity.
     * @param unwrappingFunction A {@link Function} that can decompose an instance of an object pointed to by the
     *                           component into a {@link Collection]} of smaller atoms. Note how the type must be
     *                           preserved. The collection can have any size as deemed necessary by the client.
     * @param wrapper            A {@link Function} that can compose together a {@link Collection} of objects pointed to
     *                           by the component into a single composite unit. Note how the type must be preserved. The
     *                           function is allowed to throw {@link IllegalArgumentException} in case the amount of
     *                           items present in the collection is incorrect for proper composition.
     * @param <T>                The type of objects being pointed to by the component.
     *
     * @return A new recipe component constructed according to the given data.
     *
     * @since 10.0.0
     */
    static <T> IRecipeComponent<T> composite(
            final ResourceLocation id,
            final TypeToken<T> objectType,
            final BiPredicate<T, T> matcher,
            final Function<T, Collection<T>> unwrappingFunction,
            final Function<Collection<T>, T> wrapper
    ) {
        
        return new ListRecipeComponent<>(
                Objects.requireNonNull(id),
                Objects.requireNonNull(objectType),
                Objects.requireNonNull(matcher),
                Objects.requireNonNull(unwrappingFunction),
                Objects.requireNonNull(wrapper)
        );
    }
    
    /**
     * Gets the ID that uniquely identifies this component.
     *
     * @return This component's ID.
     *
     * @since 10.0.0
     */
    ResourceLocation id();
    
    /**
     * Gets a {@link TypeToken} representing the type of objects pointed to by this component.
     *
     * @return The type.
     *
     * @since 10.0.0
     */
    TypeToken<T> objectType();
    
    /**
     * Verifies if the two given objects are the same according to this component.
     *
     * <p>Note that the distinction between the first and second argument is not necessarily meaningful, as the general
     * contract of equality must still be followed, with reflectivity, symmetry, and transitivity.</p>
     *
     * @param oracle The element that is used to perform the comparison.
     * @param object The element that must be compared to the oracle.
     *
     * @return Whether the two objects match.
     *
     * @since 10.0.0
     */
    boolean match(final T oracle, final T object);
    
    /**
     * Unwraps the given element into a collection of smaller "atoms" if possible.
     *
     * <p>It is disallowed for this method to throw an exception if no decomposition is possible. In such a case, the
     * given object is already the most decomposed instance, and a {@link Collection} containing it as the sole element
     * must be returned.</p>
     *
     * <p>The type of the component is preserved.</p>
     *
     * @param object The object that should be unwrapped.
     *
     * @return A collection containing atoms.
     *
     * @since 10.0.0
     */
    Collection<T> unwrap(final T object);
    
    /**
     * Wraps the given {@link Collection} of elements into a single composite element if possible.
     *
     * <p>If the object is already a base type, then the sole instance of it acts as also the composite element, so that
     * instance should be returned.</p>
     *
     * <p>It is allowed for this method to throw an exception if the size of the collection is invalid, as specified
     * further.</p>
     *
     * <p>The type of the component is preserved.</p>
     *
     * @param objects The collection of objects that should be wrapped.
     *
     * @return The composite type containing the given atoms.
     *
     * @throws IllegalArgumentException If the size of the collection does not allow proper recomposition. For example,
     *                                  a non-decomposable element should throw an exception if the given collection's
     *                                  size is not {@code 1}, as no merging can be performed.
     * @since 10.0.0
     */
    T wrap(final Collection<T> objects);
    
    @Override
    default String getCommandString() {
        
        return "<recipecomponent:" + this.id() + ">";
    }
    
}
