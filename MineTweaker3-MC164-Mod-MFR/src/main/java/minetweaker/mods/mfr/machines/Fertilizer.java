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
import minetweaker.mods.mfr.MFRHacks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;
import stanhebben.minetweaker.mods.mfr.FertilizableType;
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
	public void addFertilizable(IItemStack block, String fertilizeType, @Optional String method, @Optional IItemStack replacement) {
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
	public void removeFertilizable(IItemStack item) {
		MineTweakerAPI.apply(new FertilizerRemoveFertilizableAction(item));
	}
	
	@ZenMethod
	public void addFertilizer(IItemStack item, String type) {
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
	public void remmoveFertilizer(IItemStack item) {
		MineTweakerAPI.apply(new FertilizerRemoveFertilizerAction(item));
	}
	
	// ######################
	// ### Action Classes ###
	// ######################
	
	public class FertilizerAddFertilizableAction implements IUndoableAction {
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
			
			old = MFRHacks.fertilizables == null ? null : MFRHacks.fertilizables.get(MineTweakerMC.getItemStack(block).itemID);
		}

		@Override
		public void apply() {
			FactoryRegistry.registerFertilizable(new SimpleFertilizable(block, plant, type));
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.fertilizables != null;
		}

		@Override
		public void undo() {
			if (old == null) {
				MFRHacks.fertilizables.remove(MineTweakerMC.getItemStack(block).itemID);
			} else {
				FactoryRegistry.registerFertilizable(old);
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

			old = MFRHacks.fertilizers == null ? null : MFRHacks.fertilizers.get(MineTweakerMC.getItemStack(item).itemID);
		}

		@Override
		public void apply() {
			FactoryRegistry.registerFertilizer(new SimpleFertilizer(MineTweakerMC.getItemStack(item), type));
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.fertilizers != null;
		}

		@Override
		public void undo() {
			if (old == null) {
				MFRHacks.fertilizers.remove(MineTweakerMC.getItemStack(item).itemID);
			} else {
				FactoryRegistry.registerFertilizer(old);
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
	
	public class FertilizerRemoveFertilizableAction implements IUndoableAction {
		private final IItemStack item;
		private final IFactoryFertilizable old;

		public FertilizerRemoveFertilizableAction(IItemStack item) {
			this.item = item;
			old = MFRHacks.fertilizables.get(MineTweakerMC.getItemStack(item).itemID);
		}

		@Override
		public void apply() {
			MFRHacks.fertilizables.remove(MineTweakerMC.getItemStack(item).itemID);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			FactoryRegistry.registerFertilizable(old);
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
	
	public class FertilizerRemoveFertilizerAction implements IUndoableAction {
		private final IItemStack item;
		private final IFactoryFertilizer old;

		public FertilizerRemoveFertilizerAction(IItemStack item) {
			this.item = item;
			this.old = MFRHacks.fertilizers.get(MineTweakerMC.getItemStack(item).itemID);
		}

		@Override
		public void apply() {
			MFRHacks.fertilizers.remove(MineTweakerMC.getItemStack(item).itemID);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			FactoryRegistry.registerFertilizer(old);
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
		public int getFertilizableBlockId() {
			return MineTweakerMC.getItemStack(block).itemID;
		}

		@Override
		public boolean canFertilizeBlock(World world, int x, int y, int z, FertilizerType fertilizerType) {
			if (this.type != null && type != fertilizerType)
				return false;
			
			IItemStack blockAt = MineTweakerMC.getIItemStack(new ItemStack(world.getBlockId(x, y, z), 1, world.getBlockMetadata(x, y, z)));
			return block.matches(blockAt);
		}

		@Override
		public boolean fertilize(World world, Random rand, int x, int y, int z, FertilizerType fertilizerType) {
			if (plant == null) {
				return ItemDye.applyBonemeal(new ItemStack(Item.dyePowder.itemID, 1, 15), world, x, y, z, null);
			} else {
				ItemStack istack = MineTweakerMC.getItemStack(plant);
				world.setBlock(x, y, z, istack.itemID, plant.getDamage(), 0);
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
		public int getFertilizerId() {
			return item.itemID;
		}

		@Override
		public int getFertilizerMeta() {
			return item.getItemDamage();
		}

		@Override
		public FertilizerType getFertilizerType() {
			return type;
		}

		@Override
		public void consume(ItemStack fertilizer) {
			fertilizer.stackSize--;
		}
	}
}
