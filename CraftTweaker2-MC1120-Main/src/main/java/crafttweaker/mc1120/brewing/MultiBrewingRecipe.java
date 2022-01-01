package crafttweaker.mc1120.brewing;

import java.util.List;
import java.util.stream.Collectors;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class MultiBrewingRecipe implements IBrewingRecipe {
	
	private final IIngredient input, ingredient;
	private final ItemStack output;
	private final boolean visible;

	public MultiBrewingRecipe(IIngredient input, IIngredient[] ingredients, IItemStack output, boolean visible) {
		this.input = input;
		this.output = CraftTweakerMC.getItemStack(output);
		this.visible = visible;
		this.ingredient = readIngredientArray(ingredients);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		return (isInput(input) && isIngredient(ingredient)) ? getOutput() : ItemStack.EMPTY;
	}

	@Override
	public boolean isIngredient(ItemStack tester) {
		return ingredient.matches(CraftTweakerMC.getIItemStackForMatching(tester)); 
	}

	@Override
	public boolean isInput(ItemStack tester) {
		return input.matches(CraftTweakerMC.getIItemStackForMatching(tester));
	}
	
	public ItemStack getOutput() {
		return output.copy();
	}
	
	public List<ItemStack> getInputs() {
		return input.getItems().stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList());
	}
	
	public List<ItemStack> getIngredients() {
		return ingredient.getItems().stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList());
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isValid() {
		return !input.getItems().isEmpty() && !ingredient.getItems().isEmpty() && output != ItemStack.EMPTY;
	}

	/**
	 * Condenses all ingredients into one using the or method
	 * @return ingredients as single ingredient object
	 */
	public IIngredient readIngredientArray(IIngredient[] ingredients) {
		//Shouldn't happen
		if(ingredients.length == 0) {
			throw new IllegalArgumentException("Brewing ingredient list may not be empty");
		//Why should we or if there's only one ingredient?
		} else if (ingredients.length == 1) {
			return ingredients[0];
		//or's all IIngredients to get one resulting ingredient
		} else {
			IIngredient ing = ingredients[0];
			for (int i = 1; i < ingredients.length; i++) {
				ing = ing.or(ingredients[i]);
			}
			return ing;
		}
	}
}
