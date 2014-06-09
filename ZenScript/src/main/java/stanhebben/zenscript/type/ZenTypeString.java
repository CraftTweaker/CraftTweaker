package stanhebben.zenscript.type;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionAs;
import stanhebben.zenscript.expression.ExpressionCompareGeneric;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.ExpressionStringConcat;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

public class ZenTypeString extends ZenType {
	public static final ZenTypeString INSTANCE = new ZenTypeString();
	
	private final Type type = Type.getType("java.lang.String");
	
	private ZenTypeString() {}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this || type == BOOL || type.getNumberType() > 0 || canCastExpansion(environment, type);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this || type == BOOL || type.getNumberType() > 0 || canCastExpansion(environment, type);
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type == this) {
			return value;
		} else if (type == BOOL || type.getNumberType() > 0) {
			return new ExpressionAs(position, value, type);
		} else if (canCastExpansion(environment, type)) {
			return castExpansion(position, environment, value, type);
		} else {
			return new ExpressionAs(position, value, type);
		}
	}

	@Override
	public Type toASMType() {
		return type;
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public IPartialExpression getMember(
			ZenPosition position,
			IEnvironmentGlobal environment,
			IPartialExpression value,
			String name) {
		IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
		if (result == null) {
			environment.error(position, "bool value has no members");
			return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
		} else {
			return result;
		}
	}

	@Override
	public IPartialExpression getStaticMember(
			ZenPosition position,
			IEnvironmentGlobal environment,
			String name) {
		return null;
	}

	@Override
	public String getSignature() {
		return "Ljava/lang/String;";
	}

	@Override
	public boolean isPointer() {
		return true;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		MethodOutput output = environment.getOutput();
		
		if (type == BOOL) {
			output.invokeStatic(Boolean.class, "parseBoolean", boolean.class, String.class);
		} else if (type == ZenTypeBoolObject.INSTANCE) {
			output.invokeStatic(Boolean.class, "valueOf", Boolean.class, String.class);
		} else if (type == BYTE) {
			output.invokeStatic(Byte.class, "parseByte", byte.class, String.class);
		} else if (type == ZenTypeByteObject.INSTANCE) {
			output.invokeStatic(Byte.class, "valueOf", Byte.class, String.class);
		} else if (type == SHORT) {
			output.invokeStatic(Short.class, "parseShort", short.class, String.class);
		} else if (type == ZenTypeShortObject.INSTANCE) {
			output.invokeStatic(Short.class, "valueof", Short.class, String.class);
		} else if (type == INT) {
			output.invokeStatic(Integer.class, "parseInt", int.class, String.class);
		} else if (type == ZenTypeIntObject.INSTANCE) {
			output.invokeStatic(Integer.class, "valueof", Integer.class, String.class);
		} else if (type == LONG) {
			output.invokeStatic(Long.class, "parseLong", long.class, String.class);
		} else if (type == ZenTypeLongObject.INSTANCE) {
			output.invokeStatic(Long.class, "valueof", Long.class, String.class);
		} else if (type == FLOAT) {
			output.invokeStatic(Float.class, "parseFloat", float.class, String.class);
		} else if (type == ZenTypeFloatObject.INSTANCE) {
			output.invokeStatic(Float.class, "valueOf", Float.class, String.class);
		} else if (type == DOUBLE) {
			output.invokeStatic(Double.class, "parseDouble", double.class, String.class);
		} else if (type == ZenTypeDoubleObject.INSTANCE) {
			output.invokeStatic(Double.class, "valueOf", Double.class, String.class);
		} else if (!compileCastExpansion(position, environment, type)) {
			environment.error(position, "cannot cast " + this + " to " + type);
		}
	}

	@Override
	public Expression unary(
			ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		Expression result = unaryExpansion(position, environment, value, operator);
		if (result == null) {
			environment.error(position, "operator not supported on a string");
			return new ExpressionInvalid(position);
		} else {
			return result;
		}
	}

	@Override
	public Expression binary(
			ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		if (operator == OperatorType.CAT || operator == OperatorType.ADD) {
			if (left instanceof ExpressionStringConcat) {
				((ExpressionStringConcat) left).add(right.cast(position, environment, this));
				return left;
			} else {
				List<Expression> values = new ArrayList<Expression>();
				values.add(left);
				values.add(right.cast(position, environment, this));
				return new ExpressionStringConcat(position, values);
			}
		} else {
			Expression result = binaryExpansion(position, environment, left, right, operator);
			if (result == null) {
				environment.error(position, "operator not supported on strings");
				return new ExpressionInvalid(position, this);
			} else {
				return result;
			}
		}
	}

	@Override
	public Expression trinary(
			ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		Expression result = trinaryExpansion(position, environment, first, second, third, operator);
		if (result == null) {
			environment.error(position, "operator not supported on strings");
			return new ExpressionInvalid(position, this);
		} else {
			return result;
		}
	}

	@Override
	public Expression compare(
			ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		Expression result = binaryExpansion(position, environment, left, right, OperatorType.COMPARE);
		if (result == null) {
			environment.error(position, "cannot compare strings");
			return new ExpressionInvalid(position, BOOL);
		} else {
			return new ExpressionCompareGeneric(position, result, type);
		}
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "Cannot call a string value");
		return new ExpressionInvalid(position, INSTANCE);
	}

	@Override
	public Class toJavaClass() {
		return String.class;
	}

	@Override
	public String getName() {
		return "string";
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
}
