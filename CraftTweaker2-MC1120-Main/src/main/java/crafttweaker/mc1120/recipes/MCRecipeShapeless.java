package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.*;

public class MCRecipeShapeless extends MCRecipeBase implements IRecipe {
    
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
                ingredientList.set(index, Ingredient.fromStacks(CraftTweakerMC.getItemStacks(ingredients[index].getItems())));
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
                if(ingredient.matches(CraftTweakerMC.getIItemStack(stackInSlot))) {
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
            if(ingredient.getMark() == null)
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
        IItemStack out = recipeFunction.process(output, marks, new CraftingInfo(new MCCraftingInventorySquared(inv), null));
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
                    if(ingredients[ingredientIndex].matches(stack))
                        if(ingredients[ingredientIndex].hasTransformers()) {
                            IItemStack remainingItem = ingredients[ingredientIndex].applyTransform(stack, null);
                            out.set(slot, remainingItem == null ? ItemStack.EMPTY : CraftTweakerMC.getItemStack(remainingItem));
                            visited[slot] = true;
                            break;
                        } else
                            out.set(slot, ForgeHooks.getContainerItem(stackInSlot));
                    
                }
            }
        }
        return out;
    }
    
    @Override
    public String toCommandString() {
        StringBuilder commandString = new StringBuilder("recipes.addShapeless(\"");
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
    public MCRecipeShapeless update() {
        this.ingredientList = createIngredientList(ingredients);
        return this;
    }
}
