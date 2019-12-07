package minetweaker.mods.ic2.expand;

import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenExpansion("minetweaker.item.IIngredient")
@ModOnly("IC2")
public class IngredientExpansion {

    @ZenMethod
    public static IIngredient onlyIC2ChargeAtLeast(IIngredient ingredient, final int charge) {
        return ingredient.only(stack -> ItemExpansion.getIC2Charge(stack) >= charge);
    }

    @ZenMethod
    public static IIngredient onlyIC2ChargeAtMost(IIngredient ingredient, final int charge) {
        return ingredient.only(stack -> ItemExpansion.getIC2Charge(stack) <= charge);
    }

    @ZenMethod
    public static IIngredient transformIC2Discharge(IIngredient ingredient, final int amount) {
        return ingredient.transform((item, byPlayer) -> ItemExpansion.ic2Discharge(item, amount, 0).withAmount(item.getAmount() + 1));
    }
}
