package minetweaker.api.vanilla;

import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("vanilla.IVanilla")
public interface IVanilla {
    
    @ZenGetter("loot")
    ILootRegistry getLoot();
    
    @ZenGetter("seeds")
    ISeedRegistry getSeeds();
}
