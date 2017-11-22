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

	public MultiBrewingRecipe(IIngredient input, IIngredient ingredient, IItemStack output, boolean visible) {
		this.input = input;
		this.ingredient = ingredient;
		this.output = CraftTweakerMC.getItemStack(output);
		this.visible = visible;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		return (isInput(input) && isIngredient(ingredient)) ? getOutput() : ItemStack.EMPTY;
	}

	@Override
	public boolean isIngredient(ItemStack tester) {
		return ingredient.matches(CraftTweakerMC.getIItemStack(tester)); 
	}

	@Override
	public boolean isInput(ItemStack tester) {
		return input.matches(CraftTweakerMC.getIItemStack(tester));
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
	
	public void print() {
		System.out.println("Brewing recipe for " + output.getDisplayName());
		System.out.println("\t\tAccepts input: " + input.toString());
		System.out.println("\t\tAccepts ingredient: " + ingredient.toString());
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isValid() {
		return !input.getItems().isEmpty() && !ingredient.getItems().isEmpty() && output != ItemStack.EMPTY;
	}

}
