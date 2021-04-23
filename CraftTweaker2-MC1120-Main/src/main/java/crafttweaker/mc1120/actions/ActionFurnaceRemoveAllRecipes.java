package crafttweaker.mc1120.actions;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ActionFurnaceRemoveAllRecipes implements IActionFurnaceRemoval {
    
    
    public ActionFurnaceRemoveAllRecipes() {
    }
    
    @Override
    public void apply() {
        int size = FurnaceRecipes.instance().getSmeltingList().size();
        FurnaceRecipes.instance().getSmeltingList().clear();
        FurnaceRecipes.instance().experienceList.clear();
        CraftTweakerAPI.logInfo(size + " furnace recipes removed for");
    }
    
    @Override
    public String describe() {
        return "Removing all furnace recipes";
    }
}