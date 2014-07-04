/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.furnace;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getIItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStacks;
import minetweaker.api.recipes.IFurnaceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class MCFurnaceManager implements IFurnaceManager {
	public MCFurnaceManager() {
		
	}
	
	@Override
	public void remove(IIngredient output, IIngredient input) {
		if (output == null) throw new IllegalArgumentException("output cannot be null");
		
		Map<Integer, ItemStack> smeltingList = FurnaceRecipes.smelting().getSmeltingList();
		Map<List<Integer>, ItemStack> metaSmeltingList = FurnaceRecipes.smelting().getMetaSmeltingList();
		
		List<Integer> toRemove = new ArrayList<Integer>();
		List<List<Integer>> toRemoveMeta = new ArrayList<List<Integer>>();
		List<ItemStack> toRemoveValues = new ArrayList<ItemStack>();
		List<ItemStack> toRemoveMetaValues = new ArrayList<ItemStack>();
		
		for (Map.Entry<Integer, ItemStack> entry : smeltingList.entrySet()) {
			if (output.matches(getIItemStack(entry.getValue()))
					&& (input == null || input.matches(getIItemStack(new ItemStack(entry.getKey(), 1, 0))))) {
				toRemove.add(entry.getKey());
				toRemoveValues.add(entry.getValue());
			}
		}
		
		for (Map.Entry<List<Integer>, ItemStack> entry : metaSmeltingList.entrySet()) {
			if (output.matches(getIItemStack(entry.getValue()))
					&& (input == null || input.matches(getIItemStack(new ItemStack(entry.getKey().get(0), 1, entry.getKey().get(1)))))) {
				toRemoveMeta.add(entry.getKey());
				toRemoveMetaValues.add(entry.getValue());
			}
		}
		
		if (toRemove.isEmpty() && toRemoveMeta.isEmpty()) {
			MineTweakerAPI.getLogger().logWarning("No furnace recipes for " + output.toString());
		} else {
			MineTweakerAPI.tweaker.apply(new RemoveAction(toRemove, toRemoveValues, toRemoveMeta, toRemoveMetaValues));
		}
	}

	@Override
	public void addRecipe(IItemStack output, IIngredient input, double xp) {
		List<IItemStack> items = input.getItems();
		if (items == null) {
			MineTweakerAPI.getLogger().logError("Cannot turn " + input.toString() + " into a furnace recipe");
		}
		
		ItemStack[] items2 = getItemStacks(items);
		ItemStack output2 = getItemStack(output);
		MineTweakerAPI.tweaker.apply(new AddRecipeAction(input, items2, output2, xp));
	}

	@Override
	public void setFuel(IIngredient item, int fuel) {
		MineTweakerAPI.tweaker.apply(new SetFuelAction(new SetFuelPattern(item, fuel)));
	}

	@Override
	public int getFuel(IItemStack item) {
		return GameRegistry.getFuelValue(getItemStack(item));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class RemoveAction implements IUndoableAction {
		private final List<Integer> items;
		private final List<ItemStack> values;
		private final List<List<Integer>> metaItems;
		private final List<ItemStack> metaValues;
		
		public RemoveAction(List<Integer> items, List<ItemStack> values, List<List<Integer>> metaItems, List<ItemStack> metaValues) {
			this.items = items;
			this.values = values;
			this.metaItems = metaItems;
			this.metaValues = metaValues;
		}
		
		@Override
		public void apply() {
			for (Integer item : items) {
				FurnaceRecipes.smelting().getSmeltingList().remove(item);
			}
			for (List<Integer> item : metaItems) {
				FurnaceRecipes.smelting().getMetaSmeltingList().remove(item);
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
			for (int i = 0; i < metaItems.size(); i++) {
				FurnaceRecipes.smelting().getMetaSmeltingList().put(metaItems.get(i), metaValues.get(i));
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

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddRecipeAction implements IUndoableAction {
		private final IIngredient ingredient;
		private final ItemStack[] input;
		private final ItemStack output;
		private final double xp;
		
		public AddRecipeAction(IIngredient ingredient, ItemStack[] input, ItemStack output, double xp) {
			this.ingredient = ingredient;
			this.input = input;
			this.output = output;
			this.xp = xp;
		}

		@Override
		public void apply() {
			for (ItemStack inputStack : input) {
				if (inputStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
					FurnaceRecipes.smelting().addSmelting(inputStack.itemID, output, (float) xp);
				} else {
					FurnaceRecipes.smelting().addSmelting(inputStack.itemID, inputStack.getItemDamage(), output, (float) xp);
				}
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (ItemStack inputStack : input) {
				FurnaceRecipes.smelting().getSmeltingList().remove(inputStack);
			}
		}

		@Override
		public String describe() {
			return "Adding furnace recipe for " + ingredient;
		}

		@Override
		public String describeUndo() {
			return "Removing furnace recipe for " + ingredient;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class SetFuelAction implements IUndoableAction {
		private final SetFuelPattern pattern;
		
		public SetFuelAction(SetFuelPattern pattern) {
			this.pattern = pattern;
		}
		
		@Override
		public void apply() {
			FuelTweaker.INSTANCE.addFuelPattern(pattern);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			FuelTweaker.INSTANCE.removeFuelPattern(pattern);
		}

		@Override
		public String describe() {
			return "Setting fuel for " + pattern.getPattern();
		}

		@Override
		public String describeUndo() {
			return "Removing fuel for " + pattern.getPattern();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
