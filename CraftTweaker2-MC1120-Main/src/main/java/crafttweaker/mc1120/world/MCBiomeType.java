package crafttweaker.mc1120.world;

import crafttweaker.api.world.IBiomeType;
import net.minecraftforge.common.BiomeDictionary;

public class MCBiomeType implements IBiomeType {
    
    private final BiomeDictionary.Type type;
    
    public MCBiomeType(BiomeDictionary.Type type) {
        this.type = type;
    }
    
    @Override
    public String getName() {
        return type.getName();
    }
    
}
