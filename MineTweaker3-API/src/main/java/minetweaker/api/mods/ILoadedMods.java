/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.mods;

import stanhebben.zenscript.annotations.IterableSimple;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.mods.ILoadedMods")
@IterableSimple("minetweaker.mods.IMod")
public interface ILoadedMods extends Iterable<IMod> {
	@ZenOperator(OperatorType.CONTAINS)
	public boolean contains(String name);

	@ZenOperator(OperatorType.INDEXGET)
	public IMod get(String name);
}
