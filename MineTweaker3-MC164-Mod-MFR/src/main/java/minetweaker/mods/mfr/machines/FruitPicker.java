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
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mods.mfr.MFRHacks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.FruitPicker")
@ModOnly("MineFactoryReloaded")
public class FruitPicker {
	@ZenMethod
	public static void addFruit(IBlockPattern block, IBlock replacement, WeightedItemStack drop) {
		addFruit(block, replacement, new WeightedItemStack[] { drop });
	}
	
	@ZenMethod
	public static void addFruit(IBlockPattern block, IBlock replacement, WeightedItemStack[] drops) {
		TweakerFruit fruit = new TweakerFruit(block, replacement, drops);
		MineTweakerAPI.apply(new AddFruitAction(fruit));
	}
	
	@ZenMethod
	public static void removeFruit(IBlockPattern block) {
		if (MFRHacks.fruitBlocks == null) {
			MineTweakerAPI.logWarning("Could not remove MFR Fruit Picker fruits");
		} else {
			MineTweakerAPI.apply(new RemoveFruitAction(block));
		}
	}
	
	@ZenMethod
	public static void addLog(IBlockPattern block) {
		MineTweakerAPI.apply(new AddLogAction(block));
	}
	
	@ZenMethod
	public static void removeLog(IBlockPattern block) {
		if (MFRHacks.fruitLogBlocks == null) {
			MineTweakerAPI.logWarning("Cannot remove MFR Fruit Picker Logs");
		} else {
			MineTweakerAPI.apply(new RemoveLogAction(block));
		}
	}
	
	// #####################
	// ### Inner classes ###
	// #####################
	
	private static class TweakerFruit {
		private final IBlockPattern block;
		private final IBlock replacement;
		private final WeightedItemStack[] possibleDrops;
		
		public TweakerFruit(IBlockPattern block, IBlock replacement, WeightedItemStack[] possibleDrops) {
			this.block = block;
			this.replacement = replacement;
			this.possibleDrops = possibleDrops;
		}
	}
	
	private static class TweakerFruitPartial implements IFactoryFruit {
		private final int blockId;
		private final List<TweakerFruit> fruits;
		
		public TweakerFruitPartial(int blockId) {
			this.blockId = blockId;
			fruits = new ArrayList<TweakerFruit>();
		}

		@Override
		public int getSourceBlockId() {
			return blockId;
		}

		@Override
		public boolean canBePicked(World world, int x, int y, int z) {
			for (TweakerFruit fruit : fruits) {
				if (fruit.block.matches(MineTweakerMC.getBlock(world, x, y, z)))
					return true;
			}
			
			return false;
		}

		@Override
		public ItemStack getReplacementBlock(World world, int x, int y, int z) {
			IBlock block = MineTweakerMC.getBlock(world, x, y, z);
			for (TweakerFruit fruit : fruits) {
				if (fruit.block.matches(block))
					return MineTweakerMC.toItemStack(fruit.replacement);
			}
			return null;
		}

		@Override
		public void prePick(World world, int x, int y, int z) {}

		@Override
		public List<ItemStack> getDrops(World world, Random rand, int x, int y, int z) {
			IBlock block = MineTweakerMC.getBlock(world, x, y, z);
			for (TweakerFruit fruit : fruits) {
				if (fruit.block.matches(block))
					return Arrays.asList(MineTweakerMC.getItemStacks(WeightedItemStack.pickRandomDrops(rand, fruit.possibleDrops)));
			}
			
			return Collections.EMPTY_LIST;
		}

