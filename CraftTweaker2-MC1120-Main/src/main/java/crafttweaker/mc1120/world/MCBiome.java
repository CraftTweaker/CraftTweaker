package crafttweaker.mc1120.world;

import java.util.LinkedList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.world.IBiome;
import crafttweaker.mc1120.entity.MCEntityDefinition;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @author Stan
 */

public class MCBiome implements IBiome {

    private final Biome biome;

    public MCBiome(Biome biome) {
        this.biome = biome;
    }

    @Override
    public String getName() {
        return biome.getBiomeName();
    }

	@Override
	public boolean getCanRain() {
		return biome.canRain();
	}

	@Override
	public boolean isSnowyBiome() {
		return biome.isSnowyBiome();
	}

	@Override
	public boolean getIsHighHumidity() {
		return biome.isHighHumidity();
	}

	@Override
	public float getSpawningChance() {
		return biome.getSpawningChance();
	}

	@Override
	public float getBaseHeight() {
		return biome.getBaseHeight();
	}

	@Override
	public float getRainfall() {
		return biome.getRainfall();
	}

	@Override
	public int getWaterColorMultiplier() {
		return biome.getWaterColorMultiplier();
	}

	@Override
	public boolean getIgnorePlayerSpawnSuitability() {
		return biome.ignorePlayerSpawnSuitability();
	}

	
	
	@Override
	public float getHeightVariation() {
		return biome.getHeightVariation();
	}

	@Override
	public float getTemperature() {
		return biome.getTemperature();
	}
}
