/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.oredict;

import java.util.ArrayList;
import java.util.List;
import minetweaker.api.minecraft.MineTweakerMC;
import static minetweaker.api.minecraft.MineTweakerMC.getOreDict;
import minetweaker.api.oredict.IOreDict;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class MCOreDict implements IOreDict {
	@Override
	public IOreDictEntry get(String name) {
		return getOreDict(name);
	}

	@Override
	public List<IOreDictEntry> getEntries() {
		List<IOreDictEntry> entries = new ArrayList<IOreDictEntry>();
		for (String key : OreDictionary.getOreNames()) {
			if (!OreDictionary.getOres(key).isEmpty()) {
				entries.add(MineTweakerMC.getOreDict(key));
			}
		}
		return entries;
	}
}
