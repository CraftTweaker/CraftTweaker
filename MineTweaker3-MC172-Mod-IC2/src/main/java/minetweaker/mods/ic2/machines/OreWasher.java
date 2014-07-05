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
import minetweaker.mods.ic2.IC2RecipeInput;
import minetweaker.mods.ic2.MachineAddRecipeAction;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan Hebben
 */
@ZenClass("mods.ic2.OreWasher")
@ModOnly("IC2")
public class OreWasher {
	@ZenMethod
	public static void addRecipe(IItemStack[] output, IIngredient input, int millibuckets) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("amount", millibuckets);
		MineTweakerAPI.apply(new MachineAddRecipeAction(
				"ore washer",
				Recipes.oreWashing,
				getItemStacks(output),
				nbt,
				new IC2RecipeInput(input)));
	}
	
	@ZenMethod
	public static IItemStack[] getOutput(IItemStack input) {
		RecipeOutput output = Recipes.oreWashing.getOutputFor(getItemStack(input), false);
		if (output == null || output.items.isEmpty()) return null;
		return getIItemStacks(output.items);
	}
}
