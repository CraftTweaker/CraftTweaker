/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.util;

import minetweaker.minecraft.item.IItemStack;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class MineTweakerUtil {
	public static ItemStack[] fromArray(IItemStack[] items) {
		ItemStack[] output = new ItemStack[items.length];
		for (int i = 0; i < items.length; i++) {
			Object internal = items[i].getInternal();
			if (internal != null && internal instanceof ItemStack) {
				output[i] = (ItemStack) internal;
			}
		}
		return output;
	}
}
