/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.minetweaker.mods.ic2;

import ic2.api.recipe.IMachineRecipeManager;
import minetweaker.OneWayAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author Stan
 */
public class MachineAddRecipeAction extends OneWayAction {
	private final String name;
	private final IMachineRecipeManager machine;
	private final ItemStack output;
	private final IC2RecipeInput input;
	private final NBTTagCompound tag;
	
	public MachineAddRecipeAction(String name, IMachineRecipeManager machine, ItemStack output, NBTTagCompound tag, IC2RecipeInput input) {
		this.name = name;
		this.machine = machine;
		this.output = output;
		this.input = input;
		this.tag = tag;
	}

	@Override
	public void apply() {
		machine.addRecipe(input, tag, output);
	}

	@Override
	public String describe() {
		return "Adding " + name + " recipe for " + output.getDisplayName();
	}
}
