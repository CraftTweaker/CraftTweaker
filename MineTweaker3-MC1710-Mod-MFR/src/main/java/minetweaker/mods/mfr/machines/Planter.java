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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;
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
		IBlock blockDirt = MineTweakerMC.getBlockAnyMeta((Block) Block.blockRegistry.getObject("minecraft:dirt"));
		IBlock blockGrass = MineTweakerMC.getBlockAnyMeta((Block) Block.blockRegistry.getObject("minecraft:grass"));
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
	public static void addPlantable(IItemStack seed, IItemStack itemblock, @Optional IBlockPattern canPlantOn) {
		IBlock block = itemblock.asBlock();
		if (block == null) {
			MineTweakerAPI.logError("The given itemblock has no block equivalant");
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
		private final Item seed;
		private final List<TweakerPlantable> plantables;
		
		public TweakerPlantablePartial(Item seed) {
			this.seed = seed;
			plantables = new ArrayList<TweakerPlantable>();
		}
		
		public TweakerPlantablePartial(Item seed, TweakerPlantable plantable) {
			this(seed);
			
			plantables.add(plantable);
		}
		
		@Override
		public Item getSeed() {
			return seed;
		}
		
		@Override
		public boolean canBePlanted(ItemStack is, boolean bln) {
			IItemStack iSeed = MineTweakerMC.getIItemStack(is);
			for (TweakerPlantable plantable : plantables) {
				if (plantable.seed.matches(iSeed))
					return true;
			}
			
			return false;
		}

		@Override
		public ReplacementBlock getPlantedBlock(World world, int i, int i1, int i2, ItemStack seed) {
			IItemStack iSeed = MineTweakerMC.getIItemStack(seed);
			for (TweakerPlantable plantable : plantables) {
				if (plantable.seed.matches(iSeed))
					return new ReplacementBlock(MineTweakerMC.getBlock(plantable.block)).setMeta(plantable.block.getMeta());
			}
			
			return null;
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
			for (IItemStack iitem : plantable.seed.getItems()) {
				Item item = MineTweakerMC.getItemStack(iitem).getItem();
				if (MFRRegistry.getPlantables().containsKey(item)) {
					IFactoryPlantable existing = MFRRegistry.getPlantables().get(item);
					if (existing instanceof TweakerPlantablePartial) {
						((TweakerPlantablePartial) existing).plantables.add(plantable);
					} else {
						MineTweakerAPI.logError("Existing non-MineTweaker plantable already exists for seed ID " + Item.itemRegistry.getNameForObject(item));
					}
				} else {
					MFRRegistry.registerPlantable(new TweakerPlantablePartial(item, plantable));
				}
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (IItemStack iItem : plantable.seed.getItems()) {
				Item item = MineTweakerMC.getItemStack(iItem).getItem();
				IFactoryPlantable existing = MFRRegistry.getPlantables().get(item);
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
		private final Map<Item, IFactoryPlantable> removed;
		
		private RemovePlantableAction(IIngredient seed) {
			this.seed = seed;
			
			removed = new HashMap<Item, IFactoryPlantable>();
			for (IItemStack iItem : seed.getItems()) {
				Item item = MineTweakerMC.getItemStack(iItem).getItem();
				if (!removed.containsKey(item)) {
					if (MFRRegistry.getPlantables().containsKey(item)) {
						removed.put(item, MFRRegistry.getPlantables().get(item));
					}
				}
			}
		}

		@Override
		public void apply() {
			for (Item seedItem : removed.keySet()) {
				MFRRegistry.getPlantables().remove(seedItem);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (Map.Entry<Item, IFactoryPlantable> removedEntry : removed.entrySet()) {
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
