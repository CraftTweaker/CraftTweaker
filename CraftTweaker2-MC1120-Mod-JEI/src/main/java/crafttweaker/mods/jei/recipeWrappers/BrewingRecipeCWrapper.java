package crafttweaker.mods.jei.recipeWrappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.brewing.MultiBrewingRecipe;
import crafttweaker.mc1120.brewing.VanillaBrewingPlus;
import crafttweaker.mods.jei.JEIAddonPlugin;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.VanillaPlugin;
import mezz.jei.plugins.vanilla.brewing.BrewingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class BrewingRecipeCWrapper extends BrewingRecipeWrapper  {
	
	public final ItemStack output;
	public final List<List<ItemStack>> ingredientList;
	public final List<ItemStack> inputs;
	
	public BrewingRecipeCWrapper(MultiBrewingRecipe recipe) {
		super(recipe.getIngredients(), recipe.getInputs().get(0), recipe.getOutput());
		
		this.inputs = recipe.getInputs();
		this.ingredientList = Arrays.asList(recipe.getInputs(), recipe.getInputs(), recipe.getInputs(), recipe.getIngredients());
		this.output = recipe.getOutput();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, ingredientList);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	@Override
	public List<?> getInputs() {
		// returns our inputs
		return inputs;
	}
	
	
	
	//Static Methods//	
    public static List<IRecipeWrapper> createBrewingRecipes() {
    	return BrewingRecipeRegistry.getRecipes()
    					.stream()
    					.filter(MultiBrewingRecipe.class::isInstance)
    					.map(MultiBrewingRecipe.class::cast)
    					.filter(MultiBrewingRecipe::isVisible)
    					.map(BrewingRecipeCWrapper::new)
    					.collect(Collectors.toList());
    }
    
    public static void registerBrewingRecipe() {
    	List<IRecipeWrapper> recipes = createBrewingRecipes();
    	if (recipes.isEmpty()) return;    	
    	JEIAddonPlugin.modRegistry.addRecipes(recipes, VanillaRecipeCategoryUid.BREWING);
    }	
}