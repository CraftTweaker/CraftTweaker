package com.blamejared.crafttweaker.impl.actions.brewing;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl_native.potion.ExpandPotion;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ActionRemoveBrewingRecipeByPotionOutput extends ActionBrewingBase {
    
    private final List removed = new ArrayList();
    private final Potion output;
    
    
    public ActionRemoveBrewingRecipeByPotionOutput(List<IBrewingRecipe> recipes, Potion output) {
        
        super(recipes);
        this.output = output;
        
    }
    
    @Override
    public void apply() {
        
        Iterator iterator = PotionBrewing.POTION_TYPE_CONVERSIONS.iterator();
        while(iterator.hasNext()) {
            Object mix = iterator.next();
            
            IRegistryDelegate<Potion> potionOutput = getPotionOutput(mix);
            if(potionOutput == null) {
                throw new RuntimeException("Error getting potion from mix: " + mix + "! Please make an issue on the issue tracker!");
            }
            if(potionOutput.get().equals(output)) {
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
        
        return "Removing Brewing recipes that output: " + ExpandPotion.getCommandString(output);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing removal of Brewing recipes that output: " + ExpandPotion.getCommandString(output);
    }
    
}
