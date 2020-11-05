package crafttweaker.mc1120.vanilla;

import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.vanilla.ISeedRegistry;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.actions.*;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import net.minecraft.util.WeightedRandom;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class MCSeedRegistry implements ISeedRegistry {
    
    public static final List<WeightedRandom.Item> SEEDS = CraftTweakerHacks.getSeeds();
    
    @Override
    public void addSeed(WeightedItemStack item) {
        CraftTweaker.LATE_ACTIONS.add(new ActionAddSeed(item));
    }
    
    @Override
    public void removeSeed(IIngredient pattern) {
        CraftTweaker.LATE_ACTIONS.add(new ActionRemoveSeed(pattern));
    }
    
    @Override
    public List<WeightedItemStack> getSeeds() {
        List<? extends WeightedRandom.Item> entries = (List<? extends WeightedRandom.Item>) SEEDS;
        
        return entries.stream().map(entry -> new WeightedItemStack(CraftTweakerMC.getIItemStack(CraftTweakerHacks.getSeedEntrySeed(entry)), entry.itemWeight)).collect(Collectors.toList());
    }
}
