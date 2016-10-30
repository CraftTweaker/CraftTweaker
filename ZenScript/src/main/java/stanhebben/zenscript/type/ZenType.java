package stanhebben.zenscript.type;

import org.objectweb.asm.Type;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.ParseException;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.type.casting.CastingRuleDelegateMap;
import stanhebben.zenscript.type.casting.ICastingRule;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.expand.ZenExpandCaster;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static stanhebben.zenscript.util.ZenTypeUtil.EMPTY_REGISTRY;

public abstract class ZenType {
	public static final ZenTypeAny ANY = ZenTypeAny.INSTANCE;
	public static final ZenTypeBool BOOL = new ZenTypeBool();
	public static final ZenTypeBoolObject BOOLOBJECT = ZenTypeBoolObject.INSTANCE;
	public static final ZenTypeByte BYTE = ZenTypeByte.INSTANCE;
	public static final ZenTypeByteObject BYTEOBJECT = ZenTypeByteObject.INSTANCE;
	public static final ZenTypeShort SHORT = ZenTypeShort.INSTANCE;
	public static final ZenTypeShortObject SHORTOBJECT = ZenTypeShortObject.INSTANCE;
	public static final ZenTypeInt INT = ZenTypeInt.INSTANCE;
	public static final ZenTypeIntObject INTOBJECT = ZenTypeIntObject.INSTANCE;
	public static final ZenTypeLong LONG = ZenTypeLong.INSTANCE;
	public static final ZenTypeLongObject LONGOBJECT = ZenTypeLongObject.INSTANCE;
	public static final ZenTypeFloat FLOAT = ZenTypeFloat.INSTANCE;
	public static final ZenTypeFloatObject FLOATOBJECT = ZenTypeFloatObject.INSTANCE;
	public static final ZenTypeDouble DOUBLE = ZenTypeDouble.INSTANCE;
	public static final ZenTypeDoubleObject DOUBLEOBJECT = ZenTypeDoubleObject.INSTANCE;
	public static final ZenTypeString STRING = ZenTypeString.INSTANCE;
	public static final ZenTypeVoid VOID = ZenTypeVoid.INSTANCE;
	public static final ZenTypeNull NULL = ZenTypeNull.INSTANCE;

	public static final ZenTypeArrayBasic ANYARRAY = new ZenTypeArrayBasic(ANY);
	public static final ZenTypeAssociative ANYMAP = new ZenTypeAssociative(ANY, ANY);

	public static final int NUM_BYTE = 1;
	public static final int NUM_SHORT = 2;
	public static final int NUM_INT = 3;
	public static final int NUM_LONG = 4;
	public static final int NUM_FLOAT = 5;
	public static final int NUM_DOUBLE = 6;

	protected static final IJavaMethod BOOL_VALUEOF = JavaMethod.get(EMPTY_REGISTRY, Boolean.class, "valueOf", boolean.class);
	protected static final IJavaMethod BYTE_VALUEOF = JavaMethod.get(EMPTY_REGISTRY, Byte.class, "valueOf", byte.class);
	protected static final IJavaMethod SHORT_VALUEOF = JavaMethod.get(EMPTY_REGISTRY, Short.class, "valueOf", short.class);
	protected static final IJavaMethod INT_VALUEOF = JavaMethod.get(EMPTY_REGISTRY, Integer.class, "valueOf", int.class);
	protected static final IJavaMethod LONG_VALUEOF = JavaMethod.get(EMPTY_REGISTRY, Long.class, "valueOf", long.class);
	protected static final IJavaMethod FLOAT_VALUEOF = JavaMethod.get(EMPTY_REGISTRY, Float.class, "valueOf", float.class);
	protected static final IJavaMethod DOUBLE_VALUEOF = JavaMethod.get(EMPTY_REGISTRY, Double.class, "valueOf", double.class);

	protected static final IJavaMethod BOOL_VALUE = JavaMethod.get(EMPTY_REGISTRY, Boolean.class, "booleanValue");
	protected static final IJavaMethod BYTE_VALUE = JavaMethod.get(EMPTY_REGISTRY, Number.class, "byteValue");
	protected static final IJavaMethod SHORT_VALUE = JavaMethod.get(EMPTY_REGISTRY, Number.class, "shortValue");
	protected static final IJavaMethod INT_VALUE = JavaMethod.get(EMPTY_REGISTRY, Number.class, "intValue");
	protected static final IJavaMethod LONG_VALUE = JavaMethod.get(EMPTY_REGISTRY, Number.class, "longValue");
	protected static final IJavaMethod FLOAT_VALUE = JavaMethod.get(EMPTY_REGISTRY, Number.class, "floatValue");
	protected static final IJavaMethod DOUBLE_VALUE = JavaMethod.get(EMPTY_REGISTRY, Number.class, "doubleValue");

