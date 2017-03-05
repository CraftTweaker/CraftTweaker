package minetweaker.expand;

import minetweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenExpansion("minetweaker.item.IItemStack")
public class ExpandItemStack {

    @ZenCaster
    public static WeightedItemStack asWeightedItemStack(IItemStack value) {
        return new WeightedItemStack(value, 1.0f);
    }
}
