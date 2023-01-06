package com.blamejared.crafttweaker.api.recipe.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class CTShapelessRecipeBase implements CraftingRecipe {
    
    
    private final IIngredient[] ingredients;
    private final IItemStack output;
    @Nullable
    private final RecipeFunction1D function;
    private final ResourceLocation resourceLocation;
    
    
    public CTShapelessRecipeBase(String name, IItemStack output, IIngredient[] ingredients, @Nullable RecipeFunction1D function) {
        
        this.resourceLocation = CraftTweakerConstants.rl(name);
        this.output = output;
        this.function = function;
        
        boolean containsNull = false;
        for(IIngredient ingredient : ingredients) {
            if(ingredient == null || ingredient.asVanillaIngredient().isEmpty()) {
                CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME)
                        .warn("Shapeless recipe with ID '{}' contains null or empty ingredients, removing entries!", resourceLocation);
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
        this.ingredients = ingredients;
        
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
        return visitedCount == this.ingredients.length;
    }
    
    
    @Override
    public ItemStack assemble(CraftingContainer inv) {
        
        if(this.function == null) {
            return this.output.getInternal().copy();
        }
        
        final IItemStack[] stacks = new IItemStack[this.ingredients.length];
        
        forAllUniqueMatches(inv, (ingredientIndex, matchingSlot, stack) -> stacks[ingredientIndex] = stack.setAmount(1));
        
        return this.function.process(this.output, stacks).getImmutableInternal();
    }
    
    
    @Nullable
    public RecipeFunction1D getFunction() {
        
        return function;
    }
    
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        
        return width * height >= this.ingredients.length;
    }
    
    @Override
    public ItemStack getResultItem() {
        
        return output.getInternal().copy();
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        
        final NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        forAllUniqueMatches(inv, (ingredientIndex, matchingSlot, stack) -> remainingItems.set(matchingSlot, this.ingredients[ingredientIndex]
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
        for(int ingredientIndex = 0; ingredientIndex < this.ingredients.length; ingredientIndex++) {
            IIngredient ingredient = this.ingredients[ingredientIndex];
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
        for(IIngredient ingredient : this.ingredients) {
            ingredients.add(ingredient.asVanillaIngredient());
        }
        return ingredients;
    }
    
    @Override
    public String getGroup() {
        
        return CraftingRecipe.super.getGroup();
    }
    
    @Override
    public ResourceLocation getId() {
        
        return resourceLocation;
    }
    
    @Override
    public RecipeSerializer<CTShapelessRecipeBase> getSerializer() {
        
        return Services.REGISTRY.getCTShapelessRecipeSerializer();
    }
    
    
    @Override
    public RecipeType<?> getType() {
        
        return RecipeType.CRAFTING;
    }
    
    public IIngredient[] getCtIngredients() {
        
        return this.ingredients;
    }
    
    public IItemStack getCtOutput() {
        
        return this.output;
    }
    
    private interface ForAllUniqueAction {
        
        void accept(int ingredientIndex, int matchingSlot, IItemStack stack);
        
    }
    
    
}
