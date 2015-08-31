/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.item;

import java.util.ArrayList;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class MCItemDefinition implements IItemDefinition {
	private final String id;
	private final Item item;

	public MCItemDefinition(String id, Item item) {
		this.id = id;
		this.item = item;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return item.getUnlocalizedName();
	}

	@Override
	public IItemStack makeStack(int meta) {
		return MineTweakerMC.getIItemStackWildcardSize(new ItemStack(item, 1, meta));
	}

	@Override
	public List<IOreDictEntry> getOres() {
		List<IOreDictEntry> result = new ArrayList<IOreDictEntry>();

		for (String key : OreDictionary.getOreNames()) {
			for (ItemStack is : OreDictionary.getOres(key)) {
				if (is.getItem() == item) {
					result.add(MineTweakerAPI.oreDict.get(key));
					break;
				}
			}
		}

		return result;
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
