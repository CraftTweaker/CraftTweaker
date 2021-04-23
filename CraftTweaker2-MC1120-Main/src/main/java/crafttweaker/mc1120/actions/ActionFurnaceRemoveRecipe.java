package crafttweaker.mc1120.actions;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.*;
import java.util.stream.Collectors;

import static crafttweaker.api.minecraft.CraftTweakerMC.getIItemStack;

public class ActionFurnaceRemoveRecipe implements IActionFurnaceRemoval {
    
    private IIngredient output;
    private IIngredient input;
    Map<ItemStack, ItemStack> smeltingMap = new HashMap<>();
    
    public ActionFurnaceRemoveRecipe(IIngredient output, IIngredient input) {
        this.output = output;
        this.input = input;
    }
    
    @Override
    public void apply() {
        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();
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
        for(Map.Entry<ItemStack, ItemStack> entry : smeltingMap.entrySet()) {
            FurnaceRecipes.instance().getSmeltingList().remove(entry.getKey(), entry.getValue());
            FurnaceRecipes.instance().experienceList.keySet().removeIf(itemStack -> output.matches(getIItemStack(itemStack)));
        }
        CraftTweakerAPI.logInfo(smeltingMap.size() + " recipes removed for: " + output);
    }
    
    @Override
    public String describe() {
        return "Removing furnace recipes for: " + output;
    }
}