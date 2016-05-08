/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import java.util.Random;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;
import minetweaker.mods.mfr.FertilizableType;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.Fertilizer")
@ModOnly("MineFactoryReloaded")
public class Fertilizer {
	@ZenMethod
	public static void addFertilizable(IItemStack block, String fertilizeType, @Optional String method, @Optional IItemStack replacement) {
		FertilizerType fertilizer;
		if (fertilizeType.equals("any")) {
			fertilizer = null;
		} else if (fertilizeType.equals("normal")) {
			fertilizer = FertilizerType.GrowPlant;
		} else if (fertilizeType.equals("grass")) {
			fertilizer = FertilizerType.Grass;
		} else if (fertilizeType.equals("magic")) {
			fertilizer = FertilizerType.GrowMagicalCrop;
		} else {
			throw new IllegalArgumentException("Unknown fertilizer type: " + fertilizeType);
		}
		
		FertilizableType type = FertilizableType.BONEMEAL;
		
		if (method == null || method.equals("bonemeal")) {
			type = FertilizableType.BONEMEAL;
		} else if (method.equals("replace")) {
			type = FertilizableType.REPLACE;
			if (replacement == null)
				throw new IllegalArgumentException("replace method requires a replacement block");
		} else {
			throw new IllegalArgumentException("Unknown fertilization method: " + method);
		}
		
		MineTweakerAPI.apply(new FertilizerAddFertilizableAction(block, replacement, fertilizer, type));
	}
	
	@ZenMethod
	public static void removeFertilizable(IItemStack item) {
		MineTweakerAPI.apply(new FertilizerRemoveFertilizableAction(item));
	}
	
	@ZenMethod
	public static void addFertilizer(IItemStack item, String type) {
		FertilizerType fertilizerType;
		if (type.equals("grass")) {
			fertilizerType = FertilizerType.Grass;
		} else if (type.equals("normal")) {
			fertilizerType = FertilizerType.GrowPlant;
		} else if (type.equals("magic")) {
			fertilizerType = FertilizerType.GrowMagicalCrop;
		} else {
			throw new IllegalArgumentException("Unknown fertilizer type: " + type);
		}
		
		MineTweakerAPI.apply(new FertilizerAddFertilizerAction(item, fertilizerType));
	}
	
	@ZenMethod
	public static void removeFertilizer(IItemStack item) {
		MineTweakerAPI.apply(new FertilizerRemoveFertilizerAction(item));
	}
	
	// ######################
	// ### Action Classes ###
	// ######################
	
	public static class FertilizerAddFertilizableAction implements IUndoableAction {
		private final IItemStack block;
		private final IItemStack plant;
		private final FertilizerType type;
		private final FertilizableType method;

		private final IFactoryFertilizable old;

		public FertilizerAddFertilizableAction(IItemStack block, IItemStack plant, FertilizerType type, FertilizableType method) {
			this.block = block;
			this.plant = plant;
			this.type = type;
			this.method = method;
			
			old = MFRRegistry.getFertilizables().get(MineTweakerMC.getBlock(block));
		}

		@Override
		public void apply() {
			MFRRegistry.registerFertilizable(new SimpleFertilizable(block, plant, type));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (old == null) {
				MFRRegistry.getFertilizables().remove(MineTweakerMC.getBlock(block));
			} else {
				MFRRegistry.registerFertilizable(old);
			}
		}

		@Override
		public String describe() {
			return "Making " + block.getDisplayName() + " fertilizable";
		}

