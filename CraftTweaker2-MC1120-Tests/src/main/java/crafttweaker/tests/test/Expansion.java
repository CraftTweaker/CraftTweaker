package crafttweaker.tests.test;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.item.WeightedItemStack")
@ZenRegister
public class Expansion {
    @ZenMethod
    public static void print(WeightedItemStack stack) {
        CraftTweakerAPI.logInfo("STACKKKKK: " + stack.getStack().getDisplayName());
    }
    
    @ZenCaster
    public static IOreDictEntry asOreDict(IItemStack stack) {
    	return stack.getOres().get(0);
    }
    
    @ZenMethodStatic
    public static WeightedItemStack create(String name, int meta, float percent) {
    	return BracketHandlerItem.getItem(name, meta).percent(percent);
    }
}
