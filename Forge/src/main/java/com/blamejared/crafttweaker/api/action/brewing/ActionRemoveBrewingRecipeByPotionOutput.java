package com.blamejared.crafttweaker.api.action.brewing;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.mixin.common.access.brewing.AccessPotionBrewing;
import com.blamejared.crafttweaker.natives.item.alchemy.ExpandPotion;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionRemoveBrewingRecipeByPotionOutput extends ActionBrewingBase {
    
    private final List<PotionBrewing.Mix<Potion>> removed = new ArrayList<>();
    private final Potion output;
    
    
    public ActionRemoveBrewingRecipeByPotionOutput(List<IBrewingRecipe> recipes, Potion output) {
        
        super(recipes);
        this.output = output;
        
    }
    
    @Override
    public void apply() {
        
        Iterator<PotionBrewing.Mix<Potion>> iterator = AccessPotionBrewing.getPOTION_MIXES().iterator();
        while(iterator.hasNext()) {
            PotionBrewing.Mix<Potion> mix = iterator.next();
            
            if(mix.to == null) {
                throw new RuntimeException("Error getting potion from mix: " + mix + "! Please make an issue on the issue tracker!");
            }
            if(mix.to.get().equals(output)) {
                removed.add(mix);
                iterator.remove();
            }
        }
    }
    
    @Override
    public void undo() {
        
        for(PotionBrewing.Mix<Potion> potion : removed) {
            IRegistryDelegate<Potion> potionInput = potion.from;
            Ingredient itemReagent = potion.ingredient;
            IRegistryDelegate<Potion> potionOutput = potion.to;
            if(potionInput == null || itemReagent == null || potionOutput == null) {
                CraftTweakerAPI.LOGGER.error("Error getting mix entries! potionInput: {}, itemReagent: {}, potionOutput: {}", potionInput, itemReagent, potionOutput);
                continue;
            }
            
            AccessPotionBrewing.callAddMix(potionInput.get(), itemReagent.getItems()[0].getItem(), potionOutput.get());
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
