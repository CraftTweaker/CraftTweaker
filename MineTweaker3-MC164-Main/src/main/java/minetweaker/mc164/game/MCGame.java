/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import minetweaker.api.game.IGame;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.world.IBiome;
import minetweaker.mc164.item.MCItemDefinition;
import minetweaker.mc164.liquid.MCLiquidDefinition;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 *
 * @author Stan
 */
public class MCGame implements IGame {
	public static final MCGame INSTANCE = new MCGame();
	
	private MCGame() {}
	
	@Override
	public List<IItemDefinition> getItems() {
		List<IItemDefinition> result = new ArrayList<IItemDefinition>();
		for (Item item : Item.itemsList) {
			if (item == null) continue;
			result.add(new MCItemDefinition(item));
		}
		return result;
	}

	@Override
	public List<ILiquidDefinition> getLiquids() {
		List<ILiquidDefinition> result = new ArrayList<ILiquidDefinition>();
		for (Map.Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			result.add(new MCLiquidDefinition(entry.getValue()));
		}
		return result;
	}

	@Override
	public List<IBiome> getBiomes() {
		List<IBiome> result = new ArrayList<IBiome>();
		for (IBiome biome : MineTweakerMC.biomes) {
			if (biome != null) {
				result.add(biome);
			}
		}
		return result;
	}
}
