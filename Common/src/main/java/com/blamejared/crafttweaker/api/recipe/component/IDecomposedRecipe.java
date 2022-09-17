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
     * Gets the values that are being pointed to by the given {@link IRecipeComponent}.
     *
     * <p>If the given component is not part of this decomposed recipe instance, then an
     * {@link IllegalArgumentException} is raised. The returned list need not be modifiable. Clients wanting to perform
     * modifications are encouraged to copy the list, edit it, then
     * {@linkplain #set(IRecipeComponent, List) set it back} into the instance.</p>
     *
     * <p>It is not allowed for a recipe to throw an exception for any component that is listed in
     * {@link #components()}.</p>
     *
     * @param component The component whose values need to be retrieved.
     * @param <C>       The type of elements that the component points to.
     *
     * @return A {@link List} containing the values pointed to by the component, if any.
     *
     * @throws IllegalArgumentException If the component is missing in the current recipe.
     * @since 10.0.0
     */
    default <C> List<C> getOrThrow(final IRecipeComponent<C> component) {
        
        final List<C> list = this.get(component);
        if(list == null) {
            throw new IllegalArgumentException("Missing " + component.getCommandString());
        }
        return list;
    }
    
    /**
     * Gets the single value that is being pointed to by the given {@link IRecipeComponent}.
     *
     * <p>If the given component is not part of this decomposed recipe instance, then an
     * {@link IllegalArgumentException} is raised. Similarly, if the given component points to a {@link List} with more
     * than one element, a {@code IllegalArgumentException} is raised. The returned element might or might not be a
     * copy. Clients are thus encouraged not to modify the instance directly, but rather create a new copy and
     * {@linkplain #set(IRecipeComponent, Object) set it back}.</p>
     *
     * <p>It is not allowed for a recipe to throw an exception for any component that is listed in
     * {@link #components()}.</p>
     *
     * @param component The component whose values need to be retrieved.
     * @param <C>       The type of elements that the component points to.
     *
     * @return The value pointed to by the component, if any
     *
     * @throws IllegalArgumentException If the component is missing or has more than one instance in the current recipe.
     * @since 10.0.0
     */
    default <C> C getOrThrowSingle(final IRecipeComponent<C> component) {
        
        final List<C> list = this.getOrThrow(component);
        if(list.size() != 1) {
            final String message = String.format(
                    "Expected a list with a single element for %s, but got %d-sized list: %s",
                    component.getCommandString(),
                    list.size(),
                    list
            );
            throw new IllegalArgumentException(message);
        }
        return list.get(0);
    }
    
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
     * Sets the value that is being pointed to by the given {@link IRecipeComponent} to the given object.
     *
     * <p>If the given component is not part of the decomposed recipe instance, then it is added. Otherwise the existing
     * data is modified to refer to the new object. Take note that no checks are performed on the actual size of the
     * list, meaning that the component will point to a single element after this call. It is the caller's
     * responsibility to ensure this respects the contract.</p>
     *
     * <p>The given element might be copied by the implementation to prevent tampering. Clients are thus encouraged not
     * to set the instance directly assuming modifiability, but rather set the element only when all modifications have
     * been completed.</p>
     *
     * <p>It is disallowed to use {@code null} to remove a component from the decomposed recipe.</p>
     *
     * @param component The component whose value needs to be set.
     * @param object    The value that the component should point to.
     * @param <C>       The type of elements the component points to.
     *
     * @since 10.0.0
     */
    default <C> void set(final IRecipeComponent<C> component, final C object) {
        
        this.set(component, List.of(object));
    }
    
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
