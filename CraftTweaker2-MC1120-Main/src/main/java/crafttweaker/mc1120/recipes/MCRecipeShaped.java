package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import crafttweaker.util.Pair;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;
import stanhebben.zenscript.util.ArrayUtil;

import java.util.*;

public class MCRecipeShaped extends MCRecipeBase implements IShapedRecipe {
    
    private static final Pair<Integer, Integer> offsetInvalid = new Pair<>(-1, -1);
    
    private final IIngredient[][] ingredients;
    private final int width, height;
    private final boolean isMirrored;
    
    public MCRecipeShaped(IIngredient[][] ingredients, IItemStack output, IRecipeFunction recipeFunction, IRecipeAction recipeAction, boolean isMirrored) {
        super(output, createIngredientList(ingredients), recipeFunction, recipeAction);
        
        this.height = ingredients.length;
        this.isMirrored = isMirrored;
        int width = 0;
        for(IIngredient[] ingredientLine : ingredients) {
            width = Math.max(width, ingredientLine.length);
        }
        this.ingredients = ingredients;
        for(int index = 0; index < this.ingredients.length; index++) {
            if(this.ingredients[index].length < width)
                this.ingredients[index] = Arrays.copyOf(this.ingredients[index], width);
        }
        
        this.width = width;
    }
    
