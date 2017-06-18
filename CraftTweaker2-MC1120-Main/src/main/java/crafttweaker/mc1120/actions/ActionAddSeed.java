package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.*;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import net.minecraft.util.WeightedRandom;

import static crafttweaker.mc1120.vanilla.MCSeedRegistry.SEEDS;

public class ActionAddSeed implements IAction {
        
        private final IItemStack item;
        private final WeightedRandom.Item entry;
        
        public ActionAddSeed(WeightedItemStack item) {
            this.item = item.getStack();
            entry = CraftTweakerHacks.constructSeedEntry(item);
        }
        
        @Override
        public void apply() {
            SEEDS.add(entry);
        }
        
        @Override
        public String describe() {
            return "Adding seed entry " + item;
        }
        
    }