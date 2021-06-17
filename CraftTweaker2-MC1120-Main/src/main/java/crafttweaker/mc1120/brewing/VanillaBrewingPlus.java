package crafttweaker.mc1120.brewing;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;

import java.util.List;

public final class VanillaBrewingPlus extends VanillaBrewingRecipe {
	
	private final List<Tuple<IIngredient, IIngredient>> removedRecipes;

	public VanillaBrewingPlus(List<Tuple<IIngredient, IIngredient>> removedRecipes) {
		this.removedRecipes = removedRecipes;
	}
	
	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		IItemStack _input = CraftTweakerMC.getIItemStack(input);
		IItemStack _ingredient = CraftTweakerMC.getIItemStack(ingredient);
		
		if (removedRecipes.stream().anyMatch(t -> t.getFirst().matches(_input) && t.getSecond().matches(_ingredient))) {
			return ItemStack.EMPTY;
		}
		
		return super.getOutput(input, ingredient);
	}
	
	public ItemStack getRealOutput(ItemStack input, ItemStack ingredient) {
		return super.getOutput(input, ingredient);
	}
	
	public List<Tuple<IIngredient, IIngredient>> getRemovedRecipes() {
		return removedRecipes;
	}
}
