package crafttweaker.tests.wiki;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly(value = "mcp")
@ZenClass(value = "crafttweaker.tests.modOnly")
@ZenRegister
public class ModOnlyWiki {
	@ZenMethod
	public static void print() {
		CraftTweakerAPI.logInfo("print issued");
	}
}
