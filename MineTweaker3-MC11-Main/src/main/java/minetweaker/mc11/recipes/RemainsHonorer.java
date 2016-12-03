package minetweaker.mc11.recipes;

import minetweaker.api.recipes.ICraftingRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RemainsHonorer implements IRecipe {
	
	private IRecipe recipe;
	private ICraftingRecipe toHonor;
	
	public RemainsHonorer(IRecipe recipe, ICraftingRecipe toHonor) {
		if(recipe == null || toHonor == null) {
			throw new NullPointerException();
		}
		this.recipe = recipe;
		this.toHonor = toHonor;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return recipe.matches(inv, worldIn);
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return recipe.getCraftingResult(inv);
	}
	
	@Override
	public int getRecipeSize() {
		return recipe.getRecipeSize();
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		return recipe.getRecipeOutput();
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		
		// transformers should not have access to player / inventory in the first place
		if(toHonor.hasTransformers()) {
			ProtectedInventoryCrafting protectedInventory = new ProtectedInventoryCrafting(inv);
			
			MCCraftingInventory craftingMatrix = MCCraftingInventory.get(protectedInventory);
			toHonor.applyTransformers(craftingMatrix, craftingMatrix.getPlayer());
			
			return protectedInventory.getProtectionStacks();
		} else {
			return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY); //recipe.getRemainingItems(inv); enable if unbottling is desired
		}
	}
	
}
