package crafttweaker.mc1120.recipes;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.item.MCItemStack;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static crafttweaker.api.minecraft.CraftTweakerMC.getIItemStack;
import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;
import static crafttweaker.api.minecraft.CraftTweakerMC.getOreDict;

/**
 * @author Stan, BloodWorkXGaming
 */
public final class MCRecipeManager implements IRecipeManager {
    
    public final static List<ActionBaseAddRecipe> recipesToAdd = new ArrayList<>();
    public final static List<ActionBaseRemoveRecipes> recipesToRemove = new ArrayList<>();
    public final static ActionRemoveRecipesNoIngredients actionRemoveRecipesNoIngredients = new ActionRemoveRecipesNoIngredients();
    public static Set<Map.Entry<ResourceLocation, IRecipe>> recipes;
    
    public static List<MCRecipeBase> transformerRecipes = new ArrayList<>();
    private static TIntSet usedHashes = new TIntHashSet();
    private static HashSet<String> usedRecipeNames = new HashSet<>();
    
    public MCRecipeManager() {
    }
    
    private static boolean matchesItem(ItemStack input, IIngredient ingredient) {
        return ingredient == null ? input.isEmpty() : !input.isEmpty() && ingredient.matches(getIItemStack(input));
    }
    
    private static boolean matches(Object input, IIngredient ingredient) {
        if(input instanceof String) {
            return ingredient.contains(getOreDict((String) input));
        } else if(input instanceof ItemStack) {
            return matchesItem((ItemStack) input, ingredient);
        } else if(input instanceof Ingredient) {
            return matchesItem(((Ingredient) input).getMatchingStacks()[0], ingredient);
        }
        
        return false;
    }
    
    @Deprecated
    public static String saveToString(Object ingredient) {
        if(ingredient == null) {
            return "_";
        } else {
            return ingredient.toString();
        }
    }
    
    @Deprecated
    public static String saveToString(IIngredient ingredient) {
        return saveToString((Object) ingredient);
    }
    
    public static String cleanRecipeName(String s) {
        if(s.contains(":"))
            CraftTweakerAPI.logWarning("Recipe name [" + s + "] may not contain a ':', replacing with '_'!");
        return s.replace(":", "_");
    }
    
    @Override
    public List<ICraftingRecipe> getRecipesFor(IIngredient ingredient) {
        List<ICraftingRecipe> results = new ArrayList<>();
        
        if(recipes == null)
            recipes = ForgeRegistries.RECIPES.getEntries();
        
        for(Map.Entry<ResourceLocation, IRecipe> ent : recipes) {
            ItemStack stack = ent.getValue().getRecipeOutput();
            if(!stack.isEmpty()) {
                if(ingredient.matches(CraftTweakerMC.getIItemStack(stack))) {
                    if(ent.getValue() instanceof MCRecipeBase) {
                        results.add((MCRecipeBase) ent.getValue());
                    } else
                        results.add(new MCRecipeWrapper(ent.getValue()));
                }
            }
        }
        
        return results;
    }
    
    @Override
    public List<ICraftingRecipe> getAll() {
        List<ICraftingRecipe> results = new ArrayList<>();
        if(recipes == null)
            recipes = ForgeRegistries.RECIPES.getEntries();
        for(Map.Entry<ResourceLocation, IRecipe> recipeEntry : recipes) {
            IRecipe recipe = recipeEntry.getValue();
            if(recipe instanceof MCRecipeBase)
                results.add((MCRecipeBase) recipe);
            else
                results.add(new MCRecipeWrapper(recipe));
        }
        
        return results;
    }
    
