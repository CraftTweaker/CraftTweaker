package stanhebben.zenscript.type;

import java.io.IOException;
import org.objectweb.asm.Type;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.parser.ParseException;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.expand.ZenExpandCaster;
import stanhebben.zenscript.util.ZenPosition;

public abstract class ZenType {
	public static final ZenTypeAny ANY = ZenTypeAny.INSTANCE;
	public static final ZenTypeBool BOOL = ZenTypeBool.INSTANCE;
	public static final ZenTypeByte BYTE = ZenTypeByte.INSTANCE;
	public static final ZenTypeShort SHORT = ZenTypeShort.INSTANCE;
	public static final ZenTypeInt INT = ZenTypeInt.INSTANCE;
	public static final ZenTypeLong LONG = ZenTypeLong.INSTANCE;
	public static final ZenTypeFloat FLOAT = ZenTypeFloat.INSTANCE;
	public static final ZenTypeDouble DOUBLE = ZenTypeDouble.INSTANCE;
	public static final ZenTypeString STRING = ZenTypeString.INSTANCE;
	public static final ZenTypeVoid VOID = ZenTypeVoid.INSTANCE;
	public static final ZenTypeNull NULL = ZenTypeNull.INSTANCE;
	
	public static final int NUM_BYTE = 1;
	public static final int NUM_SHORT = 2;
	public static final int NUM_INT = 3;
	public static final int NUM_LONG = 4;
	public static final int NUM_FLOAT = 5;
	public static final int NUM_DOUBLE = 6;
	
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
				IPartialExpression partial = environment.getValue(next.getValue(), next.getPosition());
				while (parser.optional(ZenTokener.T_DOT) != null) {
					Token member = parser.required(ZenTokener.T_ID, "identifier expected");
					partial = partial.getMember(member.getPosition(), environment, member.getValue());
				}
				base = partial.toType(environment);
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
	
	public abstract Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type);
	
	public abstract IZenIterator makeIterator(
			int numValues,
			IEnvironmentMethod methodOutput);
	
	public abstract boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment);

	public abstract boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment);
	
	public abstract Class toJavaClass();
	
	public abstract Type toASMType();
	
	public abstract int getNumberType();
	
	public abstract String getSignature();
	
	public abstract boolean isPointer();
	
	public abstract void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type);
	
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
				caster.compile(environment);
				return true;
			}
		}

		return false;
	}
}
