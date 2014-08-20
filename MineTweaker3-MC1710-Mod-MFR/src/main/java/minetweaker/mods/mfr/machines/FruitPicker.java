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
import minetweaker.mc1710.block.MCBlockDefinition;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;
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
		MineTweakerAPI.apply(new RemoveFruitAction(block));
	}
	
	@ZenMethod
	public static void addLog(IBlockPattern block) {
		MineTweakerAPI.apply(new AddLogAction(block));
	}
	
	@ZenMethod
	public static void removeLog(IBlockPattern block) {
		MineTweakerAPI.apply(new RemoveLogAction(block));
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
		private final Block block;
		private final List<TweakerFruit> fruits;
		
		public TweakerFruitPartial(Block block) {
			this.block = block;
			fruits = new ArrayList<TweakerFruit>();
		}

		@Override
		public Block getPlant() {
			return block;
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
		public boolean breakBlock() {
			return false;
		}

		@Override
		public ReplacementBlock getReplacementBlock(World world, int x, int y, int z) {
			IBlock iBlock = MineTweakerMC.getBlock(world, x, y, z);
			for (TweakerFruit fruit : fruits) {
				if (fruit.block.matches(iBlock))
					return new ReplacementBlock(MineTweakerMC.getBlock(fruit.replacement)).setMeta(iBlock.getMeta());
			}
			return null;
		}

		@Override
		public void prePick(World world, int x, int y, int z) {}

		@Override
		public List<ItemStack> getDrops(World world, Random rand, int x, int y, int z) {
			IBlock iBlock = MineTweakerMC.getBlock(world, x, y, z);
			for (TweakerFruit fruit : fruits) {
				if (fruit.block.matches(iBlock))
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
			Map<Block, IFactoryFruit> fruits = MFRRegistry.getFruits();
			for (IBlock partial : fruit.block.getBlocks()) {
				Block block = ((MCBlockDefinition) partial.getDefinition()).getInternalBlock();
				if (fruits != null && fruits.containsKey(block)) {
					IFactoryFruit existingFruit = fruits.get(block);
					if (existingFruit instanceof TweakerFruitPartial) {
						TweakerFruitPartial existingFruitPartial = (TweakerFruitPartial) existingFruit;
						if (!existingFruitPartial.fruits.contains(fruit)) {
							existingFruitPartial.fruits.add(fruit);
						}
					} else {
						MineTweakerAPI.logError("A non-MineTweaker fruit already exists for this ID");
					}
				} else {
					TweakerFruitPartial factoryFruit = new TweakerFruitPartial(block);
					MFRRegistry.registerFruit(factoryFruit);
				}
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Map<Block, IFactoryFruit> fruits = MFRRegistry.getFruits();
			for (IBlock partial : fruit.block.getBlocks()) {
				Block block = MineTweakerMC.getBlock(partial);
				IFactoryFruit factoryFruit = fruits.get(block);
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
		private final Map<Block, IFactoryFruit> removed;
		
		public RemoveFruitAction(IBlockPattern block) {
			this.block = block;
			
			Map<Block, IFactoryFruit> fruits = MFRRegistry.getFruits();
			removed = new HashMap<Block, IFactoryFruit>();
			for (IBlock partial : block.getBlocks()) {
				Block mcBlock = MineTweakerMC.getBlock(partial);
				if (fruits.containsKey(mcBlock)) {
					removed.put(mcBlock, fruits.get(mcBlock));
				}
			}
		}
		
		@Override
		public void apply() {
			Map<Block, IFactoryFruit> fruits = MFRRegistry.getFruits();
			for (Block key : removed.keySet()) {
				fruits.remove(key);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Map<Block, IFactoryFruit> fruits = MFRRegistry.getFruits();
			for (Map.Entry<Block, IFactoryFruit> restore : removed.entrySet()) {
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
		private final List<Block> addedLogs = new ArrayList<Block>();
		
		public AddLogAction(IBlockPattern block) {
			this.block = block;
			
			for (IBlock partial : block.getBlocks()) {
				Block mcBlock = MineTweakerMC.getBlock(partial);
				if (!MFRRegistry.getFruitLogBlocks().contains(mcBlock)) {
					if (!addedLogs.contains(mcBlock))
						addedLogs.add(mcBlock);
				}
			}
		}

		@Override
		public void apply() {
			for (Block log : addedLogs) {
				MFRRegistry.registerFruitLogBlock(log);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (Block log : addedLogs) {
				MFRRegistry.getFruitLogBlocks().remove(log);
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
		private final List<Block> removedLogs = new ArrayList<Block>();
		
		public RemoveLogAction(IBlockPattern block) {
			this.block = block;
			
			for (IBlock partial : block.getBlocks()) {
				Block mcBlock = MineTweakerMC.getBlock(partial);
				if (MFRRegistry.getFruitLogBlocks().contains(mcBlock)) {
					if (!removedLogs.contains(mcBlock))
						removedLogs.add(mcBlock);
				}
			}
		}

		@Override
		public void apply() {
			for (Block log : removedLogs) {
				MFRRegistry.getFruitLogBlocks().remove(log);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (Block log : removedLogs) {
				MFRRegistry.registerFruitLogBlock(log);
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