    private static NonNullList<Ingredient> createIngredientList(IIngredient[][] ingredients) {
        int height = ingredients.length;
        int width = 0;
        for(IIngredient[] ingredientLine : ingredients) {
            width = Math.max(width, ingredientLine.length);
        }
        
        NonNullList<Ingredient> ingredientList = NonNullList.withSize(width * height, Ingredient.EMPTY);
        for(int x = 0; x < ingredients.length; x++) {
            for(int y = 0; y < ingredients[x].length; y++) {
                ingredientList.set(x * y + y, Ingredient.fromStacks(CraftTweakerMC.getItemStacks(ingredients[x][y].getItems())));
            }
        }
        return ingredientList;
    }
    
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        //ICraftingInventory inventory = MCCraftingInventorySquared.get(inv);
        /*
        boolean[] visited = new boolean[inv.getSizeInventory()];
        for(int rowOffset = 0; rowOffset <= inv.getHeight() - height; rowOffset++) {
            outer:
            for(int columnOffset = 0; columnOffset <= inv.getWidth() - width; columnOffset++) {
                for(int row = 0; row < ingredients.length; row++) {
                    for(int column = 0; column < ingredients[row].length; column++) {
                        ItemStack itemStack = inv.getStackInRowAndColumn(column + columnOffset, row + rowOffset);
                        if(itemStack.isEmpty() || !ingredients[row][column].matches(CraftTweakerMC.getIItemStack(itemStack)))
                            continue outer;
                        visited[(column + columnOffset) + (row + rowOffset) * inv.getWidth()] = true;
                    }
                    for(int slot = 0; slot < visited.length; slot++) {
                        if(visited[slot])
                            continue;
                        if(!inv.getStackInSlot(slot).isEmpty())
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
        */
        return calculateOffset(inv) != offsetInvalid;
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        //if(!matches(inv, null))
        //    return ItemStack.EMPTY;
        if(recipeFunction != null) {
            Map<String, IItemStack> marks = new HashMap<>();
            for(int x = 0; x < ingredients.length; x++) {
                for(int y = 0; y < ingredients[x].length; y++) {
                    if(ingredients[x][y].getMark() != null)
                        marks.put(ingredients[x][y].getMark(), CraftTweakerMC.getIItemStack(inv.getStackInRowAndColumn(y, x)));
                }
            }
            
            return CraftTweakerMC.getItemStack(recipeFunction.process(output, marks, new CraftingInfo(MCCraftingInventorySquared.get(inv), null))).copy();
        }
        return CraftTweakerMC.getItemStack(output).copy();
    }
    
    @Override
    public boolean canFit(int width, int height) {
        return this.width <= width && this.height <= height;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return CraftTweakerMC.getItemStack(output);
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        Pair<Integer, Integer> offsetPair;
        //TODO calculate offsets and mirroring
        offsetPair = checkRecipe(ingredients, inv);
        if(offsetPair != offsetInvalid)
            return getRemainingItems(inv, offsetPair, ingredients);
        
        //Mirror on X-Axis
        IIngredient[][] ingredients = ArrayUtil.inverse(this.ingredients, height);
        offsetPair = checkRecipe(ingredients, inv);
        if(offsetPair != offsetInvalid)
            return getRemainingItems(inv, offsetPair, ingredients);
        
        //Mirror on Y-Axis
        for(int i = 0; i < ingredients.length; i++)
            ingredients[i] = ArrayUtil.inverse(this.ingredients[i], width);
        offsetPair = checkRecipe(ingredients, inv);
        if(offsetPair != offsetInvalid)
            return getRemainingItems(inv, offsetPair, ingredients);
        
        //Mirror on X-/Y-Axis
        ingredients = ArrayUtil.inverse(ingredients, height);
        offsetPair = checkRecipe(ingredients, inv);
        return getRemainingItems(inv, offsetPair, ingredients);
    }
    
    private NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv, Pair<Integer, Integer> offsetPair, IIngredient[][] ingredients) {
        NonNullList<ItemStack> out = IShapedRecipe.super.getRemainingItems(inv);
        if(offsetPair == offsetInvalid)
            return out;
        
        int rowOffset = offsetPair.getKey();
        int columnOffset = offsetPair.getValue();
        for(int column = 0; column < height; column++) {
            for(int row = 0; row < width; row++) {
                //TODO Player is null!
                ItemStack itemStack = inv.getStackInRowAndColumn(row + rowOffset, column + columnOffset);
                if(ingredients.length > column && ingredients[column].length > row) {
                    IIngredient ingredient = ingredients[row][column];
                    if(ingredient != null && ingredient.hasTransformers())
                        out.set((column + columnOffset) + (row + rowOffset) * inv.getWidth(), Optional.ofNullable(CraftTweakerMC.getItemStack(ingredient.applyTransform(CraftTweakerMC.getIItemStack(itemStack), null))).orElse(ItemStack.EMPTY));
                }
            }
        }
        return out;
    }
    
    public IIngredient[][] getIIngredients() {
        return ingredients;
    }
    
    private Pair<Integer, Integer> calculateOffset(InventoryCrafting inv) {
        //just test it in all possible mirrored states
        Pair<Integer, Integer> offset;
        offset = checkRecipe(ingredients, inv);
        if(offset != offsetInvalid || !isMirrored)
            return offset;
        offset = checkRecipe(ArrayUtil.inverse(ingredients, height), inv);
        if(offset != offsetInvalid)
            return offset;
        IIngredient[][] ingredients = new IIngredient[this.ingredients.length][];
        for(int i = 0; i < ingredients.length; i++)
            ingredients[i] = ArrayUtil.inverse(this.ingredients[i], width);
        
        offset = checkRecipe(ingredients, inv);
        if(offset != offsetInvalid)
            return offset;
        return checkRecipe(ArrayUtil.inverse(ingredients, height), inv);
    }
    
    //checks if the given IIngredient[][] somehow fits...
    private Pair<Integer, Integer> checkRecipe(IIngredient[][] ingredients, InventoryCrafting inv) {
        boolean[] visited = new boolean[inv.getSizeInventory()];
        for(int rowOffset = 0; rowOffset <= inv.getHeight() - height; rowOffset++) {
            outer:
            for(int columnOffset = 0; columnOffset <= inv.getWidth() - width; columnOffset++) {
                for(int row = 0; row < ingredients.length; row++) {
                    for(int column = 0; column < ingredients[row].length; column++) {
                        ItemStack itemStack = inv.getStackInRowAndColumn(column + columnOffset, row + rowOffset);
                        if(ingredients[row][column] == null)
                            if(itemStack.isEmpty())
                                continue;
                            else
                                return offsetInvalid;
                        if(itemStack.isEmpty() || !ingredients[row][column].matches(CraftTweakerMC.getIItemStack(itemStack)))
                            continue outer;
                        visited[(column + columnOffset) + (row + rowOffset) * inv.getWidth()] = true;
                        
                    }
                }
                for(int slot = 0; slot < visited.length; slot++) {
                    if(visited[slot])
                        continue;
                    if(!inv.getStackInSlot(slot).isEmpty())
                        return offsetInvalid;
                }
                return new Pair<>(rowOffset, columnOffset);
            }
        }
        return offsetInvalid;
    }
    
    @Override
    public String toCommandString() {
        StringBuilder commandString = new StringBuilder("recipes.addShaped(\"");
        commandString.append(this.getName()).append("\", ");
        commandString.append(this.output.toString()).append(", [");
        if(height > 0 && width > 0) {
            for(int row = 0; row < height; row++) {
                commandString.append("[");
                for(int column = 0; column < width; column++) {
                    IIngredient ingredient = ingredients[row][column];
                    commandString.append(ingredient == null ? "null" : ingredient.toCommandString()).append(", ");
                }
                //Remove last ,
                commandString.deleteCharAt(commandString.length() - 1);
                commandString.deleteCharAt(commandString.length() - 1);
                commandString.append("], ");
            }
            //Remove last ,
            commandString.deleteCharAt(commandString.length() - 1);
            commandString.deleteCharAt(commandString.length() - 1);
        }
        return commandString.append("]);").toString();
    }
    
    @Override
    public int getRecipeWidth() {
        return width;
    }
    
    @Override
    public int getRecipeHeight() {
        return height;
    }
}