    @Override
    public void addShaped(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(output, ingredients, function, action, false, false));
    }
    
    @Override
    public void addShaped(String name, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(name, output, ingredients, function, action, false, false));
    }

    @Override
    public void addShaped(String group, String name, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ActionAddShapedRecipe recipe = new ActionAddShapedRecipe(name, output, ingredients, function, action, false, false);
        recipe.setGroup(group);
        recipesToAdd.add(recipe);
    }
    
    @Override
    public void addShapedMirrored(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(output, ingredients, function, action, true, false));
    }
    
    @Override
    public void addShapedMirrored(String name, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        recipesToAdd.add(new ActionAddShapedRecipe(name, output, ingredients, function, action, true, false));
    }
    
    @Override
    public void addShapeless(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        boolean valid = output != null;
        for(IIngredient ing : ingredients) {
            if(ing == null) {
                valid = false;
            }
        }
        if(!valid) {
            CraftTweakerAPI.logError("Null not allowed in shapeless recipes! Recipe for: " + output + " not created!");
            return;
        }
        recipesToAdd.add(new ActionAddShapelessRecipe(output, ingredients, function, action));
    }
    
    @Override
    public void addShapeless(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        boolean valid = output != null;
        for(IIngredient ing : ingredients) {
            if(ing == null) {
                valid = false;
            }
        }
        if(!valid) {
            CraftTweakerAPI.logError("Null not allowed in shapeless recipes! Recipe for: " + output + " not created!");
            return;
        }
        recipesToAdd.add(new ActionAddShapelessRecipe(name, output, ingredients, function, action, false));
    }

    @Override
    public void addShapeless(String group, String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        boolean valid = output != null;
        for(IIngredient ing : ingredients) {
            if(ing == null) {
                valid = false;
            }
        }
        if(!valid) {
            CraftTweakerAPI.logError("Null not allowed in shapeless recipes! Recipe for: " + output + " not created!");
            return;
        }
        ActionAddShapelessRecipe recipe = new ActionAddShapelessRecipe(name, output, ingredients, function, action, false);
        recipe.setGroup(group);
        recipesToAdd.add(recipe);
    }
    
    @Override
    public void addHiddenShapeless(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        boolean valid = output != null;
        for(IIngredient ing : ingredients) {
            if(ing == null) {
                valid = false;
            }
        }
        if(!valid) {
            CraftTweakerAPI.logError("Null not allowed in shapeless recipes! Recipe for: " + output + " not created!");
            return;
        }
        recipesToAdd.add(new ActionAddShapelessRecipe(name, output, ingredients, function, action, true));
    }
    
    @Override
    public void addHiddenShaped(String name, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action, @Optional boolean mirrored) {
        recipesToAdd.add(new ActionAddShapedRecipe(name, output, ingredients, function, action, mirrored, true));
    }
    
    @Override
    public void removeAll() {
        recipesToRemove.add(new ActionRemoveAllRecipes());
    }
    
    @Override
    public void remove(IIngredient output, @Optional boolean nbtMatch) {
        actionRemoveRecipesNoIngredients.addOutput(output, nbtMatch);
    }
    
    @Override
    public void removeByRecipeName(String recipeName, @Optional IItemStack outputFilter) {
        recipesToRemove.add(new ActionRemoveRecipeByRecipeName(recipeName, outputFilter));
    }
    
    @Override
    public void removeByRegex(String regexString, @Optional IItemStack outputFilter) {
        recipesToRemove.add(new ActionRemoveRecipeByRegex(regexString, outputFilter));
    }
    
    @Override
    public void removeByMod(String modid) {
        recipesToRemove.add(new ActionRemoveRecipeByMod(modid));
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
    
    @Override
    public void replaceAllOccurences(IIngredient toReplace, IIngredient replaceWith, IIngredient forOutput) {
        recipesToRemove.add(new ActionReplaceAllOccurences(toReplace, replaceWith, forOutput));
    }
    
    
    /**
     * Classes of all removeRecipe Actions
     */
    public static abstract class ActionBaseRemoveRecipes implements IAction {
        
        public void removeRecipes(List<ResourceLocation> removingRecipes) {
            removingRecipes.forEach(recipe -> RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(recipe));
        }
    }
    
    public static class ActionReplaceAllOccurences extends ActionBaseRemoveRecipes {
    
        //I'm odd, in that I'm an ActionBaseRemoveRecipes, that also creates recipes.
        private final IIngredient toReplace;
        private final IIngredient replaceWith;
        private final IIngredient forOutput;
        private List<MCRecipeBase> toChange;
        private List<ResourceLocation> toRemove;
        
        public ActionReplaceAllOccurences(IIngredient toReplace, IIngredient replaceWith, IIngredient forOutput) {
            this.toReplace = toReplace;
            this.replaceWith = replaceWith;
            this.forOutput = forOutput == null ? IngredientAny.INSTANCE : forOutput;
        }
        
        @Override
        public void apply() {
            toChange = getAllForIngredient(toReplace);
            toRemove = toChange.stream().map(f -> new ResourceLocation(f.getFullResourceName())).collect(Collectors.toList());
            removeRecipes(toRemove);
            changeIngredients(toChange);
        }
        
        @Override
        public void removeRecipes(List<ResourceLocation> removingRecipes) {
            List<ActionBaseAddRecipe> toUnAdd = new ArrayList<>();
            removingRecipes.forEach(recipe -> {
                RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(recipe);
                recipesToAdd.stream().filter(f -> f instanceof ActionDummyAddRecipe).filter(f -> f.recipe.getRegistryName().equals(recipe)).forEach(toUnAdd::add);
            });
            toUnAdd.forEach(f -> {
                recipesToAdd.remove(f);
                usedRecipeNames.remove(f.getName());
            });
        }
        
        @Override
        public String describe() {
            return "Removing all occurences of ingredient: " + toReplace + " and replacing them with " + replaceWith;
        }
        
        private void changeIngredients(List<MCRecipeBase> toChange) {
            for(MCRecipeBase recipe : toChange) {
                if(recipe.isShaped()) {
                    IIngredient[][] ingredients = recipe.getIngredients2D();
                    for(IIngredient[] targRow : ingredients) {
                        for(int i = 0; i < targRow.length; i++) {
                            if(targRow[i] != null && targRow[i].contains(toReplace))
                                targRow[i] = replaceWith;
                        }
                    }
                    
                    MCRecipeShaped newRecipe = new MCRecipeShaped(ingredients, recipe.getOutput(), recipe.recipeFunction, recipe.getRecipeAction(), false, recipe.hidden);
                    registerNewRecipe(newRecipe, getNewRecipeName(recipe));
                    
                } else {
                    if(replaceWith == null)
                        continue; //No null's in shapeless recipies... We can't do anything, so we just won't add the recipe.
                    IIngredient[] ingredients = recipe.getIngredients1D();
                    for(int i = 0; i < ingredients.length; i++) {
                        final IIngredient ingredient = ingredients[i];
                        if(ingredient != null && ingredient.contains(toReplace)) {
                            ingredients[i] = replaceWith;
                        }
                    }
                    MCRecipeShapeless newRecipe = new MCRecipeShapeless(ingredients, recipe.output, recipe.recipeFunction, recipe.recipeAction, recipe.hidden);
                    registerNewRecipe(newRecipe, getNewRecipeName(recipe));
                }
            }
            
        }
        
        private void registerNewRecipe(MCRecipeBase newRecipe, String name) {
            ActionDummyAddRecipe dummyRecipe = new ActionDummyAddRecipe(newRecipe, newRecipe.output, true);
            dummyRecipe.setName(name);
            MCRecipeManager.recipesToAdd.add(dummyRecipe);
            ForgeRegistries.RECIPES.register(newRecipe);
        }
        
        private String getNewRecipeName(MCRecipeBase recipe) {
            if(recipe.getName().contains("modified")) {
                //This should keep adding re in front of a modified recipe every time it's modified.
                //If you wind up with rererereremodified recipes, you're doing something wrong.  Stop it.
                return recipe.getName().replace("modified", "remodified");
            }
            return recipe.getRegistryName().getResourceDomain() + "-" + recipe.getName() + "-modified";
        }
        
        private List<MCRecipeBase> getAllForIngredient(IIngredient target) {
            Set<Map.Entry<ResourceLocation, IRecipe>> recipes;
            List<MCRecipeBase> results = new ArrayList<>();
            
            recipes = ForgeRegistries.RECIPES.getEntries();
            
            for(Map.Entry<ResourceLocation, IRecipe> recipeEntry : recipes) {
                IRecipe recipe = recipeEntry.getValue();
    
                //Check the recipe output if provided.
                {
                    final IItemStack output = CraftTweakerMC.getIItemStack(recipe.getRecipeOutput());
                    if(forOutput != IngredientAny.INSTANCE && output != null && !forOutput.matches(output))
                        continue;
                }
    
                for(Ingredient ingredient : recipe.getIngredients()) {
                    final IIngredient iIngredient = CraftTweakerMC.getIIngredient(ingredient);
                    if(iIngredient == null)
                        continue;
                    if(target.contains(iIngredient)) {
                        if(recipe instanceof MCRecipeBase) {
                            results.add((MCRecipeBase) recipe);
                            break; //One ingredient is enough to get it added, bail out of ingredient loop.
                        } else {
                            results.add(new MCRecipeWrapper(recipe));
                            break; //See previous comment.
                        }
                    }
                }
            }
            return results;
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
            int ingredientsWidth = 0;
            int ingredientsHeight = 0;
            
            if(ingredients != null) {
                ingredientsHeight = ingredients.length;
                
                for(final IIngredient[] ingredient : ingredients) {
                    ingredientsWidth = Math.max(ingredientsWidth, ingredient.length);
                }
            }
            
            final List<ResourceLocation> toRemove = new ArrayList<>();
            outer:
            
            for(Map.Entry<ResourceLocation, IRecipe> recipeEntry : recipes) {
                final IRecipe recipe = recipeEntry.getValue();
                final ItemStack output = recipe.getRecipeOutput();
                if(output.isEmpty() || !this.output.matches(MCItemStack.createNonCopy(output))) {
                    continue;
                }
                
                if(recipe instanceof IShapedRecipe) {//We only want to remove ShapedRecipes, so do nothing if not one.
                    if(ingredients != null) {
                        //NonNull ingredientList given, compare against the provided IIngredients
                        final IShapedRecipe shapedRecipe = (IShapedRecipe) recipe;
                        final int width = shapedRecipe.getRecipeWidth();
                        final int height = shapedRecipe.getRecipeHeight();
                        
                        if(ingredientsWidth != width || ingredientsHeight != height) {
                            continue;
                        }
                        
                        for(int j = 0; j < ingredientsHeight; j++) {
                            final IIngredient[] row = ingredients[j];
                            for(int k = 0; k < ingredientsWidth; k++) {
                                final IIngredient ingredient = k > row.length ? null : row[k];
                                final ItemStack input;
                                final Ingredient ing = shapedRecipe.getIngredients().get(j * width + k);
                                if(ing == Ingredient.EMPTY || ing.test(ItemStack.EMPTY) || ing.getMatchingStacks().length == 0) {
                                    input = ItemStack.EMPTY;
                                } else {
                                    input = ing.getMatchingStacks()[0];
                                }
                                if(!matches(input, ingredient)) {
                                    continue outer;
                                }
                            }
                        }
                        toRemove.add(recipeEntry.getKey());
                    } else {
                        //null ingredient list given, remove all ShapedRecipes with the given output
                        toRemove.add(recipeEntry.getKey());
                    }
                }
            }
            CraftTweakerAPI.logInfo(toRemove.size() + " removed");
            super.removeRecipes(toRemove);
        }
        
        @Override
        public String describe() {
            if(output != null) {
                return "Removing Shaped recipes for " + output.toString();
            } else {
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
            if(output == null) {
                return;
            }
            List<ResourceLocation> toRemove = new ArrayList<>();
            outer:
            for(Map.Entry<ResourceLocation, IRecipe> entry : recipes) {
                IRecipe recipe = entry.getValue();
                if(entry.getValue().getRecipeOutput().isEmpty() || !output.matches(MCItemStack.createNonCopy(entry.getValue().getRecipeOutput()))) {
                    continue;
                }
                if(recipe instanceof IShapedRecipe) {
                    continue;
                }
                if(!(recipe instanceof ShapelessRecipes) && !(recipe instanceof ShapelessOreRecipe)){
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
                    
                }
                toRemove.add(entry.getKey());
            }
            
            CraftTweakerAPI.logInfo("Removing " + toRemove.size() + " Shapeless recipes.");
            super.removeRecipes(toRemove);
        }
        
        @Override
        public String describe() {
            if(output != null) {
                return "Removing Shapeless recipes for " + output.toString();
            } else {
                return "Trying to remove recipes for invalid output";
            }
        }
    }
    
    public static class ActionRemoveRecipesNoIngredients extends ActionBaseRemoveRecipes {
        
        // pair of output, nbtMatch
        private final List<Pair<IIngredient, Boolean>> outputs = new ArrayList<>();
        
        public void addOutput(IIngredient output, @Optional boolean nbtMatch) {
            outputs.add(Pair.of(output, nbtMatch));
        }
        
        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();
            
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                ItemStack recipeOutput = recipe.getValue().getRecipeOutput();
                IItemStack stack = getIItemStack(recipeOutput);
                if(stack != null && matches(stack)) {
                    toRemove.add(recipe.getKey());
                }
            }
            
            super.removeRecipes(toRemove);
        }
        
        private boolean matches(IItemStack stack) {
            for(Pair<IIngredient, Boolean> entry : outputs) {
                IIngredient output = entry.getKey();
                Boolean nbtMatch = entry.getValue();
                if(nbtMatch ? output.matchesExact(stack) : output.matches(stack)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public String describe() {
            return "Removing recipes for various outputs";
        }
    }
    
    public static class ActionRemoveRecipeByRecipeName extends ActionBaseRemoveRecipes {
        
        String recipeName;
        IItemStack filter;
        
        
        public ActionRemoveRecipeByRecipeName(String recipeName, IItemStack filter) {
            this.recipeName = recipeName;
            this.filter = filter;
        }
        
        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();
            
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                if(recipe.getKey().toString().equals(recipeName)) {
                    if(filter != null) {
                        if(filter.matches(getIItemStack(recipe.getValue().getRecipeOutput())))
                            toRemove.add(recipe.getKey());
                    } else {
                        toRemove.add(recipe.getKey());
                    }
                }
            }
            
            super.removeRecipes(toRemove);
        }
        
        @Override
        public String describe() {
            if(recipeName != null) {
                if(filter != null) {
                    return "Removing recipe with name \"" + recipeName + "\", Matching filter: " + filter.getDisplayName();
                }
                return "Removing recipe with name \"" + recipeName + "\"";
            } else {
                return "No name for the recipe to remove was given.";
            }
        }
    }
    
    
    public static class ActionRemoveRecipeByMod extends ActionBaseRemoveRecipes {
        
        String modid;
        
        public ActionRemoveRecipeByMod(String modid) {
            this.modid = modid;
        }
        
        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                if(recipe.getKey().getResourceDomain().equalsIgnoreCase(modid)) {
                    toRemove.add(recipe.getKey());
                }
            }
            
            super.removeRecipes(toRemove);
        }
        
        @Override
        public String describe() {
            return "Removing recipes matching: " + modid;
        }
    }
    
    public static class ActionRemoveRecipeByRegex extends ActionBaseRemoveRecipes {
        
        String regexCheck;
        IItemStack filter;
        
        public ActionRemoveRecipeByRegex(String regexCheck, IItemStack filter) {
            this.regexCheck = regexCheck;
            this.filter = filter;
        }
        
        @Override
        public void apply() {
            List<ResourceLocation> toRemove = new ArrayList<>();
            Pattern p = Pattern.compile(regexCheck);
            for(Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
                ResourceLocation resourceLocation = recipe.getKey();
                Matcher m = p.matcher(resourceLocation.toString());
                if(m.matches()) {
                    if(filter != null) {
                        if(filter.matches(getIItemStack(recipe.getValue().getRecipeOutput())))
                            toRemove.add(recipe.getKey());
                    } else {
                        toRemove.add(resourceLocation);
                    }
                }
            }
            
            super.removeRecipes(toRemove);
        }
        
        @Override
        public String describe() {
            if(regexCheck != null) {
                if(filter != null) {
                    return "Removing all recipes matching this regex: \"" + regexCheck + "\", Matching filter: " + filter.getDisplayName();
                }
                return "Removing all recipes matching this regex: \"" + regexCheck + "\"";
            } else {
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
    public static class ActionBaseAddRecipe implements IAction {
        
        // this is != null only _after_ it has been applied and is actually registered
        protected MCRecipeBase recipe;
        protected IItemStack output;
        protected boolean isShaped;
        protected String name;
        protected String group = "crafttweaker";
        
        @Deprecated
        public ActionBaseAddRecipe() {
        }
        
        private ActionBaseAddRecipe(MCRecipeBase recipe, IItemStack output, boolean isShaped) {
            this.recipe = recipe;
            this.output = output;
            this.isShaped = isShaped;
            if(recipe.hasTransformers())
                transformerRecipes.add(recipe);
        }
        
        public IItemStack getOutput() {
            return output;
        }
        
        public void setOutput(IItemStack output) {
            this.output = output;
        }
        
        public String getName() {
            return name;
        }

        public void setGroup(String group) {
            if (group != null) {
                this.group = group;
            }
        }
        
        protected void setName(String name) {
            if(name != null) {
                String proposedName = cleanRecipeName(name);
                if(usedRecipeNames.contains(proposedName)) {
                    this.name = calculateName();
                    CraftTweakerAPI.logWarning("Recipe name [" + name + "] has duplicate uses, defaulting to calculated hash!");
                } else {
                    this.name = proposedName;
                }
            } else {
                this.name = calculateName();
            }
            usedRecipeNames.add(this.name); //TODO: FINISH THIS and replace stuff in constructor call.
        }
        
        public String calculateName() {
            int hash = recipe.toCommandString().hashCode();
            while(usedHashes.contains(hash))
                ++hash;
            usedHashes.add(hash);
            
            return (isShaped ? "ct_shaped" : "ct_shapeless") + hash;
        }
        
        @Override
        public void apply() {
            ForgeRegistries.RECIPES.register(recipe.setRegistryName(new ResourceLocation(group, name)));
        }
        
        @Override
        public String describe() {
            if(output != null) {
                return "Adding " + (isShaped ? "shaped" : "shapeless") + " recipe for " + output.getDisplayName() + " with name " + name;
            } else {
                return "Trying to add " + (isShaped ? "shaped" : "shapeless") + "recipe without correct output";
            }
        }
        
        public MCRecipeBase getRecipe() {
            return recipe;
        }
    }
    
    public static class ActionDummyAddRecipe extends ActionBaseAddRecipe {
        
        public ActionDummyAddRecipe(MCRecipeBase recipe, IItemStack output, boolean isShaped) {
            super(recipe, output, isShaped);
        }
        
        //This whole class is a dirty hack.
        //It exists only to hold information for the CraftTweaker JEI plugin.
        @Override
        public void apply() {
            //Our work was done elsehwere, apply is a noop.
        }
        
        @Override
        protected void setName(String name) {
            super.setName(name);
            //This is necessary to allow recipe name progression if we repeatedly modify a recipe.
            recipe.setRegistryName(new ResourceLocation(group, this.name));
        }
    }
    
    private static class ActionAddShapedRecipe extends ActionBaseAddRecipe {
        
        public ActionAddShapedRecipe(IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action, boolean mirrored, boolean hidden) {
            this(null, output, ingredients, function, action, mirrored, hidden);
        }
        
        public ActionAddShapedRecipe(String name, IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, IRecipeAction action, boolean mirrored, boolean hidden) {
            super(new MCRecipeShaped(ingredients, output, function, action, mirrored, hidden), output, true);
            setName(name);
        }
    }
    
    private static class ActionAddShapelessRecipe extends ActionBaseAddRecipe {
        
        public ActionAddShapelessRecipe(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
            this(null, output, ingredients, function, action, false);
        }
        
        public ActionAddShapelessRecipe(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action, boolean hidden) {
            super(new MCRecipeShapeless(ingredients, output, function, action, hidden), output, false);
            setName(name);
        }
    }
    
    private static class ContainerVirtual extends Container {
        
        @Override
        public boolean canInteractWith(EntityPlayer var1) {
            return false;
        }
    }
}
