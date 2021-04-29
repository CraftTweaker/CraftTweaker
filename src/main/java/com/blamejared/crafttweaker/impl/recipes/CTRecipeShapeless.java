package com.blamejared.crafttweaker.impl.recipes;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Objects;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CTRecipeShapeless implements ICraftingRecipe {
    
    private final IIngredient[] ingredients;
    private final IItemStack output;
    @Nullable
    private final IRecipeManager.RecipeFunctionArray function;
    private final ResourceLocation resourceLocation;
    
    public CTRecipeShapeless(String name, IItemStack output, IIngredient[] ingredients, @Nullable IRecipeManager.RecipeFunctionArray function) {
        this.resourceLocation = new ResourceLocation("crafttweaker", name);
        this.output = output;
        this.function = function;
        
        boolean containsNull = false;
        for(IIngredient ingredient : ingredients) {
            if(ingredient == null) {
                CraftTweakerAPI.logWarning("Shapeless recipe with ID '%s' contains null ingredient, removing entries!", resourceLocation);
                containsNull = true;
                break;
            }
        }
        if(containsNull) {
            ingredients = Arrays.stream(ingredients)
                    .filter(Objects::nonNull)
                    .toArray(IIngredient[]::new);
        }
        this.ingredients = ingredients;
    
    }
    
    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        //Don't do anything here, just make sure all slots have been visited
        final boolean[] visited = forAllUniqueMatches(inv, (ingredientIndex, matchingSlot, stack) -> { });
        
        int visitedCount = 0;
        for(int slot = 0; slot < visited.length; slot++) {
            if(visited[slot])
                visitedCount++;
            else if(!inv.getStackInSlot(slot).isEmpty())
                return false;
        }
        return visitedCount == this.ingredients.length;
    }
    
    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        if(this.function == null)
            return this.output.getInternal().copy();
        
        final IItemStack[] stacks = new IItemStack[this.ingredients.length];
        
        forAllUniqueMatches(inv, (ingredientIndex, matchingSlot, stack) -> stacks[ingredientIndex] = stack.setAmount(1));
        
        return this.function.process(this.output, stacks).getImmutableInternal();
    }
    
    
    @Nullable
    public IRecipeManager.RecipeFunctionArray getFunction() {
        return function;
    }
    
    @Override
    public boolean canFit(int width, int height) {
        return width * height >= this.ingredients.length;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return output.getInternal().copy();
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        final NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
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
    private boolean[] forAllUniqueMatches(IInventory inv, ForAllUniqueAction action) {
        final boolean[] visited = new boolean[inv.getSizeInventory()];
        
        outer:
        for(int ingredientIndex = 0; ingredientIndex < this.ingredients.length; ingredientIndex++) {
            IIngredient ingredient = this.ingredients[ingredientIndex];
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                if(visited[i])
                    continue;
                
                final ItemStack stackInSlot = inv.getStackInSlot(i);
                if(stackInSlot.isEmpty())
                    continue;
                
                final MCItemStack stack = new MCItemStack(stackInSlot);
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
    public boolean isDynamic() {
        return false;
    }
    
    @Override
    public String getGroup() {
        return ICraftingRecipe.super.getGroup();
    }
    
    @Override
    public ResourceLocation getId() {
        return resourceLocation;
    }
    
    @Override
    public IRecipeSerializer<CTRecipeShapeless> getSerializer() {
        return SerializerShapeless.INSTANCE;
    }
    
    
    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.CRAFTING;
    }
    
    public IIngredient[] getCtIngredients() {
        return this.ingredients;
    }
    
    private interface ForAllUniqueAction {
        
        void accept(int ingredientIndex, int matchingSlot, IItemStack stack);
        
    }
}
