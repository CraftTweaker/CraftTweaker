/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.world;

import minetweaker.api.world.IBiome;
import net.minecraft.world.biome.BiomeGenBase;

/**
 *
 * @author Stan
 */
public class MCBiome implements IBiome {
	private final BiomeGenBase biome;

	public MCBiome(BiomeGenBase biome) {
		this.biome = biome;
	}

	@Override
	public String getName() {
		return biome.biomeName;
	}
}
