/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minetweaker.mods.buildcraft61;

import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleCrafter;
import buildcraft.api.recipes.IFlexibleRecipe;
import java.util.ArrayList;
import java.util.List;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc1710.liquid.MCLiquidStack;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class MTFlexibleRecipe implements IFlexibleRecipe<ItemStack>
{
	private static int idCounter = 0;
	
	private final String id;
	private final IItemStack output;
	private final IIngredient[] inputs;
	private final int energyCost;
	private final long craftingTime;
	
	public MTFlexibleRecipe(IItemStack output, IIngredient[] inputs, int energyCost, long craftingTime)
	{
		if (inputs.length == 0)
			throw new IllegalArgumentException("recipe must have at least 1 input");
		
		this.id = "minetweaker" + (idCounter++);
		this.output = output;
		this.inputs = inputs;
		this.energyCost = energyCost;
		this.craftingTime = craftingTime;
	}
	
	@Override
	public boolean canBeCrafted(IFlexibleCrafter ifc)
	{
		List<IItemStack> items = new ArrayList<IItemStack>();
		for (int i = 0; i < ifc.getCraftingItemStackSize(); i++) {
			if (ifc.getCraftingItemStack(i) != null)
				items.add(MineTweakerMC.getIItemStack(ifc.getCraftingItemStack(i)));
		}
		
		List<ILiquidStack> liquids = new ArrayList<ILiquidStack>();
		for (int i = 0; i < ifc.getCraftingFluidStackSize(); i++) {
			if (ifc.getCraftingFluidStack(i) != null)
				liquids.add(new MCLiquidStack(ifc.getCraftingFluidStack(i)));
		}
		
		outer: for (IIngredient ingredient : inputs) {
			for (IItemStack item : items) {
				if (ingredient.matches(item))
					continue outer;
			}
			
			for (ILiquidStack liquid : liquids) {
				if (ingredient.matches(liquid))
					continue outer;
			}
			
			return false;
		}
		
		return true;
	}

	@Override
	public CraftingResult<ItemStack> craft(IFlexibleCrafter ifc, boolean preview)
	{
		CraftingResult<ItemStack> result = new CraftingResult<ItemStack>();
		
		List<IItemStack> items = new ArrayList<IItemStack>();
		for (int i = 0; i < ifc.getCraftingItemStackSize(); i++) {
			if (ifc.getCraftingItemStack(i) != null)
				items.add(MineTweakerMC.getIItemStack(ifc.getCraftingItemStack(i)));
		}
		
		List<ILiquidStack> liquids = new ArrayList<ILiquidStack>();
		for (int i = 0; i < ifc.getCraftingFluidStackSize(); i++) {
			if (ifc.getCraftingFluidStack(i) != null)
				liquids.add(new MCLiquidStack(ifc.getCraftingFluidStack(i)));
		}
		
		result.energyCost = energyCost;
		result.craftingTime = craftingTime;
		
		outer: for (IIngredient ingredient : inputs) {
			for (IItemStack item : items) {
				if (ingredient.matches(item)) {
					result.usedItems.add(MineTweakerMC.getItemStack(item.withAmount(ingredient.getAmount())));
					continue outer;
				}
			}
			
			for (ILiquidStack liquid : liquids) {
				if (ingredient.matches(liquid)) {
					result.usedFluids.add(MineTweakerMC.getLiquidStack(liquid.withAmount(ingredient.getAmount())));
					continue outer;
				}
			}
			
			return null;
		}
		
		return result;
	}

	@Override
	public CraftingResult<ItemStack> canCraft(ItemStack is)
	{
		if (!output.matches(MineTweakerMC.getIItemStack(is)))
			return null;
		
		/*if (inputs.length > 1)
			return null;
		
		if (!inputs[0].matches(MineTweakerMC.getIItemStack(is)))
			return null;*/
		
		CraftingResult<ItemStack> result = new CraftingResult<ItemStack>();
		result.crafted = MineTweakerMC.getItemStack(output);
		ItemStack consumed = new ItemStack(is.getItem(), inputs[0].getAmount(), is.getItemDamage());
		consumed.setTagCompound(is.stackTagCompound);
		result.usedItems.add(consumed);
		return result;
	}

	@Override
	public String getId()
	{
		return id;
	}
}
