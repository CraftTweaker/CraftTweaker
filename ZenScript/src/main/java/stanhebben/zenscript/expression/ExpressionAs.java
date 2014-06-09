package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class ExpressionAs extends Expression {
	private final Expression value;
	private final ZenType type;
	
	public ExpressionAs(ZenPosition position, Expression value, ZenType type) {
		super(position);
		
		this.value = value;
		this.type = type;
	}

	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
		if (!this.type.canCastExplicit(type, environment)) {
			environment.error(position, "cannot cast " + this.type + " to " + type);
		}
		return new ExpressionAs(getPosition(), this, type);
	}

	@Override
	public ZenType getType() {
		return type;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		value.compile(result, environment);
		if (result) {
			value.getType().compileCast(getPosition(), environment, type);
		}
	}
}
