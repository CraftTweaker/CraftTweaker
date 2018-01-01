package crafttweaker.mc1120.world.expand;

import crafttweaker.api.world.IWorldProvider;
import crafttweaker.mc1120.world.MCWorldProvider;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.world.IWorldProvider")
public class ExpandWorldProvider {

	@ZenMethodStatic
	public static IWorldProvider getFromID(int id) {
		return new MCWorldProvider(id);
	}
}
