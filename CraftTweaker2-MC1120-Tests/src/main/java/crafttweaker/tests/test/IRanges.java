package crafttweaker.tests.test;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.value.IntRange;

@ZenClass("crafttweaker.tests.intRanges")
@ZenRegister
public class IRanges {

	@ZenMethod
	public static void printRange(IntRange ir) {
		CraftTweakerAPI.logWarning(ir.getFrom() + "~" + ir.getTo());
	}
}
