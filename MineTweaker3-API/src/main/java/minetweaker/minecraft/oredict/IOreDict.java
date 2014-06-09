/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.oredict;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMemberGetter;

/**
 *
 * @author Stan
 */
@ZenClass("minecraft.oredict.IOreDict")
public interface IOreDict {
	@ZenMemberGetter
	public IOreDictEntry get(String name);
}
