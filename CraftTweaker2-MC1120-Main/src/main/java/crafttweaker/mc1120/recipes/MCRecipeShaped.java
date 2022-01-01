package crafttweaker.mc1120.recipes;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IShapedRecipe;
import stanhebben.zenscript.util.*;

import java.util.*;

public class MCRecipeShaped extends MCRecipeBase implements IShapedRecipe {

    private static final Pair<Integer, Integer> offsetInvalid = new Pair<>(-1, -1);

    private final IIngredient[][] ingredients;
    private final int width, height;
    private final boolean isMirrored;

    public MCRecipeShaped(IIngredient[][] ingredients, IItemStack output, IRecipeFunction recipeFunction, IRecipeAction recipeAction, boolean isMirrored, boolean isHidden) {
        super(output, createIngredientList(ingredients), recipeFunction, recipeAction, isHidden);

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
        for(int row = 0; row < ingredients.length; row++) {
            for(int column = 0; column < ingredients[row].length; column++) {
                if(ingredients[row][column] != null)
                    ingredientList.set(row * width + column, CraftTweakerMC.getIngredient(ingredients[row][column]));
            }
        }
        return ingredientList;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return calculateOffset(inv) != offsetInvalid;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {

        Pair<Integer, Integer> offsetPair;
        offsetPair = checkRecipe(ingredients, inv);
        if(offsetPair != offsetInvalid || !isMirrored)
            return getCraftingResult(inv, offsetPair, ingredients);

        //Mirror on X-Axis
        IIngredient[][] ingredients = ArrayUtil.inverse(this.ingredients, height);
        offsetPair = checkRecipe(ingredients, inv);
        if(offsetPair != offsetInvalid)
            return getCraftingResult(inv, offsetPair, ingredients);

        //Mirror on Y-Axis
        for(int i = 0; i < ingredients.length; i++)
            ingredients[i] = ArrayUtil.inverse(this.ingredients[i], width);
        offsetPair = checkRecipe(ingredients, inv);
        if(offsetPair != offsetInvalid)
            return getCraftingResult(inv, offsetPair, ingredients);

        //Mirror on X-/Y-Axis
        ingredients = ArrayUtil.inverse(ingredients, height);
        offsetPair = checkRecipe(ingredients, inv);
        return getCraftingResult(inv, offsetPair, ingredients);

    }

    private ItemStack getCraftingResult(InventoryCrafting inv, Pair<Integer, Integer> offsetPair, IIngredient[][] ingredients) {
        if(offsetPair == offsetInvalid)
            return ItemStack.EMPTY;
        int rowOffset = offsetPair.getKey();
        int columnOffset = offsetPair.getValue();
        if(recipeFunction != null) {
            Map<String, IItemStack> marks = new HashMap<>();
            for(int row = 0; row < ingredients.length; row++) {
                for(int column = 0; column < ingredients[row].length; column++) {
                    IIngredient ingredient = ingredients[row][column];
                    if(ingredient != null && ingredient.getMark() != null)
                        marks.put(ingredient.getMark(), CraftTweakerMC.getIItemStack(inv.getStackInRowAndColumn(column + columnOffset, row + rowOffset)));
                }
            }
            IItemStack out = null;
            try {
                out = recipeFunction.process(output, marks, new CraftingInfo(MCCraftingInventorySquared.get(inv), null));
            } catch(Exception exception) {
                CraftTweakerAPI.logError("Could not execute RecipeFunction: ", exception);
            }
            return CraftTweakerMC.getItemStack(out);
        }
        return CraftTweakerMC.getItemStack(output);
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
    public void applyTransformers(InventoryCrafting inventory, IPlayer byPlayer) {
        Pair<Integer, Integer> offsetPair;
        offsetPair = checkRecipe(ingredients, inventory);
        if(offsetPair != offsetInvalid || !isMirrored) {
            applyTransformers(inventory, byPlayer, offsetPair, ingredients);
            return;
        }

        //Mirror on X-Axis
        IIngredient[][] ingredients = ArrayUtil.inverse(this.ingredients, height);
        offsetPair = checkRecipe(ingredients, inventory);
        if(offsetPair != offsetInvalid) {
            applyTransformers(inventory, byPlayer, offsetPair, ingredients);
            return;
        }

        //Mirror on Y-Axis
        for(int i = 0; i < ingredients.length; i++)
            ingredients[i] = ArrayUtil.inverse(this.ingredients[i], width);
        offsetPair = checkRecipe(ingredients, inventory);
        if(offsetPair != offsetInvalid) {
            applyTransformers(inventory, byPlayer, offsetPair, ingredients);
            return;
        }

        //Mirror on X-/Y-Axis
        ingredients = ArrayUtil.inverse(ingredients, height);
        offsetPair = checkRecipe(ingredients, inventory);
        applyTransformers(inventory, byPlayer, offsetPair, ingredients);
    }

    private void applyTransformers(InventoryCrafting inventory, IPlayer byPlayer, Pair<Integer, Integer> offsetPair, IIngredient[][] ingredients) {
        if(offsetPair == offsetInvalid)
            return;
        int rowOffset = offsetPair.getKey();
        int columnOffset = offsetPair.getValue();

        for(int column = 0; column < height; column++) {
            for(int row = 0; row < width; row++) {
                ItemStack itemStack = inventory.getStackInSlot((column + columnOffset) + (row + rowOffset) * inventory.getWidth());
                if(ingredients.length > row && ingredients[row].length > column) {
                    IIngredient ingredient = ingredients[row][column];
                    if(ingredient != null && ingredient.hasTransformers()) {
                        IItemStack out = null;
                        try {
                            out = ingredient.applyTransform(CraftTweakerMC.getIItemStack(itemStack), byPlayer);
                        } catch(Exception exception) {
                            CraftTweakerAPI.logError("Could not execute RecipeTransformer on : " + ingredient.toCommandString(), exception);
                        }
                        inventory.setInventorySlotContents((column + columnOffset) + (row + rowOffset) * inventory.getWidth(), CraftTweakerMC.getItemStack(out));

                    }
                }
            }

        }
    }

    @Override
    public MCRecipeShaped update() {
        this.ingredientList = createIngredientList(ingredients);
        return this;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        Pair<Integer, Integer> offsetPair;
        offsetPair = checkRecipe(ingredients, inv);
        if(offsetPair != offsetInvalid || !isMirrored)
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
        NonNullList<ItemStack> out = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        if(offsetPair == offsetInvalid)
            return out;

        int rowOffset = offsetPair.getKey();
        int columnOffset = offsetPair.getValue();
        for(int column = 0; column < width; column++) {
            for(int row = 0; row < height; row++) {
                //TODO Player is null!
                ItemStack itemStack = inv.getStackInSlot((column + columnOffset) + (row + rowOffset) * inv.getWidth());
                if(ingredients.length > row && ingredients[row].length > column) {
                    IIngredient ingredient = ingredients[row][column];
                    boolean needsContainerItem = true;
                    if(ingredient == null) {
                        continue;
                    }
                    if(ingredient.hasNewTransformers()) {
                        IItemStack remainingItem = null;
                        try {
                            remainingItem = ingredient.applyNewTransform(CraftTweakerMC.getIItemStack(itemStack));
                        } catch(Exception exception) {
                            CraftTweakerAPI.logError("Could not execute NewRecipeTransformer on " + ingredient.toCommandString() + ":", exception);
                        }
                        if(remainingItem != ItemStackUnknown.INSTANCE) {
                            out.set((column + columnOffset) + (row + rowOffset) * inv.getWidth(), CraftTweakerMC.getItemStack(remainingItem));
                            needsContainerItem = false;
                        }
                    }
                    if(ingredient.hasTransformers()) {
                        //increase stackSize by 1 so that it can then be decreased by one in the crafting process
                        //done to insure the transformer works as intended
                        itemStack.setCount(itemStack.getCount() + 1);
                        needsContainerItem = false;
                    }

                    if(needsContainerItem) {
                        out.set((column + columnOffset) + (row + rowOffset) * inv.getWidth(), ForgeHooks.getContainerItem(itemStack));
                    }
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
                        if(itemStack.isEmpty() || !ingredients[row][column].matches(CraftTweakerMC.getIItemStackForMatching(itemStack)))
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

                //Check if other slots are empty
                for(int slot = 0; slot < inv.getSizeInventory(); slot++) {
                    final int row = (slot / inv.getWidth()) - rowOffset;
                    final int column = (slot % inv.getWidth()) - columnOffset;
                    if((row < 0 || column < 0) && !inv.getStackInSlot(slot).isEmpty())
                        return offsetInvalid;
                }
                return new Pair<>(rowOffset, columnOffset);
            }
        }
        return offsetInvalid;
    }

    @Override
    public String toCommandString() {
        StringBuilder commandString = new StringBuilder("recipes.add");
        commandString.append(hidden ? "Hidden" : "");
        commandString.append("Shaped(\"");
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
    public boolean hasTransformers() {
        for(IIngredient[] row : ingredients)
            for(IIngredient ingredient : row)
                if(ingredient != null && ingredient.hasTransformers())
                    return true;
        return false;
    }

    @Override
    public IIngredient[] getIngredients1D() {
        IIngredient[] out = new IIngredient[ingredientList.size()];
        int index = 0;
        for(IIngredient[] row : ingredients) {
            for(IIngredient ingredient : row) {
                out[index++] = ingredient;
            }
        }
        return out;
    }

    @Override
    public IIngredient[][] getIngredients2D() {
        return ingredients;
    }

    @Override
    public boolean isShaped() {
        return true;
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
