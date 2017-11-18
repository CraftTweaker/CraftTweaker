package stanhebben.zenscript.symbols;

import stanhebben.zenscript.definitions.ParsedGlobalValue;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialGlobalValue;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class SymbolGlobalValue implements IZenSymbol {

	private final ParsedGlobalValue value;

	public SymbolGlobalValue(ParsedGlobalValue value) {
		this.value = value;
	}

	@Override
	public IPartialExpression instance(ZenPosition position) {
		return new PartialGlobalValue(this);
	}

	public String getName() {
		return value.getName();
	}
	
	
	public ZenType getType() {
		return value.getType();
	}
	
	public ParsedGlobalValue getValue() {
		return value;
	}
}
