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
import stanhebben.zenscript.expression.ExpressionInt;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import static stanhebben.zenscript.type.ZenType.STRING;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

public class ZenTypeInt extends ZenType {
	public static final ZenTypeInt INSTANCE = new ZenTypeInt();
	
	private ZenTypeInt() {}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return (type.getNumberType() != 0 && type.getNumberType() >= NUM_INT)
				|| type == ZenTypeString.INSTANCE
				|| canCastExpansion(environment, type);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type.getNumberType() != 0
				|| type == ZenTypeString.INSTANCE
				|| canCastExpansion(environment, type);
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type.getNumberType() > 0 || type == STRING) {
			return new ExpressionAs(position, value, type);
		} else if (canCastExpansion(environment, type)) {
			return castExpansion(position, environment, value, type);
		} else {
			return new ExpressionAs(position, value, type);
		}
	}

	@Override
	public Type toASMType() {
		return Type.INT_TYPE;
	}

	@Override
	public int getNumberType() {
		return NUM_INT;
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
		return null;
	}

	@Override
	public String getSignature() {
		return "I";
	}

	@Override
	public boolean isPointer() {
		return false;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		MethodOutput output = environment.getOutput();
		
		if (type == BYTE) {
			output.i2b();
		} else if (type == ZenTypeByteObject.INSTANCE) {
			output.i2b();
			output.invokeStatic(Byte.class, "valueOf", Byte.class, byte.class);
		} else if (type == SHORT) {
			output.i2s();
		} else if (type == ZenTypeShortObject.INSTANCE) {
			output.i2s();
			output.invokeStatic(Short.class, "valueOf", Short.class, short.class);
		} else if (type == INT) {
			// nothing to do
		} else if (type == ZenTypeIntObject.INSTANCE) {
			output.invokeStatic(Integer.class, "valueOf", Integer.class, int.class);
		} else if (type == LONG) {
			output.i2l();
		} else if (type == ZenTypeLongObject.INSTANCE) {
			output.i2l();
			output.invokeStatic(Long.class, "valueOf", Long.class, long.class);
		} else if (type == FLOAT) {
			output.i2f();
		} else if (type == ZenTypeFloatObject.INSTANCE) {
			output.i2f();
			output.invokeStatic(Float.class, "valueOf", Float.class, float.class);
		} else if (type == DOUBLE) {
			output.i2d();
		} else if (type == ZenTypeDoubleObject.INSTANCE) {
			output.i2d();
			output.invokeStatic(Double.class, "valueOf", Double.class, double.class);
		} else if (type == STRING) {
			output.invokeStatic(Integer.class, "toString", String.class, int.class);
		} else if (!compileCastExpansion(position, environment, type)) {
			environment.error(position, "cannot cast " + this + " to " + type);
		}
	}
	
	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		return new ExpressionArithmeticUnary(position, operator, value);
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		return new ExpressionArithmeticBinary(position, operator, left, right.cast(position, environment, this));
	}
	
	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		environment.error(position, "int doesn't support this operation");
		return new ExpressionInvalid(position);
	}
	
	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		return new ExpressionArithmeticCompare(position, type, left, right.cast(position, environment, this));
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "cannot call integer values");
		return new ExpressionInvalid(position);
	}

	@Override
	public Class toJavaClass() {
		return int.class;
	}

	@Override
	public String getName() {
		return "int";
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionInt(position, 0, INT);
	}
}
