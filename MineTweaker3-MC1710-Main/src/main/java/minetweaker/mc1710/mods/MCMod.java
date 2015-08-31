/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.mods;

import cpw.mods.fml.common.ModContainer;
import minetweaker.api.mods.IMod;

/**
 *
 * @author Stan
 */
public class MCMod implements IMod {
	private final ModContainer mod;

	public MCMod(ModContainer mod) {
		this.mod = mod;
	}

	@Override
	public String getId() {
		return mod.getModId();
	}

	@Override
	public String getName() {
		return mod.getName();
	}

	@Override
	public String getVersion() {
		return mod.getVersion();
	}

	@Override
	public String getDescription() {
		return mod.getMetadata().description;
	}
}
