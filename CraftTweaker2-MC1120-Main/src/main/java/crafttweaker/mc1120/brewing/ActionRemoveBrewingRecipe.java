package crafttweaker.mc1120.brewing;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.List;

/**
 * @author youyihj
 */
public class ActionRemoveBrewingRecipe implements IBrewingAction {
    private final IIngredient input;
    private final IIngredient ingredient;
    private final List<IBrewingRecipe> allBrewingRecipes;

    public ActionRemoveBrewingRecipe(IIngredient input, IIngredient ingredient, List<IBrewingRecipe> allBrewingRecipes) {
        this.input = input;
        this.ingredient = ingredient;
        this.allBrewingRecipes = allBrewingRecipes;
    }

    @Override
    public void apply() {
        ItemStack _input = CraftTweakerMC.getItemStack(input);
        ItemStack _ingredient = CraftTweakerMC.getItemStack(ingredient);
        allBrewingRecipes.removeIf(it -> it.getClass() != VanillaBrewingPlus.class && !it.getOutput(_input, _ingredient).isEmpty());
    }

    @Override
    public String describe() {
        return "Removing Brewing Recipes for input: " + input.toCommandString() + ", ingredient: " + ingredient.toCommandString();
    }
}
