/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.vanilla;

import minetweaker.api.vanilla.ILootRegistry;
import minetweaker.api.vanilla.ISeedRegistry;
import minetweaker.api.vanilla.IVanilla;

/**
 *
 * @author Stan
 */
public class MCVanilla implements IVanilla {
	private final MCLootRegistry lootRegistry = new MCLootRegistry();
	private final MCSeedRegistry seedRegistry = new MCSeedRegistry();

	@Override
	public ILootRegistry getLoot() {
		return lootRegistry;
	}

	@Override
	public ISeedRegistry getSeeds() {
		return seedRegistry;
	}
}
