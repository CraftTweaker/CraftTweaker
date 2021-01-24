package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.recipes.IIngredientPredicate")
public interface IIngredientPredicate {
    boolean test(IIngredient ingredient);

    static IIngredientPredicate fromIIngredientDefaultImplementation(IIngredient arg) {
        return (ingredient) -> ingredient.contains(arg);
    }
}
