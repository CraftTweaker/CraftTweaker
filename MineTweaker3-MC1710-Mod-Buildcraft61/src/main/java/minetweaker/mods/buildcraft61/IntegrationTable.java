/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.buildcraft61;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleCrafter;
import buildcraft.api.recipes.IIntegrationRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.buildcraft.IntegrationTable")
@ModOnly(value="BuildCraft|Core", version="6.1")
public class IntegrationTable {
	@ZenMethod
	public static void addRecipe(IItemStack output, double energy, IIngredient inputA, IIngredient inputB, @Optional IIntegrationRecipeFunction function) {
		MineTweakerAPI.apply(new AddRecipeAction(output, energy, inputA, inputB, function));
	}
	
	/*@ZenMethod
	public static void remove(IIngredient output) {
		removeRecipe(output, null, null);
	}*/
	
	/*@ZenMethod
	public static void removeRecipe(IIngredient output, @Optional IIngredient inputA, @Optional IIngredient inputB) {
		IIngredient[] ingredients = null;
		if (inputA != null && inputB != null) {
			ingredients = new IIngredient[] { inputA, inputB };
		} else if (inputA != null) {
			ingredients = new IIngredient[] { inputA };
		} else if (inputB != null) {
			ingredients = new IIngredient[] { inputB };
		}
		
		List<? extends IIntegrationRecipe> recipes = BuildcraftRecipeRegistry.integrationTable.getRecipes();
		List<IFlexibleRecipe<ItemStack>> toRemove = BuildcraftRecipes.removeRecipes(output, ingredients, recipes);
		
		for (IFlexibleRecipe<ItemStack> recipe : toRemove) {
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}*/
	
	// ######################
	// ### Action Classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final MTIntegrationRecipe recipe;
		
		public AddRecipeAction(IItemStack output, double energy, IIngredient inputA, IIngredient inputB, IIntegrationRecipeFunction function) {
			recipe = new MTIntegrationRecipe(output, energy, inputA, inputB, function);
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.integrationTable.addRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.integrationTable.getRecipes().remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding integration table recipe for " + recipe.output;
		}

		@Override
		public String describeUndo() {
			return "Removing integration table recipe for " + recipe.output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	/*
	private static class RemoveRecipeAction implements IUndoableAction {
		private final IFlexibleRecipe<ItemStack> recipe;
		
		public RemoveRecipeAction(IFlexibleRecipe<ItemStack> recipe) {
			this.recipe = recipe;
		}
		
		@Override
		public void apply() {
			BuildcraftRecipeRegistry.integrationTable.getRecipes().remove(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.integrationTable.getRecipes().add((IIntegrationRecipe) recipe);
		}

		@Override
		public String describe() {
			return "Removing integration table recipe for " + MineTweakerMC.getIItemStack(recipe.getComponents()[0]);
		}

		@Override
		public String describeUndo() {
			return "Restoring integration table recipe for " + MineTweakerMC.getIItemStack(recipe.getComponents()[0]);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}*/
	
	private static class MTIntegrationRecipe implements IIntegrationRecipe {
		private static int idCounter = 0;
		
		private final String id;
		private final IItemStack output;
		private final ItemStack[] components;
		private final double energy;
		private final IIngredient inputA;
		private final ItemStack[] inputAExamples;
		private final IIngredient inputB;
		private final ItemStack[] inputBExamples;
		private final IIntegrationRecipeFunction function;
		
		private MTIntegrationRecipe(IItemStack output, double energy, IIngredient inputA, IIngredient inputB, IIntegrationRecipeFunction function) {
			this.id = "mtIntegration" + (idCounter++);
			this.output = output;
			this.components = new ItemStack[] { MineTweakerMC.getItemStack(output) };
			this.energy = energy;
			this.inputA = inputA;
			this.inputAExamples = MineTweakerMC.getExamples(inputA);
			this.inputB = inputB;
			this.inputBExamples = MineTweakerMC.getExamples(inputB);
			this.function = function;
		}
		
		@Override
		public String getId() {
			return id;
		}

		@Override
		public boolean isValidInputA(ItemStack is) {
			return inputA.matches(MineTweakerMC.getIItemStack(is));
		}

		@Override
		public boolean isValidInputB(ItemStack is) {
			return inputB.matches(MineTweakerMC.getIItemStack(is));
		}

		/*@Override
		public ItemStack getOutputForInputs(ItemStack is, ItemStack is1, ItemStack[] iss) {
			if (function != null) {
				IItemStack actualInputA = MineTweakerMC.getIItemStack(is);
				IItemStack actualInputB = MineTweakerMC.getIItemStack(is1);
				return MineTweakerMC.getItemStack(function.recipe(output, actualInputA, actualInputB));
			} else {
				return MineTweakerMC.getItemStack(output);
			}
		}*/

		@Override
		public boolean canBeCrafted(IFlexibleCrafter ifc)
		{
			return inputA.matches(MineTweakerMC.getIItemStack(ifc.getCraftingItemStack(0)))
					&& (inputB == null || inputB.matches(MineTweakerMC.getIItemStack(ifc.getCraftingItemStack(1))));
		}

		@Override
		public CraftingResult<ItemStack> craft(IFlexibleCrafter ifc, boolean bln)
		{
			if (!canBeCrafted(ifc))
				return null;
			
			CraftingResult<ItemStack> result = new CraftingResult<ItemStack>();
			result.crafted = MineTweakerMC.getItemStack(output);
			
			ItemStack consumedA = new ItemStack(ifc.getCraftingItemStack(0).getItem(), inputA.getAmount(), ifc.getCraftingItemStack(0).getItemDamage());
			consumedA.setTagCompound(ifc.getCraftingItemStack(0).stackTagCompound);
			result.usedItems.add(consumedA);
			
			if (inputB != null) {
				ItemStack consumedB = new ItemStack(ifc.getCraftingItemStack(1).getItem(), inputB.getAmount(), ifc.getCraftingItemStack(0).getItemDamage());
				consumedB.setTagCompound(ifc.getCraftingItemStack(1).stackTagCompound);
				result.usedItems.add(consumedB);
			}
			
			return result;
		}

		@Override
		public CraftingResult<ItemStack> canCraft(ItemStack is)
		{
			if (inputB != null)
				return null;
			
			if (!inputA.matches(MineTweakerMC.getIItemStack(is)))
				return null;
			
			CraftingResult<ItemStack> result = new CraftingResult<ItemStack>();
			result.crafted = MineTweakerMC.getItemStack(output);
			ItemStack consumed = new ItemStack(is.getItem(), inputA.getAmount(), is.getItemDamage());
			consumed.setTagCompound(is.stackTagCompound);
			result.usedItems.add(consumed);
			return result;
		}
	}
}
