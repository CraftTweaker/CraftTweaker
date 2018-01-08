package crafttweaker.mc1120.brewing;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.recipes.IBrewingManager;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;

public class MCBrewing implements IBrewingManager{	
	private static List<Tuple<IItemStack, IItemStack>> removedRecipes = new ArrayList<>();
	
	public MCBrewing() {
	}

	@Override
	public void addBrew(IIngredient input, IIngredient ingredient, IItemStack output, boolean hidden) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, new IIngredient[] {ingredient}, output, hidden));		
	}

	@Override
	public void addBrew(IIngredient input, IIngredient[] ingredients, IItemStack output, boolean hidden) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, ingredients, output, hidden));
	}
	
	@Override
	public void removeRecipe(IItemStack input, IItemStack ingredient) {
		removedRecipes.add(new Tuple<>(input, ingredient));
	}
	
	
	private class ActionAddBrewingRecipe implements IAction{
		private final MultiBrewingRecipe recipe;
		private final String outName;
		private final boolean valid;
		
		public ActionAddBrewingRecipe(IIngredient input, IIngredient[] ingredients, IItemStack output, boolean hidden) {
			this.outName = output.toString();
			this.recipe = new MultiBrewingRecipe(input, ingredients, output, !hidden);
			this.valid = recipe.isValid();
		}



		@Override
		public void apply() {
			if(!valid) {
				CraftTweakerAPI.logError(String.format("Brewing recipe for %s is invalid", outName));
				return;
			}
			BrewingRecipeRegistry.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding brewing recipe for " + outName + ", Registry size now: " + BrewingRecipeRegistry.getRecipes().size();
		}
	}



	public static void fixBrewingRecipes() {
		if (removedRecipes.isEmpty()) {
			return;
		}
		
		List <IBrewingRecipe> brewings = CraftTweakerHacks.getPrivateStaticObject(BrewingRecipeRegistry.class, "recipes");
        brewings.removeIf(VanillaBrewingRecipe.class::isInstance);
        brewings.add(new VanillaBrewingPlus(removedRecipes));
	}
}
