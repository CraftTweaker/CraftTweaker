package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenRegister
@ZenClass("crafttweaker.world.IBiomeType")
public interface IBiomeType {

    Object getInternal();
    
    @ZenGetter("name")
    String getName();

    @ZenGetter("biomes")
    List<IBiome> getBiomes();
}
