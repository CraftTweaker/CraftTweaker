/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.vanilla;

import minetweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
@ZenClass("vanilla.LootEntry")
public class LootEntry {
	private final WeightedItemStack item;
	private final int minAmount;
	private final int maxAmount;

	public LootEntry(WeightedItemStack item, int minAmount, int maxAmount) {
		this.item = item;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
	}

	@ZenGetter("item")
	public WeightedItemStack getItem() {
		return item;
	}

	@ZenGetter("minAmount")
	public int getMinAmount() {
		return minAmount;
	}

	@ZenGetter("maxAmount")
	public int getMaxAmount() {
		return maxAmount;
	}

	@Override
	public String toString() {
		StringBuilder message = new StringBuilder();
		message.append(getItem().getStack());
		message.append(" (weight ");
		message.append((int) getItem().getChance());
		if (getMinAmount() != 0 || getMaxAmount() != 1) {
			message.append(", ");
			message.append(getMinAmount());
			message.append("-");
			message.append(getMaxAmount());
		}
		message.append(")");

		return message.toString();
	}
}
