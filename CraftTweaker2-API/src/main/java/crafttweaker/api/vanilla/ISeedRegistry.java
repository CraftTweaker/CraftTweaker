package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

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
