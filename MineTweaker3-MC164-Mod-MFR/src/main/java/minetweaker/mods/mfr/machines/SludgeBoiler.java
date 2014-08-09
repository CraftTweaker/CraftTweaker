/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mods.mfr.MFRHacks;
import powercrystals.core.random.WeightedRandomItemStack;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.SludgeBoiler")
@ModOnly("MineFactoryReloaded")
public class SludgeBoiler {
	@ZenMethod
	public static void addDrop(WeightedItemStack item) {
		MineTweakerAPI.apply(new AddDropAction(item));
	}
	
	@ZenMethod
	public static void removeDrop(IIngredient item) {
		if (MFRHacks.sludgeDrops == null) {
			MineTweakerAPI.logWarning("Cannot remove drops from the sludge boiler");
		} else {
			MineTweakerAPI.apply(new RemoveDropAction(item));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddDropAction implements IUndoableAction {
		private final WeightedRandomItemStack item;
		
		public AddDropAction(WeightedItemStack item) {
			this.item = new WeightedRandomItemStack((int) item.getChance(), MineTweakerMC.getItemStack(item.getStack()));
		}

		@Override
		public void apply() {
			if (MFRHacks.sludgeDrops == null) {
				FactoryRegistry.registerSludgeDrop(item.itemWeight, item.getStack());
			} else {
				MFRHacks.sludgeDrops.add(item);
			}
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.sludgeDrops != null;
		}

		@Override
		public void undo() {
			MFRHacks.sludgeDrops.remove(item);
		}

		@Override
		public String describe() {
			return "Adding sludge boiler drop " + item.getStack().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing sludge boiler drop " + item.getStack().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveDropAction implements IUndoableAction {
		private final IIngredient item;
		private final List<WeightedRandomItemStack> toRemove;
		
		private RemoveDropAction(IIngredient item) {
			this.item = item;
			
			toRemove = new ArrayList<WeightedRandomItemStack>();
			for (WeightedRandomItemStack itemStack : MFRHacks.sludgeDrops) {
				if (item.matches(MineTweakerMC.getIItemStack(itemStack.getStack()))) {
					toRemove.add(itemStack);
				}
			}
		}

		@Override
		public void apply() {
			for (WeightedRandomItemStack itemStack : toRemove) {
				MFRHacks.sludgeDrops.remove(itemStack);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (WeightedRandomItemStack item : toRemove) {
				MFRHacks.sludgeDrops.add(item);
			}
		}

		@Override
		public String describe() {
			return "Removing " + toRemove.size() + " sludge boiler drops for " + item;
		}

		@Override
		public String describeUndo() {
			return "Restoring " + toRemove.size() + " sludge boiler drops for " + item;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
