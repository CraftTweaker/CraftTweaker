package minetweaker.api.vanilla;

import minetweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("vanilla.ISeedRegistry")
public interface ISeedRegistry {

    @ZenMethod
    void addSeed(WeightedItemStack item);

    @ZenMethod
    void removeSeed(IIngredient item);

    @ZenGetter("seeds")
    List<WeightedItemStack> getSeeds();
}