	protected static final IJavaMethod BOOL_TOSTRING_STATIC = JavaMethod.get(EMPTY_REGISTRY, Boolean.class, "toString", boolean.class);
	protected static final IJavaMethod BYTE_TOSTRING_STATIC = JavaMethod.get(EMPTY_REGISTRY, Byte.class, "toString", byte.class);
	protected static final IJavaMethod SHORT_TOSTRING_STATIC = JavaMethod.get(EMPTY_REGISTRY, Short.class, "toString", short.class);
	protected static final IJavaMethod INT_TOSTRING_STATIC = JavaMethod.get(EMPTY_REGISTRY, Integer.class, "toString", int.class);
	protected static final IJavaMethod LONG_TOSTRING_STATIC = JavaMethod.get(EMPTY_REGISTRY, Long.class, "toString", long.class);
	protected static final IJavaMethod FLOAT_TOSTRING_STATIC = JavaMethod.get(EMPTY_REGISTRY, Float.class, "toString", float.class);
	protected static final IJavaMethod DOUBLE_TOSTRING_STATIC = JavaMethod.get(EMPTY_REGISTRY, Double.class, "toString", double.class);

	protected static final IJavaMethod BOOL_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Boolean.class, "toString");
	protected static final IJavaMethod BYTE_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Byte.class, "toString");
	protected static final IJavaMethod SHORT_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Short.class, "toString");
	protected static final IJavaMethod INT_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Integer.class, "toString");
	protected static final IJavaMethod LONG_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Long.class, "toString");
	protected static final IJavaMethod FLOAT_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Float.class, "toString");
	protected static final IJavaMethod DOUBLE_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Double.class, "toString");

	protected static final IJavaMethod PARSE_BOOL = JavaMethod.get(EMPTY_REGISTRY, Boolean.class, "parseBoolean", String.class);
	protected static final IJavaMethod PARSE_BYTE = JavaMethod.get(EMPTY_REGISTRY, Byte.class, "parseByte", String.class);
	protected static final IJavaMethod PARSE_SHORT = JavaMethod.get(EMPTY_REGISTRY, Short.class, "parseShort", String.class);
	protected static final IJavaMethod PARSE_INT = JavaMethod.get(EMPTY_REGISTRY, Integer.class, "parseInt", String.class);
	protected static final IJavaMethod PARSE_LONG = JavaMethod.get(EMPTY_REGISTRY, Long.class, "parseLong", String.class);
	protected static final IJavaMethod PARSE_FLOAT = JavaMethod.get(EMPTY_REGISTRY, Float.class, "parseFloat", String.class);
	protected static final IJavaMethod PARSE_DOUBLE = JavaMethod.get(EMPTY_REGISTRY, Double.class, "parseDouble", String.class);

	protected static final IJavaMethod PARSE_BOOL_OBJECT = JavaMethod.get(EMPTY_REGISTRY, Boolean.class, "valueOf", String.class);
	protected static final IJavaMethod PARSE_BYTE_OBJECT = JavaMethod.get(EMPTY_REGISTRY, Byte.class, "valueOf", String.class);
	protected static final IJavaMethod PARSE_SHORT_OBJECT = JavaMethod.get(EMPTY_REGISTRY, Short.class, "valueOf", String.class);
	protected static final IJavaMethod PARSE_INT_OBJECT = JavaMethod.get(EMPTY_REGISTRY, Integer.class, "valueOf", String.class);
	protected static final IJavaMethod PARSE_LONG_OBJECT = JavaMethod.get(EMPTY_REGISTRY, Long.class, "valueOf", String.class);
	protected static final IJavaMethod PARSE_FLOAT_OBJECT = JavaMethod.get(EMPTY_REGISTRY, Float.class, "valueOf", String.class);
	protected static final IJavaMethod PARSE_DOUBLE_OBJECT = JavaMethod.get(EMPTY_REGISTRY, Double.class, "valueOf", String.class);

	public static ZenType parse(String type, IEnvironmentGlobal environment) {
		try {
			ZenTokener parser = new ZenTokener(type, environment.getEnvironment());
			return read(parser, environment);
		} catch (IOException ex) {
			return null;
		}
	}

	public static ZenType read(ZenTokener parser, IEnvironmentGlobal environment) {
		ZenType base;

		Token next = parser.next();
		switch (next.getType()) {
			case ZenTokener.T_ANY:
				base = ANY;
				break;
			case ZenTokener.T_VOID:
				base = VOID;
				break;
			case ZenTokener.T_BOOL:
				base = BOOL;
				break;
			case ZenTokener.T_BYTE:
				base = BYTE;
				break;
			case ZenTokener.T_SHORT:
				base = SHORT;
				break;
			case ZenTokener.T_INT:
				base = INT;
				break;
			case ZenTokener.T_LONG:
				base = LONG;
				break;
			case ZenTokener.T_FLOAT:
				base = FLOAT;
				break;
			case ZenTokener.T_DOUBLE:
				base = DOUBLE;
				break;
			case ZenTokener.T_STRING:
				base = STRING;
				break;
			case ZenTokener.T_ID:
				base = ANY;

				StringBuilder typeName = new StringBuilder();
				typeName.append(next.getValue());
				IPartialExpression partial = environment.getValue(next.getValue(), next.getPosition());
				while (parser.optional(ZenTokener.T_DOT) != null) {
					typeName.append('.');

					Token member = parser.required(ZenTokener.T_ID, "identifier expected");
					typeName.append(member.getValue());
					partial = partial.getMember(member.getPosition(), environment, member.getValue());

					if (partial == null) {
						environment.error(member.getPosition(), "could not find type " + typeName);
						break;
					}
				}
				if (partial != null) {
					base = partial.toType(environment);
				}
				break;
			default:
				throw new ParseException(next, "Unknown type: " + next.getValue());
		}

		while (parser.optional(ZenTokener.T_SQBROPEN) != null) {
			if (parser.optional(ZenTokener.T_SQBRCLOSE) == null) {
				base = new ZenTypeAssociative(base, read(parser, environment));
				parser.required(ZenTokener.T_SQBRCLOSE, "] expected");
			} else {
				base = new ZenTypeArrayBasic(base);
			}
		}

		return base;
	}

	private Map<ZenType, ICastingRule> castingRules = null;

	public abstract Expression unary(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression value,
			OperatorType operator);

	public abstract Expression binary(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression left,
			Expression right,
			OperatorType operator);

	public abstract Expression trinary(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression first,
			Expression second,
			Expression third,
			OperatorType operator);

	public abstract Expression compare(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression left,
			Expression right,
			CompareType type);

	public abstract IPartialExpression getMember(
			ZenPosition position,
			IEnvironmentGlobal environment,
			IPartialExpression value,
			String name);

	public abstract IPartialExpression getStaticMember(
			ZenPosition position,
			IEnvironmentGlobal environment,
			String name);

	public abstract Expression call(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression receiver,
			Expression... arguments);

	public ZenType[] predictCallTypes(int numArguments) {
		return new ZenType[numArguments];
	}

	public abstract void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters);

	public abstract IZenIterator makeIterator(
			int numValues,
			IEnvironmentMethod methodOutput);

	public ICastingRule getCastingRule(ZenType type, IEnvironmentGlobal environment) {
		if (castingRules == null) {
			castingRules = new HashMap<>();
			constructCastingRules(environment, new CastingRuleDelegateMap(this, castingRules), true);
		}

		return castingRules.get(type);
	}

	public final boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return equals(type) || getCastingRule(type, environment) != null;

	}

	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return canCastImplicit(type, environment);
	}

	public abstract Class toJavaClass();

	public abstract Type toASMType();

	public abstract int getNumberType();

	public abstract String getSignature();

	public abstract boolean isPointer();

	public final void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		if (equals(type))
			return;

		ICastingRule castingRule = getCastingRule(type, environment);
		if (castingRule != null) {
			castingRule.compile(environment);
		} else {
			throw new RuntimeException("Cannot cast " + getName() + " to " + type.getName());
		}
	}

	public abstract String getAnyClassName(IEnvironmentGlobal environment);

	public abstract String getName();

	public abstract Expression defaultValue(ZenPosition position);

	public boolean isLarge() {
		return false;
	}

	@Override
	public String toString() {
		return getName();
	}

	protected Expression unaryExpansion(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			return expansion.unary(position, environment, value, operator);
		}
		return null;
	}

	protected Expression binaryExpansion(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression left,
			Expression right,
			OperatorType operator) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			return expansion.binary(position, environment, left, right, operator);
		}
		return null;
	}

	protected Expression trinaryExpansion(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression first,
			Expression second,
			Expression third,
			OperatorType operator) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			return expansion.ternary(position, environment, first, second, third, operator);
		}
		return null;
	}

	protected IPartialExpression memberExpansion(ZenPosition position, IEnvironmentGlobal environment, Expression value, String member) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			return expansion.instanceMember(position, environment, value, member);
		}
		return null;
	}

	protected IPartialExpression staticMemberExpansion(ZenPosition position, IEnvironmentGlobal environment, String member) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			return expansion.staticMember(position, environment, member);
		}
		return null;
	}

	protected void constructExpansionCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			expansion.constructCastingRules(environment, rules);
		}
	}

	protected Expression castExpansion(
			ZenPosition position,
			IEnvironmentGlobal environment,
			Expression value,
			ZenType toType) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			ZenExpandCaster caster = expansion.getCaster(toType, environment);
			if (caster != null) {
				return caster.cast(position, environment, value);
			}
		}
		return null;
	}

	protected boolean canCastExpansion(IEnvironmentGlobal environment, ZenType toType) {
		String name = getName();
		TypeExpansion expansion = environment.getExpansion(name);
		if (expansion != null) {
			ZenExpandCaster caster = expansion.getCaster(toType, environment);
			if (caster != null) {
				return true;
			}
		}

		return false;
	}

	protected boolean compileCastExpansion(ZenPosition position, IEnvironmentMethod environment, ZenType toType) {
		TypeExpansion expansion = environment.getExpansion(getName());
		if (expansion != null) {
			ZenExpandCaster caster = expansion.getCaster(toType, environment);
			if (caster != null) {
				caster.compile(environment.getOutput());
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof ZenType && getName().equals(((ZenType) other).getName());
	}
}
