package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolGlobalValue;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.util.ZenPosition;

public class PartialGlobalValue implements IPartialExpression {

	private final SymbolGlobalValue value;
	
	public PartialGlobalValue(SymbolGlobalValue symbolGlobalValue) {
		this.value = symbolGlobalValue;
	}

	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		return value.getValue().getValue().compile((IEnvironmentMethod) environment, getType()).eval(environment);
	}

	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		environment.error(position, "cannot assign to a global value");
		return new ExpressionInvalid(position);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return value.getType().getMember(position, environment, this, name);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		return value.getType().call(position, environment, eval(environment), values);
	}

	@Override
	public ZenType[] predictCallTypes(int numArguments) {
		return value.getType().predictCallTypes(numArguments);
	}

	@Override
	public IZenSymbol toSymbol() {
		return value;
	}

	@Override
	public ZenType getType() {
		return value.getType();
	}

	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		return value.getType();
	}

	public String getName() {
		return value.getName();
	}

}
