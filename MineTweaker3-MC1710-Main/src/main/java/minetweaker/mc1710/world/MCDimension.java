/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.world;

import minetweaker.api.block.IBlock;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.world.IDimension;
import net.minecraft.world.World;

/**
 *
 * @author Stan
 */
public class MCDimension implements IDimension {
	private final World world;

	public MCDimension(World world) {
		this.world = world;
	}

	@Override
	public boolean isDay() {
		return world.isDaytime();
	}

	@Override
	public int getBrightness(int x, int y, int z) {
		return world.getBlockLightValue(x, y, z);
	}

	@Override
	public IDimension getDimension() {
		return this;
	}

	@Override
	public IBlock getBlock(int x, int y, int z) {
		return MineTweakerMC.getBlock(world, x, y, z);
	}
}
