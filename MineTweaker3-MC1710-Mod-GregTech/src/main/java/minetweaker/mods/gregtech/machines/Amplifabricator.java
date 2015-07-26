package minetweaker.mods.gregtech.machines;

import gregtech.api.GregTech_API;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the amplifabricator.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Amplifabricator")
@ModOnly("gregtech")
public class Amplifabricator {
	/**
	 * Adds an amplifier.
	 * 
	 * @param item amplifier item
	 * @param durationTicks duration in ticks
	 * @param amount amplification amount
	 */
	@ZenMethod
	public static void addAmplifier(IItemStack item, int durationTicks, int amount) {
		MineTweakerAPI.apply(new AddAmplifierAction(item, durationTicks, amount));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddAmplifierAction extends OneWayAction {
		private final IItemStack item;
		private final int duration;
		private final int amount;
		
		public AddAmplifierAction(IItemStack item, int duration, int amount) {
			this.item = item;
			this.duration = duration;
			this.amount = amount;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addAmplifier(
					MineTweakerMC.getItemStack(item),
					duration,
					amount);
		}

		@Override
		public String describe() {
			return "Adding amplifier " + item;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 59 * hash + (this.item != null ? this.item.hashCode() : 0);
			hash = 59 * hash + this.duration;
			hash = 59 * hash + this.amount;
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
			final AddAmplifierAction other = (AddAmplifierAction) obj;
			if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
				return false;
			}
			if (this.duration != other.duration) {
				return false;
			}
			if (this.amount != other.amount) {
				return false;
			}
			return true;
		}
	}
}
