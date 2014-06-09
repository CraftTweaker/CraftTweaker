package stanhebben.zenscript.definitions;

import stanhebben.zenscript.type.ZenType;

/**
 *
 * @author Stanneke
 */
public class ParsedFunctionArgument {
	private final String name;
	private final ZenType type;
	
	public ParsedFunctionArgument(String name, ZenType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public ZenType getType() {
		return type;
	}
}
