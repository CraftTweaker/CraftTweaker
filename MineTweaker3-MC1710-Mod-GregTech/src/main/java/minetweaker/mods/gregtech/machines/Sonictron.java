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
 * Provides access to the sonictron.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Sonictron")
@ModOnly("gregtech")
public class Sonictron {
	/**
	 * Adds a sonictron sound.
	 * 
	 * @param itemStack item you want to display for this sound
	 * @param soundName name of the Sound in the resources/newsound-folder like Vanillasounds
	 */
	@ZenMethod
	public static void addSound(IItemStack itemStack, String soundName) {
		MineTweakerAPI.apply(new AddSoundAction(itemStack, soundName));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddSoundAction extends OneWayAction {
		private final IItemStack itemStack;
		private final String soundName;
		
		public AddSoundAction(IItemStack itemStack, String soundName) {
			this.itemStack = itemStack;
			this.soundName = soundName;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addSonictronSound(
					MineTweakerMC.getItemStack(itemStack),
					soundName);
		}

		@Override
		public String describe() {
			return "Adding sonictron sound " + soundName;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 79 * hash + (this.itemStack != null ? this.itemStack.hashCode() : 0);
			hash = 79 * hash + (this.soundName != null ? this.soundName.hashCode() : 0);
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
			final AddSoundAction other = (AddSoundAction) obj;
			if (this.itemStack != other.itemStack && (this.itemStack == null || !this.itemStack.equals(other.itemStack))) {
				return false;
			}
			if ((this.soundName == null) ? (other.soundName != null) : !this.soundName.equals(other.soundName)) {
				return false;
			}
			return true;
		}
	}
}
