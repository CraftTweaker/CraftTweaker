/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc11.furnace;

import minetweaker.*;
import minetweaker.api.item.*;
import minetweaker.api.recipes.*;
import minetweaker.mc11.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.stream.Collectors;

import static minetweaker.api.minecraft.MineTweakerMC.*;

/**
 * @author Stan
 */
public class MCFurnaceManager implements IFurnaceManager {
	
	public MCFurnaceManager() {
		
	}
	
	@Override
	public void remove(IIngredient output, @Optional IIngredient input) {
		if(output == null)
			throw new IllegalArgumentException("output cannot be null");
		
		Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();
		
		List<ItemStack> toRemove = new ArrayList<>();
		List<ItemStack> toRemoveValues = new ArrayList<>();
		smeltingList.entrySet().stream().filter(entry -> output.matches(new MCItemStack(entry.getValue())) && (input == null || input.matches(new MCItemStack(entry.getKey())))).forEach(entry -> {
			toRemove.add(entry.getKey());
			toRemoveValues.add(entry.getValue());
		});
		
		if(toRemove.isEmpty()) {
			MineTweakerAPI.logWarning("No furnace recipes for " + output.toString());
		} else {
			MineTweakerAPI.apply(new RemoveAction(toRemove, toRemoveValues));
		}
	}
	
	@Override
	public void addRecipe(IItemStack output, IIngredient input, @Optional double xp) {
		List<IItemStack> items = input.getItems();
		if(items == null) {
			MineTweakerAPI.logError("Cannot turn " + input.toString() + " into a furnace recipe");
		}
		
		ItemStack[] items2 = getItemStacks(items);
		ItemStack output2 = getItemStack(output);
		MineTweakerAPI.logInfo("Adding recipe for: " + output.toString());
		MineTweakerAPI.apply(new AddRecipeAction(input, items2, output2, xp));
	}
	
	@Override
	public void setFuel(IIngredient item, int fuel) {
		MineTweakerAPI.apply(new SetFuelAction(new SetFuelPattern(item, fuel)));
	}
	
	@Override
	public int getFuel(IItemStack item) {
		return GameRegistry.getFuelValue(getItemStack(item));
	}
	
	@Override
	public List<IFurnaceRecipe> getAll() {
		return FurnaceRecipes.instance().getSmeltingList().entrySet().stream().map(ent -> new FurnaceRecipe(new MCItemStack(ent.getKey()), new MCItemStack(ent.getValue()), FurnaceRecipes.instance().getSmeltingExperience(ent.getValue()))).collect(Collectors.toList());
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
			for(ItemStack item : items) {
				FurnaceRecipes.instance().getSmeltingList().remove(item);
			}
		}
		
		@Override
		public boolean canUndo() {
			return true;
		}
		
		@Override
		public void undo() {
			for(int i = 0; i < items.size(); i++) {
				FurnaceRecipes.instance().getSmeltingList().put(items.get(i), values.get(i));
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
			for(ItemStack inputStack : input) {
				MineTweakerAPI.logInfo("adding recipe using: " + inputStack.toString());
				FurnaceRecipes.instance().addSmeltingRecipe(inputStack, output, (float) xp);
			}
		}
		
		@Override
		public boolean canUndo() {
			return true;
		}
		
		@Override
		public void undo() {
			for(ItemStack inputStack : input) {
				FurnaceRecipes.instance().getSmeltingList().remove(inputStack);
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
