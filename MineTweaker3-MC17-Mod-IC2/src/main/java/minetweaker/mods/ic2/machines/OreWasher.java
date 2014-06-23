package minetweaker.mods.ic2.machines;

import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.mc172.util.MineTweakerUtil;
import minetweaker.mods.ic2.IC2RecipeInput;
import minetweaker.mods.ic2.MachineAddRecipeAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan Hebben
 */
@ZenClass("mods.ic2.OreWasher")
public class OreWasher {
	@ZenMethod
	public static void addRecipe(IItemStack[] output, IIngredient input, int millibuckets) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("amount", millibuckets);
		MineTweakerAPI.tweaker.apply(new MachineAddRecipeAction(
				"ore washer",
				Recipes.oreWashing,
				MineTweakerUtil.getItemStacks(output),
				nbt,
				new IC2RecipeInput(input)));
	}
	
	@ZenMethod
	public static IItemStack[] getOutput(IItemStack input) {
		RecipeOutput output = Recipes.oreWashing.getOutputFor((ItemStack) input.getInternal(), false);
		if (output == null || output.items.isEmpty()) return null;
		return MineTweakerUtil.getItemStacks(output.items);
	}
}
