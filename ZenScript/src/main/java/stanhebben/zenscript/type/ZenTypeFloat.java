package stanhebben.zenscript.type;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionArithmeticBinary;
import stanhebben.zenscript.expression.ExpressionArithmeticCompare;
import stanhebben.zenscript.expression.ExpressionArithmeticUnary;
import stanhebben.zenscript.expression.ExpressionFloat;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import static stanhebben.zenscript.type.ZenType.ANY;
import static stanhebben.zenscript.type.ZenType.BYTE;
import static stanhebben.zenscript.type.ZenType.BYTEOBJECT;
import static stanhebben.zenscript.type.ZenType.BYTE_VALUEOF;
import static stanhebben.zenscript.type.ZenType.LONG;
import static stanhebben.zenscript.type.ZenType.STRING;
import stanhebben.zenscript.type.casting.CastingRuleF2D;
import stanhebben.zenscript.type.casting.CastingRuleF2I;
import stanhebben.zenscript.type.casting.CastingRuleF2L;
import stanhebben.zenscript.type.casting.CastingRuleI2B;
import stanhebben.zenscript.type.casting.CastingRuleI2S;
import stanhebben.zenscript.type.casting.CastingRuleStaticMethod;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.AnyClassWriter;
import static stanhebben.zenscript.util.AnyClassWriter.METHOD_ASFLOAT;
import static stanhebben.zenscript.util.AnyClassWriter.METHOD_ASSTRING;
import static stanhebben.zenscript.util.AnyClassWriter.throwCastException;
import static stanhebben.zenscript.util.AnyClassWriter.throwUnsupportedException;
import stanhebben.zenscript.util.IAnyDefinition;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;
import stanhebben.zenscript.value.IAny;

public class ZenTypeFloat extends ZenType {
	public static final ZenTypeFloat INSTANCE = new ZenTypeFloat();
	
	//private static final JavaMethod FLOAT_TOSTRING = JavaMethod.get(EMPTY_REGISTRY, Float.class, "toString", float.class);
	private static final String ANY_NAME = "any/AnyFloat";
	private static final String ANY_NAME_2 = "any.AnyFloat";
	
