package com.blamejared.crafttweaker.impl.actions.brewing;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ActionRemoveBrewingRecipeByReagent extends ActionBrewingBase {
    
    private final IItemStack reagent;
    private final List<PotionBrewing.MixPredicate<Potion>> removed = new ArrayList();
    private final List<IBrewingRecipe> removedRecipes = new ArrayList<>();
    
    public ActionRemoveBrewingRecipeByReagent(List<IBrewingRecipe> recipes, IItemStack reagent) {
        
        super(recipes);
        this.reagent = reagent;
        
    }
    
    @Override
    public void apply() {
        
        Iterator<PotionBrewing.MixPredicate<Potion>> vanillaIterator = PotionBrewing.POTION_TYPE_CONVERSIONS.iterator();
        while(vanillaIterator.hasNext()) {
            PotionBrewing.MixPredicate<Potion> mix = vanillaIterator.next();
            
            Ingredient reagentInput = getItemReagent(mix);
            if(reagentInput == null) {
                throw new RuntimeException("Error getting potion from mix: " + mix + "! Please make an issue on the issue tracker!");
            }
            if(reagentInput.test(reagent.getInternal())) {
                removed.add(mix);
                vanillaIterator.remove();
            }
        }
        
        Iterator<IBrewingRecipe> registryIterator = recipes.iterator();
        while(registryIterator.hasNext()) {
            IBrewingRecipe next = registryIterator.next();
            if(next.isIngredient(reagent.getInternal())) {
                removedRecipes.add(next);
                registryIterator.remove();
            }
        }
    }
    
    @Override
    public void undo() {
        
        for(PotionBrewing.MixPredicate<Potion> potion : removed) {
            IRegistryDelegate<Potion> potionInput = potion.input;
            Ingredient itemReagent = getItemReagent(potion);
            IRegistryDelegate<Potion> potionOutput = potion.output;
            if(potionInput == null || itemReagent == null || potionOutput == null) {
                CraftTweakerAPI.logError("Error getting mix entries! potionInput: %s, itemReagent: %s, potionOutput: %s", potionInput, itemReagent, potionOutput);
                continue;
            }
            PotionBrewing.addMix(potionInput.get(), itemReagent.getMatchingStacks()[0].getItem(), potionOutput.get());
        }
        removedRecipes.forEach(BrewingRecipeRegistry::addRecipe);
    }
    
    @Override
    public String describe() {
        
        return "Removing Brewing recipes that have a reagent of: " + reagent;
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing removal of Brewing recipes that have a reagent of: " + reagent;
    }
    
}
