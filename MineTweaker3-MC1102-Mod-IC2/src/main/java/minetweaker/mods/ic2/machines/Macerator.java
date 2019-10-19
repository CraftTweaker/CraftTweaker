package minetweaker.mods.ic2.machines;

import ic2.api.recipe.*;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.*;
import minetweaker.mods.ic2.*;
import stanhebben.zenscript.annotations.*;

import static minetweaker.api.minecraft.MineTweakerMC.*;

/**
 * Provides access to the IC2 macerator recipes. Recipes can be added but not
 * removed, due to IC2 API restrictions.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.ic2.Macerator")
@ModOnly("IC2")
public class Macerator {

    /**
     * Adds a new recipe to the macerator.
     * <p>
     * The recipe input can be any pattern, as long as the stack size is determined.
     * The output can be any item stack.
     *
     * @param output     recipe output
     * @param ingredient recipe input
     */
    @ZenMethod
    public static void addRecipe(@NotNull IItemStack output, @NotNull IIngredient ingredient) {
        if(ingredient.getAmount() < 0) {
            MineTweakerAPI.logWarning("invalid ingredient: " + ingredient + " - stack size not known");
        } else {
            MineTweakerAPI.apply(new MachineAddRecipeAction("macerator", Recipes.macerator, getItemStacks(output), null, new IC2RecipeInput(ingredient)));
        }
    }

    /**
     * Determines the recipe output for the given input. Will return the output
     * of a single item, even if the stack contains multiple items.
     *
     * @param input recipe input
     *
     * @return recipe output
     */
    @ZenMethod
    public static IItemStack getOutput(@NotNull IItemStack input) {
        RecipeOutput output = Recipes.macerator.getOutputFor(getItemStack(input), false);
        if(output == null || output.items.isEmpty())
            return null;
        return getIItemStack(output.items.get(0));
    }
}
