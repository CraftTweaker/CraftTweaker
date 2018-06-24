package crafttweaker.mc1120.world;

import crafttweaker.api.world.*;
import crafttweaker.mc1120.brackets.BracketHandlerBiomeType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.*;

/**
 * @author Stan
 */

public class MCBiome implements IBiome {
    
    private final Biome biome;
    
    public MCBiome(Biome biome) {
        this.biome = biome;
    }
    
    @Override
    public String getId() {
        return biome.getRegistryName().toString();
    }
    
    @Override
    public String getName() {
        return biome.biomeName;
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
        return biome.getDefaultTemperature();
    }
    
    @Override
    public List<IBiomeType> getTypes() {
        List<IBiomeType> types = new ArrayList<>();
        for(BiomeDictionary.Type type : BiomeDictionary.getTypes(biome)) {
            types.add(BracketHandlerBiomeType.getBiomeType(type.getName()));
        }
        return types;
    }
}
