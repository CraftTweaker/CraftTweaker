package minetweaker.mc1102.brewing;

import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientItem;
import minetweaker.api.item.IngredientOr;
import minetweaker.api.recipes.BrewingRecipe;
import minetweaker.api.recipes.IBrewingManager;
import minetweaker.api.recipes.IBrewingRecipe;
import minetweaker.mc1102.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.AbstractBrewingRecipe;
import net.minecraftforge.common.brewing.BrewingOreRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MCBrewingManager implements IBrewingManager {
    @Override
    public List<IBrewingRecipe> getAll() {
        List<IBrewingRecipe> ret = new ArrayList<>();
        for(net.minecraftforge.common.brewing.IBrewingRecipe rec : BrewingRecipeRegistry.getRecipes()) {
            if(rec instanceof net.minecraftforge.common.brewing.AbstractBrewingRecipe) {
                AbstractBrewingRecipe abs = (AbstractBrewingRecipe) rec;
                if(abs instanceof BrewingOreRecipe) {
                    BrewingOreRecipe brew = (BrewingOreRecipe) abs;
                    ret.add(new BrewingRecipe(new MCItemStack(brew.getOutput()), new MCItemStack(brew.getInput()), convert(brew.getIngredient())));
                }
            }
        }
        return ret;
    }

    @Override
    public void remove(IItemStack output) {
//        if (output == null)
//            throw new IllegalArgumentException("output cannot be null");
//
//       for(net.minecraftforge.common.brewing.IBrewingRecipe recipe : BrewingRecipeRegistry.getRecipes()){
//           if(recipe.is)
//       }
//        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();
//
//        List<ItemStack> toRemove = new ArrayList<ItemStack>();
//        List<ItemStack> toRemoveValues = new ArrayList<ItemStack>();
//        for (Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
//            if (output.matches(new MCItemStack(entry.getValue())) && (input == null || input.matches(new MCItemStack(entry.getKey())))) {
//                toRemove.add(entry.getKey());
//                toRemoveValues.add(entry.getValue());
//            }
//        }
//
//        if (toRemove.isEmpty()) {
//            MineTweakerAPI.logWarning("No furnace recipes for " + output.toString());
//        } else {
//            MineTweakerAPI.apply(new MCFurnaceManager.RemoveAction(toRemove, toRemoveValues));
//        }
    }

    @Override
    public void add(IItemStack output, IItemStack ingredient, IItemStack input) {

    }

    @Override
    public int remove(IItemStack output, IItemStack ingredients) {
        return 0;
    }

    private IngredientOr convert(List<ItemStack> ingredients) {
        IngredientOr ing;

        ing = new IngredientOr((IngredientItem[]) ingredients.toArray());

        return ing;
    }
}
