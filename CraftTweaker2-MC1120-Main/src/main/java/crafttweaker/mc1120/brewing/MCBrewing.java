package crafttweaker.mc1120.brewing;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.recipes.IBrewingManager;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.ArrayList;
import java.util.List;

public class MCBrewing implements IBrewingManager{
	private static final List<Tuple<IIngredient, IIngredient>> toRemoveVanillaRecipes = new ArrayList<>();
	private final List<IBrewingRecipe> allBrewingRecipes = CraftTweakerHacks.getPrivateStaticObject(BrewingRecipeRegistry.class, "recipes");
	
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
		if (toRemoveVanillaRecipes.isEmpty()) { // toRemoveVanillaRecipes list is empty means the first time to call the method
			CraftTweaker.LATE_ACTIONS.add(new ActionFixVanillaBrewingRecipes(toRemoveVanillaRecipes, allBrewingRecipes));
		}
		toRemoveVanillaRecipes.add(new Tuple<>(input, ingredient));
		CraftTweaker.LATE_ACTIONS.add(new ActionRemoveBrewingRecipe(input, ingredient, allBrewingRecipes));
	}

	@Override
	public void removeRecipe(IItemStack ingredient) {
		if (toRemoveVanillaRecipes.isEmpty()) {
			CraftTweaker.LATE_ACTIONS.add(new ActionFixVanillaBrewingRecipes(toRemoveVanillaRecipes, allBrewingRecipes));
		}
		toRemoveVanillaRecipes.add(new Tuple<>(IngredientAny.INSTANCE, ingredient));
		CraftTweaker.LATE_ACTIONS.add(new ActionRemoveBrewingRecipeForIngredient(ingredient, allBrewingRecipes));
	}
}
