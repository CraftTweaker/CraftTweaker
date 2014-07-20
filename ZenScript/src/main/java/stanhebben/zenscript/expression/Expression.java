package stanhebben.zenscript.expression;

import org.objectweb.asm.Label;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeBool;
import stanhebben.zenscript.util.ZenPosition;

public abstract class Expression implements IPartialExpression {	
	public static final Expression parse(ZenTokener parser, IEnvironmentMethod environment) {
		return ParsedExpression.read(parser, environment)
				.compile(environment)
				.eval(environment);
	}
	
	private final ZenPosition position;
	
	public Expression(ZenPosition position) {
		this.position = position;
	}
	
	public ZenPosition getPosition() {
		return position;
	}
	
	public abstract ZenType getType();
	
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
		if (getType() == type) {
			return this;
		} else {
			return getType().cast(position, environment, this, type);
		}
	}
	
	public abstract void compile(boolean result, IEnvironmentMethod environment);
	
	public void compileIf(Label onElse, IEnvironmentMethod environment) {
		if (getType() == ZenType.BOOL) {
			compile(true, environment);
			environment.getOutput().ifEQ(onElse);
		} else if (getType().isPointer()) {
			compile(true, environment);
			environment.getOutput().ifNull(onElse);
		} else {
			throw new RuntimeException("cannot compile non-pointer non-boolean value to if condition");
		}
	}
	
	// #########################################
	// ### IPartialExpression implementation ###
	// #########################################
	
	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		return this;
	}
	
	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		environment.error(position, "not a valid lvalue");
		return new ExpressionInvalid(position, getType());
	}
	
	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return getType().getMember(position, environment, this, name);
	}
	
	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		return getType().call(position, environment, this, values);
	}
	
	@Override
	public IZenSymbol toSymbol() {
		return null;
	}
	
	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		environment.error(position, "not a valid type");
		return ZenType.ANY;
	}
}
