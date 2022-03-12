package com.blamejared.crafttweaker.api.action.brewing;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IItemStack;
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

public class ActionRemovePotionBrewingRecipe extends ActionBrewingBase {
    
    private final List<PotionBrewing.Mix<Potion>> removed = new ArrayList<>();
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
        
        Iterator<PotionBrewing.Mix<Potion>> vanillaIterator = AccessPotionBrewing.crafttweaker$getPOTION_MIXES().iterator();
        while(vanillaIterator.hasNext()) {
            PotionBrewing.Mix<Potion> mix = vanillaIterator.next();
            
            IRegistryDelegate<Potion> potionInput = mix.from;
            IRegistryDelegate<Potion> potionOutput = mix.to;
            Ingredient reagent = mix.ingredient;
            
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
        
        for(PotionBrewing.Mix<Potion> potion : removed) {
            IRegistryDelegate<Potion> potionInput = potion.from;
            Ingredient itemReagent = potion.ingredient;
            IRegistryDelegate<Potion> potionOutput = potion.to;
            if(potionInput == null || itemReagent == null || potionOutput == null) {
                CraftTweakerAPI.LOGGER.error("Error getting mix entries! potionInput: {}, itemReagent: {}, potionOutput: {}", potionInput, itemReagent, potionOutput);
                continue;
            }
            
            AccessPotionBrewing.crafttweaker$callAddMix(potionInput.get(), itemReagent.getItems()[0].getItem(), potionOutput.get());
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
