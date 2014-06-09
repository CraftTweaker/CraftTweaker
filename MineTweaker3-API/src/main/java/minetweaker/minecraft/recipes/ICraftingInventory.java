/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.recipes;

import minetweaker.minecraft.item.IItemStack;

/**
 *
 * @author Stan
 */
public interface ICraftingInventory {
	public int getSize();
	
	public int getWidth();
	
	public int getHeight();
	
	public IItemStack getStack(int i);
	
	public IItemStack getStack(int x, int y);
	
	public void setStack(int x, int y, IItemStack stack);
	
	public void setStack(int i, IItemStack stack);
}
