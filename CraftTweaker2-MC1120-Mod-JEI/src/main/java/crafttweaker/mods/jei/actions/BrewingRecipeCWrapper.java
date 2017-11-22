package crafttweaker.mods.jei.actions;

import java.util.Arrays;
import java.util.List;
import crafttweaker.mc1120.brewing.MultiBrewingRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.plugins.vanilla.brewing.BrewingRecipeWrapper;
import net.minecraft.item.ItemStack;

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
	public List getInputs() {
		// returns our inputs
		return inputs;
	}
	
}