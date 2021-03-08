package com.blamejared.crafttweaker.impl.actions.brewing;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IItemStack;
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
public class ActionRemovePotionBrewingRecipe extends ActionBrewingBase {
    
    private final List removed = new ArrayList();
    private final Potion input;
    private final Potion output;
    private final IItemStack reagentStack;
    
    public ActionRemovePotionBrewingRecipe(List<IBrewingRecipe> recipes, Potion output, IItemStack reagentStack, Potion input) {
        
        super(recipes);
        this.output = output;
        this.input = input;
        this.reagentStack = reagentStack;
        
    }
    
    @Override
    public void apply() {
        
        Iterator vanillaIterator = PotionBrewing.POTION_TYPE_CONVERSIONS.iterator();
        while(vanillaIterator.hasNext()) {
            Object mix = vanillaIterator.next();
            
            IRegistryDelegate<Potion> potionInput = getPotionInput(mix);
            IRegistryDelegate<Potion> potionOutput = getPotionOutput(mix);
            Ingredient reagent = getItemReagent(mix);
            
            if(potionInput == null || potionOutput == null || reagent == null) {
                throw new RuntimeException("Error getting potion from mix: " + mix + "! Please make an issue on the issue tracker!");
            }
            if(potionInput.get().getRegistryName().equals(input.getRegistryName()) && potionOutput.get()
                    .getRegistryName()
                    .equals(output.getRegistryName()) && reagent.test(reagentStack.getInternal())) {
                removed.add(mix);
                vanillaIterator.remove();
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
        
        return "Removing Brewing recipes that have an input of: " + ExpandPotion.getCommandString(input) + ", output of: " + ExpandPotion
                .getCommandString(output) + " and a reagent of: " + reagentStack;
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing removal of Brewing recipes that have an input of: " + ExpandPotion.getCommandString(input) + ", output of: " + ExpandPotion
                .getCommandString(output) + " and a reagent of: " + reagentStack;
    }
    
    
}
