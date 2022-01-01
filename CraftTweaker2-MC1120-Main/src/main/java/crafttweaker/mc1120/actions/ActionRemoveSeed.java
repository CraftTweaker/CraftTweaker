package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import net.minecraft.item.ItemStack;

import java.util.*;

import static crafttweaker.mc1120.vanilla.MCSeedRegistry.SEEDS;

public class ActionRemoveSeed implements IAction {
        
        private final IIngredient pattern;
        private final List<Object> removed;
        
        public ActionRemoveSeed(IIngredient ingredient) {
            this.pattern = ingredient;
            removed = new ArrayList<>();
        }
        
        @Override
        public void apply() {
            removed.clear();
            
            for(Object entry : SEEDS) {
                ItemStack itemStack = CraftTweakerHacks.getSeedEntrySeed(entry);
                if(pattern.matches(CraftTweakerMC.getIItemStackForMatching(itemStack))) {
                    removed.add(entry);
                }
            }
    
            SEEDS.removeAll(removed);
        }
        
        @Override
        public String describe() {
            return "Removing seeds " + pattern;
        }
    }