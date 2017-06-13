package minetweaker.mc1120.recipes;

import minetweaker.*;
import minetweaker.api.item.*;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.api.recipes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.oredict.*;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;

import static minetweaker.api.minecraft.MineTweakerMC.*;

/**
 * @author Stan
 */
public class MCRecipeManager implements IRecipeManager {
    
    public static List<IRecipe> recipes;
    private static List<ICraftingRecipe> transformerRecipes;
    
    public MCRecipeManager() {
        //TODO change this back
        recipes = new ArrayList<>();
        
        CraftingManager.field_193380_a.getKeys().forEach(key -> {
            recipes.add(CraftingManager.func_193373_a(key));
        });
        transformerRecipes = new ArrayList<>();
    }
    
    private static boolean matches(Object input, IIngredient ingredient) {
        if((input == null) != (ingredient == null)) {
            return false;
        } else if(ingredient != null) {
            if(input instanceof ItemStack) {
                if(((ItemStack) input).isEmpty()) {
                    return false;
                }
                if(!ingredient.matches(getIItemStack((ItemStack) input))) {
                    return false;
                }
            } else if(input instanceof String) {
                if(!ingredient.contains(getOreDict((String) input))) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public boolean hasTransformerRecipes() {
        return transformerRecipes.size() > 0;
    }
    
    public void applyTransformations(ICraftingInventory inventory, IPlayer byPlayer) {
        for(ICraftingRecipe recipe : transformerRecipes) {
            if(recipe.matches(inventory)) {
                recipe.applyTransformers(inventory, byPlayer);
                return;
            }
        }
    }
    
    @Override
    public List<ICraftingRecipe> getRecipesFor(IIngredient ingredient) {
        List<ICraftingRecipe> results = new ArrayList<>();
        
        recipes.stream().filter(recipe -> ingredient.matches(MineTweakerMC.getIItemStack(recipe.getRecipeOutput()))).forEach(recipe -> {
            ICraftingRecipe converted = RecipeConverter.toCraftingRecipe(recipe);
            results.add(converted);
        });
        
        return results;
    }
    
    @Override
    public List<ICraftingRecipe> getAll() {
        List<ICraftingRecipe> results = new ArrayList<>();
        
        for(IRecipe recipe : recipes) {
            ICraftingRecipe converted = RecipeConverter.toCraftingRecipe(recipe);
            results.add(converted);
        }
        
        return results;
    }
    
    @Override
    public int remove(IIngredient output, @Optional boolean nbtMatch) {
        List<IRecipe> toRemove = new ArrayList<>();
        List<Integer> removeIndex = new ArrayList<>();
        for(int i = 0; i < recipes.size(); i++) {
            IRecipe recipe = recipes.get(i);
            
            // certain special recipes have no predefined output. ignore those
            // since these cannot be removed with MineTweaker scripts
            if(!recipe.getRecipeOutput().isEmpty()) {
                if(nbtMatch ? output.matchesExact(getIItemStack(recipe.getRecipeOutput())) : output.matches(getIItemStack(recipe.getRecipeOutput()))) {
                    toRemove.add(recipe);
                    removeIndex.add(i);
                }
            }
        }
        
        MineTweakerAPI.apply(new ActionRemoveRecipes(toRemove, removeIndex));
        return toRemove.size();
    }
    
    @Override
    public void addShaped(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        addShaped(output, ingredients, function, action, false);
    }
    
    @Override
    public void addShapedMirrored(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        addShaped(output, ingredients, function, action, true);
    }
    
    @Override
    public void addShapeless(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ShapelessRecipe recipe = new ShapelessRecipe(output, ingredients, function, action);
        IRecipe irecipe = RecipeConverter.convert(recipe);
        MineTweakerAPI.apply(new ActionAddRecipe(irecipe, recipe));
    }
    
    @Override
    public int removeShaped(IIngredient output, IIngredient[][] ingredients) {
        int ingredientsWidth = 0;
        int ingredientsHeight = 0;
        
        if(ingredients != null) {
            ingredientsHeight = ingredients.length;
            
            for(int i = 0; i < ingredients.length; i++) {
                ingredientsWidth = Math.max(ingredientsWidth, ingredients[i].length);
            }
        }
        
        List<IRecipe> toRemove = new ArrayList<>();
        List<Integer> removeIndex = new ArrayList<>();
        outer:
        for(int i = 0; i < recipes.size(); i++) {
            IRecipe recipe = recipes.get(i);
            
            if(recipe.getRecipeOutput().isEmpty() || !output.matches(getIItemStack(recipe.getRecipeOutput()))) {
                continue;
            }
            
            if(ingredients != null) {
                if(recipe instanceof ShapedRecipes) {
                    ShapedRecipes srecipe = (ShapedRecipes) recipe;
                    if(ingredientsWidth != srecipe.recipeWidth || ingredientsHeight != srecipe.recipeHeight) {
                        continue;
                    }
                    
                    for(int j = 0; j < ingredientsHeight; j++) {
                        IIngredient[] row = ingredients[j];
                        for(int k = 0; k < ingredientsWidth; k++) {
                            IIngredient ingredient = k > row.length ? null : row[k];
                            ItemStack recipeIngredient = srecipe.func_192400_c().get(j * srecipe.recipeWidth + k).func_193365_a()[0];
                            
                            if(!matches(recipeIngredient, ingredient)) {
                                continue outer;
                            }
                        }
                    }
                } else if(recipe instanceof ShapedOreRecipe) {
                    ShapedOreRecipe srecipe = (ShapedOreRecipe) recipe;
                    int recipeWidth = srecipe.getWidth();
                    int recipeHeight = srecipe.getHeight();
                    if(ingredientsWidth != recipeWidth || ingredientsHeight != recipeHeight) {
                        continue;
                    }
                    
                    for(int j = 0; j < ingredientsHeight; j++) {
                        IIngredient[] row = ingredients[j];
                        for(int k = 0; k < ingredientsWidth; k++) {
                            IIngredient ingredient = k > row.length ? null : row[k];
                            Object input = srecipe.func_192400_c().get(j * recipeWidth + k).func_193365_a()[0];
                            if(!matches(input, ingredient)) {
                                continue outer;
                            }
                        }
                    }
                } else {
                    if(recipe instanceof ShapelessRecipes) {
                        continue;
                    } else if(recipe instanceof ShapelessOreRecipe) {
                        continue;
                    }
                }
            } else {
                if(recipe instanceof ShapelessRecipes) {
                    continue;
                } else if(recipe instanceof ShapelessOreRecipe) {
                    continue;
                }
            }
            
            toRemove.add(recipe);
            removeIndex.add(i);
        }
        
        MineTweakerAPI.apply(new ActionRemoveRecipes(toRemove, removeIndex));
        return toRemove.size();
    }
    
    @Override
    public int removeShapeless(IIngredient output, IIngredient[] ingredients, boolean wildcard) {
        List<IRecipe> toRemove = new ArrayList<>();
        List<Integer> removeIndex = new ArrayList<>();
        outer:
        for(int i = 0; i < recipes.size(); i++) {
            IRecipe recipe = recipes.get(i);
            
            if(recipe.getRecipeOutput().isEmpty() || !output.matches(getIItemStack(recipe.getRecipeOutput()))) {
                continue;
            }
            
            if(ingredients != null) {
                if(recipe instanceof ShapelessRecipes) {
                    ShapelessRecipes srecipe = (ShapelessRecipes) recipe;
                    
                    if(ingredients.length > srecipe.func_192400_c().size()) {
                        continue;
                    } else if(!wildcard && ingredients.length < srecipe.func_192400_c().size()) {
                        continue;
                    }
                    
                    checkIngredient:
                    for(int j = 0; j < ingredients.length; j++) {
                        for(int k = 0; k < srecipe.func_192400_c().size(); k++) {
                            if(matches(srecipe.recipeItems.get(k), ingredients[j])) {
                                continue checkIngredient;
                            }
                        }
                        
                        continue outer;
                    }
                } else if(recipe instanceof ShapelessOreRecipe) {
                    ShapelessOreRecipe srecipe = (ShapelessOreRecipe) recipe;
                    NonNullList<Ingredient> inputs = srecipe.func_192400_c();
                    
                    if(inputs.size() < ingredients.length) {
                        continue;
                    }
                    if(!wildcard && inputs.size() > ingredients.length) {
                        continue;
                    }
                    
                    checkIngredient:
                    for(int j = 0; j < ingredients.length; j++) {
                        for(int k = 0; k < srecipe.func_192400_c().size(); k++) {
                            if(matches(inputs.get(k), ingredients[j])) {
                                continue checkIngredient;
                            }
                        }
                        
                        continue outer;
                    }
                }
                if(recipe instanceof ShapedRecipes) {
                    continue;
                } else if(recipe instanceof ShapedOreRecipe) {
                    continue;
                }
            } else {
                if(recipe instanceof ShapedRecipes) {
                    continue;
                } else if(recipe instanceof ShapedOreRecipe) {
                    continue;
                }
            }
            toRemove.add(recipe);
            removeIndex.add(i);
        }
        
        MineTweakerAPI.apply(new ActionRemoveRecipes(toRemove, removeIndex));
        return toRemove.size();
    }
    
    @Override
    public IItemStack craft(IItemStack[][] contents) {
        Container container = new ContainerVirtual();
        
        int width = 0;
        int height = contents.length;
        for(IItemStack[] row : contents) {
            width = Math.max(width, row.length);
        }
        
        ItemStack[] iContents = new ItemStack[width * height];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < contents[i].length; j++) {
                if(contents[i][j] != null) {
                    iContents[i * width + j] = getItemStack(contents[i][j]);
                }
            }
        }
        
        InventoryCrafting inventory = new InventoryCrafting(container, width, height);
        for(int i = 0; i < iContents.length; i++) {
            inventory.setInventorySlotContents(i, iContents[i]);
        }
        ItemStack result = CraftingManager.findMatchingRecipe(inventory, null);
        if(result.isEmpty()) {
            return null;
        } else {
            return getIItemStack(result);
        }
    }
    
    private void addShaped(IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action, boolean mirrored) {
        ShapedRecipe recipe = new ShapedRecipe(output, ingredients, function, action, mirrored);
        IRecipe irecipe = RecipeConverter.convert(recipe);
        MineTweakerAPI.apply(new ActionAddRecipe(irecipe, recipe));
    }
    
    private class ContainerVirtual extends Container {
        
        @Override
        public boolean canInteractWith(EntityPlayer var1) {
            return false;
        }
    }
    
    public static class ActionRemoveRecipes implements IUndoableAction {
        
        private final List<Integer> removingIndices;
        private final List<IRecipe> removingRecipes;
        
        public ActionRemoveRecipes(List<IRecipe> recipes, List<Integer> indices) {
            this.removingIndices = indices;
            this.removingRecipes = recipes;
        }
        
        @Override
        public void apply() {
            for(int i = removingIndices.size() - 1; i >= 0; i--) {
                recipes.remove((int) removingIndices.get(i));
                if(removingRecipes.get(i) != null)
                    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(removingRecipes.get(i), "minecraft.crafting");
            }
        }
        
        @Override
        public boolean canUndo() {
            return true;
        }
        
        @Override
        public void undo() {
            for(int i = 0; i < removingIndices.size(); i++) {
                int index = Math.min(recipes.size(), removingIndices.get(i));
                recipes.add(index, removingRecipes.get(i));
                if(removingRecipes.get(i) != null)
                    MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(removingRecipes.get(i), "minecraft.crafting");
            }
        }
        
        @Override
        public String describe() {
            return "Removing " + removingIndices.size() + " recipes";
        }
        
        @Override
        public String describeUndo() {
            return "Restoring " + removingIndices.size() + " recipes";
        }
        
        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
    
    private class ActionAddRecipe implements IUndoableAction {
        
        private final IRecipe recipe;
        private final ICraftingRecipe craftingRecipe;
        
        public ActionAddRecipe(IRecipe recipe, ICraftingRecipe craftingRecipe) {
            this.recipe = recipe;
            this.craftingRecipe = craftingRecipe;
        }
        
        @Override
        public void apply() {
            recipes.add(recipe);
            //TODO change the resource location and remove this when forge has a system
            CraftingManager.func_193372_a(new ResourceLocation("crafttweaker:test" + recipe.getRecipeOutput().getUnlocalizedName()), recipe);
            if(recipe instanceof ShapedRecipeBasic) {
                ShapedRecipeBasic r = (ShapedRecipeBasic) recipe;
            }
            if(craftingRecipe.hasTransformers()) {
                transformerRecipes.add(craftingRecipe);
            }
            if(recipe != null)
                MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe, "minecraft.crafting");
        }
        
        @Override
        public boolean canUndo() {
            return true;
        }
        
        @Override
        public void undo() {
            recipes.remove(recipe);
            if(craftingRecipe.hasTransformers()) {
                transformerRecipes.remove(craftingRecipe);
            }
            if(recipe != null)
                MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe, "minecraft.crafting");
        }
        
        @Override
        public String describe() {
            return "Adding recipe for " + recipe.getRecipeOutput().getDisplayName();
        }
        
        @Override
        public String describeUndo() {
            return "Removing recipe for " + recipe.getRecipeOutput().getDisplayName();
        }
        
        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
