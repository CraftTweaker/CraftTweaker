/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.world;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.world.IDimension")
public interface IDimension extends IBlockGroup {
	@ZenGetter
	public boolean isDay();

	@ZenMethod
	public int getBrightness(int x, int y, int z);
}
