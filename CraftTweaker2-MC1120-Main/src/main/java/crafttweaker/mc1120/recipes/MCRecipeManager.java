package crafttweaker.mc1120.recipes;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.CraftTweaker;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

/**
 * @author Stan
 */
public class MCRecipeManager implements IRecipeManager {
    
    public static Set<Map.Entry<ResourceLocation, IRecipe>> recipes;
    public static List<ActionBaseAddRecipe> recipesToAdd = new ArrayList<>();
    public static List<ActionBaseRemoveRecipes> recipesToRemove = new ArrayList<>();
    private static TIntSet usedHashes = new TIntHashSet();
    
    private static List<ICraftingRecipe> transformerRecipes;
    
    public MCRecipeManager() {
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
        
        recipes.stream().filter(recipe -> ingredient.matches(CraftTweakerMC.getIItemStack(recipe.getValue().getRecipeOutput()))).forEach(recipe -> {
            ICraftingRecipe converted = RecipeConverter.toCraftingRecipe(recipe.getValue());
            results.add(converted);
        });
        
        return results;
    }
    
    @Override
    public List<ICraftingRecipe> getAll() {
        List<ICraftingRecipe> results = new ArrayList<>();
        for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
            ICraftingRecipe converted = RecipeConverter.toCraftingRecipe(recipe.getValue());
            results.add(converted);
        }
        
