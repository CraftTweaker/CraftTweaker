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
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.*;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.AnyClassWriter;
import stanhebben.zenscript.util.IAnyDefinition;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import stanhebben.zenscript.value.IAny;

import static stanhebben.zenscript.util.AnyClassWriter.*;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

public class ZenTypeByte extends ZenType {
	public static final ZenTypeByte INSTANCE = new ZenTypeByte();

	private static final String ANY_NAME = "any/AnyByte";
	private static final String ANY_NAME_2 = "any.AnyByte";

	private ZenTypeByte() {
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}

	@Override
	public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {

		rules.registerCastingRule(BYTEOBJECT, new CastingRuleStaticMethod(BYTE_VALUEOF));
		rules.registerCastingRule(SHORT, new CastingRuleNone(BYTE, SHORT));
		rules.registerCastingRule(SHORTOBJECT, new CastingRuleStaticMethod(SHORT_VALUEOF));
		rules.registerCastingRule(INT, new CastingRuleNone(BYTE, INT));
		rules.registerCastingRule(INTOBJECT, new CastingRuleStaticMethod(INT_VALUEOF));
		rules.registerCastingRule(LONG, new CastingRuleI2L(null));
		rules.registerCastingRule(LONGOBJECT, new CastingRuleStaticMethod(LONG_VALUEOF, new CastingRuleI2L(null)));
		rules.registerCastingRule(FLOAT, new CastingRuleI2F(null));
		rules.registerCastingRule(FLOATOBJECT, new CastingRuleStaticMethod(FLOAT_VALUEOF, new CastingRuleI2F(null)));
		rules.registerCastingRule(DOUBLE, new CastingRuleI2D(null));
		rules.registerCastingRule(DOUBLEOBJECT, new CastingRuleStaticMethod(DOUBLE_VALUEOF, new CastingRuleI2D(null)));
		rules.registerCastingRule(STRING, new CastingRuleStaticMethod(BYTE_TOSTRING_STATIC));
		rules.registerCastingRule(ANY, new CastingRuleStaticMethod(JavaMethod.getStatic(getAnyClassName(environment), "valueOf", ANY, BYTE)));

		if (followCasters) {
			constructExpansionCastingRules(environment, rules);
		}
	}

	/*
	 * @Override public boolean canCastImplicit(ZenType type, IEnvironmentGlobal
	 * environment) { int itype = type.getNumberType(); return itype != 0 ||
	 * type == STRING || type == ANY || canCastExpansion(environment, type); }
	 * 
	 * @Override public boolean canCastExplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type.getNumberType() != 0 || type == STRING || type
	 * == ANY || canCastExpansion(environment, type); }
	 * 
	 * @Override public Expression cast(ZenPosition position, IEnvironmentGlobal
	 * environment, Expression value, ZenType type) { if (type.getNumberType() >
	 * 0 || type == STRING || type == ANY) { return new ExpressionAs(position,
	 * value, type); } else if (canCastExpansion(environment, type)) { return
	 * castExpansion(position, environment, value, type); } else { return new
	 * ExpressionAs(position, value, type); } }
	 */

	@Override
	public Class toJavaClass() {
		return byte.class;
	}

	@Override
	public Type toASMType() {
		return Type.BYTE_TYPE;
	}

