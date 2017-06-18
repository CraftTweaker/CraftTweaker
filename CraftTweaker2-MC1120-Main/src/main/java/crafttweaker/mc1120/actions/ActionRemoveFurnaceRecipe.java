package crafttweaker.mc1120.actions;

import crafttweaker.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.*;

public class ActionRemoveFurnaceRecipe implements IAction {
    
    private Map<ItemStack, ItemStack> smeltingMap = new HashMap<>();
    
    public ActionRemoveFurnaceRecipe(Map<ItemStack, ItemStack> smeltingMap) {
        this.smeltingMap = smeltingMap;
    }
    
    @Override
    public void apply() {
        for(Map.Entry<ItemStack, ItemStack> entry : smeltingMap.entrySet()) {
            FurnaceRecipes.instance().getSmeltingList().remove(entry.getKey(), entry.getValue());
            CraftTweakerAPI.getIjeiRecipeRegistry().removeFurnace(entry.getKey());
        }
        
    }
    
    @Override
    public String describe() {
        return "Removing " + smeltingMap.size() + " furnace recipes";
    }
    
}