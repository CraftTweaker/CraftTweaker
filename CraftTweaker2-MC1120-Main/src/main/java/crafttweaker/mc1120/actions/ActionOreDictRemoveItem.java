package crafttweaker.mc1120.actions;

import crafttweaker.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static crafttweaker.api.minecraft.CraftTweakerMC.getIItemStackWildcardSize;

public class ActionOreDictRemoveItem implements IAction {
    
    private final String id;
    private final IItemStack item;
    
    public ActionOreDictRemoveItem(String id, IItemStack item) {
        this.id = id;
        this.item = item;
    }
    
    @Override
    public void apply() {
        ItemStack result = ItemStack.EMPTY;
        for(ItemStack itemStack : OreDictionary.getOres(id)) {
            if(item.matches(getIItemStackWildcardSize(itemStack))) {
                result = itemStack;
                break;
            }
        }
        int oreId = OreDictionary.getOreID(id);
        if(result.isEmpty()) {
            CraftTweakerAPI.logError(item + " does not exist in oredict: " + id);
            return;
        }
        MCOreDictEntry.getOredictContents().get(oreId).remove(result);
    }
    
    
    @Override
    public String describe() {
        return "Removing " + item.getDisplayName() + " from ore dictionary entry " + id;
    }
    
}