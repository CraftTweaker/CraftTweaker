package crafttweaker.mc1120.recipes;

import crafttweaker.*;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.*;
import net.minecraftforge.registries.*;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.regex.*;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

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
        if((input.isEmpty()) == (ingredient == null)) {
            return true;
        } else if(ingredient != null) {
            return !input.isEmpty() && ingredient.matches(getIItemStack(input));
        }
        return true;
    }
    
    private static boolean matches(Object input, IIngredient ingredient) {
        if(input instanceof String) {
            return ingredient.contains(getOreDict((String) input));
        } else if(input instanceof ItemStack) {
            return matchesItem((ItemStack) input, ingredient);
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
                    if(recipe.getValue() instanceof ShapedRecipes) {
                        ShapedRecipes srecipe = (ShapedRecipes) recipe.getValue();
                        if(ingredientsWidth != srecipe.recipeWidth || ingredientsHeight != srecipe.recipeHeight) {
                            continue;
                        }
                        
                        for(int j = 0; j < ingredientsHeight; j++) {
                            IIngredient[] row = ingredients[j];
                            for(int k = 0; k < ingredientsWidth; k++) {
                                IIngredient ingredient = k > row.length ? null : row[k];
                                ItemStack input;
                                Ingredient ing = srecipe.getIngredients().get(j * srecipe.recipeWidth + k);
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
                    } else if(recipe.getValue() instanceof ShapedOreRecipe) {
                        ShapedOreRecipe srecipe = (ShapedOreRecipe) recipe.getValue();
                        int recipeWidth = srecipe.getRecipeWidth();
                        int recipeHeight = srecipe.getRecipeHeight();
                        if(ingredientsWidth != recipeWidth || ingredientsHeight != recipeHeight) {
                            continue;
                        }
                        
                        for(int j = 0; j < ingredientsHeight; j++) {
                            IIngredient[] row = ingredients[j];
                            for(int k = 0; k < ingredientsWidth; k++) {
                                IIngredient ingredient = k > row.length ? null : row[k];
                                ItemStack input;
                                Ingredient ing = srecipe.getIngredients().get(j * recipeWidth + k);
                                if(ing == Ingredient.EMPTY || ing.test(ItemStack.EMPTY) || ing.getMatchingStacks().length > 0) {
                                    input = ItemStack.EMPTY;
                                } else {
                                    input = ing.getMatchingStacks()[0];
                                }
                                
                                if(!matches(input, ingredient)) {
                                    continue outer;
                                }
                            }
                        }
                    } else {
                        if(recipe.getValue() instanceof ShapelessRecipes) {
                            continue;
                        } else if(recipe.getValue() instanceof ShapelessOreRecipe) {
                            continue;
                        }
                    }
                } else {
                    if(recipe.getValue() instanceof ShapelessRecipes) {
                        continue;
                    } else if(recipe.getValue() instanceof ShapelessOreRecipe) {
                        continue;
                    }
                }
                toRemove.add(recipe.getKey());
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
                if(entry.getValue().getRecipeOutput().isEmpty() || !output.matches(getIItemStack(entry.getValue().getRecipeOutput()))) {
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
                toRemove.add(entry.getKey());
            }
            
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
            ForgeRegistries.RECIPES.register(recipe.setRegistryName(new ResourceLocation("crafttweaker", name)));
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
