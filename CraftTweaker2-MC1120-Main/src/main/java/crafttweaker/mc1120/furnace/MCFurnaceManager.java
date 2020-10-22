package crafttweaker.mc1120.furnace;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.actions.*;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.stream.Collectors;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

/**
 * @author Stan
 */
public class MCFurnaceManager implements IFurnaceManager {


    public static final Map<IItemStack, Integer> fuelMap = new HashMap<>();

    public static List<ActionAddFurnaceRecipe> recipesToAdd = new ArrayList<>();
    public static List<IActionFurnaceRemoval> recipesToRemove = new ArrayList<>();

    public MCFurnaceManager() {

    }

    @Override
    public void remove(IIngredient output, @Optional IIngredient input) {
        if(output == null)
            throw new IllegalArgumentException("output cannot be null");

        recipesToRemove.add(new ActionFurnaceRemoveRecipe(output, input));
    }

    @Override
    public void removeAll() {
        recipesToRemove.add(new ActionFurnaceRemoveAllRecipes());
    }

    @Override
    public void addRecipe(IItemStack output, IIngredient input, @Optional double xp) {
        List<IItemStack> items = input.getItems();
        if(items == null) {
            CraftTweakerAPI.logError("Cannot turn " + input.toString() + " into a furnace recipe");
            return;
        }
        ItemStack[] items2 = getItemStacks(items);
        ItemStack output2 = getItemStack(output);
        recipesToAdd.add(new ActionAddFurnaceRecipe(input, items2, output2, xp));
    }

    @Override
    public void setFuel(IIngredient item, int fuel) {
        CraftTweakerAPI.apply(new ActionSetFuel(new SetFuelPattern(item, fuel)));
    }

    @Override
    public int getFuel(IItemStack item) {
        return TileEntityFurnace.getItemBurnTime(CraftTweakerMC.getItemStack(item));
    }

    @Override
    public List<IFurnaceRecipe> getAll() {
        return FurnaceRecipes.instance()
                .getSmeltingList().entrySet().stream()
                .filter(ent -> {
                    if (!ent.getValue().isEmpty() && !ent.getKey().isEmpty()) {
                        return true;
                    } else {
                        CraftTweakerAPI.logWarning("Furnace recipe from " + ent.getKey() + " to " + ent.getValue() + " has a empty stack.");
                        return false;
                    }
                })
                .map(ent -> new FurnaceRecipe(MCItemStack.createNonCopy(ent.getKey()), MCItemStack.createNonCopy(ent.getValue()), FurnaceRecipes.instance().getSmeltingExperience(ent.getValue())))
                .collect(Collectors.toList());
    }


}
