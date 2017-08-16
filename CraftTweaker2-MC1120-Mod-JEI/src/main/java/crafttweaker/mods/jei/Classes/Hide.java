package crafttweaker.mods.jei.Classes;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.oredict.OreDictionary;

public class Hide implements IAction {
    
    private final IItemStack stack;
    
    public Hide(IItemStack stack) {
        this.stack = stack;
    }
    

    @Override
    public void apply() {
        if(stack == null){
            CraftTweakerAPI.logError("Cannot hide null item!");
            return;
        }
        
        
        // TODO make it work for OreDictionary.WILDCARD meta values
        ItemStack IStack = getItemStack(stack);
        JEIAddonPlugin.itemRegistry.removeIngredientsAtRuntime(ItemStack.class, Collections.singletonList(IStack));

    }
    
    @Override
    public String describe() {
        return "Hiding item in JEI: " + stack;
    }
    
}
