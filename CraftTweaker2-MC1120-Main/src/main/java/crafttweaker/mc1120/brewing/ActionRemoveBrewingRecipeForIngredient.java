package crafttweaker.mc1120.brewing;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.List;

/**
 * @author youyihj
 */
public class ActionRemoveBrewingRecipeForIngredient implements IBrewingAction {
    private final IItemStack ingredient;
    private final List<IBrewingRecipe> allBrewingRecipes;

    public ActionRemoveBrewingRecipeForIngredient(IItemStack ingredient, List<IBrewingRecipe> allBrewingRecipes) {
        this.ingredient = ingredient;
        this.allBrewingRecipes = allBrewingRecipes;
    }

    @Override
    public void apply() {
        ItemStack _ingredient = CraftTweakerMC.getItemStack(ingredient);
        allBrewingRecipes.removeIf(it -> it.getClass() != VanillaBrewingPlus.class && it.isIngredient(_ingredient));
    }

    @Override
    public String describe() {
        return "Removing brewing recipes for ingredient: " + ingredient.toCommandString();
    }
}
