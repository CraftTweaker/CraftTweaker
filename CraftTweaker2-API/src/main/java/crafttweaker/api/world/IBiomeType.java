package crafttweaker.api.world;

import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IBiomeType")
public interface IBiomeType {
    
    @ZenGetter("name")
    String getName();
    
}
