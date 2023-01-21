package com.blamejared.crafttweaker.api.recipe.type;

<<<<<<< HEAD:Common/src/main/java/com/blamejared/crafttweaker/api/recipe/type/CTShapelessRecipe.java
import com.blamejared.crafttweaker.api.*;
=======
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
>>>>>>> 10ed8b8b (Add common loggers for code cleanup):Common/src/main/java/com/blamejared/crafttweaker/api/recipe/type/CTShapelessRecipeBase.java
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;

public class CTShapelessRecipe extends ShapelessRecipe {
    
    private final IIngredient[] ctIngredients;
    private final IItemStack output;
    @Nullable
    private final RecipeFunction1D function;
    
    public CTShapelessRecipe(String name, IItemStack output, IIngredient[] ingredients, @Nullable RecipeFunction1D function) {
        
        this(name, CraftingBookCategory.MISC, output, ingredients, function);
    }
    
    public CTShapelessRecipe(String name, CraftingBookCategory category, IItemStack output, IIngredient[] ingredients, @Nullable RecipeFunction1D function) {
        
        super(CraftTweakerConstants.rl(name), "", category, output.getInternal(), NonNullList.create());
        
        this.output = output;
        this.function = function;
        
        boolean containsNull = false;
        for(IIngredient ingredient : ingredients) {
            if(ingredient == null || ingredient.asVanillaIngredient().isEmpty()) {
                CommonLoggers.own().warn("Shapeless recipe with ID '{}' contains null or empty ingredients, removing entries!", getId());
                containsNull = true;
                break;
            }
        }
        if(containsNull) {
            ingredients = Arrays.stream(ingredients)
                    .filter(Objects::nonNull)
                    .filter(iIngredient -> !iIngredient.asVanillaIngredient().isEmpty())
                    .toArray(IIngredient[]::new);
        }
        this.ctIngredients = ingredients;
        this.getIngredients().addAll(Arrays.stream(this.ctIngredients).map(IIngredient::asVanillaIngredient).toList());
    }
    
    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        //Don't do anything here, just make sure all slots have been visited
        final boolean[] visited = forAllUniqueMatches(inv, (ingredientIndex, matchingSlot, stack) -> {});
        
        int visitedCount = 0;
        for(int slot = 0; slot < visited.length; slot++) {
            if(visited[slot]) {
                visitedCount++;
            } else if(!inv.getItem(slot).isEmpty()) {
                return false;
            }
        }
        return visitedCount == this.ctIngredients.length;
    }
    
    
    @Override
    public ItemStack assemble(CraftingContainer inv) {
        
        if(this.function == null) {
            return this.output.getInternal().copy();
        }
        
        final IItemStack[] stacks = new IItemStack[this.ctIngredients.length];
        
        forAllUniqueMatches(inv, (ingredientIndex, matchingSlot, stack) -> stacks[ingredientIndex] = stack.setAmount(1));
        
        return this.function.process(this.output, stacks).getImmutableInternal();
    }
    
    
    @Nullable
    public RecipeFunction1D getFunction() {
        
        return function;
    }
    
    @Override
    public ItemStack getResultItem() {
        
        return output.getInternal().copy();
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        
        final NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        forAllUniqueMatches(inv, (ingredientIndex, matchingSlot, stack) -> remainingItems.set(matchingSlot, this.ctIngredients[ingredientIndex]
                .getRemainingItem(stack)
                .getInternal()));
        return remainingItems;
    }
    
    /**
     * Helper method to avoid redundant code.
     * performs the Action for all matched items.
     *
     * Returns a bool array which slots have been visited, false implies the slot was not used.
     * If there are less 'true' slots than there are ingredients, then the recipe doesn't match.
     * It can be possible for a slot to not be visited but still contain items.
     * Both cases need to be checked in the matches function!
     */
    private boolean[] forAllUniqueMatches(Container inv, ForAllUniqueAction action) {
        
        final boolean[] visited = new boolean[inv.getContainerSize()];
        
        outer:
        for(int ingredientIndex = 0; ingredientIndex < this.ctIngredients.length; ingredientIndex++) {
            IIngredient ingredient = this.ctIngredients[ingredientIndex];
            for(int i = 0; i < inv.getContainerSize(); i++) {
                if(visited[i]) {
                    continue;
                }
                
                final ItemStack stackInSlot = inv.getItem(i);
                if(stackInSlot.isEmpty()) {
                    continue;
                }
                
                final IItemStack stack = IItemStack.of(stackInSlot);
                if(ingredient.matches(stack)) {
                    visited[i] = true;
                    action.accept(ingredientIndex, i, stack);
                    continue outer;
                }
            }
        }
        return visited;
    }
    
    @Override
    public NonNullList<Ingredient> getIngredients() {
        
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for(IIngredient ingredient : this.ctIngredients) {
            ingredients.add(ingredient.asVanillaIngredient());
        }
        return ingredients;
    }
    
    @Override
    public RecipeSerializer<CTShapelessRecipe> getSerializer() {
        
        return CTShapelessRecipeSerializer.INSTANCE;
    }
    
    public IIngredient[] getCtIngredients() {
        
        return this.ctIngredients;
    }
    
    public IItemStack getCtOutput() {
        
        return this.output;
    }
    
    private interface ForAllUniqueAction {
        
        void accept(int ingredientIndex, int matchingSlot, IItemStack stack);
        
    }
    
    
}
