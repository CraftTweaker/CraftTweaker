package crafttweaker.mc1120.recipes;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.*;

public class MCRecipeShapeless extends MCRecipeBase {

    private final IIngredient ingredients[];


    public MCRecipeShapeless(IIngredient[] ingredients, IItemStack output, IRecipeFunction recipeFunction, IRecipeAction recipeAction, boolean isHidden) {
        super(output, createIngredientList(ingredients), recipeFunction, recipeAction, isHidden);
        this.ingredients = ingredients;
    }

    private static NonNullList<Ingredient> createIngredientList(IIngredient[] ingredients) {
        NonNullList<Ingredient> ingredientList = NonNullList.withSize(ingredients.length, Ingredient.EMPTY);
        for(int index = 0; index < ingredients.length; index++) {
            IIngredient ingredient = ingredients[index];
            if(ingredient != null)
                ingredientList.set(index, CraftTweakerMC.getIngredient(ingredients[index]));
        }
        return ingredientList;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean[] visited = new boolean[inv.getSizeInventory()];
        boolean matches = true;

        //Check if all recipe items are present
        outer:
        for(int ingredientIndex = 0; ingredientIndex < ingredients.length; ingredientIndex++) {
            IIngredient ingredient = ingredients[ingredientIndex];
            for(int slot = 0; slot < inv.getSizeInventory(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if(visited[slot] || stackInSlot.isEmpty())
                    continue;
                if(ingredient.matches(CraftTweakerMC.getIItemStackForMatching(stackInSlot))) {
                    //make sure no slot is matched twice
                    visited[slot] = true;
                    continue outer;
                }
            }
            matches = false;
        }


        //make sure no other items are in the remaining slots
        for(int slot = 0; slot < visited.length; slot++) {
            if(visited[slot])
                continue;
            if(!inv.getStackInSlot(slot).isEmpty())
                return false;
        }
        return matches;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        if(recipeFunction == null)
            return outputStack.copy();
        Map<String, IItemStack> marks = new HashMap<>();
        boolean[] visited = new boolean[inv.getSizeInventory()];
        for(int ingredientIndex = 0; ingredientIndex < ingredients.length; ingredientIndex++) {
            IIngredient ingredient = ingredients[ingredientIndex];
            if(ingredient == null || ingredient.getMark() == null)
                continue;
            for(int slot = 0; slot < inv.getSizeInventory(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if(!stackInSlot.isEmpty() && !visited[slot]) {
                    IItemStack stack = CraftTweakerMC.getIItemStack(stackInSlot);
                    if(ingredient.matches(stack)) {
                        marks.put(ingredient.getMark(), stack);
                        visited[slot] = true;
                        break;
                    }
                }
            }
        }
        IItemStack out = null;
        try {
            out = recipeFunction.process(output, marks, new CraftingInfo(new MCCraftingInventorySquared(inv), null));
        } catch(Exception exception) {
            CraftTweakerAPI.logError("Could not execute RecipeFunction: ", exception);
        }
        return CraftTweakerMC.getItemStack(out);
    }

    @Override
    public boolean canFit(int width, int height) {
        return (width * height) >= ingredients.length;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> out = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        boolean[] visited = new boolean[inv.getSizeInventory()];

        for(int ingredientIndex = 0; ingredientIndex < ingredients.length; ingredientIndex++) {
            for(int slot = 0; slot < inv.getSizeInventory(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if(!stackInSlot.isEmpty() && !visited[slot]) {
                    IItemStack stack = CraftTweakerMC.getIItemStack(stackInSlot);
                    IIngredient ingredient = ingredients[ingredientIndex];
                    if(ingredient.matches(stack)) {
                        boolean needsContainerItem = true;
                        if(ingredient.hasNewTransformers()) {
                            IItemStack remainingItem = null;
                            try {
                                remainingItem = ingredient.applyNewTransform(stack);
                            } catch(Exception exception) {
                                CraftTweakerAPI.logError("Could not execute NewRecipeTransformer on " + ingredient.toCommandString() + ":", exception);
                            }
                            if(remainingItem != ItemStackUnknown.INSTANCE) {
                                out.set(slot, CraftTweakerMC.getItemStack(remainingItem));
                                needsContainerItem = false;
                            }
                        }
                        if(ingredient.hasTransformers()) {
                            //increase stackSize by 1 so that it can then be decreased by one in the crafting process
                            //done to insure the transformer works as intended
                            stackInSlot.setCount(stackInSlot.getCount() + 1);
                            needsContainerItem = false;
                        }
                        if(needsContainerItem) {
                            out.set(slot, ForgeHooks.getContainerItem(stackInSlot));
                        }
                        visited[slot] = true;
                        break;
                    }
                }
            }
        }
        return out;
    }

    @Override
    public String toCommandString() {
        StringBuilder commandString = new StringBuilder("recipes.add");
        commandString.append(hidden ? "Hidden" : "");
        commandString.append("Shapeless(\"");
        commandString.append(this.getName()).append("\", ");
        commandString.append(this.output.toString()).append(", [");
        if(ingredients.length > 0) {
            for(IIngredient ingredient : ingredients) {
                commandString.append(ingredient.toCommandString()).append(", ");
            }
            //Remove last ,
            commandString.deleteCharAt(commandString.length() - 1);
            commandString.deleteCharAt(commandString.length() - 1);
        }

        commandString.append("]);");
        return commandString.toString();
    }

    @Override
    public boolean hasTransformers() {
        for(IIngredient ingredient : ingredients)
            if(ingredient != null && ingredient.hasTransformers())
                return true;
        return false;
    }

    @Override
    public IIngredient[] getIngredients1D() {
        return ingredients;
    }

    @Override
    public IIngredient[][] getIngredients2D() {
        return new IIngredient[][]{ingredients};
    }

    @Override
    public boolean isShaped() {
        return false;
    }

    @Override
    public void applyTransformers(InventoryCrafting inventory, IPlayer byPlayer) {
        boolean[] visited = new boolean[inventory.getSizeInventory()];
        for(int ingredientIndex = 0; ingredientIndex < ingredients.length; ingredientIndex++) {
            IIngredient ingredient = ingredients[ingredientIndex];
            for(int slot = 0; slot < inventory.getSizeInventory(); slot++) {
                ItemStack stackInSlot = inventory.getStackInSlot(slot);
                if(!stackInSlot.isEmpty() && !visited[slot]) {
                    IItemStack stack = CraftTweakerMC.getIItemStack(stackInSlot);
                    if(ingredient.matches(stack)) {
                        IItemStack out = null;
                        try {
                            out = ingredient.applyTransform(stack, byPlayer);
                        } catch(Exception exception) {
                            CraftTweakerAPI.logError("Could not execute RecipeTransformer on " + ingredient.toCommandString() + ":", exception);
                        }
                        inventory.setInventorySlotContents(slot, CraftTweakerMC.getItemStack(out));
                        visited[slot] = true;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public MCRecipeShapeless update() {
        this.ingredientList = createIngredientList(ingredients);
        return this;
    }
}
