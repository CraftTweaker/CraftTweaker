package crafttweaker.mc1120.recipes;

import crafttweaker.*;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.*;
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
import net.minecraft.util.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.regex.*;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

/**
 * @author Stan, BloodWorkXGaming
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
    public void addShaped(String name, IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(name, output, ingredients, function, action, false));
    }
    
    @Override
    public void addShapedMirrored(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(output, ingredients, function, action, true));
    }
    
    @Override
    public void addShapedMirrored(String name, IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(name, output, ingredients, function, action, true));
    }
    
    @Override
    public void addShapeless(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapelessRecipe(output, ingredients, function, action));
    }
    
    @Override
    public void addShapeless(String name, IItemStack output, IIngredient[] ingredients, IRecipeFunction function, IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapelessRecipe(name, output, ingredients, function, action));
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
    public void removeByRecipeName(String recipeName) {
        recipesToRemove.add(new ActionRemoveRecipeByRecipeName(recipeName));
    }
    
    @Override
    public void removeByRegex(String regexString) {
        recipesToRemove.add(new ActionRemoveRecipeByRegex(regexString));
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
    
    /**
     * Classes of all removeRecipe Actions
     */
    public static abstract class ActionBaseRemoveRecipes implements IAction {
        
        public void removeRecipes(List<ResourceLocation> removingRecipes) {
            removingRecipes.forEach(recipe -> RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(recipe));
        }
    }
    
    public static class ActionRemoveShapedRecipes extends ActionBaseRemoveRecipes {
        
        IIngredient output;
        IIngredient[][] ingredients;
        
        
        public ActionRemoveShapedRecipes(IIngredient output, IIngredient[][] ingredients) {
            this.output = output;
            this.ingredients = ingredients;
        }
        
        @Override
        public void apply() {
            if(output == null){
                return;
            }
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
            if (output != null){
                return "Removing Shaped recipes for " + output.toString();
            }else {
                return "Trying to remove recipes for invalid output";
            }
        }
    }
    
    public static class ActionRemoveShapelessRecipes extends ActionBaseRemoveRecipes {
        
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
            if(output == null){
                return;
            }
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
            if (output != null){
                return "Removing Shapeless recipes for " + output.toString();
            }else {
                return "Trying to remove recipes for invalid output";
            }
        }
    }
    
    public static class ActionRemoveRecipesNoIngredients extends ActionBaseRemoveRecipes {
        
        IIngredient output;
        boolean nbtMatch;
        
        public ActionRemoveRecipesNoIngredients(IIngredient output, @Optional boolean nbtMatch) {
            this.output = output;
            this.nbtMatch = nbtMatch;
        }
        
        @Override
        public void apply() {
            if(output == null){
                return;
            }
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
            if (output != null){
                return "Removing all recipes for " + output.toString();
            }else {
                return "Trying to remove recipes for invalid output";
            }
        }
    }
    
    public static class ActionRemoveRecipeByRecipeName extends ActionBaseRemoveRecipes {
        String recipeName;
        
        public ActionRemoveRecipeByRecipeName(String recipeName) {
            this.recipeName = recipeName;
        }
        
        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();
            
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                if (recipe.getKey().toString().equals(recipeName)){
                    toRemove.add(recipe.getKey());
                    
                    super.removeRecipes(toRemove);
                }
            }
            
            super.removeRecipes(toRemove);
        }
        
        @Override
        public String describe() {
            if (recipeName != null){
                return "Removing recipe with name \"" + recipeName + "\"";
            }else {
                return "No name for the recipe to remove was given.";
            }
        }
    }
    
    public static class ActionRemoveRecipeByRegex extends ActionBaseRemoveRecipes {
        String regexCheck;
        
        public ActionRemoveRecipeByRegex(String regexCheck) {
            this.regexCheck = regexCheck;
        }
        
        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();
            Pattern p = Pattern.compile(regexCheck);
            
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                ResourceLocation resourceLocation = recipe.getKey();
                Matcher m = p.matcher(resourceLocation.toString());
                if (m.matches()){
                    toRemove.add(resourceLocation);
                }
            }
            
            super.removeRecipes(toRemove);
        }
        
        @Override
        public String describe() {
            if (regexCheck != null){
                return "Removing all recipes matching this regex: \"" + regexCheck + "\"";
            }else {
                return "No regex String for the recipe to remove was given.";
            }
        }
    }
    
    public static class ActionRemoveAllRecipes extends ActionBaseRemoveRecipes {
        
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
    public static abstract class ActionBaseAddRecipe implements IAction {
        
        // this is != null only _after_ it has been applied and is actually registered
        protected IRecipe recipe;
        protected String name;
        
        
        public void registerRecipe(IRecipe recipe, ICraftingRecipe craftingRecipe) {
            recipe.setRegistryName(new ResourceLocation("crafttweaker", name));
            ForgeRegistries.RECIPES.register(recipe);
            
            this.recipe = recipe;
            
            if(craftingRecipe.hasTransformers()) {
                transformerRecipes.add(craftingRecipe);
            }
        }
        
        public IRecipe getRecipe() {
            return recipe;
        }
    
        public String getName() {
            return name;
        }
    }
    
    public static class ActionAddShapedRecipe extends ActionBaseAddRecipe {
        
        IItemStack output;
        IIngredient[][] ingredients;
        IRecipeFunction function;
        IRecipeAction action;
        boolean mirrored;
        
        public ActionAddShapedRecipe(IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action, boolean mirrored) {
            this.output = output;
            this.ingredients = ingredients;
            this.function = function;
            this.action = action;
            this.mirrored = mirrored;
            this.name = calculateName();
        }
        
        public ActionAddShapedRecipe(String name, IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action, boolean mirrored) {
            this.name = cleanRecipeName(name);
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
    
    
        String calculateName() {
            StringBuilder sb = new StringBuilder();
            sb.append(saveToString(output));
        
            for(IIngredient[] ingredient : ingredients) {
                for(IIngredient iIngredient : ingredient) {
                    sb.append(saveToString(iIngredient));
                }
            }
        
            int hash = sb.toString().hashCode();
            while(usedHashes.contains(hash))
                ++hash;
            usedHashes.add(hash);
        
            return "ct_shaped" + hash;
        }
        
        @Override
        public String describe() {
            if (output != null){
                return "Adding shaped recipe for " + output.getDisplayName() + " with name " + name;
            }else {
                return "Trying to add shaped recipe without correct output";
            }
        }
    }
    
    public static class ActionAddShapelessRecipe extends ActionBaseAddRecipe {
        
        IItemStack output;
        IIngredient[] ingredients;
        IRecipeFunction function;
        IRecipeAction action;
        
        public ActionAddShapelessRecipe(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
            this.output = output;
            this.ingredients = ingredients;
            this.function = function;
            this.action = action;
            this.name = calculateName();
        }
        
        public ActionAddShapelessRecipe(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
            this.name = cleanRecipeName(name);
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
    
        public String calculateName() {
            StringBuilder sb = new StringBuilder();
            sb.append(saveToString(output));
    
            for(IIngredient ingredient : ingredients) {
                sb.append(saveToString(ingredient));
            }
            
            int hash = sb.toString().hashCode();
            while(usedHashes.contains(hash))
                ++hash;
            usedHashes.add(hash);
            
            return "ct_shapeless" + hash;
        }
        
        @Override
        public String describe() {
            if (output != null){
                return "Adding shapeless recipe for " + output.getDisplayName() + " with name " + name;
            }else {
                return "Trying to add shapeless recipe without correct output";
            }
            
        }
    }
    
    private class ContainerVirtual extends Container {
        
        @Override
        public boolean canInteractWith(EntityPlayer var1) {
            return false;
        }
    }
    
    
    public static String saveToString(IIngredient ingredient){
        if (ingredient == null) {
            return "_";
        }else {
            return ingredient.toString();
        }
    }
    
    public static String cleanRecipeName(String s){
        if (s.contains(":"))
            CraftTweakerAPI.logWarning("String may not contain a \":\"");
        return s.replace(":", "_");
    }
}
