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
import stanhebben.zenscript.expression.ExpressionBool;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import static stanhebben.zenscript.type.ZenType.BYTE;
import static stanhebben.zenscript.type.ZenType.STRING;
import stanhebben.zenscript.type.casting.CastingRuleStaticMethod;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.AnyClassWriter;
import static stanhebben.zenscript.util.AnyClassWriter.METHOD_ASBOOL;
import static stanhebben.zenscript.util.AnyClassWriter.METHOD_ASBYTE;
import static stanhebben.zenscript.util.AnyClassWriter.METHOD_ASSTRING;
import static stanhebben.zenscript.util.AnyClassWriter.throwCastException;
import static stanhebben.zenscript.util.AnyClassWriter.throwUnsupportedException;
import stanhebben.zenscript.util.IAnyDefinition;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;
import stanhebben.zenscript.value.IAny;

public class ZenTypeBool extends ZenType {
	private static final String ANY_NAME = "any/AnyBool";
	private static final String ANY_NAME_2 = "any.AnyBool";
	private static final String ANY_NAME_DESC = "Lany/AnyBool;";
	
	public ZenTypeBool() {}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}
	
	@Override
	public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
		rules.registerCastingRule(STRING, new CastingRuleStaticMethod(BOOL_TOSTRING_STATIC));
		rules.registerCastingRule(BOOLOBJECT, new CastingRuleStaticMethod(BOOL_VALUEOF));
		rules.registerCastingRule(ANY, new CastingRuleStaticMethod(JavaMethod.getStatic(
				getAnyClassName(environment), "valueOf", ANY, BOOL
		)));
		
		if (followCasters) {
			constructExpansionCastingRules(environment, rules);
		}
	}
	
	/*@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == BOOL || type == ANY || type == ZenTypeString.INSTANCE || canCastExpansion(environment, type);
	}*/

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return canCastImplicit(type, environment);
	}
	
	/*@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type == BOOL || type == ZenTypeBoolObject.INSTANCE || type == STRING || type == ANY) {
			return new ExpressionAs(position, value, type);
		} else if (canCastExpansion(environment, type)) {
			return castExpansion(position, environment, value, type);
		} else {
			return new ExpressionAs(position, value, type);
		}
	}*/
	
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
		if (operator == OperatorType.CAT) {
			return STRING.binary(
					position,
					environment,
					left.cast(position, environment, STRING),
					right.cast(position, environment, STRING), OperatorType.CAT);
		}
		
		if (right.getType().canCastImplicit(BOOL, environment)) {
			switch (operator) {
				case AND:
				case OR:
				case XOR:
					if (right.getType() != BOOL) {
						right = right.cast(position, environment, BOOL);
					}
					
					return new ExpressionArithmeticBinary(position, operator, left, right);
				default:
					environment.error(position, "unsupported bool operator: " + operator);
					return new ExpressionInvalid(position, BOOL);
			}
		} else {
			environment.error(right.getPosition(), "not a valid bool value");
			return new ExpressionInvalid(position, BOOL);
		}
	}
	
	@Override
	public Expression trinary(
			ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		environment.error(position, "operation not supported on a bool value");
		return new ExpressionInvalid(position, BOOL);
	}
	
	@Override
	public Expression compare(
			ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		if (type == CompareType.EQ || type == CompareType.NE) {
			return new ExpressionArithmeticCompare(position, type, left, right);
		} else {
			environment.error(position, "such comparison not supported on a bool");
			return new ExpressionInvalid(position, BOOL);
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

	/*@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		if (type == this) {
			// nothing to do
		} else if (type == ZenTypeBoolObject.INSTANCE) {
			environment.getOutput().invokeStatic(Boolean.class, "valueOf", Boolean.class, boolean.class);
		} else if (type == STRING) {
			environment.getOutput().invokeStatic(Boolean.TYPE, "toString", String.class, boolean.class);
		} else if (type == ANY) {
			environment.getOutput().invokeStatic(getAnyClassName(environment), "valueOf", "(Z)" + signature(IAny.class));
		} else if (!compileCastExpansion(position, environment, type)) {
			environment.error(position, "Cannot compile bool to " + type);
		}
	}*/

	@Override
	public String getName() {
		return "bool";
	}
	
	@Override
	public String getAnyClassName(IEnvironmentGlobal environment) {
		if (!environment.containsClass(ANY_NAME_2)) {
			environment.putClass(ANY_NAME_2, new byte[0]);
			environment.putClass(ANY_NAME_2, AnyClassWriter.construct(new AnyDefinitionBool(environment), ANY_NAME, Type.BOOLEAN_TYPE));
		}
		
		return ANY_NAME;
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionBool(position, false);
	}
	
	private class AnyDefinitionBool implements IAnyDefinition {
		private final IEnvironmentGlobal environment;
		
		public AnyDefinitionBool(IEnvironmentGlobal environment) {
			this.environment = environment;
		}

		@Override
		public void defineMembers(ClassVisitor output) {
			output.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, "TRUE", ANY_NAME_DESC, null, null);
			output.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, "FALSE", ANY_NAME_DESC, null, null);
			
			output.visitField(Opcodes.ACC_PRIVATE, "value", "Z", null, null);
			
			MethodOutput clinit = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
			clinit.start();
			clinit.newObject(ANY_NAME);
			clinit.dup();
			clinit.iConst0();
			clinit.invokeSpecial(ANY_NAME, "<init>", "(Z)V");
			clinit.putStaticField(ANY_NAME, "FALSE", ANY_NAME_DESC);
			clinit.newObject(ANY_NAME);
			clinit.dup();
			clinit.iConst1();
			clinit.invokeSpecial(ANY_NAME, "<init>", "(Z)V");
			clinit.putStaticField(ANY_NAME, "TRUE", ANY_NAME_DESC);
			clinit.returnType(Type.VOID_TYPE);
			clinit.end();
			
			MethodOutput valueOf = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "valueOf", "(Z)" + signature(IAny.class), null, null);
			Label lblFalse = new Label();
			valueOf.start();
			valueOf.load(Type.BOOLEAN_TYPE, 0);
			valueOf.ifEQ(lblFalse);
			valueOf.getStaticField(ANY_NAME, "TRUE", ANY_NAME_DESC);
			valueOf.returnObject();
			valueOf.label(lblFalse);
			valueOf.getStaticField(ANY_NAME, "FALSE", ANY_NAME_DESC);
			valueOf.returnObject();
			valueOf.end();
			
			MethodOutput constructor = new MethodOutput(output, Opcodes.ACC_PUBLIC, "<init>", "(Z)V", null, null);
			constructor.start();
			constructor.loadObject(0);
			constructor.invokeSpecial(internal(Object.class), "<init>", "()V");
			constructor.loadObject(0);
			constructor.load(Type.BOOLEAN_TYPE, 1);
			constructor.putField(ANY_NAME, "value", "Z");
			constructor.returnType(Type.VOID_TYPE);
			constructor.end();
		}
		
		@Override
		public void defineStaticCanCastImplicit(MethodOutput output) {
			Label lblCan = new Label();
			
			output.constant(Type.BOOLEAN_TYPE);
			output.loadObject(0);
			output.ifACmpEq(lblCan);
			
			TypeExpansion expansion = environment.getExpansion(getName());
			if (expansion != null) {
				expansion.compileAnyCanCastImplicit(BOOL, output, environment, 0);
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
				expansion.compileAnyCast(BOOL, output, environment, 0, 1);
			}
			
			throwCastException(output, "bool", 1);
		}

		@Override
		public void defineNot(MethodOutput output) {
			getValue(output);
			output.iNot();
			valueOf(output);
			output.returnObject();
		}

		@Override
		public void defineNeg(MethodOutput output) {
			throwUnsupportedException(output, "bool", "negate");
		}

		@Override
		public void defineAdd(MethodOutput output) {
			throwUnsupportedException(output, "bool", "+");
		}

		@Override
		public void defineSub(MethodOutput output) {
			throwUnsupportedException(output, "bool", "-");
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
			output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, boolean.class);
			output.loadObject(1);
			METHOD_ASSTRING.invokeVirtual(output);
			output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, String.class);
			output.invokeVirtual(StringBuilder.class, "toString", String.class);
			output.invokeStatic(STRING.getAnyClassName(environment), "valueOf", "(Ljava/lang/String;)" + signature(IAny.class));
			output.returnObject();
		}

		@Override
		public void defineMul(MethodOutput output) {
			throwUnsupportedException(output, "bool", "*");
		}

		@Override
		public void defineDiv(MethodOutput output) {
			throwUnsupportedException(output, "bool", "/");
		}

		@Override
		public void defineMod(MethodOutput output) {
			throwUnsupportedException(output, "bool", "%");
		}

		@Override
		public void defineAnd(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBOOL.invokeVirtual(output);
			output.iAnd();
			valueOf(output);
			output.returnObject();
		}

		@Override
		public void defineOr(MethodOutput output) {
			output.newObject(ANY_NAME);
			output.dup();
			getValue(output);
			output.loadObject(1);
			METHOD_ASBOOL.invokeVirtual(output);
			output.iOr();
			valueOf(output);
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
			valueOf(output);
			output.returnObject();
		}

		@Override
		public void defineRange(MethodOutput output) {
			throwUnsupportedException(output, "bool", "range");
		}

		@Override
		public void defineCompareTo(MethodOutput output) {
			getValue(output);
			output.loadObject(1);
			METHOD_ASBOOL.invokeVirtual(output);
			output.iSub();
			output.returnInt();
		}

		@Override
		public void defineContains(MethodOutput output) {
			throwUnsupportedException(output, "bool", "in");
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
			throwUnsupportedException(output, "bool", "get []");
		}

		@Override
		public void defineIndexSet(MethodOutput output) {
			throwUnsupportedException(output, "bool", "set []");
		}

		@Override
		public void defineCall(MethodOutput output) {
			throwUnsupportedException(output, "bool", "call");
		}

		@Override
		public void defineAsBool(MethodOutput output) {
			throwCastException(output, ANY_NAME, "bool");
		}

		@Override
		public void defineAsByte(MethodOutput output) {
			throwCastException(output, "bool", "byte");
		}

		@Override
		public void defineAsShort(MethodOutput output) {
			throwCastException(output, "bool", "short");
		}

		@Override
		public void defineAsInt(MethodOutput output) {
			throwCastException(output, "bool", "int");
		}

		@Override
		public void defineAsLong(MethodOutput output) {
			throwCastException(output, "bool", "long");
		}

		@Override
		public void defineAsFloat(MethodOutput output) {
			throwCastException(output, "bool", "float");
		}

		@Override
		public void defineAsDouble(MethodOutput output) {
			throwCastException(output, "bool", "double");
		}

		@Override
		public void defineAsString(MethodOutput output) {
			getValue(output);
			output.invokeStatic(Boolean.class, "toString", String.class, boolean.class);
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
			
			throwCastException(output, "bool", 1);
		}

		@Override
		public void defineIs(MethodOutput output) {
			Label lblEq = new Label();
			
			output.loadObject(1);
			output.constant(Type.BOOLEAN_TYPE);
			output.ifACmpEq(lblEq);
			output.iConst0();
			output.returnInt();
			output.label(lblEq);
			output.iConst1();
			output.returnInt();
		}
		
		@Override
		public void defineGetNumberType(MethodOutput output) {
			output.iConst0();
			output.returnInt();
		}

		@Override
		public void defineIteratorSingle(MethodOutput output) {
			throwUnsupportedException(output, "bool", "iterator");
		}

		@Override
		public void defineIteratorMulti(MethodOutput output) {
			throwUnsupportedException(output, "bool", "iterator");
		}

		@Override
		public void defineEquals(MethodOutput output) {
			Label lblEqual = new Label();
			output.loadObject(0);
			output.loadObject(1);
			
			output.ifACmpEq(lblEqual);
			
			output.iConst0();
			output.returnInt();
			
			output.label(lblEqual);
			output.iConst1();
			output.returnInt();
		}

		@Override
		public void defineHashCode(MethodOutput output) {
			output.invokeSpecial("java/lang/Object", "hashCode", "()I");
		}
		
		private void getValue(MethodOutput output) {
			output.loadObject(0);
			output.getField(ANY_NAME, "value", "Z");
		}
		
		private void valueOf(MethodOutput output) {
			output.invokeStatic(ANY_NAME, "valueOf", "(Z)" + signature(IAny.class));
		}
	}
}
