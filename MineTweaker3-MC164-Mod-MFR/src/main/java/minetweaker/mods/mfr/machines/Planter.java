/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.block.IBlock;
import minetweaker.api.block.IBlockPattern;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.Planter")
@ModOnly("MineFactoryReloaded")
public class Planter {
	private static final IBlockPattern GRASS_OR_DIRT;
	
	static {
		IBlock blockDirt = MineTweakerMC.getBlockAnyMeta(Block.dirt);
		IBlock blockGrass = MineTweakerMC.getBlockAnyMeta(Block.grass);
		GRASS_OR_DIRT = blockDirt.or(blockGrass);
	}
	
	@ZenMethod
	public static void addPlantable(IItemStack seed, @Optional IBlockPattern canPlantOn) {
		IBlock block = seed.asBlock();
		if (block == null) {
			MineTweakerAPI.logError("The given seed has no block equivalant");
		}
		addPlantable(seed, block, canPlantOn);
	}
	
	@ZenMethod
	public static void addPlantable(IIngredient seed, IBlock block, @Optional IBlockPattern canPlantOn) {
		if (canPlantOn == null) {
			canPlantOn = GRASS_OR_DIRT;
		}
		
		if (seed.getItems() == null) {
			MineTweakerAPI.logError("Seed must be a defined ingredient and cannot be <*>");
		} else {
			MineTweakerAPI.apply(new AddPlantableAction(new TweakerPlantable(seed, block, canPlantOn)));
		}
	}
	
	@ZenMethod
	public static void removePlantable(IIngredient seed) {
		MineTweakerAPI.apply(new RemovePlantableAction(seed));
	}
	
	// #####################
	// ### Inner classes ###
	// #####################
	
	private static class TweakerPlantable {
		private final IIngredient seed;
		private final IBlock block;
		private final IBlockPattern canPlantOn;
		
		public TweakerPlantable(IIngredient seed, IBlock block, IBlockPattern canPlantOn) {
			this.seed = seed;
			this.block = block;
			this.canPlantOn = canPlantOn;
		}
	}
	
	private static class TweakerPlantablePartial implements IFactoryPlantable {
		private final int seedId;
		private final List<TweakerPlantable> plantables;
		
		public TweakerPlantablePartial(int seedId) {
			this.seedId = seedId;
			plantables = new ArrayList<TweakerPlantable>();
		}
		
		public TweakerPlantablePartial(int seedId, TweakerPlantable plantable) {
			this(seedId);
			
			plantables.add(plantable);
		}
		
		@Override
		public int getSeedId() {
			return seedId;
		}

		@Override
		public int getPlantedBlockId(World world, int i, int i1, int i2, ItemStack seed) {
			IItemStack iSeed = MineTweakerMC.getIItemStack(seed);
			for (TweakerPlantable plantable : plantables) {
				if (plantable.seed.matches(iSeed))
					return MineTweakerMC.getBlockId(plantable.block.getDefinition());
			}
			
			return 1;
		}

		@Override
		public int getPlantedBlockMetadata(World world, int i, int i1, int i2, ItemStack seed) {
			IItemStack iSeed = MineTweakerMC.getIItemStack(seed);
			for (TweakerPlantable plantable : plantables) {
				if (plantable.seed.matches(iSeed))
					return plantable.block.getMeta();
			}
			
			return 0;
		}

		@Override
		public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack seed) {
			if (!world.isAirBlock(x, y, z))
				return false;
			
			for (TweakerPlantable plantable : plantables) {
				if (!plantable.seed.matches(MineTweakerMC.getIItemStack(seed)))
					continue;

				if (!plantable.canPlantOn.matches(MineTweakerMC.getBlock(world, x,  y - 1, z)))
					continue;
				
				return true;
			}
			
			return false;
		}

		@Override
		public void prePlant(World world, int i, int i1, int i2, ItemStack is) {
			
		}

		@Override
		public void postPlant(World world, int i, int i1, int i2, ItemStack is) {
			
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddPlantableAction implements IUndoableAction {
		private final TweakerPlantable plantable;
		
		private AddPlantableAction(TweakerPlantable plantable) {
			this.plantable = plantable;
		}

		@Override
		public void apply() {
			for (IItemStack item : plantable.seed.getItems()) {
				int itemId = MineTweakerMC.getItemStack(item).itemID;
				if (MFRRegistry.getPlantables().containsKey(itemId)) {
					IFactoryPlantable existing = MFRRegistry.getPlantables().get(itemId);
					if (existing instanceof TweakerPlantablePartial) {
						((TweakerPlantablePartial) existing).plantables.add(plantable);
					} else {
						MineTweakerAPI.logError("Existing non-MineTweaker plantable already exists for seed ID " + itemId);
					}
				} else {
					FactoryRegistry.registerPlantable(new TweakerPlantablePartial(itemId, plantable));
				}
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (IItemStack item : plantable.seed.getItems()) {
				int itemId = MineTweakerMC.getItemStack(item).itemID;
				IFactoryPlantable existing = MFRRegistry.getPlantables().get(itemId);
				if (existing instanceof TweakerPlantablePartial) {
					((TweakerPlantablePartial) existing).plantables.remove(plantable);
				}
			}
		}

		@Override
		public String describe() {
			return "Adding MFR Plantable for seed " + plantable.seed;
		}

		@Override
		public String describeUndo() {
			return "Removing MFR Plantable for seed " + plantable.seed;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemovePlantableAction implements IUndoableAction {
		private final IIngredient seed;
		private final Map<Integer, IFactoryPlantable> removed;
		
		private RemovePlantableAction(IIngredient seed) {
			this.seed = seed;
			
			removed = new HashMap<Integer, IFactoryPlantable>();
			for (IItemStack item : seed.getItems()) {
				int itemId = MineTweakerMC.getItemStack(item).itemID;
				if (!removed.containsKey(itemId)) {
					if (MFRRegistry.getPlantables().containsKey(itemId)) {
						removed.put(itemId, MFRRegistry.getPlantables().get(itemId));
					}
				}
			}
		}

		@Override
		public void apply() {
			for (Integer seedId : removed.keySet()) {
				MFRRegistry.getPlantables().remove(seedId);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (Map.Entry<Integer, IFactoryPlantable> removedEntry : removed.entrySet()) {
				MFRRegistry.getPlantables().put(removedEntry.getKey(), removedEntry.getValue());
			}
		}

		@Override
		public String describe() {
			return "Removing MFR Planter seed " + seed;
		}

		@Override
		public String describeUndo() {
			return "Restoring MFR Planter seed " + seed;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
