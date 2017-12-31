package crafttweaker.mc1120.world.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IPosition3f;
import crafttweaker.mc1120.util.Position3f;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.world.IPosition3f")
@ZenRegister
public class ExpandPosition3f {

	@ZenMethodStatic
	public static IPosition3f create(float x, float y, float z) {
		return new Position3f(x, y, z);
	}
}
