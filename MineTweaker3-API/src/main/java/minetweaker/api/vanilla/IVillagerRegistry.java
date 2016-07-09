package minetweaker.api.vanilla;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

/**
 * Created by Jared on 5/9/2016.
 */
public interface IVillagerRegistry {

    @ZenMethod
    public void addSeed(WeightedItemStack item);

    @ZenMethod
    public void removeSeed(IIngredient item);

    @ZenGetter("seeds")
    public List<WeightedItemStack> getSeeds();
}
