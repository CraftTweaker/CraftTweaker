package stanhebben.zenscript.type;

import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionArithmeticBinary;
import stanhebben.zenscript.expression.ExpressionArithmeticCompare;
import stanhebben.zenscript.expression.ExpressionArithmeticUnary;
import stanhebben.zenscript.expression.ExpressionAs;
import stanhebben.zenscript.expression.ExpressionBool;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import static stanhebben.zenscript.type.ZenType.STRING;
import stanhebben.zenscript.util.ZenPosition;

public class ZenTypeBool extends ZenType {
	public static final ZenTypeBool INSTANCE = new ZenTypeBool();
	
	private ZenTypeBool() {}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == INSTANCE || type == ZenTypeString.INSTANCE || canCastExpansion(environment, type);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == INSTANCE || type == ZenTypeString.INSTANCE || canCastExpansion(environment, type);
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type == BOOL || type == ZenTypeBoolObject.INSTANCE || type == STRING) {
			return new ExpressionAs(position, value, type);
		} else if (canCastExpansion(environment, type)) {
			return castExpansion(position, environment, value, type);
		} else {
			return new ExpressionAs(position, value, type);
		}
	}
	
	@Override
	public Class toJavaClass() {
		return boolean.class;
	}

	@Override
	public Type toASMType() {
		return Type.BOOLEAN_TYPE;
	}

	@Override
	public int getNumberType() {
		return 0;
	}
	
	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		return new ExpressionArithmeticUnary(position, operator, value);
	}
	
	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		if (right.getType().canCastImplicit(ZenTypeBool.INSTANCE, environment)) {
			switch (operator) {
				case AND:
				case OR:
				case XOR:
					if (right.getType() != ZenTypeBool.INSTANCE) {
						right = right.cast(position, environment, ZenTypeBool.INSTANCE);
					}
					
					return new ExpressionArithmeticBinary(position, operator, left, right);
				default:
					environment.error(position, "unsupported bool operator: " + operator);
					return new ExpressionInvalid(position, ZenTypeBool.INSTANCE);
			}
		} else {
			environment.error(right.getPosition(), "not a valid bool value");
			return new ExpressionInvalid(position, ZenTypeBool.INSTANCE);
		}
	}
	
	@Override
	public Expression trinary(
			ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		environment.error(position, "operation not supported on a bool value");
		return new ExpressionInvalid(position, ZenTypeBool.INSTANCE);
	}
	
	@Override
	public Expression compare(
			ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		if (type == CompareType.EQ || type == CompareType.NE) {
			return new ExpressionArithmeticCompare(position, type, left, right);
		} else {
			environment.error(position, "such comparison not supported on a bool");
			return new ExpressionInvalid(position, ZenTypeBool.INSTANCE);
		}
	}
	
	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "cannot call a boolean value");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
		if (result == null) {
			environment.error(position, "bool value has no members");
			return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
		} else {
			return result;
		}
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		environment.error(position, "bool type has no static members");
		return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
	}

	@Override
	public String getSignature() {
		return "Z";
	}

	@Override
	public boolean isPointer() {
		return false;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		if (type == this) {
			// nothing to do
		} else if (type == ZenTypeBoolObject.INSTANCE) {
			environment.getOutput().invokeStatic(Boolean.class, "valueOf", Boolean.class, boolean.class);
		} else if (type == STRING) {
			environment.getOutput().invokeStatic(Boolean.TYPE, "toString", String.class, boolean.class);
		} else if (!compileCastExpansion(position, environment, type)) {
			environment.error(position, "Cannot compile bool to " + type);
		}
	}

	@Override
	public String getName() {
		return "bool";
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionBool(position, false);
	}
}
