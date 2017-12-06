package crafttweaker.tests.wiki;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


//What can be imported, what will be the prefix if calling the static methods
@ZenClass(value = "crafttweaker.tests.devWikiTest")

//Telling ZS to automatically register at the appropriate time
@ZenRegister
public class DevWikiMethod {
	
	//statics which will be called using crafttweaker.tests.devWikiTest.methodName(arguments)
	@ZenMethod
	public static DevWikiMethod staticMethod(int arg1) {
		return new DevWikiMethod(arg1);
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
	
	public DevWikiMethod(int value) {
		this.value = value;
	}
}
