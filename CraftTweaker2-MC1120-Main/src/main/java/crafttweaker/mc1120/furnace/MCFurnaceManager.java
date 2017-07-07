package crafttweaker.mc1120.furnace;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.actions.*;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.stream.Collectors;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

/**
 * @author Stan
 */
public class MCFurnaceManager implements IFurnaceManager {
    
    
    public static final Map<IItemStack, Integer> fuelMap = new HashMap<>();
    
    public MCFurnaceManager() {
    
    }
    
    @Override
    public void remove(IIngredient output, @Optional IIngredient input) {
        if(output == null)
            throw new IllegalArgumentException("output cannot be null");
        
        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();
        
        Map<ItemStack, ItemStack> smeltingMap = new HashMap<>();
        if(input == null) {
            for(Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
                if(output.matches(getIItemStack(entry.getValue()))) {
                    smeltingMap.put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            for(Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
                if(output.matches(getIItemStack(entry.getValue())) && input.matches(getIItemStack(entry.getKey()))) {
                    smeltingMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if(smeltingMap.isEmpty()) {
            CraftTweakerAPI.logWarning("No furnace recipes for " + output.toString());
        } else {
            CraftTweakerAPI.apply(new ActionRemoveFurnaceRecipe(smeltingMap));
        }
    }
    
    @Override
    public void removeAll() {
        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();
        
        Map<ItemStack, ItemStack> smeltingMap = new HashMap<>();
        for(Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            smeltingMap.put(entry.getKey(), entry.getValue());
        }
        CraftTweakerAPI.apply(new ActionRemoveFurnaceRecipe(smeltingMap));
    }
    
    @Override
    public void addRecipe(IItemStack output, IIngredient input, @Optional double xp) {
        List<IItemStack> items = input.getItems();
        if(items == null) {
            CraftTweakerAPI.logError("Cannot turn " + input.toString() + " into a furnace recipe");
        }
        
        ItemStack[] items2 = getItemStacks(items);
        ItemStack output2 = getItemStack(output);
        CraftTweakerAPI.apply(new ActionAddFurnaceRecipe(input, items2, output2, xp));
    }
    
    @Override
    public void setFuel(IIngredient item, int fuel) {
        CraftTweakerAPI.apply(new ActionSetFuel(new SetFuelPattern(item, fuel)));
    }
    
    @Override
    public int getFuel(IItemStack item) {
        return GameRegistry.getFuelValue(getItemStack(item));
    }
    
    @Override
    public List<IFurnaceRecipe> getAll() {
        return FurnaceRecipes.instance().getSmeltingList().entrySet().stream().map(ent -> new FurnaceRecipe(new MCItemStack(ent.getKey()), new MCItemStack(ent.getValue()), FurnaceRecipes.instance().getSmeltingExperience(ent.getValue()))).collect(Collectors.toList());
    }
    
    
}
