package crafttweaker.mc1120.recipes;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientOr;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.CraftTweaker;
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
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static crafttweaker.api.minecraft.CraftTweakerMC.getIItemStackForMatching;
import static crafttweaker.api.minecraft.CraftTweakerMC.getIItemStack;
import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;
import static crafttweaker.api.minecraft.CraftTweakerMC.getOreDict;

/**
 * @author Stan, BloodWorkXGaming
 */
public final class MCRecipeManager implements IRecipeManager {
    
    public static final List<ActionBaseAddRecipe> recipesToAdd = new ArrayList<>();
    public static final List<ActionBaseRemoveRecipes> recipesToRemove = new ArrayList<>();
    public static final ActionRemoveRecipesNoIngredients actionRemoveRecipesNoIngredients = new ActionRemoveRecipesNoIngredients();
    public static Set<Map.Entry<ResourceLocation, IRecipe>> recipes;
    
    public static final List<MCRecipeBase> transformerRecipes = new ArrayList<>();
    public static final List<MCRecipeBase> actionRecipes = new ArrayList<>();
    private static final TIntSet usedHashes = new TIntHashSet();
    private static final HashSet<String> usedRecipeNames = new HashSet<>();
    
    public MCRecipeManager() {
    }
    
    private static boolean matchesItem(ItemStack input, IIngredient ingredient) {
        return ingredient == null ? input.isEmpty() : 
                !input.isEmpty() && ingredient.matches(getIItemStackForMatching(input));
    }
    
