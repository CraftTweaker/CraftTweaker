package minetweaker.mc11.functions;

import net.minecraftforge.fml.common.Loader;

import static minetweaker.runtime.GlobalRegistry.*;

public class MinetweakerFunctions {
	
	public MinetweakerFunctions() {
		registerGlobal("isModLoaded", getStaticFunction(MinetweakerFunctions.class, "isModLoaded", String.class));
	}
	
	public static boolean isModLoaded(String modid) {
		return Loader.isModLoaded(modid);
	}
	
}
