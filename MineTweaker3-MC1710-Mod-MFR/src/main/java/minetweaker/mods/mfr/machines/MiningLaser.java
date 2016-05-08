/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import cofh.lib.util.WeightedRandomItemStack;
import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import powercrystals.minefactoryreloaded.MFRRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.MiningLaser")
@ModOnly("MineFactoryReloaded")
public class MiningLaser {
	@ZenMethod
	public static void addOre(WeightedItemStack ore) {
		MineTweakerAPI.apply(new AddOreAction(ore));
	}
	
	@ZenMethod
	public static void removeOre(IIngredient ore) {
		MineTweakerAPI.apply(new RemoveOreAction(ore));
	}
	
	@ZenMethod
	public static void addPreferredOre(int color, IIngredient ore) {
		MineTweakerAPI.apply(new AddPreferredOreAction(color, ore));
	}
	
	@ZenMethod
	public static void removePreferredOre(int color, IIngredient ore) {
		MineTweakerAPI.apply(new RemovePreferredOreAction(color, ore));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddOreAction implements IUndoableAction {
		private final WeightedRandomItemStack item;
		
		public AddOreAction(WeightedItemStack item) {
			this.item = new WeightedRandomItemStack(MineTweakerMC.getItemStack(item.getStack()), (int) item.getChance());
		}

		@Override
		public void apply() {
			MFRRegistry.getLaserOres().add(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getLaserOres().remove(item);
		}

		@Override
		public String describe() {
			return "Adding laser ore " + item.getStack().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing laser ore " + item.getStack().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveOreAction implements IUndoableAction {
		private final IIngredient ingredient;
		private final List<WeightedRandomItemStack> removed;
		
		public RemoveOreAction(IIngredient ingredient) {
			this.ingredient = ingredient;
			removed = new ArrayList<WeightedRandomItemStack>();
			
			for (WeightedRandom.Item iStack : MFRRegistry.getLaserOres()) {
				if (iStack instanceof WeightedRandomItemStack) {
					WeightedRandomItemStack stack = (WeightedRandomItemStack) iStack;
					if (ingredient.matches(MineTweakerMC.getIItemStack(stack.getStack()))) {
						removed.add(stack);
					}
				}
			}
		}

		@Override
		public void apply() {
			MFRRegistry.getLaserOres().removeAll(removed);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getLaserOres().addAll(removed);
		}

		@Override
		public String describe() {
			return "Removing laser ore " + ingredient.toString();
		}

		@Override
		public String describeUndo() {
			return "Restoring laser ore " + ingredient.toString();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddPreferredOreAction implements IUndoableAction {
		private final int color;
		private final IIngredient ore;
		
		public AddPreferredOreAction(int color, IIngredient ore) {
			this.color = color;
			this.ore = ore;
		}
		
		@Override
		public void apply() {
			for (IItemStack item : ore.getItems()) {
				MFRRegistry.addLaserPreferredOre(color, MineTweakerMC.getItemStack(item));
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (IItemStack item : ore.getItems()) {
				ItemStack stack = MineTweakerMC.getItemStack(item);
				MFRRegistry.getLaserPreferredOres(color).remove(stack);
			}
		}

		@Override
		public String describe() {
			return "Adding laser preferred ore " + ore + " to color " + color;
		}

		@Override
		public String describeUndo() {
			return "Removing laser preferred ore " + ore + " from color " + color;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemovePreferredOreAction implements IUndoableAction {
		private final int color;
		private final IIngredient ore;
		private final List<ItemStack> toRemove;
		
		public RemovePreferredOreAction(int color, IIngredient ore) {
			this.color = color;
			this.ore = ore;
			
			toRemove = new ArrayList<ItemStack>();
			for (ItemStack item : MFRRegistry.getLaserPreferredOres(color)) {
				if (ore.matches(MineTweakerMC.getIItemStack(item))) {
					toRemove.add(item);
				}
			}
		}
		
		@Override
		public void apply() {
			MFRRegistry.getLaserPreferredOres(color).removeAll(toRemove);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getLaserPreferredOres(color).addAll(toRemove);
		}

		@Override
		public String describe() {
			return "Removing preferred ore " + ore + " for color " + color;
		}

		@Override
		public String describeUndo() {
			return "Adding preferred ore " + ore + " for color " + color;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
