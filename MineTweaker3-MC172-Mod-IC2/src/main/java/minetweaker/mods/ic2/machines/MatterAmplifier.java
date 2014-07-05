/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.ic2.machines;

import ic2.api.recipe.Recipes;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.mods.ic2.IC2RecipeInput;
import minetweaker.mods.ic2.MachineAddRecipeAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.ic2.MatterAmplifier")
@ModOnly("IC2")
public class MatterAmplifier {
	private MatterAmplifier() {}
	
	@ZenMethod
	public static void setAmplifier(IIngredient item, int amplifier) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("amplification", amplifier);
		MineTweakerAPI.apply(new MachineAddRecipeAction("matter amplifier", Recipes.matterAmplifier, new ItemStack[0], data, new IC2RecipeInput(item)));
	}
}
