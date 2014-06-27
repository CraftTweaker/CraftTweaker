/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.oredict;

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
		return new MCOreDictEntry(OreDictionary.getOreID(name));
	}
}