	private ZenTypeFloat() {}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}

	@Override
	public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
		rules.registerCastingRule(BYTE, new CastingRuleI2B(new CastingRuleF2I(null)));
		rules.registerCastingRule(BYTEOBJECT, new CastingRuleStaticMethod(BYTE_VALUEOF, new CastingRuleI2B(new CastingRuleF2I(null))));
		rules.registerCastingRule(SHORT, new CastingRuleI2S(new CastingRuleF2I(null)));
		rules.registerCastingRule(SHORTOBJECT, new CastingRuleStaticMethod(SHORT_VALUEOF, new CastingRuleI2S(new CastingRuleF2I(null))));
		rules.registerCastingRule(INT, new CastingRuleF2I(null));
		rules.registerCastingRule(INTOBJECT, new CastingRuleStaticMethod(INT_VALUEOF, new CastingRuleF2I(null)));
		rules.registerCastingRule(LONG, new CastingRuleF2L(null));
		rules.registerCastingRule(LONGOBJECT, new CastingRuleStaticMethod(LONG_VALUEOF, new CastingRuleF2L(null)));
		rules.registerCastingRule(FLOATOBJECT, new CastingRuleStaticMethod(FLOAT_VALUEOF));
		rules.registerCastingRule(DOUBLE, new CastingRuleF2D(null));
		rules.registerCastingRule(DOUBLEOBJECT, new CastingRuleStaticMethod(DOUBLE_VALUEOF, new CastingRuleF2D(null)));
		
		rules.registerCastingRule(STRING, new CastingRuleStaticMethod(FLOAT_TOSTRING_STATIC));
		rules.registerCastingRule(ANY, new CastingRuleStaticMethod(JavaMethod.getStatic(getAnyClassName(environment), "valueOf", ANY, FLOAT)));
		
		if (followCasters) {
			constructExpansionCastingRules(environment, rules);
		}
	}

	/*@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return (type.getNumberType() != 0)
				|| type == ZenTypeString.INSTANCE
				|| type == ANY
				|| canCastExpansion(environment, type);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type.getNumberType() != 0
				|| type == ZenTypeString.INSTANCE
				|| type == ANY
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
	}*/

	@Override
	public Type toASMType() {
		return Type.FLOAT_TYPE;
	}

	@Override
	public int getNumberType() {
		return NUM_FLOAT;
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
		if (result == null) {
			environment.error(position, "float value has no members");
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
		return "F";
	}

	@Override
	public boolean isPointer() {
		return false;
	}

	/*@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		MethodOutput output = environment.getOutput();
		
		if (type == BYTE) {
			output.f2i();
			output.i2b();
		} else if (type == ZenTypeByteObject.INSTANCE) {
			output.f2i();
			output.i2b();
			output.invokeStatic(Byte.class, "valueOf", Byte.class, byte.class);
		} else if (type == SHORT) {
			output.f2i();
			output.i2s();
		} else if (type == ZenTypeShortObject.INSTANCE) {
			output.f2i();
			output.i2s();
			output.invokeStatic(Short.class, "valueOf", Short.class, short.class);
		} else if (type == INT) {
			output.f2i();
		} else if (type == ZenTypeIntObject.INSTANCE) {
			output.f2i();
			output.invokeStatic(Integer.class, "valueOf", Integer.class, int.class);
		} else if (type == LONG) {
			output.f2l();
		} else if (type == ZenTypeLongObject.INSTANCE) {
			output.f2l();
			output.invokeStatic(Long.class, "valueOf", Long.class, long.class);
		} else if (type == FLOAT) {
			// nothing to do
		} else if (type == ZenTypeFloatObject.INSTANCE) {
			output.invokeStatic(Float.class, "valueOf", Float.class, float.class);
		} else if (type == DOUBLE) {
			output.f2d();
		} else if (type == ZenTypeDoubleObject.INSTANCE) {
			output.f2d();
			output.invokeStatic(Double.class, "valueOf", Double.class, double.class);
		} else if (type == STRING) {
			output.invokeStatic(Float.class, "toString", String.class, float.class);
		} else if (type == ANY) {
			output.invokeStatic(getAnyClassName(environment), "valueOf", "(F)" + signature(IAny.class));
		} else if (!compileCastExpansion(position, environment, type)) {
			environment.error(position, "cannot cast " + this + " to " + type);
		}
	}*/
	
	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		return new ExpressionArithmeticUnary(position, operator, value);
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		if (operator == OperatorType.CAT) {
			return STRING.binary(
					position,
					environment,
					left.cast(position, environment, STRING),
					right.cast(position, environment, STRING), OperatorType.CAT);
		}
		
		return new ExpressionArithmeticBinary(position, operator, left, right.cast(position, environment, this));
	}
	
	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		environment.error(position, "float doesn't support this operation");
		return new ExpressionInvalid(position);
	}
	
	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		return new ExpressionArithmeticCompare(position, type, left, right.cast(position, environment, this));
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "cannot call a float value");
		return new ExpressionInvalid(position);
	}

	@Override
	public Class toJavaClass() {
		return float.class;
	}

	@Override
	public String getName() {
		return "float";
	}
	
	@Override
	public String getAnyClassName(IEnvironmentGlobal environment) {
		if (!environment.containsClass(ANY_NAME_2)) {
			environment.putClass(ANY_NAME_2, new byte[0]);
			environment.putClass(ANY_NAME_2, AnyClassWriter.construct(new AnyDefinitionFloat(environment), ANY_NAME, Type.FLOAT_TYPE));
		}
		
		return ANY_NAME;
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionFloat(position, 0.0, FLOAT);
	}
	
	private class AnyDefinitionFloat implements IAnyDefinition {
		private final IEnvironmentGlobal environment;
		
		public AnyDefinitionFloat(IEnvironmentGlobal environment) {
			this.environment = environment;
		}

		@Override
		public void defineMembers(ClassVisitor output) {
			output.visitField(Opcodes.ACC_PRIVATE, "value", "F", null, null);
			
			MethodOutput valueOf = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "valueOf", "(F)" + signature(IAny.class), null, null);
			valueOf.start();
			valueOf.newObject(ANY_NAME);
			valueOf.dup();
			valueOf.load(Type.FLOAT_TYPE, 0);
			valueOf.construct(ANY_NAME, "F");
			valueOf.returnObject();
			valueOf.end();
			
			MethodOutput constructor = new MethodOutput(output, Opcodes.ACC_PUBLIC, "<init>", "(F)V", null, null);
			constructor.start();
			constructor.loadObject(0);
			constructor.invokeSpecial(internal(Object.class), "<init>", "()V");
			constructor.loadObject(0);
			constructor.load(Type.FLOAT_TYPE, 1);
			constructor.putField(ANY_NAME, "value", "F");
			constructor.returnType(Type.VOID_TYPE);
			constructor.end();
		}
		
		@Override
		public void defineStaticCanCastImplicit(MethodOutput output) {
			Label lblCan = new Label();
			
			output.constant(Type.BYTE_TYPE);
			output.loadObject(0);
			output.ifACmpEq(lblCan);
			
			output.constant(Type.SHORT_TYPE);
			output.loadObject(0);
			output.ifACmpEq(lblCan);
			
			output.constant(Type.INT_TYPE);
			output.loadObject(0);
			output.ifACmpEq(lblCan);
			
			output.constant(Type.LONG_TYPE);
			output.loadObject(0);
			output.ifACmpEq(lblCan);
			
			output.constant(Type.FLOAT_TYPE);
			output.loadObject(0);
			output.ifACmpEq(lblCan);
			
			output.constant(Type.DOUBLE_TYPE);
			output.loadObject(0);
			output.ifACmpEq(lblCan);
			
			TypeExpansion expansion = environment.getExpansion(getName());
			if (expansion != null) {
				expansion.compileAnyCanCastImplicit(FLOAT, output, environment, 0);
			}
			
			output.iConst0();
			output.returnInt();
			
			output.label(lblCan);
			output.iConst1();
			output.returnInt();
		}

		@Override
		public void defineStaticAs(MethodOutput output) {
			TypeExpansion expansion = environment.getExpansion(getName());
			if (expansion != null) {
				expansion.compileAnyCast(FLOAT, output, environment, 0, 1);
			}
			
			throwCastException(output, "float", 1);
		}

		@Override
		public void defineNot(MethodOutput output) {
			throwUnsupportedException(output, "float", "not");
		}

		@Override
		public void defineNeg(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.fNeg();
			output.invokeSpecial(ANY_NAME, "<init>", "(F)V");
			output.returnObject();
		}

		@Override
		public void defineAdd(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASFLOAT.invokeVirtual(output);
			output.fAdd();
			output.invokeSpecial(ANY_NAME, "<init>", "(F)V");
			output.returnObject();
		}

		@Override
		public void defineSub(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASFLOAT.invokeVirtual(output);
			output.fSub();
			output.invokeSpecial(ANY_NAME, "<init>", "(F)V");
			output.returnObject();
		}

		@Override
		public void defineCat(MethodOutput output) {
			// StringBuilder builder = new StringBuilder();
			// builder.append(value);
			// builder.append(other.asString());
			// return new AnyString(builder.toString());
			output.newObject(StringBuilder.class);
			output.dup();
			output.invokeSpecial(internal(StringBuilder.class), "<init>", "()V");
			getValue(output);
			output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, float.class);
			output.loadObject(1);
			METHOD_ASSTRING.invokeVirtual(output);
			output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, String.class);
			output.invokeVirtual(StringBuilder.class, "toString", String.class);
			output.invokeStatic(STRING.getAnyClassName(environment), "valueOf", "(Ljava/lang/String;)" + signature(IAny.class));
			output.returnObject();
		}

		@Override
		public void defineMul(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASFLOAT.invokeVirtual(output);
			output.fMul();
			output.invokeSpecial(ANY_NAME, "<init>", "(F)V");
			output.returnObject();
		}

		@Override
		public void defineDiv(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASFLOAT.invokeVirtual(output);
			output.fDiv();
			output.invokeSpecial(ANY_NAME, "<init>", "(F)V");
			output.returnObject();
		}

		@Override
		public void defineMod(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASFLOAT.invokeVirtual(output);
			output.fRem();
			output.invokeSpecial(ANY_NAME, "<init>", "(F)V");
			output.returnObject();
		}

		@Override
		public void defineAnd(MethodOutput output) {
			throwUnsupportedException(output, "float", "and");
		}

		@Override
		public void defineOr(MethodOutput output) {
			throwUnsupportedException(output, "float", "or");
		}

		@Override
		public void defineXor(MethodOutput output) {
			throwUnsupportedException(output, "float", "xor");
		}

		@Override
		public void defineRange(MethodOutput output) {
			throwUnsupportedException(output, "float", "range");
		}

		@Override
		public void defineCompareTo(MethodOutput output) {
			// return Float.compare(x, y)
			getValue(output);
			output.loadObject(1);
			METHOD_ASFLOAT.invokeVirtual(output);
			output.invokeStatic(Float.class, "compare", int.class, float.class, float.class);
			output.returnInt();
		}

		@Override
		public void defineContains(MethodOutput output) {
			throwUnsupportedException(output, "float", "in");
		}

		@Override
		public void defineMemberGet(MethodOutput output) {
			// TODO
			output.aConstNull();
			output.returnObject();
		}

		@Override
		public void defineMemberSet(MethodOutput output) {
			// TODO
			output.returnType(Type.VOID_TYPE);
		}

		@Override
		public void defineMemberCall(MethodOutput output) {
			// TODO
			output.aConstNull();
			output.returnObject();
		}

		@Override
		public void defineIndexGet(MethodOutput output) {
			throwUnsupportedException(output, "float", "get []");
		}

		@Override
		public void defineIndexSet(MethodOutput output) {
			throwUnsupportedException(output, "float", "set []");
		}

		@Override
		public void defineCall(MethodOutput output) {
			throwUnsupportedException(output, "float", "call");
		}

		@Override
		public void defineAsBool(MethodOutput output) {
			throwCastException(output, ANY_NAME, "bool");
		}

		@Override
		public void defineAsByte(MethodOutput output) {
			getValue(output);
			output.f2i();
			output.i2b();
			output.returnType(Type.BYTE_TYPE);
		}

		@Override
		public void defineAsShort(MethodOutput output) {
			getValue(output);
			output.f2i();
			output.i2s();
			output.returnType(Type.SHORT_TYPE);
		}

		@Override
		public void defineAsInt(MethodOutput output) {
			getValue(output);
			output.f2i();
			output.returnType(Type.INT_TYPE);
		}

		@Override
		public void defineAsLong(MethodOutput output) {
			getValue(output);
			output.f2l();
			output.returnType(Type.LONG_TYPE);
		}

		@Override
		public void defineAsFloat(MethodOutput output) {
			getValue(output);
			output.returnType(Type.FLOAT_TYPE);
		}

		@Override
		public void defineAsDouble(MethodOutput output) {
			getValue(output);
			output.f2d();
			output.returnType(Type.DOUBLE_TYPE);
		}

		@Override
		public void defineAsString(MethodOutput output) {
			getValue(output);
			output.invokeStatic(Float.class, "toString", String.class, float.class);
			output.returnObject();
		}

		@Override
		public void defineAs(MethodOutput output) {
			int localValue = output.local(Type.FLOAT_TYPE);
			
			getValue(output);
			output.store(Type.FLOAT_TYPE, localValue);
			TypeExpansion expansion = environment.getExpansion(getName());
			if (expansion != null) {
				expansion.compileAnyCast(FLOAT, output, environment, localValue, 1);
			}
			
			throwCastException(output, "float", 1);
		}

		@Override
		public void defineIs(MethodOutput output) {
			Label lblEq = new Label();
			
			output.loadObject(1);
			output.constant(Type.FLOAT_TYPE);
			output.ifACmpEq(lblEq);
			output.iConst0();
			output.returnInt();
			output.label(lblEq);
			output.iConst1();
			output.returnInt();
		}
		
		@Override
		public void defineGetNumberType(MethodOutput output) {
			output.constant(IAny.NUM_FLOAT);
			output.returnInt();
		}

		@Override
		public void defineIteratorSingle(MethodOutput output) {
			throwUnsupportedException(output, "float", "iterator");
		}

		@Override
		public void defineIteratorMulti(MethodOutput output) {
			throwUnsupportedException(output, "float", "iterator");
		}
		
		private void getValue(MethodOutput output) {
			output.loadObject(0);
			output.getField(ANY_NAME, "value", "F");
		}

		@Override
		public void defineEquals(MethodOutput output) {
			Label lblNope = new Label();
			
			output.loadObject(1);
			output.instanceOf(IAny.NAME);
			output.ifEQ(lblNope);
			
			getValue(output);
			output.loadObject(1);
			METHOD_ASFLOAT.invokeVirtual(output);
			output.fCmp();
			output.ifICmpNE(lblNope);
			
			output.iConst1();
			output.returnInt();
			
			output.label(lblNope);
			output.iConst0();
			output.returnInt();
		}

		@Override
		public void defineHashCode(MethodOutput output) {
			//return Float.floatToIntBits(value);
			getValue(output);
			output.invokeStatic(Float.class, "floatToIntBits", float.class, int.class);
			output.returnInt();
		}
	}
}
