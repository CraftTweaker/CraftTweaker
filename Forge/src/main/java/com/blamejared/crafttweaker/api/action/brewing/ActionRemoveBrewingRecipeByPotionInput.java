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

public class ActionRemoveBrewingRecipeByPotionInput extends ActionBrewingBase {
    
    private final List<PotionBrewing.Mix<Potion>> removed = new ArrayList<>();
    private final Potion input;
    
    public ActionRemoveBrewingRecipeByPotionInput(List<IBrewingRecipe> recipes, Potion input) {
        
        super(recipes);
        this.input = input;
    }
    
    @Override
    public void apply() {
        
        Iterator<PotionBrewing.Mix<Potion>> iterator = AccessPotionBrewing.crafttweaker$getPOTION_MIXES().iterator();
        while(iterator.hasNext()) {
            PotionBrewing.Mix<Potion> mix = iterator.next();
            
            IRegistryDelegate<Potion> potionInput = mix.from;
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
        
        for(PotionBrewing.Mix<Potion> potion : removed) {
            Ingredient itemReagent = potion.ingredient;
            IRegistryDelegate<Potion> potionOutput = potion.to;
            if(potion.from == null || itemReagent == null || potionOutput == null) {
                CraftTweakerAPI.LOGGER.error("Error getting mix entries! potionInput: {}, itemReagent: {}, potionOutput: {}", potion.from, itemReagent, potionOutput);
                continue;
            }
            AccessPotionBrewing.crafttweaker$callAddMix(potion.from.get(), itemReagent.getItems()[0].getItem(), potionOutput.get());
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
