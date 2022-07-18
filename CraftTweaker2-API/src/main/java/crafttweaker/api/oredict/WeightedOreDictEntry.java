package crafttweaker.api.oredict;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IWeightedIngredient;
import crafttweaker.api.liquid.WeightedLiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Gary Bryson Luis Jr.
 */
@ZenClass("crafttweaker.oredict.WeightedOreDictEntry")
@ZenRegister
public class WeightedOreDictEntry implements IWeightedIngredient {
    private final IOreDictEntry entry;
    private final float p;

    public WeightedOreDictEntry(IOreDictEntry entry, float p) {
        this.entry = entry;
        this.p = p;
    }

    @ZenGetter("entry")
    public IOreDictEntry getEntry() {
        return entry;
    }

    @Override
    @ZenGetter("ingredient")
    public IIngredient getIngredient() {
        return entry;
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
        hash = 29 * hash + (this.entry != null ? this.entry.hashCode() : 0);
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
        final WeightedOreDictEntry other = (WeightedOreDictEntry) obj;
        return !(this.entry != other.entry && (this.entry == null || !this.entry.equals(other.entry))) && Float.floatToIntBits(this.p) == Float.floatToIntBits(other.p);
    }
}
