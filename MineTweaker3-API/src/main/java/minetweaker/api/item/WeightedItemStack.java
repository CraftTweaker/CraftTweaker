/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.item.WeightedItemStack")
public final class WeightedItemStack {
	private final IItemStack stack;
	private final float p;
	
	public WeightedItemStack(IItemStack stack, float p) {
		this.stack = stack;
		this.p = p;
	}
	
	@ZenGetter("stack")
	public IItemStack getStack() {
		return stack;
	}
	
	@ZenGetter("chance")
	public float getChance() {
		return p;
	}
	
	@ZenGetter("percent")
	public float getPercent() {
		return p * 100;
	}
	
	// #############################
	// ### Object implementation ###
	// #############################
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + (this.stack != null ? this.stack.hashCode() : 0);
		hash = 29 * hash + Float.floatToIntBits(this.p);
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
		final WeightedItemStack other = (WeightedItemStack) obj;
		if (this.stack != other.stack && (this.stack == null || !this.stack.equals(other.stack))) {
			return false;
		}
		if (Float.floatToIntBits(this.p) != Float.floatToIntBits(other.p)) {
			return false;
		}
		return true;
	}
}
