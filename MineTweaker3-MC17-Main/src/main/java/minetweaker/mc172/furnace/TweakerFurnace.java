/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.furnace;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.recipes.IFurnaceManager;
import minetweaker.mc172.item.TweakerItemStack;
import minetweaker.mc172.util.MineTweakerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 *
 * @author Stan
 */
public class TweakerFurnace implements IFurnaceManager {
	public TweakerFurnace() {
		
	}
	
	@Override
	public void remove(IIngredient ingredient) {
		Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.smelting().getSmeltingList();
		
		List<ItemStack> toRemove = new ArrayList<ItemStack>();
		List<ItemStack> toRemoveValues = new ArrayList<ItemStack>();
		for (Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
			if (ingredient.matches(new TweakerItemStack(entry.getValue()))) {
				toRemove.add(entry.getKey());
				toRemoveValues.add(entry.getValue());
			}
		}
		
		if (toRemove.isEmpty()) {
			MineTweakerAPI.logger.logWarning("No furnace recipes for " + ingredient.toString());
		} else {
			MineTweakerAPI.tweaker.apply(new RemoveAction(toRemove, toRemoveValues));
		}
	}

	@Override
	public void addRecipe(IItemStack output, IIngredient input, double xp) {
		List<IItemStack> items = input.getItems();
		if (items == null) {
			MineTweakerAPI.logger.logError("Cannot turn " + input.toString() + " into a furnace recipe");
		}
		
		ItemStack[] items2 = MineTweakerUtil.getItemStacks(items);
		ItemStack output2 = (ItemStack) output.getInternal();
		MineTweakerAPI.tweaker.apply(new AddRecipeAction(items2, output2, xp));
	}

	@Override
	public void setFuel(IIngredient item, int fuel) {
		// TODO: complete this method
	}

	@Override
	public int getFuel(IItemStack item) {
		return GameRegistry.getFuelValue((ItemStack) item.getInternal());
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class RemoveAction implements IUndoableAction {
		private final List<ItemStack> items;
		private final List<ItemStack> values;
		
		public RemoveAction(List<ItemStack> items, List<ItemStack> values) {
			this.items = items;
			this.values = values;
		}
		
		@Override
		public void apply() {
			for (ItemStack item : items) {
				FurnaceRecipes.smelting().getSmeltingList().remove(item);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (int i = 0; i < items.size(); i++) {
				FurnaceRecipes.smelting().getSmeltingList().put(items.get(i), values.get(i));
			}
		}

		@Override
		public String describe() {
			return "Removing " + items.size() + " furnace recipes";
		}

		@Override
		public String describeUndo() {
			return "Restoring " + items.size() + " furnace recipes";
		}
	}
	
	private static class AddRecipeAction implements IUndoableAction {
		private final ItemStack[] input;
		private final ItemStack output;
		private final double xp;
		
		public AddRecipeAction(ItemStack[] input, ItemStack output, double xp) {
			this.input = input;
			this.output = output;
			this.xp = xp;
		}

		@Override
		public void apply() {
			for (ItemStack inputStack : input) {
				FurnaceRecipes.smelting().func_151396_a(inputStack.getItem(), output, (float) xp);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			// TODO
		}

		@Override
		public String describe() {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public String describeUndo() {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}
	}
}
