/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import powercrystals.minefactoryreloaded.MFRRegistry;
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
		MineTweakerAPI.apply(new RemoveBiomeAction(biome));
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
			MFRRegistry.registerRubberTreeBiome(biome);
		}

		@Override
		public boolean canUndo() {
			return MFRRegistry.getRubberTreeBiomes() != null;
		}

		@Override
		public void undo() {
			MFRRegistry.getRubberTreeBiomes().remove(biome);
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
			MFRRegistry.getRubberTreeBiomes().remove(biome);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getRubberTreeBiomes().add(biome);
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
