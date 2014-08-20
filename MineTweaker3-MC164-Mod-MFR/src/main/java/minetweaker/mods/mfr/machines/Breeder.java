/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import static minetweaker.mc164.util.MineTweakerPlatformUtils.getLivingEntityClass;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.Breeder")
@ModOnly("MineFactoryReloaded")
public class Breeder {
	@ZenMethod
	public static void addFood(String entityClassName, IItemStack item) {
		Class<?> entityClass = getLivingEntityClass(entityClassName);
		MineTweakerAPI.apply(new BreederAddFoodAction(entityClass, item));
	}
	
	@ZenMethod
	public static void removeFood(String entityClassName, IIngredient item) {
		Class<?> entityClass = getLivingEntityClass(entityClassName);
		
		List<Integer> toRemove = new ArrayList<Integer>();
		List<ItemStack> foods = MFRRegistry.getBreederFoods().get(entityClass);

		for (int i = foods.size() - 1; i >= 0; i--) {
			if (item.matches(MineTweakerMC.getIItemStack(foods.get(i)))) {
				toRemove.add(i);
			}
		}
		for (int i : toRemove) {
			MineTweakerAPI.apply(new BreederRemoveFoodAction(entityClass, i));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class BreederAddFoodAction implements IUndoableAction {
		private final Class<?> entityClass;
		private final IItemStack item;

		public BreederAddFoodAction(Class<?> entityClass, IItemStack item) {
			this.entityClass = entityClass;
			this.item = item;
		}

		@Override
		public void apply() {
			FactoryRegistry.registerBreederFood(entityClass, MineTweakerMC.getItemStack(item));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			List<ItemStack> list = MFRRegistry.getBreederFoods().get(entityClass);
			list.remove(list.size() - 1);
		}

		@Override
		public String describe() {
			return "Adding breeder food " + item.getDisplayName() + " for " + entityClass.getCanonicalName();
		}

		@Override
		public String describeUndo() {
			return "Removing breeder food " + item.getDisplayName() + " for " + entityClass.getCanonicalName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class BreederRemoveFoodAction implements IUndoableAction {
		private final Class<?> entityClass;
		private final int index;
		private final ItemStack item;
		
		public BreederRemoveFoodAction(Class<?> entityClass, int index) {
			this.entityClass = entityClass;
			this.index = index;
			item = MFRRegistry.getBreederFoods().get(entityClass).get(index);
		}

		@Override
		public void apply() {
			MFRRegistry.getBreederFoods().get(entityClass).remove(index);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getBreederFoods().get(entityClass).add(index, item);
		}

		@Override
		public String describe() {
			return "Removing breeder food " + item.getDisplayName() + " for " + entityClass.getCanonicalName();
		}

		@Override
		public String describeUndo() {
			return "Restoring breeder food " + item.getDisplayName() + " for " + entityClass.getCanonicalName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