		@Override
		public void postPick(World world, int x, int y, int z) {}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddFruitAction implements IUndoableAction {
		private final TweakerFruit fruit;
		
		public AddFruitAction(TweakerFruit fruit) {
			this.fruit = fruit;
		}
		
		@Override
		public void apply() {
			Map<Integer, IFactoryFruit> fruits = MFRHacks.fruitBlocks;
			for (IBlock partial : fruit.block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				if (fruits != null && fruits.containsKey(blockId)) {
					IFactoryFruit existingFruit = fruits.get(blockId);
					if (existingFruit instanceof TweakerFruitPartial) {
						TweakerFruitPartial existingFruitPartial = (TweakerFruitPartial) existingFruit;
						if (!existingFruitPartial.fruits.contains(fruit)) {
							existingFruitPartial.fruits.add(fruit);
						}
					} else {
						MineTweakerAPI.logError("A non-MineTweaker fruit already exists for this ID");
					}
				} else {
					TweakerFruitPartial factoryFruit = new TweakerFruitPartial(blockId);
					FactoryRegistry.registerFruit(factoryFruit);
				}
			}
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.fruitBlocks != null;
		}

		@Override
		public void undo() {
			Map<Integer, IFactoryFruit> fruits = MFRHacks.fruitBlocks;
			for (IBlock partial : fruit.block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				IFactoryFruit factoryFruit = fruits.get(blockId);
				if (factoryFruit != null && factoryFruit instanceof TweakerFruitPartial) {
					((TweakerFruitPartial) factoryFruit).fruits.remove(fruit);
				}
			}
		}

		@Override
		public String describe() {
			return "Adding Fruit Picker fruit block " + fruit.block.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing Fruit Picker fruit block " + fruit.block.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveFruitAction implements IUndoableAction {
		private final IBlockPattern block;
		private final Map<Integer, IFactoryFruit> removed;
		
		public RemoveFruitAction(IBlockPattern block) {
			this.block = block;
			
			Map<Integer, IFactoryFruit> fruits = MFRHacks.fruitBlocks;
			removed = new HashMap<Integer, IFactoryFruit>();
			for (IBlock partial : block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				if (fruits.containsKey(blockId)) {
					removed.put(blockId, fruits.get(blockId));
				}
			}
		}
		
		@Override
		public void apply() {
			Map<Integer, IFactoryFruit> fruits = MFRHacks.fruitBlocks;
			for (Integer key : removed.keySet()) {
				fruits.remove(key);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Map<Integer, IFactoryFruit> fruits = MFRHacks.fruitBlocks;
			for (Map.Entry<Integer, IFactoryFruit> restore : removed.entrySet()) {
				fruits.put(restore.getKey(), restore.getValue());
			}
		}

		@Override
		public String describe() {
			return "Removing fruit picker fruit block " + block.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring fruit picker fruit block " + block.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddLogAction implements IUndoableAction {
		private final IBlockPattern block;
		private final List<Integer> addedLogs = new ArrayList<Integer>();
		
		public AddLogAction(IBlockPattern block) {
			this.block = block;
			
			for (IBlock partial : block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				if (MFRHacks.fruitLogBlocks == null || !MFRHacks.fruitLogBlocks.contains(blockId)) {
					if (!addedLogs.contains(blockId))
						addedLogs.add(blockId);
				}
			}
		}

		@Override
		public void apply() {
			for (int log : addedLogs) {
				FactoryRegistry.registerFruitLogBlockId(log);
			}
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.fruitLogBlocks != null;
		}

		@Override
		public void undo() {
			for (int log : addedLogs) {
				MFRHacks.fruitLogBlocks.remove(log);
			}
		}

		@Override
		public String describe() {
			return "Adding MFR Fruit Picker log block " + block.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing MFR Fruit Picker log block " + block.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveLogAction implements IUndoableAction {
		private final IBlockPattern block;
		private final List<Integer> removedLogs = new ArrayList<Integer>();
		
		public RemoveLogAction(IBlockPattern block) {
			this.block = block;
			
			for (IBlock partial : block.getBlocks()) {
				int blockId = MineTweakerMC.getBlockId(partial.getDefinition());
				if (MFRHacks.fruitLogBlocks.contains(blockId)) {
					if (!removedLogs.contains(blockId))
						removedLogs.add(blockId);
				}
			}
		}

		@Override
		public void apply() {
			for (int log : removedLogs) {
				MFRHacks.fruitLogBlocks.remove(log);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (int log : removedLogs) {
				FactoryRegistry.registerFruitLogBlockId(log);
			}
		}

		@Override
		public String describe() {
			return "Removing MFR Fruit Picker log block " + block.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring MFR Fruit Picker log block " + block.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
