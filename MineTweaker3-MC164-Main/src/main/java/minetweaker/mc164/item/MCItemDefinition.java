/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.item;

import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class MCItemDefinition implements IItemDefinition {
	private final Item item;
	
	public MCItemDefinition(Item item) {
		this.item = item;
	}

	@Override
	public String getId() {
		return item.getUnlocalizedName();
	}

	@Override
	public String getName() {
		return item.getUnlocalizedName();
	}

	@Override
	public IItemStack makeStack(int meta) {
		return MineTweakerMC.getIItemStackWildcardSize(new ItemStack(item, 1, meta));
	}
	
	// #############################
	// ### Object implementation ###
	// #############################

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (this.item != null ? this.item.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MCItemDefinition other = (MCItemDefinition) obj;
		if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
			return false;
		}
		return true;
	}
}
