/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.world;

import java.util.List;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.world.IGame")
public interface IGame {
	@ZenGetter("dimensions")
	public List<IDimension> getDimensions();
}
