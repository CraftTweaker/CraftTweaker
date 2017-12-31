package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.recipes.ICraftingRecipe")
@ZenRegister
public interface ICraftingRecipe {

    @ZenMethod
    @ZenOperator(OperatorType.CONTAINS)
    boolean matches(ICraftingInventory inventory);

    @ZenMethod
    IItemStack getCraftingResult(ICraftingInventory inventory);

    @ZenMethod
    @ZenGetter("transformers")
    boolean hasTransformers();

    @ZenMethod
    void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer);

    @ZenMethod
    @ZenGetter("commandString")
    String toCommandString();

    @ZenMethod
    @ZenGetter("name")
    String getName();
}
