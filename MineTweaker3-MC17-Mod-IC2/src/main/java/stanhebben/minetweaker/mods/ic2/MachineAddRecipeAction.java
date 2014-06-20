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
 * @author Stan Hebben
 */
public class MachineAddRecipeAction extends OneWayAction {
	private final String name;
	private final IMachineRecipeManager machine;
	private final ItemStack[] output;
	private final IC2RecipeInput input;
	private final NBTTagCompound tag;
	
	public MachineAddRecipeAction(String name, IMachineRecipeManager machine, ItemStack[] output, NBTTagCompound tag, IC2RecipeInput input) {
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
		if (output.length == 1) {
			return "Adding " + name + " recipe for " + output[0].getDisplayName();
		} else {
			StringBuilder result = new StringBuilder();
			result.append("Adding ").append(name).append(" recipe for ");
			result.append("[");
			for (int i = 0; i < output.length; i++) {
				if (i == 0) {
					result.append(", ");
				} else {
					result.append(output[i].getDisplayName());
				}
			}
			result.append("]");
			return result.toString();
		}
	}
}
