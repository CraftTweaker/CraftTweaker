package minetweaker.mods.ic2.machines;

import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getIItemStacks;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStacks;
import net.minecraft.nbt.NBTTagCompound;
import minetweaker.mods.ic2.IC2RecipeInput;
import minetweaker.mods.ic2.MachineAddRecipeAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the IC2 compressor recipes. Recipes can be added but not
 * removed, due to IC2 API restrictions.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.ic2.ThermalCentrifuge")
@ModOnly("IC2")
public class ThermalCentrifuge {
	/**
	 * Adds a new recipe to the centrifuge.
	 * 
	 * The recipe input can be any ingredient, as long as the stack size is determined.
	 * The output is an array of item stacks.
	 * 
	 * @param output recipe outputs
	 * @param ingredient recipe input
	 * @param minHeat minimum centrifuge heat level
	 */
	@ZenMethod
	public static void addRecipe(IItemStack[] output, IIngredient ingredient, int minHeat) {
		if (ingredient.getAmount() < 0) {
			MineTweakerAPI.logWarning("invalid ingredient: " + ingredient + " - stack size not known");
		} else {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("minHeat", minHeat);
			MineTweakerAPI.apply(new MachineAddRecipeAction(
					"thermal centrifuge",
					Recipes.centrifuge,
					getItemStacks(output),
					tag,
					new IC2RecipeInput(ingredient)));
		}
	}
	
	/**
	 * Adds a new recipe to the centrifuge.
	 * 
	 * The recipe input can be any ingredient, as long as the stack size is determined.
	 * The output can be any item stack.
	 * 
	 * @param output recipe output
	 * @param ingredient recipe input
	 * @param minHeat minimum centrifuge heat level
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient ingredient, int minHeat) {
		addRecipe(new IItemStack[] { output }, ingredient, minHeat);
	}
	
	/**
	 * Determines the recipe output for the given input. Will return the output
	 * of a single item, even if the stack contains multiple items.
	 * 
	 * @param input recipe input
	 * @return recipe output
	 */
	@ZenMethod
	public static IItemStack[] getOutput(IItemStack input) {
		RecipeOutput output = Recipes.centrifuge.getOutputFor(getItemStack(input), false);
		if (output == null || output.items.isEmpty()) return null;
		return getIItemStacks(output.items);
	}
}
