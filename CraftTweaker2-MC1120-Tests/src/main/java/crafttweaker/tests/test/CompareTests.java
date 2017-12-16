package crafttweaker.tests.test;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenClass("crafttweaker.tests.compare")
@ZenRegister
public class CompareTests {

	public static final CompareTests A = new CompareTests(0);
	public static final CompareTests B = new CompareTests(1);
	
	
	@ZenMethod
	public static CompareTests getA() {
		return A;
	}	
	
	@ZenMethod
	public static CompareTests getB() {
		return B;
	}
	
	@ZenOperator(OperatorType.COMPARE)
	public int compare(CompareTests other) {
		return value.compareTo(other.value);
	}
	
	private final Integer value;
	public CompareTests(int value) {
		this.value = value;
	}
	
	

}
