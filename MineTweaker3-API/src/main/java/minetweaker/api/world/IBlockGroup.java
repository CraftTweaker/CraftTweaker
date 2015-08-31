/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.world;

import minetweaker.api.block.IBlock;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.world.IBlockGroup")
public interface IBlockGroup {
	@ZenGetter("dimension")
	public IDimension getDimension();

	@ZenMethod
	public IBlock getBlock(int x, int y, int z);
}
