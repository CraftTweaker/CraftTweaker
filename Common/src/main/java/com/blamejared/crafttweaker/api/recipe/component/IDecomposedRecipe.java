package com.blamejared.crafttweaker.api.recipe.component;

import java.util.List;
import java.util.Set;

/**
 * Represents a recipe that has been decomposed into {@link IRecipeComponent}s.
 *
 * <p>A decomposed recipe essentially tracks all information necessary to rebuild a recipe from scratch bar its name,
 * allowing for easier manipulation of various recipes without having to know their internal structure or requiring
 * explicit compatibility.</p>
 *
 * <p>A decomposed recipe is characterized by a series of recipe components, each of them pointing to a {@link List} of
 * elements pertaining to its type. For example, a recipe components specifying
 * {@link com.blamejared.crafttweaker.api.item.IItemStack} outputs will point to a list of {@code IItemStack}s.
 * Additional components can be added to the recipe or existing components can have their values altered.</p>
 *
 * <p>Instances of this class can be constructed through the {@link #builder()} or obtained through a recipe's specific
 * {@link com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler}.</p>
 *
 * @since 10.0.0
 */
public interface IDecomposedRecipe {
    
    /**
     * Constructs a {@linkplain DecomposedRecipeBuilder builder} which can be used to build a decomposed recipe.
     *
     * @return An instance of the builder.
     *
     * @since 10.0.0
     */
    static DecomposedRecipeBuilder builder() {
        
        return DecomposedRecipeBuilder.of();
    }
    
    /**
     * Gets the values that are being pointed to by the given {@link IRecipeComponent}, if any.
     *
     * <p>If the given component is not part of this decomposed recipe instance, then {@code null} will be returned. The
     * returned list need not be modifiable. Clients wanting to perform modifications are encouraged to copy the list,
     * edit it, then {@linkplain #set(IRecipeComponent, List) set it back} into the instance.</p>
     *
     * <p>It is not allowed for a recipe to return {@code null} for any component that is listed in
     * {@link #components()}.</p>
     *
     * @param component The component whose values need to be retrieved.
     * @param <C>       The type of elements that the component points to.
     *
     * @return A {@link List} containing the values pointed to by the component, if any.
     *
     * @since 10.0.0
     */
    <C> List<C> get(final IRecipeComponent<C> component);
    
    /**
     * Sets the values that are being pointed to by the given {@link IRecipeComponent} to the given {@link List}.
     *
     * <p>If the given component is not part of the decomposed recipe instance, then it is added. Otherwise the existing
     * data is modified to refer to the new list. The given list need not be used as is and the implementation is free
     * to copy its contents to prevent tampering. Clients wanting to perform modifications are thus encouraged to invoke
     * this method solely when all changes have been completed.</p>
     *
     * <p>It is disallowed to use a {@code null} list to remove a component from this decomposed recipe.</p>
     *
     * @param component The component whose values need to be set.
     * @param object    A {@link List} containing the new values that the component should point to.
     * @param <C>       The type of elements that the component points to.
     *
     * @since 10.0.0
     */
    <C> void set(final IRecipeComponent<C> component, final List<C> object);
    
    /**
     * Obtains all {@link IRecipeComponent}s that are currently present in the recipe.
     *
     * <p>Any components that do not exist originally in the recipe but are added through the
     * {@link #set(IRecipeComponent, List)} method need to be present in the {@link Set} returned by this method.</p>
     *
     * @return A set containing all components of this recipe.
     *
     * @since 10.0.0
     */
    Set<IRecipeComponent<?>> components();
    
}
