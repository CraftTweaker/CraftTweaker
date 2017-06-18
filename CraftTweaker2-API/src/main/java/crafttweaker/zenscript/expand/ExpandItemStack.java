package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenExpansion("crafttweaker.item.IItemStack")
@ZenRegister
public class ExpandItemStack {

    @ZenCaster
    public static WeightedItemStack asWeightedItemStack(IItemStack value) {
        return new WeightedItemStack(value, 1.0f);
    }
}
