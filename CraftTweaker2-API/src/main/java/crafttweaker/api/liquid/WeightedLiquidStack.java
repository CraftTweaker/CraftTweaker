package crafttweaker.api.liquid;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.*;

/**
 * Contains a weighted item stack. Weighted item stacks simply contain a stack
 * and a weight.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.liquid.WeightedLiquidStack")
@ZenRegister
public final class WeightedLiquidStack implements IWeightedIngredient {
    
    private final ILiquidStack stack;
    private final float p;
    
    public WeightedLiquidStack(ILiquidStack stack, float p) {
        this.stack = stack;
        this.p = p;
    }
    
    public static List<ILiquidStack> pickRandomDrops(Random random, WeightedLiquidStack[] items) {
        ArrayList<ILiquidStack> result = new ArrayList<>();
        
        for(WeightedLiquidStack item : items) {
            if(random.nextFloat() <= item.getChance()) {
                result.add(item.getStack());
            }
        }
        
        return result;
    }

    @Override
    @ZenGetter("ingredient")
    public IIngredient getIngredient() {
        return stack;
    }

    @ZenGetter("stack")
    public ILiquidStack getStack() {
        return stack;
    }

    @Override
    @ZenGetter("chance")
    public float getChance() {
        return p;
    }

    @Override
    @ZenGetter("percent")
    public float getPercent() {
        return p * 100;
    }

    // #############################
    // ### Object implementation ###
    // #############################
    
    @Override
    public int hashCode() {
        int hash = 17;
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
        final WeightedLiquidStack other = (WeightedLiquidStack) obj;
        return !(this.stack != other.stack && (this.stack == null || !this.stack.equals(other.stack))) && Float.floatToIntBits(this.p) == Float.floatToIntBits(other.p);
    }
}
