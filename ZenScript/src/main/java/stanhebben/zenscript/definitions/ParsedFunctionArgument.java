package stanhebben.zenscript.definitions;

import stanhebben.zenscript.type.ZenType;

/**
 *
 * @author Stanneke
 */
public class ParsedFunctionArgument {
	private final int index;
	private final String name;
	private final ZenType type;

	public ParsedFunctionArgument(int index, String name, ZenType type) {
		this.index = index;
		this.name = name;
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public ZenType getType() {
		return type;
	}
}
