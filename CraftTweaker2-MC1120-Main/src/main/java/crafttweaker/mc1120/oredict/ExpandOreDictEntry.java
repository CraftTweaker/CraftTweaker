package crafttweaker.mc1120.oredict;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.oredict.WeightedOreDictEntry;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 * @author Gary Bryson Luis Jr.
 */
@ZenClass("crafttweaker.oredict.IOreDictEntry")
@ZenRegister
public class ExpandOreDictEntry {
    @ZenOperator(OperatorType.MOD)
    public static WeightedOreDictEntry percent(IOreDictEntry thisEntry, float p) {
        return new WeightedOreDictEntry(thisEntry, p * 0.01f);
    }

    @ZenMethod
    public static WeightedOreDictEntry weight(IOreDictEntry thisEntry, float p) {
        return new WeightedOreDictEntry(thisEntry, p);
    }
}
