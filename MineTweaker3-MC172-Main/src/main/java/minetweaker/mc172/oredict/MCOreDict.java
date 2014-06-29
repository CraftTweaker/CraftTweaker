/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.oredict;

import static minetweaker.api.minecraft.MineTweakerMC.getOreDict;
import minetweaker.api.oredict.IOreDict;
import minetweaker.api.oredict.IOreDictEntry;

/**
 *
 * @author Stan
 */
public class MCOreDict implements IOreDict {
	@Override
	public IOreDictEntry get(String name) {
		return getOreDict(name);
	}
}
