/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.mods.mfr.MFRHacks;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.RubberTree")
@ModOnly("MineFactoryReloaded")
public class RubberTree {
	@ZenMethod
	public static void addBiome(String biome) {
		MineTweakerAPI.apply(new AddBiomeAction(biome));
	}
	
	@ZenMethod
	public static void removeBiome(String biome) {
		if (MFRHacks.rubberTreeBiomes == null) {
			MineTweakerAPI.logWarning("Cannot remove rubber tree biomes");
		} else {
			MineTweakerAPI.apply(new RemoveBiomeAction(biome));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddBiomeAction implements IUndoableAction {
		private final String biome;
		
		public AddBiomeAction(String biome) {
			this.biome = biome;
		}

		@Override
		public void apply() {
			FactoryRegistry.registerRubberTreeBiome(biome);
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.rubberTreeBiomes != null;
		}

		@Override
		public void undo() {
			MFRHacks.rubberTreeBiomes.remove(biome);
		}

		@Override
		public String describe() {
			return "Adding rubber tree biome " + biome;
		}

		@Override
		public String describeUndo() {
			return "Removing rubber tree biome " + biome;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveBiomeAction implements IUndoableAction {
		private final String biome;
		
		private RemoveBiomeAction(String biome) {
			this.biome = biome;
		}

		@Override
		public void apply() {
			MFRHacks.rubberTreeBiomes.remove(biome);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRHacks.rubberTreeBiomes.add(biome);
		}

		@Override
		public String describe() {
			return "Removing rubber tree biome " + biome;
		}

		@Override
		public String describeUndo() {
			return "Adding rubber tree biome " + biome;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
