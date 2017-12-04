package crafttweaker.tests.TEST;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "crafttweaker.tests.devWikiTest")
@ZenRegister
public class DevWikiTest {
	
	//statics which will be called using crafttweaker.tests.methods.methodName(arguments)
	@ZenMethod
	public static DevWikiTest staticMethod(int arg1) {
		return new DevWikiTest(arg1);
	}
	
	@ZenMethod
	public static void staticMethod2() {
		CraftTweakerAPI.logInfo("staticMethod2 called!");
	}
	
	@ZenMethod
	public static void staticMethodVarArg(int... args) {
		CraftTweakerAPI.logInfo("staticMethod3 called with " + args.length + " arguments");
	}
	
	
	
	//nonstatics which sill be called using instance.methodName(arguments);
	@ZenMethod
	public int getValue() {
		return value;
	}	
	
	@ZenMethod
	public void print() {
		CraftTweakerAPI.logInfo("DevWikiTest Object with value " + value);
	}
	
	@ZenMethod
	public void printWithVarArg(int... args) {
		CraftTweakerAPI.logInfo("Nonstatic called with " + args.length + " arguments");
	}
	
	
	private final int value;
	
	public DevWikiTest(int value) {
		this.value = value;
	}
}