	@Override
	public int getNumberType() {
		return NUM_BYTE;
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
		if (result == null) {
			environment.error(position, "bool value has no member named " + name);
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
		return "B";
	}

	@Override
	public boolean isPointer() {
		return false;
	}

	/*
	 * @Override public void compileCast(ZenPosition position,
	 * IEnvironmentMethod environment, ZenType type) { MethodOutput output =
	 * environment.getOutput();
	 * 
	 * if (type == BYTE) { // nothing to do } else if (type ==
	 * ZenTypeByteObject.INSTANCE) { output.invokeStatic(Byte.class, "valueOf",
	 * Byte.class, byte.class); } else if (type == SHORT) { // nothing to do }
	 * else if (type == ZenTypeShortObject.INSTANCE) {
	 * output.invokeStatic(Short.class, "valueOf", Short.class, short.class); }
	 * else if (type == INT) { // nothing to do } else if (type ==
	 * ZenTypeIntObject.INSTANCE) { output.invokeStatic(Integer.class,
	 * "valueOf", Integer.class, int.class); } else if (type == LONG) {
	 * output.i2l(); } else if (type == ZenTypeLongObject.INSTANCE) {
	 * output.i2l(); output.invokeStatic(Long.class, "valueOf", Long.class,
	 * long.class); } else if (type == FLOAT) { output.i2f(); } else if (type ==
	 * ZenTypeFloatObject.INSTANCE) { output.i2f();
	 * output.invokeStatic(Float.class, "valueOf", Float.class, float.class); }
	 * else if (type == DOUBLE) { output.i2d(); } else if (type ==
	 * ZenTypeDoubleObject.INSTANCE) { output.i2d();
	 * output.invokeStatic(Double.class, "valueOf", Double.class, double.class);
	 * } else if (type == STRING) { output.invokeStatic(Byte.class, "toString",
	 * String.class, byte.class); } else if (type == ANY) {
	 * output.invokeStatic(getAnyClassName(environment), "valueOf", "(B)" +
	 * signature(IAny.class)); } else if (!compileCastExpansion(position,
	 * environment, type)) { environment.error(position, "cannot cast " + this +
	 * " to " + type); } }
	 */

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
		environment.error(position, "byte doesn't support this operation");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		return new ExpressionArithmeticCompare(position, type, left, right.cast(position, environment, this));
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "cannot call a byte value");
		return new ExpressionInvalid(position, ZenTypeByte.INSTANCE);
	}

	@Override
	public String getName() {
		return "byte";
	}

	@Override
	public String getAnyClassName(IEnvironmentGlobal environment) {
		if (!environment.containsClass(ANY_NAME_2)) {
			environment.putClass(ANY_NAME_2, new byte[0]);
			environment.putClass(ANY_NAME_2, AnyClassWriter.construct(new AnyDefinitionByte(environment), ANY_NAME, Type.BYTE_TYPE));
		}

		return ANY_NAME;
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionInt(position, 0, ZenType.BYTE);
	}

	private class AnyDefinitionByte implements IAnyDefinition {
		private final IEnvironmentGlobal environment;

		public AnyDefinitionByte(IEnvironmentGlobal environment) {
			this.environment = environment;
		}

		@Override
		public void defineMembers(ClassVisitor output) {
			output.visitField(Opcodes.ACC_PRIVATE, "value", "B", null, null);

			MethodOutput valueOf = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "valueOf", "(B)" + signature(IAny.class), null, null);
			valueOf.start();
			valueOf.newObject(ANY_NAME);
			valueOf.dup();
			valueOf.load(Type.BYTE_TYPE, 0);
			valueOf.construct(ANY_NAME, "B");
			valueOf.returnObject();
			valueOf.end();

			MethodOutput constructor = new MethodOutput(output, Opcodes.ACC_PUBLIC, "<init>", "(B)V", null, null);
			constructor.start();
			constructor.loadObject(0);
			constructor.invokeSpecial(internal(Object.class), "<init>", "()V");
			constructor.loadObject(0);
			constructor.load(Type.BYTE_TYPE, 1);
			constructor.putField(ANY_NAME, "value", "B");
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
				expansion.compileAnyCanCastImplicit(BYTE, output, environment, 0);
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
				expansion.compileAnyCast(BYTE, output, environment, 0, 1);
			}

			throwCastException(output, "byte", 1);
		}

		@Override
		public void defineNot(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.iNot();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineNeg(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.iNeg();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineAdd(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iAdd();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineSub(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iSub();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
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
			output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, int.class);
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
			METHOD_ASBYTE.invokeVirtual(output);
			output.iMul();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineDiv(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iDiv();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineMod(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iRem();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineAnd(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iAnd();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineOr(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iOr();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineXor(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iXor();
			output.invokeSpecial(ANY_NAME, "<init>", "(B)V");
			output.returnObject();
		}

		@Override
		public void defineRange(MethodOutput output) {
			// TODO
			output.aConstNull();
			output.returnObject();
		}

		@Override
		public void defineCompareTo(MethodOutput output) {
			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.iSub();
			output.returnInt();
		}

		@Override
		public void defineContains(MethodOutput output) {
			throwUnsupportedException(output, "byte", "in");
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
			throwUnsupportedException(output, "byte", "get []");
		}

		@Override
		public void defineIndexSet(MethodOutput output) {
			throwUnsupportedException(output, "byte", "set []");
		}

		@Override
		public void defineCall(MethodOutput output) {
			throwUnsupportedException(output, "byte", "call");
		}

		@Override
		public void defineAsBool(MethodOutput output) {
			throwCastException(output, ANY_NAME, "bool");
		}

		@Override
		public void defineAsByte(MethodOutput output) {
			getValue(output);
			output.returnType(Type.BYTE_TYPE);
		}

		@Override
		public void defineAsShort(MethodOutput output) {
			getValue(output);
			output.returnType(Type.SHORT_TYPE);
		}

		@Override
		public void defineAsInt(MethodOutput output) {
			getValue(output);
			output.returnType(Type.INT_TYPE);
		}

		@Override
		public void defineAsLong(MethodOutput output) {
			getValue(output);
			output.i2l();
			output.returnType(Type.LONG_TYPE);
		}

		@Override
		public void defineAsFloat(MethodOutput output) {
			getValue(output);
			output.i2f();
			output.returnType(Type.FLOAT_TYPE);
		}

		@Override
		public void defineAsDouble(MethodOutput output) {
			getValue(output);
			output.i2d();
			output.returnType(Type.DOUBLE_TYPE);
		}

		@Override
		public void defineAsString(MethodOutput output) {
			getValue(output);
			output.invokeStatic(Byte.class, "toString", String.class, byte.class);
			output.returnObject();
		}

		@Override
		public void defineAs(MethodOutput output) {
			int localValue = output.local(Type.BYTE_TYPE);

			getValue(output);
			output.store(Type.BYTE_TYPE, localValue);
			TypeExpansion expansion = environment.getExpansion(getName());
			if (expansion != null) {
				expansion.compileAnyCast(BYTE, output, environment, localValue, 1);
			}

			throwCastException(output, "byte", 1);
		}

		@Override
		public void defineIs(MethodOutput output) {
			Label lblEq = new Label();

			output.loadObject(1);
			output.constant(Type.BYTE_TYPE);
			output.ifACmpEq(lblEq);
			output.iConst0();
			output.returnInt();
			output.label(lblEq);
			output.iConst1();
			output.returnInt();
		}

		@Override
		public void defineGetNumberType(MethodOutput output) {
			output.constant(IAny.NUM_BYTE);
			output.returnInt();
		}

		@Override
		public void defineIteratorSingle(MethodOutput output) {
			throwUnsupportedException(output, "byte", "iterator");
		}

		@Override
		public void defineIteratorMulti(MethodOutput output) {
			throwUnsupportedException(output, "byte", "iterator");
		}

		@Override
		public void defineEquals(MethodOutput output) {
			Label lblNope = new Label();

			output.loadObject(1);
			output.instanceOf(IAny.NAME);
			output.ifEQ(lblNope);

			getValue(output);
			output.loadObject(1);
			METHOD_ASBYTE.invokeVirtual(output);
			output.ifICmpNE(lblNope);

			output.iConst1();
			output.returnInt();

			output.label(lblNope);
			output.iConst0();
			output.returnInt();
		}

		@Override
		public void defineHashCode(MethodOutput output) {
			getValue(output);
			output.returnInt();
		}

		private void getValue(MethodOutput output) {
			output.loadObject(0);
			output.getField(ANY_NAME, "value", "B");
		}
	}
}