    private static boolean matches(Object input, IIngredient ingredient) {
        if(input instanceof String) {
            return ingredient.contains(getOreDict((String) input));
        } else if(input instanceof ItemStack) {
            return matchesItem((ItemStack) input, ingredient);
        } else if(input instanceof Ingredient) {
            final ItemStack[] matchingStacks = ((Ingredient) input).getMatchingStacks();
            return matchingStacks.length > 0 && matchesItem(matchingStacks[0], ingredient);
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

    public static void refreshRecipes() {
        recipes = ForgeRegistries.RECIPES.getEntries();
    }
    
    /**
     * Removes invalid recipes from the list so that they aren't added to JEI.
     * Also tells the user which recipes were not registered correctly.
     */
    public static void cleanUpRecipeList() {
        final Iterator<ActionBaseAddRecipe> iterator = recipesToAdd.iterator();
        while(iterator.hasNext()) {
            final ActionBaseAddRecipe next = iterator.next();
            final MCRecipeBase recipe = next.getRecipe();
            if(recipe == null) {
                //Debug statement...
                CraftTweakerAPI.logWarning("Recipe action " + next.getClass().getCanonicalName() + " had null recipe, please report this issue!");
            } else if(!ForgeRegistries.RECIPES.containsKey(recipe.getRegistryName())) {
                CraftTweakerAPI.logWarning("Recipe " + recipe.toCommandString() + " was created but not added to the Recipe Registry, check for other errors in your log!");
                iterator.remove();
            }
        }
    }
    
    
    @Override
    public List<ICraftingRecipe> getRecipesFor(IIngredient ingredient) {
        List<ICraftingRecipe> results = new ArrayList<>();
        
        if(recipes == null)
            recipes = ForgeRegistries.RECIPES.getEntries();
        
        for(Map.Entry<ResourceLocation, IRecipe> ent : recipes) {
            ItemStack stack = ent.getValue().getRecipeOutput();
            if(!stack.isEmpty()) {
                if(ingredient.matches(getIItemStackForMatching(stack))) {
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
        if(checkShapelessNulls(output, ingredients))
            return;
        recipesToAdd.add(new ActionAddShapelessRecipe(output, ingredients, function, action));
    }
    
    @Override
    public void addShapeless(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        if(checkShapelessNulls(output, ingredients))
            return;
        recipesToAdd.add(new ActionAddShapelessRecipe(name, output, ingredients, function, action, false));
    }
    
    @Override
    public void addHiddenShapeless(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        if(checkShapelessNulls(output, ingredients))
            return;
        recipesToAdd.add(new ActionAddShapelessRecipe(name, output, ingredients, function, action, true));
    }
    
    private boolean checkShapelessNulls(IItemStack output, IIngredient[] ingredients) {
        boolean valid = output != null;
        for(IIngredient ing : ingredients) {
            if (ing == null) {
                valid = false;
                break;
            }
        }
        if(!valid) {
            CraftTweakerAPI.logError("Null not allowed in shapeless recipes! Recipe for: " + output + " not created!");
            return true;
        }
        return false;
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
        if(output == null) {
            CraftTweakerAPI.logError("Cannot remove recipes for a null item!");
            return;
        }
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
        if(output == null) {
            CraftTweakerAPI.logError("Cannot remove recipes for a null item!");
            return;
        }
        recipesToRemove.add(new ActionRemoveShapedRecipes(output, ingredients));
    }
    
    @Override
    public void removeShapeless(IIngredient output, IIngredient[] ingredients, boolean wildcard) {
        if(output == null) {
            CraftTweakerAPI.logError("Cannot remove recipes for a null item!");
            return;
        }
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
        Arrays.fill(iContents, ItemStack.EMPTY);
        
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
        ActionReplaceAllOccurences.INSTANCE.addSubAction(
                new SubActionReplaceAllOccurences(toReplace, replaceWith, forOutput)
        );
    }
    
    
    /**
     * Classes of all removeRecipe Actions
     */
    public abstract static class ActionBaseRemoveRecipes implements IAction {
        
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
                
                for(final IIngredient[] ingredient : ingredients) {
                    ingredientsWidth = Math.max(ingredientsWidth, ingredient.length);
                }
            }
            
            final List<ResourceLocation> toRemove = new ArrayList<>();
            outer:
            
            for(Map.Entry<ResourceLocation, IRecipe> recipeEntry : recipes) {
                final IRecipe recipe = recipeEntry.getValue();
                final ItemStack output = recipe.getRecipeOutput();
                if(output.isEmpty() || !this.output.matches(getIItemStackForMatching(output))) {
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
                                if(!matchesItem(input, ingredient)) {
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
                if(entry.getValue().getRecipeOutput().isEmpty() || !output.matches(getIItemStackForMatching(entry.getValue().getRecipeOutput()))) {
                    continue;
                }
                if(recipe instanceof IShapedRecipe) {
                    continue;
                }
                if(!(recipe instanceof ShapelessRecipes) && !(recipe instanceof ShapelessOreRecipe)) {
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
                IItemStack stack = getIItemStackForMatching(recipeOutput);
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
                        if(filter.matches(getIItemStackForMatching(recipe.getValue().getRecipeOutput())))
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
                        if(filter.matches(getIItemStackForMatching(recipe.getValue().getRecipeOutput())))
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
            if (recipe.hasRecipeAction())
                actionRecipes.add(recipe);
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

    public static class SubActionReplaceAllOccurences implements IAction {
        private SubActionReplaceAllOccurences next;
        private final IIngredient toReplace;
        private final IIngredient replaceWith;
        private final IIngredient forOutput;

        public SubActionReplaceAllOccurences(IIngredient toReplace, IIngredient replaceWith, IIngredient forOutput) {
            this.toReplace = toReplace;
            this.replaceWith = replaceWith;
            this.forOutput = forOutput;
        }

        @Override
        public void apply() {
            if (ActionReplaceAllOccurences.INSTANCE.currentModifiedRecipe == null) {
                return;
            }
            if (forOutput == null || forOutput.contains(ActionReplaceAllOccurences.INSTANCE.currentModifiedRecipe.getOutput())) {
                applyShapedPattern();
                applyShapelessPattern();
            }
        }

        @Override
        public String describe() {
            String replaceWithName = replaceWith == null ? MCItemStack.EMPTY.toString() : replaceWith.toString();
            return "Removing all occurences of ingredient: " + toReplace + " and replacing them with " + replaceWithName;
        }

        private IIngredient changeIngredient(IIngredient input) {
            List<IItemStack> inputItems = input.getItems();
            if (inputItems.size() == 1) { // to avoid SingletonList#removeIf throwing UnsupportedOperationException
                if (toReplace.matches(inputItems.get(0))) {
                    return replaceWith;
                }
            } else if (inputItems.size() > 1) {
                if (inputItems.removeIf(toReplace::matches)) {
                    if (inputItems.isEmpty()) {
                        return replaceWith;
                    } else {
                        IIngredient temp = new IngredientOr(inputItems.toArray(new IIngredient[0]));
                        return replaceWith == null ? temp : temp.or(replaceWith);
                    }
                }
            }
            return input;
        }

        private void applyShapelessPattern() {
            if (ActionReplaceAllOccurences.INSTANCE.ingredients1D != null) {
                for (int i = 0; i < ActionReplaceAllOccurences.INSTANCE.ingredients1D.length; i++) {
                    IIngredient ingredient = ActionReplaceAllOccurences.INSTANCE.ingredients1D[i];
                    if (ingredient == null)
                        continue;
                    IIngredient changeResult = changeIngredient(ingredient);
                    if (changeResult != ingredient) {
                        if (changeResult == null) {
                            ActionReplaceAllOccurences.INSTANCE.ingredients1D = ArrayUtils.remove(ActionReplaceAllOccurences.INSTANCE.ingredients1D, i);
                        } else {
                            ActionReplaceAllOccurences.INSTANCE.ingredients1D[i] = changeResult;
                        }
                        ActionReplaceAllOccurences.INSTANCE.recipeModified = true;
                    }
                }
            }
        }

        private void applyShapedPattern() {
            if (ActionReplaceAllOccurences.INSTANCE.ingredients2D != null) {
                for (int i = 0; i < ActionReplaceAllOccurences.INSTANCE.ingredients2D.length; i++) {
                    for (int j = 0; j < ActionReplaceAllOccurences.INSTANCE.ingredients2D[i].length; j++) {
                        IIngredient ingredient = ActionReplaceAllOccurences.INSTANCE.ingredients2D[i][j];
                        if (ingredient == null)
                            continue;
                        IIngredient changeResult = changeIngredient(ingredient);
                        if (changeResult != ingredient) {
                            ActionReplaceAllOccurences.INSTANCE.ingredients2D[i][j] = changeResult;
                            ActionReplaceAllOccurences.INSTANCE.recipeModified = true;
                        }
                    }
                }
            }
        }
    }

    public enum ActionReplaceAllOccurences implements IAction {
        INSTANCE;

        private SubActionReplaceAllOccurences first;
        private SubActionReplaceAllOccurences last;
        private ICraftingRecipe currentModifiedRecipe;
        private IIngredient[] ingredients1D;
        private IIngredient[][] ingredients2D;
        private boolean recipeModified = false;
        private final List<ActionRemoveRecipeByRecipeName> toRemoveRecipe = new ArrayList<>();

        public void setCurrentModifiedRecipe(ICraftingRecipe currentModifiedRecipe) {
            if (currentModifiedRecipe == null || currentModifiedRecipe.getOutput() == null)
                return;
            this.currentModifiedRecipe = currentModifiedRecipe;
            if (currentModifiedRecipe.isShaped()) {
                this.ingredients2D = currentModifiedRecipe.getIngredients2D();
            } else {
                this.ingredients1D = currentModifiedRecipe.getIngredients1D();
            }
        }

        public void addSubAction(SubActionReplaceAllOccurences subAction) {
            if (first == null) {
                first = subAction;
            }
            if (last != null) {
                last.next = subAction;
            }
            last = subAction;
        }

        public boolean hasSubAction() {
            return first != null;
        }

        public void clear() {
            first = last = null;
            toRemoveRecipe.clear();
        }

        @Override
        public void apply() {
            if (this.currentModifiedRecipe == null)
                return;

            SubActionReplaceAllOccurences current = first;

            while (current != null) {
                current.apply();
                current = current.next;
            }

            if (this.recipeModified) {
                toRemoveRecipe.add(new ActionRemoveRecipeByRecipeName(this.currentModifiedRecipe.getFullResourceName(), null));
                String name = this.currentModifiedRecipe.getResourceDomain() + "_" + this.currentModifiedRecipe.getName() + "_modified";
                IItemStack out = this.currentModifiedRecipe.getOutput();
                if (this.currentModifiedRecipe.isShaped()) {
                    CraftTweakerAPI.recipes.addShaped(name, out, ingredients2D, null, null);
                } else {
                    CraftTweakerAPI.recipes.addShapeless(name, out, ingredients1D, null, null);
                }
            }

            this.currentModifiedRecipe = null;
            this.ingredients1D = null;
            this.ingredients2D = null;
            this.recipeModified = false;
        }

        public void describeSubActions() {
            SubActionReplaceAllOccurences current = first;

            while (current != null) {
                CraftTweakerAPI.logInfo(current.describe());
                current = current.next;
            }
        }

        public void removeOldRecipes() {
            CraftTweaker.INSTANCE.applyActions(toRemoveRecipe, "Removing old recipes for replace occurrences action", "Failed to remove old recipes for replace occurrences action");
        }

        @Override
        public String describe() {
            return null;
        }
    }
    
    private static class ContainerVirtual extends Container {
        
        @Override
        public boolean canInteractWith(EntityPlayer var1) {
            return false;
        }
    }
}
