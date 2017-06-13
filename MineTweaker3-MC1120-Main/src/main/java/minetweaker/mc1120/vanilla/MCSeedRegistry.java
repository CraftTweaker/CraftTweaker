package minetweaker.mc1120.vanilla;

import minetweaker.*;
import minetweaker.api.item.*;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.vanilla.ISeedRegistry;
import minetweaker.mc1120.util.MineTweakerHacks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class MCSeedRegistry implements ISeedRegistry {
    
    private static final List SEEDS = MineTweakerHacks.getSeeds();
    
    @Override
    public void addSeed(WeightedItemStack item) {
        MineTweakerAPI.apply(new AddSeedAction(item));
    }
    
    @Override
    public void removeSeed(IIngredient pattern) {
        MineTweakerAPI.apply(new RemoveSeedAction(pattern));
    }
    
    @Override
    public List<WeightedItemStack> getSeeds() {
        List<? extends WeightedRandom.Item> entries = (List<? extends WeightedRandom.Item>) SEEDS;
        
        return entries.stream().map(entry -> new WeightedItemStack(MineTweakerMC.getIItemStack(MineTweakerHacks.getSeedEntrySeed(entry)), entry.itemWeight)).collect(Collectors.toList());
    }
    
    // ######################
    // ### Action classes ###
    // ######################
    
    private static class AddSeedAction implements IUndoableAction {
        
        private final IItemStack item;
        private final WeightedRandom.Item entry;
        
        public AddSeedAction(WeightedItemStack item) {
            this.item = item.getStack();
            entry = MineTweakerHacks.constructSeedEntry(item);
        }
        
        @Override
        public void apply() {
            SEEDS.add(entry);
        }
        
        @Override
        public boolean canUndo() {
            return true;
        }
        
        @Override
        public void undo() {
            SEEDS.remove(entry);
        }
        
        @Override
        public String describe() {
            return "Adding seed entry " + item;
        }
        
        @Override
        public String describeUndo() {
            return "Removing seed entry " + item;
        }
        
        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
    
    private static class RemoveSeedAction implements IUndoableAction {
        
        private final IIngredient pattern;
        private final List<Object> removed;
        
        public RemoveSeedAction(IIngredient ingredient) {
            this.pattern = ingredient;
            removed = new ArrayList<>();
        }
        
        @Override
        public void apply() {
            removed.clear();
            
            for(Object entry : SEEDS) {
                ItemStack itemStack = MineTweakerHacks.getSeedEntrySeed(entry);
                if(pattern.matches(MineTweakerMC.getIItemStack(itemStack))) {
                    removed.add(entry);
                }
            }
            
            for(Object entry : removed) {
                SEEDS.remove(entry);
            }
        }
        
        @Override
        public boolean canUndo() {
            return true;
        }
        
        @Override
        public void undo() {
            SEEDS.addAll(removed);
        }
        
        @Override
        public String describe() {
            return "Removing seeds " + pattern;
        }
        
        @Override
        public String describeUndo() {
            return "Restoring seeds " + pattern + " (" + removed.size() + " entries)";
        }
        
        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
