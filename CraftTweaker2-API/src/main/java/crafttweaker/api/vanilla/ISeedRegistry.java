package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("vanilla.ISeedRegistry")
@ZenRegister
public interface ISeedRegistry {

    @ZenMethod
    void addSeed(WeightedItemStack item);

    @ZenMethod
    void removeSeed(IIngredient item);

    @ZenGetter("seeds")
    List<WeightedItemStack> getSeeds();
}
