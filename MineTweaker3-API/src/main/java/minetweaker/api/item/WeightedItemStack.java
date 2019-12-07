package minetweaker.api.item;

import stanhebben.zenscript.annotations.*;

import java.util.*;

/**
 * Contains a weighted item stack. Weighted item stacks simply contain a stack
 * and a weight.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.item.WeightedItemStack")
public final class WeightedItemStack {
    
    private final IItemStack stack;
    private final float p;
    public WeightedItemStack(IItemStack stack, float p) {
        this.stack = stack;
        this.p = p;
    }
    
    public static List<IItemStack> pickRandomDrops(Random random, WeightedItemStack[] items) {
        ArrayList<IItemStack> result = new ArrayList<>();
        
        for(WeightedItemStack item : items) {
            if(random.nextFloat() <= item.getChance()) {
                result.add(item.getStack());
            }
        }
        
        return result;
    }
    
    @ZenGetter("stack")
    public IItemStack getStack() {
        return stack;
    }
    
    @ZenGetter("chance")
    public float getChance() {
        return p;
    }
    
    @ZenGetter("percent")
    public float getPercent() {
        return p * 100;
    }
    
    // #############################
    // ### Object implementation ###
    // #############################
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.stack != null ? this.stack.hashCode() : 0);
        hash = 29 * hash + Float.floatToIntBits(this.p);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final WeightedItemStack other = (WeightedItemStack) obj;
        return !(this.stack != other.stack && (this.stack == null || !this.stack.equals(other.stack))) && Float.floatToIntBits(this.p) == Float.floatToIntBits(other.p);
    }
}
