package minetweaker.mc11.recipes;

import minetweaker.mc11.util.MineTweakerHacks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class ProtectedInventoryCrafting extends InventoryCrafting {
	
	private InventoryCrafting protectee;
	private NonNullList<ItemStack> protectionStacks;
	private boolean[] isProtected;
	
	
	public ProtectedInventoryCrafting(InventoryCrafting protectee) {
		super(MineTweakerHacks.getCraftingContainer(protectee), protectee.getWidth(), protectee.getHeight());
		this.protectee = protectee;
		this.protectionStacks = NonNullList.withSize(protectee.getSizeInventory(), ItemStack.EMPTY);
		this.isProtected = new boolean[protectee.getSizeInventory()];
	}
	
	
	/**
	 * Returns the stack in the given slot.
	 */
	@Nullable
	protected ItemStack getProtecteeInSlot(int index) {
		ItemStack item = protectee.getStackInSlot(index);
		return item == null ? null : item.copy();
	}
	
	/**
	 * Returns the stack in the given slot.
	 */
	@Nullable
	public ItemStack getStackInSlot(int index) {
		if(index >= this.getSizeInventory())
			return null;
		if(this.isProtected[index])
			return this.protectionStacks.get(index);
		return getProtecteeInSlot(index);
	}
	
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.protectionStacks.set(index, (stack == null ? null : stack.copy()));
		this.isProtected[index] = true;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		if(index < 0 || index >= protectionStacks.size())
			return null;
		
		ItemStack itemstack = getProtecteeInSlot(index);
		setInventorySlotContents(index, null);
		
		return itemstack;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		setInventorySlotContents(index, getProtecteeInSlot(index));
		return ItemStackHelper.getAndSplit(this.protectionStacks, index, count);
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < this.protectionStacks.size(); ++i) {
			setInventorySlotContents(i, null);
		}
	}
	
	public NonNullList<ItemStack> getProtectionStacks() {
		return protectionStacks;
	}
}