        return results;
    }

    @Override
    public void addShaped(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(output, ingredients, function, action, false));
    }
    
    @Override
    public void addShapedMirrored(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(output, ingredients, function, action, true));
    }
    
    @Override
    public void addShapeless(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapelessRecipe(output, ingredients, function, action));
    }

    @Override
    public void removeAll() {
        recipesToRemove.add(new ActionRemoveAllRecipes());
    }

    @Override
    public void remove(IIngredient output, @Optional boolean nbtMatch) {
        recipesToRemove.add(new ActionRemoveRecipesNoIngredients(output, nbtMatch));
    }

    @Override
    public void removeShaped(IIngredient output, IIngredient[][] ingredients) {
        recipesToRemove.add(new ActionRemoveShapedRecipes(output, ingredients));
    }
    
    @Override
    public void removeShapeless(IIngredient output, IIngredient[] ingredients, boolean wildcard) {
        recipesToRemove.add(new ActionRemoveShapelessRecipes(output, ingredients, wildcard));
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
        ItemStack result = CraftingManager.findMatchingResult(inventory, null);
        if(result.isEmpty()) {
            return null;
        } else {
            return getIItemStack(result);
        }
    }
    
    private class ContainerVirtual extends Container {
        
        @Override
        public boolean canInteractWith(EntityPlayer var1) {
            return false;
        }
    }

    /**
     * Classes of all removeRecipe Actions
     */
    public static abstract class ActionBaseRemoveRecipes implements IAction {
        public void removeRecipes(List<ResourceLocation> removingRecipes) {
            removingRecipes.forEach(recipe -> RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(recipe));
        }
    }

    public static class ActionRemoveShapedRecipes extends ActionBaseRemoveRecipes{
        IIngredient output;
        IIngredient[][] ingredients;


        public ActionRemoveShapedRecipes(IIngredient output, IIngredient[][] ingredients) {
            this.output = output;
            this.ingredients = ingredients;
        }

        @Override
        public void apply() {

            int ingredientsWidth = 0;
            int ingredientsHeight = 0;

            if(ingredients != null) {
                ingredientsHeight = ingredients.length;

                for(IIngredient[] ingredient : ingredients) {
                    ingredientsWidth = Math.max(ingredientsWidth, ingredient.length);
                }
            }

            List<ResourceLocation> toRemove = new ArrayList<>();
            outer:
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                if(recipe.getValue().getRecipeOutput().isEmpty() || !output.matches(getIItemStack(recipe.getValue().getRecipeOutput()))) {
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
                                ItemStack recipeIngredient = srecipe.getIngredients().get(j * srecipe.recipeWidth + k).getMatchingStacks()[0];

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
                                Object input = srecipe.getIngredients().get(j * recipeWidth + k).getMatchingStacks()[0];
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

                toRemove.add(recipe.getKey());
            }

            super.removeRecipes(toRemove);
        }

        @Override
        public String describe() {
            return "Removing Shaped recipes for " + output.toString();
        }
    }

    public static class ActionRemoveShapelessRecipes extends ActionBaseRemoveRecipes{
        IIngredient output;
        IIngredient[] ingredients;
        boolean wildcard;

        public ActionRemoveShapelessRecipes(IIngredient output, IIngredient[] ingredients, boolean wildcard) {
            this.output = output;
            this.ingredients = ingredients;
            this.wildcard = wildcard;
        }

        @Override
        public void apply() {

            List<ResourceLocation> toRemove = new ArrayList<>();
            outer:
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {

                if(recipe.getValue().getRecipeOutput().isEmpty() || !output.matches(getIItemStack(recipe.getValue().getRecipeOutput()))) {
                    continue;
                }

                if(ingredients != null) {
                    if(recipe instanceof ShapelessRecipes) {
                        ShapelessRecipes srecipe = (ShapelessRecipes) recipe;

                        if(ingredients.length > srecipe.getIngredients().size()) {
                            continue;
                        } else if(!wildcard && ingredients.length < srecipe.getIngredients().size()) {
                            continue;
                        }

                        checkIngredient:
                        for(IIngredient ingredient : ingredients) {
                            for(int k = 0; k < srecipe.getIngredients().size(); k++) {
                                if(matches(srecipe.recipeItems.get(k), ingredient)) {
                                    continue checkIngredient;
                                }
                            }

                            continue outer;
                        }
                    } else if(recipe instanceof ShapelessOreRecipe) {
                        ShapelessOreRecipe srecipe = (ShapelessOreRecipe) recipe;
                        NonNullList<Ingredient> inputs = srecipe.getIngredients();

                        if(inputs.size() < ingredients.length) {
                            continue;
                        }
                        if(!wildcard && inputs.size() > ingredients.length) {
                            continue;
                        }

                        checkIngredient:
                        for(IIngredient ingredient : ingredients) {
                            for(int k = 0; k < srecipe.getIngredients().size(); k++) {
                                if(matches(inputs.get(k), ingredient)) {
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
                toRemove.add(recipe.getKey());
            }

            super.removeRecipes(toRemove);
        }

        @Override
        public String describe() {
            return "Removing Shapeless recipes for " + output.toString();
        }
    }

    public static class ActionRemoveRecipesNoIngredients extends ActionBaseRemoveRecipes{
        IIngredient output;
        boolean nbtMatch;

        public ActionRemoveRecipesNoIngredients(IIngredient output, @Optional boolean nbtMatch) {
            this.output = output;
            this.nbtMatch = nbtMatch;
        }

        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();

            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                if(!recipe.getValue().getRecipeOutput().isEmpty()) {
                    if(nbtMatch ? output.matchesExact(getIItemStack(recipe.getValue().getRecipeOutput())) : output.matches(getIItemStack(recipe.getValue().getRecipeOutput()))) {
                        toRemove.add(recipe.getKey());
                    }
                }
            }

            super.removeRecipes(toRemove);
        }

        @Override
        public String describe() {
            return "Removing all recipes for " + output.toString();
        }
    }

    public static class ActionRemoveAllRecipes extends ActionBaseRemoveRecipes{
        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                toRemove.add(recipe.getKey());
            }

            super.removeRecipes(toRemove);
        }

        @Override
        public String describe() {
            return "Removing all crafting recipes";
        }
    }

    /**
     * Classes for all addRecipe Actions
     */
    public static abstract class ActionBaseAddRecipe implements IAction{

        public void registerRecipe(IRecipe recipe, ICraftingRecipe craftingRecipe){
            recipe.setRegistryName(new ResourceLocation("crafttweaker", calculateHashBasedName()));
            ForgeRegistries.RECIPES.register(recipe);

            if(craftingRecipe.hasTransformers()) {
                transformerRecipes.add(craftingRecipe);
            }
        }

        public abstract String calculateHashBasedName();
    }

    public static class ActionAddShapedRecipe extends ActionBaseAddRecipe{
        String name;
        IItemStack output;
        IIngredient[][] ingredients;
        IRecipeFunction function;
        IRecipeAction action;
        boolean mirrored;

        public ActionAddShapedRecipe(IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action, boolean mirrored){
            this.name = calculateHashBasedName();
            this.output = output;
            this.ingredients = ingredients;
            this.function = function;
            this.action = action;
            this.mirrored = mirrored;
        }

        @Override
        public void apply() {
            ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, mirrored);
            IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation(CraftTweaker.MODID, name));

            super.registerRecipe(irecipe, recipe);
        }

        @Override
        public String calculateHashBasedName() {
            int hash = Arrays.deepHashCode(new Object[]{output, ingredients});
            while (usedHashes.contains(hash)) ++hash;
            usedHashes.add(hash);
            return "ct_shaped" + hash;
        }

        @Override
        public String describe() {
            return "Adding shaped recipe for " + output.getDisplayName() + " with name " + name;
        }
    }

    public static class ActionAddShapelessRecipe extends ActionBaseAddRecipe{

        String name;
        IItemStack output;
        IIngredient[] ingredients;
        IRecipeFunction function;
        IRecipeAction action;

        public ActionAddShapelessRecipe(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action){
            this.name = calculateHashBasedName();
            this.output = output;
            this.ingredients = ingredients;
            this.function = function;
            this.action = action;
        }

        @Override
        public void apply() {
            ShapelessRecipe recipe = new ShapelessRecipe(name, output, ingredients, function, action);
            IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation(CraftTweaker.MODID, name));

            super.registerRecipe(irecipe, recipe);
        }

        @Override
        public String calculateHashBasedName() {
            int hash = Arrays.deepHashCode(new Object[]{output, ingredients});
            while (usedHashes.contains(hash)) ++hash;
            usedHashes.add(hash);
            return "ct_shapeless" + hash;
        }

        @Override
        public String describe() {
            return "Adding shapeless recipe for " + output.getDisplayName() + " with name " + name;
        }
    }
}
