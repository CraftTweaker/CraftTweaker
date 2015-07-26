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
 * Provider access to the boxing and unboxing recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Boxing")
@ModOnly("gregtech")
public class Boxing {
	/**
	 * Adds a boxing recipe.
	 * 
	 * @param fullBox the full box
	 * @param item the item to be packed
	 * @param emptyBox empty box
	 * @param durationTicks boxing duration, in ticks
	 * @param euPerTick eu consumption per tick
	 */
	@ZenMethod
	public static void addBoxingRecipe(IItemStack fullBox, IItemStack item, IItemStack emptyBox, int durationTicks, int euPerTick) {
		MineTweakerAPI.apply(new AddBoxingAction(fullBox, item, emptyBox, durationTicks, euPerTick));
	}
	
	/**
	 * Adds an unboxing recipe.
	 * 
	 * @param item the item after unpacking
	 * @param emptyBox the empty box after unpacking
	 * @param fullBox full box
	 * @param durationTicks unboxing duration, in ticks
	 * @param euPerTick eu consumption per tick
	 */
	@ZenMethod
	public static void addUnboxingRecipe(IItemStack item, IItemStack emptyBox, IItemStack fullBox, int durationTicks, int euPerTick) {
		MineTweakerAPI.apply(new AddUnboxingAction(item, emptyBox, fullBox, durationTicks, euPerTick));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddBoxingAction extends OneWayAction {
		private final IItemStack fullBox;
		private final IItemStack item;
		private final IItemStack emptyBox;
		private final int duration;
		private final int euPerTick;
		
		public AddBoxingAction(IItemStack fullBox, IItemStack item, IItemStack emptyBox, int duration, int euPerTick) {
			this.fullBox = fullBox;
			this.item = item;
			this.emptyBox = emptyBox;
			this.duration = duration;
			this.euPerTick = euPerTick;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addBoxingRecipe(
					MineTweakerMC.getItemStack(item),
					MineTweakerMC.getItemStack(emptyBox),
					MineTweakerMC.getItemStack(fullBox),
					duration,
					euPerTick);
		}

		@Override
		public String describe() {
			return "Adding boxing recipe to pack " + item + " in " + emptyBox;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 89 * hash + (this.fullBox != null ? this.fullBox.hashCode() : 0);
			hash = 89 * hash + (this.item != null ? this.item.hashCode() : 0);
			hash = 89 * hash + (this.emptyBox != null ? this.emptyBox.hashCode() : 0);
			hash = 89 * hash + this.duration;
			hash = 89 * hash + this.euPerTick;
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
			final AddBoxingAction other = (AddBoxingAction) obj;
			if (this.fullBox != other.fullBox && (this.fullBox == null || !this.fullBox.equals(other.fullBox))) {
				return false;
			}
			if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
				return false;
			}
			if (this.emptyBox != other.emptyBox && (this.emptyBox == null || !this.emptyBox.equals(other.emptyBox))) {
				return false;
			}
			if (this.duration != other.duration) {
				return false;
			}
			if (this.euPerTick != other.euPerTick) {
				return false;
			}
			return true;
		}
	}
	
	private static class AddUnboxingAction extends OneWayAction {
		private final IItemStack item;
		private final IItemStack emptyBox;
		private final IItemStack fullBox;
		private final int duration;
		private final int euPerTick;
		
		public AddUnboxingAction(IItemStack item, IItemStack emptyBox, IItemStack fullBox, int duration, int euPerTick) {
			this.item = item;
			this.emptyBox = emptyBox;
			this.fullBox = fullBox;
			this.duration = duration;
			this.euPerTick = euPerTick;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addUnboxingRecipe(
					MineTweakerMC.getItemStack(fullBox),
					MineTweakerMC.getItemStack(item),
					MineTweakerMC.getItemStack(emptyBox),
					duration,
					euPerTick);
		}

		@Override
		public String describe() {
			return "Adding unboxing recipe to unbox " + fullBox;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 3;
			hash = 83 * hash + (this.item != null ? this.item.hashCode() : 0);
			hash = 83 * hash + (this.emptyBox != null ? this.emptyBox.hashCode() : 0);
			hash = 83 * hash + (this.fullBox != null ? this.fullBox.hashCode() : 0);
			hash = 83 * hash + this.duration;
			hash = 83 * hash + this.euPerTick;
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
			final AddUnboxingAction other = (AddUnboxingAction) obj;
			if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
				return false;
			}
			if (this.emptyBox != other.emptyBox && (this.emptyBox == null || !this.emptyBox.equals(other.emptyBox))) {
				return false;
			}
			if (this.fullBox != other.fullBox && (this.fullBox == null || !this.fullBox.equals(other.fullBox))) {
				return false;
			}
			if (this.duration != other.duration) {
				return false;
			}
			if (this.euPerTick != other.euPerTick) {
				return false;
			}
			return true;
		}
	}
}
