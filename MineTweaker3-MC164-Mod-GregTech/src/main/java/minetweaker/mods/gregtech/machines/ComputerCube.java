package minetweaker.mods.gregtech.machines;

import gregtechmod.api.GregTech_API;
import java.util.Arrays;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the computer cube descriptions.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.ComputerCube")
@ModOnly("gregtech_addon")
public class ComputerCube {
	/**
	 * Adds a description entry to the computer cube.
	 * 
	 * @param items items to be displayed
	 * @param text text to be displayed
	 */
	@ZenMethod
	public static void addDescriptionSet(IItemStack[] items, String[] text) {
		MineTweakerAPI.apply(new AddDescriptionAction(items, text));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddDescriptionAction extends OneWayAction {
		private final IItemStack[] items;
		private final String[] text;
		
		public AddDescriptionAction(IItemStack[] items, String[] text) {
			this.items = items;
			this.text = text;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addComputercubeDescriptionSet(MineTweakerMC.getItemStacks(items), text);
		}

		@Override
		public String describe() {
			return "Adding computer cube descriptions";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 3;
			hash = 11 * hash + Arrays.deepHashCode(this.items);
			hash = 11 * hash + Arrays.deepHashCode(this.text);
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
			final AddDescriptionAction other = (AddDescriptionAction) obj;
			if (!Arrays.deepEquals(this.items, other.items)) {
				return false;
			}
			if (!Arrays.deepEquals(this.text, other.text)) {
				return false;
			}
			return true;
		}
	}
}
