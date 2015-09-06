package minetweaker.mods.ic2;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IMachineRecipeManagerExt;
import java.util.Arrays;
import minetweaker.MineTweakerAPI;
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
		try {
			if (machine instanceof IMachineRecipeManagerExt) {
				((IMachineRecipeManagerExt) machine).addRecipe(input, tag, true, output);
			} else {
				machine.addRecipe(input, tag, output);
			}
		} catch (RuntimeException ex) {
			MineTweakerAPI.logError(ex.getMessage());
		}
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

	@Override
	public Object getOverrideKey() {
		return null;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 47 * hash + (this.machine != null ? this.machine.hashCode() : 0);
		hash = 47 * hash + Arrays.deepHashCode(this.output);
		hash = 47 * hash + (this.input != null ? this.input.hashCode() : 0);
		hash = 47 * hash + (this.tag != null ? this.tag.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MachineAddRecipeAction other = (MachineAddRecipeAction) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.machine != other.machine && (this.machine == null || !this.machine.equals(other.machine))) {
			return false;
		}
		if (!Arrays.deepEquals(this.output, other.output)) {
			return false;
		}
		if (this.input != other.input && (this.input == null || !this.input.equals(other.input))) {
			return false;
		}
		if (this.tag != other.tag && (this.tag == null || !this.tag.equals(other.tag))) {
			return false;
		}
		return true;
	}
}
