package crafttweaker.api.item;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * A version of IIngredient with weights.
 * @author Gary Bryson Luis Jr.
 */
@ZenClass("crafttweaker.item.IWeightedIngredient")
@ZenRegister
public interface IWeightedIngredient {

    @ZenGetter("ingredient")
    IIngredient getIngredient();

    @ZenGetter("chance")
    float getChance();

    @ZenGetter("percent")
    float getPercent();

}
