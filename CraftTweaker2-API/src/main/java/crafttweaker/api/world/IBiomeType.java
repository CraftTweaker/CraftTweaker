package crafttweaker.api.world;

import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenClass("crafttweaker.world.IBiomeType")
public interface IBiomeType {
    
    @ZenGetter("name")
    String getName();
    
}
