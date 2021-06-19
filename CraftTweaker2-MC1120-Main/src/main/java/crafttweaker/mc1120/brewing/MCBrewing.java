package crafttweaker.mc1120.brewing;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.recipes.IBrewingManager;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.ArrayList;
import java.util.List;

public class MCBrewing implements IBrewingManager{
    private static final List<Tuple<IIngredient, IIngredient>> toRemoveVanillaRecipes = new ArrayList<>();
    private static final List<IBrewingRecipe> allBrewingRecipes = CraftTweakerHacks.getPrivateStaticObject(BrewingRecipeRegistry.class, "recipes");
    public static final List<IBrewingAction> brewingActions = new ArrayList<>();

    public MCBrewing() {
    }

    @Override
    public void addBrew(IIngredient input, IIngredient ingredient, IItemStack output, boolean hidden) {
        brewingActions.add(new ActionAddBrewingRecipe(input, new IIngredient[] {ingredient}, output, hidden));
    }

    @Override
    public void addBrew(IIngredient input, IIngredient[] ingredients, IItemStack output, boolean hidden) {
        brewingActions.add(new ActionAddBrewingRecipe(input, ingredients, output, hidden));
    }

    @Override
    public void removeRecipe(IItemStack input, IItemStack ingredient) {
        addFixVanillaRecipesAction();
        toRemoveVanillaRecipes.add(new Tuple<>(input, ingredient));
        brewingActions.add(new ActionRemoveBrewingRecipe(input, ingredient, allBrewingRecipes));
    }

    @Override
    public void removeRecipe(IItemStack ingredient) {
        addFixVanillaRecipesAction();
        toRemoveVanillaRecipes.add(new Tuple<>(IngredientAny.INSTANCE, ingredient));
        brewingActions.add(new ActionRemoveBrewingRecipeForIngredient(ingredient, allBrewingRecipes));
    }

    private static void addFixVanillaRecipesAction() {
        if (toRemoveVanillaRecipes.isEmpty()) { // toRemoveVanillaRecipes list is empty means the first time to call the method
            brewingActions.add(new ActionFixVanillaBrewingRecipes(toRemoveVanillaRecipes, allBrewingRecipes));
        }
    }
}
