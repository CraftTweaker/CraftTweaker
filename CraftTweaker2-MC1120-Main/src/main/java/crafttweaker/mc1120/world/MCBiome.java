package crafttweaker.mc1120.world;

import crafttweaker.api.world.IBiome;
import net.minecraft.world.biome.Biome;

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
}
