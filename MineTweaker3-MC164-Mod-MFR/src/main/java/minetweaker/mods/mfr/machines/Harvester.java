/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.block.IBlock;
import minetweaker.api.block.IBlockPattern;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc164.block.MCBlockDefinition;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.Harvester")
@ModOnly("MineFactoryReloaded")
public class Harvester {
	@ZenMethod
	public static void addHarvestable(IItemStack item) {
		addHarvestable(item.asBlock(), (WeightedItemStack[]) null, null);
	}
	
	@ZenMethod
	public static void addHarvestable(IItemStack item, String type) {
		addHarvestable(item.asBlock(), (WeightedItemStack[]) null, type);
	}
	
	@ZenMethod
	public static void addHarvestable(IBlockPattern block, WeightedItemStack drop, @Optional String type) {
		addHarvestable(block, new WeightedItemStack[] { drop }, type);
	}
	
	@ZenMethod
	public static void addHarvestable(IBlockPattern block, WeightedItemStack[] drops, @Optional String type) {
		TweakerHarvestable harvestable = new TweakerHarvestable(block, drops, type);
		MineTweakerAPI.apply(new AddHarvestableAction(harvestable));
	}
	
	@ZenMethod
	public static void removeHarvestable(IBlockPattern block) {
		MineTweakerAPI.apply(new RemoveHarvestableAction(block));
	}
	
	// #####################
	// ### Inner classes ###
	// #####################
	
	private static class TweakerHarvestable {
		private final IBlockPattern block;
		private final WeightedItemStack[] possibleDrops;
		private final HarvestType type;
		
		public TweakerHarvestable(IBlockPattern block, WeightedItemStack[] possibleDrops, String stringType) {
			this.block = block;
			this.possibleDrops = possibleDrops;
			
			HarvestType type = HarvestType.Normal;
			if (stringType == null || stringType.equals("normal")) {
				type = HarvestType.Normal;
			} else if (stringType.equals("column")) {
				type = HarvestType.Column;
			} else if (stringType.equals("leaveBottom")) {
				type = HarvestType.LeaveBottom;
			} else if (stringType.equals("tree")) {
				type = HarvestType.Tree;
			} else if (stringType.equals("treeFlipped")) {
				type = HarvestType.TreeFlipped;
			} else if (stringType.equals("treeLeaf")) {
				type = HarvestType.TreeLeaf;
			} else {
				throw new IllegalArgumentException("Unknown harvestable type: " + stringType);
			}
			
			this.type = type;
		}
	}
	
	private static class TweakerHarvestablePartial implements IFactoryHarvestable {
		private final int blockId;
		private final List<TweakerHarvestable> harvestables;
		
		public TweakerHarvestablePartial(int blockId) {
			this.blockId = blockId;
			harvestables = new ArrayList<TweakerHarvestable>();
		}

		@Override
		public int getPlantId() {
			return blockId;
		}

		@Override
		public HarvestType getHarvestType() {
			// WARNING: first type will be used
			// Also, take into account the possibility of having no harvestable yet
			return harvestables.isEmpty() ? HarvestType.Normal : harvestables.get(0).type;
		}

		@Override
		public boolean breakBlock() {
			return true;
		}

		@Override
		public boolean canBeHarvested(World world, Map<String, Boolean> map, int x, int y, int z) {
			for (TweakerHarvestable harvestable : harvestables) {
				if (harvestable.block.matches(MineTweakerMC.getBlock(world, x, y, z)))
					return true;
			}
			
			return false;
		}

		@Override
		public List<ItemStack> getDrops(World world, Random random, Map<String, Boolean> map, int x, int y, int z) {
			IBlock block = MineTweakerMC.getBlock(world, x, y, z);
			for (TweakerHarvestable harvestable : harvestables) {
				if (harvestable.block.matches(block)) {
					if (harvestable.possibleDrops == null) {
						Block mcBlock = ((MCBlockDefinition) block.getDefinition()).getInternalBlock();
						List<ItemStack> result = mcBlock.getBlockDropped(world, x, y, z, block.getMeta(), 0);
						return result;
					} else {
						List<ItemStack> result = Arrays.asList(MineTweakerMC.getItemStacks(WeightedItemStack.pickRandomDrops(random, harvestable.possibleDrops)));
						return result;
					}
				}
			}
			
			return Collections.EMPTY_LIST;
		}

		@Override
		public void preHarvest(World world, int x, int y, int z) {
			
		}

		@Override
		public void postHarvest(World world, int x, int y, int z) {
			
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddHarvestableAction implements IUndoableAction {
		private final TweakerHarvestable harvestable;
		
		public AddHarvestableAction(TweakerHarvestable harvestable) {
			this.harvestable = harvestable;
		}
		
		@Override
		public void apply() {
			Map<Integer, IFactoryHarvestable> harvestables = MFRRegistry.getHarvestables();
			for (IBlock partial : harvestable.block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				if (harvestable != null && harvestables.containsKey(blockId)) {
					IFactoryHarvestable existingHarvestable = harvestables.get(blockId);
					if (existingHarvestable instanceof TweakerHarvestablePartial) {
						TweakerHarvestablePartial existingHarvestablePartial = (TweakerHarvestablePartial) existingHarvestable;
						if (!existingHarvestablePartial.harvestables.contains(harvestable)) {
							existingHarvestablePartial.harvestables.add(harvestable);
						}
					} else {
						MineTweakerAPI.logError("A non-MineTweaker fruit already exists for this ID");
					}
				} else {
					TweakerHarvestablePartial factoryFruit = new TweakerHarvestablePartial(blockId);
					factoryFruit.harvestables.add(harvestable);
					FactoryRegistry.registerHarvestable(factoryFruit);
				}
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Map<Integer, IFactoryHarvestable> harvestables = MFRRegistry.getHarvestables();
			for (IBlock partial : harvestable.block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				IFactoryHarvestable factoryHarvestable = harvestables.get(blockId);
				if (factoryHarvestable != null && factoryHarvestable instanceof TweakerHarvestablePartial) {
					((TweakerHarvestablePartial) factoryHarvestable).harvestables.remove(harvestable);
				}
			}
		}

		@Override
		public String describe() {
			return "Adding Harvester harvestable block " + harvestable.block.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Harvester harvestable block " + harvestable.block.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveHarvestableAction implements IUndoableAction {
		private final IBlockPattern block;
		private final Map<Integer, IFactoryHarvestable> removed;
		
		public RemoveHarvestableAction(IBlockPattern block) {
			this.block = block;
			
			Map<Integer, IFactoryHarvestable> harvestables = MFRRegistry.getHarvestables();
			removed = new HashMap<Integer, IFactoryHarvestable>();
			for (IBlock partial : block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				if (harvestables.containsKey(blockId)) {
					removed.put(blockId, harvestables.get(blockId));
				}
			}
		}
		
		@Override
		public void apply() {
			Map<Integer, IFactoryHarvestable> harvestables = MFRRegistry.getHarvestables();
			for (Integer key : removed.keySet()) {
				harvestables.remove(key);
			}
		}
		
		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Map<Integer, IFactoryHarvestable> harvestables = MFRRegistry.getHarvestables();
			for (Map.Entry<Integer, IFactoryHarvestable> restore : removed.entrySet()) {
				harvestables.put(restore.getKey(), restore.getValue());
			}
		}

		@Override
		public String describe() {
			return "Removing Harvester harvestable block " + block.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring Harvester harvestable block " + block.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
