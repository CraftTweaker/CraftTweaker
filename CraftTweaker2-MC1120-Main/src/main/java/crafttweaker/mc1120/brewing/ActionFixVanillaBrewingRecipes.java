package crafttweaker.mc1120.brewing;

import crafttweaker.api.item.IIngredient;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;

import java.util.List;

/**
 * @author youyihj
 */
public class ActionFixVanillaBrewingRecipes implements IBrewingAction {
    private final List<Tuple<IIngredient, IIngredient>> toRemoveRecipes;
    private final List<IBrewingRecipe> allBrewingRecipes;

    public ActionFixVanillaBrewingRecipes(List<Tuple<IIngredient, IIngredient>> toRemoveRecipes, List<IBrewingRecipe> allBrewingRecipes) {
        this.toRemoveRecipes = toRemoveRecipes;
        this.allBrewingRecipes = allBrewingRecipes;
    }

    @Override
    public void apply() {
        allBrewingRecipes.removeIf(VanillaBrewingRecipe.class::isInstance);
        allBrewingRecipes.add(new VanillaBrewingPlus(toRemoveRecipes));
    }

    @Override
    public String describe() {
        return "Fixing Vanilla Brewing Recipes";
    }
}
