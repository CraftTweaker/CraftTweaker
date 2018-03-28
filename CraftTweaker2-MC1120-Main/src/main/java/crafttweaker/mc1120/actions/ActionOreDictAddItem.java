package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ActionOreDictAddItem implements IAction {
    
    private final String id;
    private final ItemStack item;
    
    public ActionOreDictAddItem(String id, ItemStack item) {
        this.id = id;
        this.item = item;
    }
    
    @Override
    public void apply() {
        OreDictionary.getOres(id).add(item);
    }
    
    
    @Override
    public String describe() {
        return "Adding " + item.getDisplayName() + " to ore dictionary entry " + id;
    }
    
}