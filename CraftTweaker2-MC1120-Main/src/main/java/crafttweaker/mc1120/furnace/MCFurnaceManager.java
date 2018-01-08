package crafttweaker.mc1120.furnace;

import crafttweaker.*;
import crafttweaker.api.item.*;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.actions.*;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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
    public static List<ActionFurnaceRemoveRecipe> recipesToRemove = new ArrayList<>();
    
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
        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();
        for(Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            recipesToRemove.add(new ActionFurnaceRemoveRecipe(getIItemStack(entry.getKey()), getIItemStack(entry.getValue())));
        }
    }
    
    @Override
    public void addRecipe(IItemStack output, IIngredient input, @Optional double xp) {
        List<IItemStack> items = input.getItems();
        if(items == null) {
            CraftTweakerAPI.logError("Cannot turn " + input.toString() + " into a furnace recipe");
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
        return ((ItemStack)item.getInternal()).getItem().getItemBurnTime(((ItemStack)item.getInternal()));
    }
    
    @Override
    public List<IFurnaceRecipe> getAll() {
        return FurnaceRecipes.instance().getSmeltingList().entrySet().stream().map(ent -> new FurnaceRecipe(new MCItemStack(ent.getKey()), new MCItemStack(ent.getValue()), FurnaceRecipes.instance().getSmeltingExperience(ent.getValue()))).collect(Collectors.toList());
    }
    
    
}
