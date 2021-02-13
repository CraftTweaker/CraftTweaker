package crafttweaker.mc1120.world;

import crafttweaker.api.world.IBiome;
import crafttweaker.api.world.IBiomeType;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.stream.Collectors;

public class MCBiomeType implements IBiomeType {

    private final BiomeDictionary.Type type;
    
    public MCBiomeType(BiomeDictionary.Type type) {
        this.type = type;
    }

    public BiomeDictionary.Type getInternal() {
        return type;
    }
    
    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public List<IBiome> getBiomes() {
        return BiomeDictionary.getBiomes(type).stream().map(MCBiome::new).collect(Collectors.toList());
    }
}