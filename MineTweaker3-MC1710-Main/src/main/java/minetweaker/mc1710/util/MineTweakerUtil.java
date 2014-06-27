/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.util;

import java.util.List;
import minetweaker.api.item.IItemStack;
import minetweaker.mc1710.item.MCItemStack;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class MineTweakerUtil {
	public static ItemStack[] getItemStacks(IItemStack... items) {
		ItemStack[] output = new ItemStack[items.length];
		for (int i = 0; i < items.length; i++) {
			Object internal = items[i].getInternal();
			if (internal != null && internal instanceof ItemStack) {
				output[i] = (ItemStack) internal;
			}
		}
		return output;
	}
	
	public static ItemStack[] getItemStacks(List<IItemStack> items) {
		ItemStack[] output = new ItemStack[items.size()];
		for (int i = 0; i < items.size(); i++) {
			Object internal = items.get(i).getInternal();
			if (internal != null && internal instanceof ItemStack) {
				output[i] = (ItemStack) internal;
			}
		}
		return output;
	}
	
	public static IItemStack[] getIItemStacks(ItemStack... items) {
		IItemStack[] result = new IItemStack[items.length];
		for (int i = 0; i < items.length; i++) {
			result[i] = new MCItemStack(items[i]);
		}
		return result;
	}
	
	public static IItemStack[] getIItemStacks(List<ItemStack> items) {
		IItemStack[] result = new IItemStack[items.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = new MCItemStack(items.get(i));
		}
		return result;
	}
}
