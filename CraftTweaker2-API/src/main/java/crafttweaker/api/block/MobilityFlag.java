package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.block.MobilityFlag")
@ZenRegister
public class MobilityFlag {
	@ZenMethod
	public static String normal() {
		return "NORMAL";
	}
	
	@ZenMethod
	public static String destroy() {
		return "DESTROY";
	}
	
	@ZenMethod
	public static String block() {
		return "BLOCK";
	}
	
	@ZenMethod
	public static String ignore() {
		return "IGNORE";
	}
	
	@ZenMethod
	public static String pushOnly() {
		return "PUSH_ONLY";
	}
}
