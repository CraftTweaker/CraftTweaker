/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.oredict;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMemberGetter;

import java.util.List;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.oredict.IOreDict")
public interface IOreDict {
	@ZenMemberGetter
    IOreDictEntry get(String name);

	@ZenGetter("entries")
    List<IOreDictEntry> getEntries();
}
