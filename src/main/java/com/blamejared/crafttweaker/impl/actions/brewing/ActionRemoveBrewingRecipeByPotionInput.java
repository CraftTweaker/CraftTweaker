package com.blamejared.crafttweaker.impl.actions.brewing;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.impl_native.potion.ExpandPotion;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.IRegistryDelegate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ActionRemoveBrewingRecipeByPotionInput extends ActionBrewingBase {
    
    private final List removed = new ArrayList();
    private final Potion input;
    
    public ActionRemoveBrewingRecipeByPotionInput(List<IBrewingRecipe> recipes, Potion input) {
        
        super(recipes);
        this.input = input;
    }
    
    @Override
    public void apply() {
        
        Iterator iterator = PotionBrewing.POTION_TYPE_CONVERSIONS.iterator();
        while(iterator.hasNext()) {
            Object mix = iterator.next();
            
            IRegistryDelegate<Potion> potionInput = getPotionInput(mix);
            if(potionInput == null) {
                throw new RuntimeException("Error getting potion from mix: " + mix + "! Please make an issue on the issue tracker!");
            }
            if(potionInput.get().getRegistryName().equals(input.getRegistryName())) {
                removed.add(mix);
                iterator.remove();
            }
        }
    }
    
    @Override
    public void undo() {
        
        for(Object o : removed) {
            IRegistryDelegate<Potion> potionInput = getPotionInput(o);
            Ingredient itemReagent = getItemReagent(o);
            IRegistryDelegate<Potion> potionOutput = getPotionOutput(o);
            if(potionInput == null || itemReagent == null || potionOutput == null) {
                CraftTweakerAPI.logError("Error getting mix entries! potionInput: %s, itemReagent: %s, potionOutput: %s", potionInput, itemReagent, potionOutput);
                continue;
            }
            PotionBrewing.addMix(potionInput.get(), itemReagent.getMatchingStacks()[0].getItem(), potionOutput.get());
        }
    }
    
    @Override
    public String describe() {
        
        return "Removing Brewing recipes that have an input of: " + ExpandPotion.getCommandString(input);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing removal of Brewing recipes that have an input of: " + ExpandPotion.getCommandString(input);
    }
    
}