		@Override
		public String describeUndo() {
			if (old == null) {
				return "Removing fertilizable " + block.getDisplayName();
			} else {
				return "Restoring fertilizable " + block.getDisplayName();
			}
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class FertilizerAddFertilizerAction implements IUndoableAction {
		private final IItemStack item;
		private final FertilizerType type;

		private final IFactoryFertilizer old;

		public FertilizerAddFertilizerAction(IItemStack item, FertilizerType type) {
			this.item = item;
			this.type = type;

			old = MFRRegistry.getFertilizers().get(MineTweakerMC.getItemStack(item).getItem());
		}

		@Override
		public void apply() {
			MFRRegistry.registerFertilizer(new SimpleFertilizer(MineTweakerMC.getItemStack(item), type));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (old == null) {
				MFRRegistry.getFertilizers().remove(MineTweakerMC.getItemStack(item).getItem());
			} else {
				MFRRegistry.registerFertilizer(old);
			}
		}

		@Override
		public String describe() {
			return "Adding fertilizer " + item.getDisplayName();
		}

		@Override
		public String describeUndo() {
			if (old == null) {
				return "Removing fertilizer " + item.getDisplayName();
			} else {
				return "Restoring fertilizer " + item.getDisplayName();
			}
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	public static class FertilizerRemoveFertilizableAction implements IUndoableAction {
		private final IItemStack item;
		private final IFactoryFertilizable old;

		public FertilizerRemoveFertilizableAction(IItemStack item) {
			this.item = item;
			old = MFRRegistry.getFertilizables().get(MineTweakerMC.getBlock(item));
		}

		@Override
		public void apply() {
			MFRRegistry.getFertilizables().remove(MineTweakerMC.getBlock(item));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.registerFertilizable(old);
		}

		@Override
		public String describe() {
			return "Removing fertilizable " + item.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring fertilizable " + item.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	public static class FertilizerRemoveFertilizerAction implements IUndoableAction {
		private final IItemStack item;
		private final IFactoryFertilizer old;

		public FertilizerRemoveFertilizerAction(IItemStack item) {
			this.item = item;
			this.old = MFRRegistry.getFertilizers().get(MineTweakerMC.getItemStack(item).getItem());
		}

		@Override
		public void apply() {
			MFRRegistry.getFertilizers().remove(MineTweakerMC.getItemStack(item).getItem());
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.registerFertilizer(old);
		}

		@Override
		public String describe() {
			return "Removing fertilizer " + item.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring fertilizer " + item.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class SimpleFertilizable implements IFactoryFertilizable {
		private final IItemStack block;
		private final IItemStack plant;
		private final FertilizerType type;

		public SimpleFertilizable(IItemStack block, IItemStack plant, FertilizerType type) {
			this.block = block;
			this.plant = plant;
			this.type = type;
		}

		@Override
		public Block getPlant() {
			return MineTweakerMC.getBlock(block);
		}

		@Override
		public boolean canFertilize(World world, int x, int y, int z, FertilizerType fertilizerType) {
			if (this.type != null && type != fertilizerType)
				return false;
			
			IItemStack blockAt = MineTweakerMC.getIItemStack(new ItemStack(world.getBlock(x, y, z), 1, world.getBlockMetadata(x, y, z)));
			return block.matches(blockAt);
		}

		@Override
		public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType) {
			if (plant == null) {
				return ItemDye.applyBonemeal(
						new ItemStack((Item) Item.itemRegistry.getObject("minecraft:dyePowder"), 1, 15),
						world,
						x, y, z,
						null);
			} else {
				ItemStack istack = MineTweakerMC.getItemStack(plant);
				world.setBlock(x, y, z, Block.getBlockFromItem(istack.getItem()), plant.getDamage(), 0);
				return true;
			}
		}
	}
	
	private static class SimpleFertilizer implements IFactoryFertilizer {
		private final ItemStack item;
		private final FertilizerType type;
		
		public SimpleFertilizer(ItemStack item, FertilizerType type) {
			this.item = item;
			this.type = type;
		}

		@Override
		public Item getFertilizer() {
			return item.getItem();
		}

		@Override
		public FertilizerType getFertilizerType(ItemStack is) {
			return type;
		}

		@Override
		public void consume(ItemStack fertilizer) {
			fertilizer.stackSize--;
		}
	}
}
