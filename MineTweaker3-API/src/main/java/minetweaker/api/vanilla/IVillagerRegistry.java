package minetweaker.api.vanilla;

import minetweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * Created by Jared on 5/9/2016.
 */
public interface IVillagerRegistry {

    @ZenMethod
    void addSeed(WeightedItemStack item);

    @ZenMethod
    void removeSeed(IIngredient item);

    @ZenGetter("seeds")
    List<WeightedItemStack> getSeeds();
}
