package stanhebben.zenscript.type;

import org.objectweb.asm.*;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.*;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.*;
import stanhebben.zenscript.value.IAny;

import static stanhebben.zenscript.util.AnyClassWriter.*;
import static stanhebben.zenscript.util.ZenTypeUtil.*;

public class ZenTypeInt extends ZenType {
    
    public static final ZenTypeInt INSTANCE = new ZenTypeInt();
    
    // private static final JavaMethod INT_TOSTRING =
    // JavaMethod.get(EMPTY_REGISTRY, Integer.class, "toString", byte.class);
    private static final String ANY_NAME = "any/AnyInt";
    private static final String ANY_NAME_2 = "any.AnyInt";
    
    private ZenTypeInt() {
    }
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        return null;
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        rules.registerCastingRule(BYTE, new CastingRuleI2B(null));
        rules.registerCastingRule(BYTEOBJECT, new CastingRuleStaticMethod(BYTE_VALUEOF));
        rules.registerCastingRule(SHORT, new CastingRuleI2S(null));
        rules.registerCastingRule(SHORTOBJECT, new CastingRuleStaticMethod(SHORT_VALUEOF));
        rules.registerCastingRule(INTOBJECT, new CastingRuleStaticMethod(INT_VALUEOF));
        rules.registerCastingRule(LONG, new CastingRuleI2L(null));
        rules.registerCastingRule(LONGOBJECT, new CastingRuleStaticMethod(LONG_VALUEOF, new CastingRuleI2L(null)));
        rules.registerCastingRule(FLOAT, new CastingRuleI2F(null));
        rules.registerCastingRule(FLOATOBJECT, new CastingRuleStaticMethod(FLOAT_VALUEOF, new CastingRuleI2F(null)));
        rules.registerCastingRule(DOUBLE, new CastingRuleI2D(null));
        rules.registerCastingRule(DOUBLEOBJECT, new CastingRuleStaticMethod(DOUBLE_VALUEOF, new CastingRuleI2D(null)));
        
        rules.registerCastingRule(STRING, new CastingRuleStaticMethod(INT_TOSTRING_STATIC));
        rules.registerCastingRule(ANY, new CastingRuleStaticMethod(JavaMethod.getStatic(getAnyClassName(environment), "valueOf", ANY, INT)));
        
        if(followCasters) {
            constructExpansionCastingRules(environment, rules);
        }
    }

	/*
     * @Override public boolean canCastImplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return (type.getNumberType() != 0 && type.getNumberType()
	 * >= NUM_INT) || type == ZenTypeString.INSTANCE || type == ANY ||
	 * canCastExpansion(environment, type); }
	 * 
	 * @Override public boolean canCastExplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type.getNumberType() != 0 || type ==
	 * ZenTypeString.INSTANCE || type == ANY || canCastExpansion(environment,
	 * type); }
	 * 
	 * @Override public Expression cast(ZenPosition position, IEnvironmentGlobal
	 * environment, Expression value, ZenType type) { if (type.getNumberType() >
	 * 0 || type == STRING) { return new ExpressionAs(position, value, type); }
	 * else if (canCastExpansion(environment, type)) { return
	 * castExpansion(position, environment, value, type); } else { return new
	 * ExpressionAs(position, value, type); } }
	 */
    
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
        if(result == null) {
            environment.error(position, "int value has no members");
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

	/*
	 * @Override public void compileCast(ZenPosition position,
	 * IEnvironmentMethod environment, ZenType type) { MethodOutput output =
	 * environment.getOutput();
	 * 
	 * if (type == BYTE) { output.i2b(); } else if (type ==
	 * ZenTypeByteObject.INSTANCE) { output.i2b();
	 * output.invokeStatic(Byte.class, "valueOf", Byte.class, byte.class); }
	 * else if (type == SHORT) { output.i2s(); } else if (type ==
	 * ZenTypeShortObject.INSTANCE) { output.i2s();
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
	 * } else if (type == STRING) { output.invokeStatic(Integer.class,
	 * "toString", String.class, int.class); } else if (type == ANY) {
	 * output.invokeStatic(getAnyClassName(environment), "valueOf", "(I)" +
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
        if(operator == OperatorType.CAT) {
            return STRING.binary(position, environment, left.cast(position, environment, STRING), right.cast(position, environment, STRING), OperatorType.CAT);
        } else if(operator == OperatorType.RANGE) {
            return new ExpressionIntegerRange(position, left, right.cast(position, environment, INT));
        }
        
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
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
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
    public String getAnyClassName(IEnvironmentGlobal environment) {
        if(!environment.containsClass(ANY_NAME_2)) {
            environment.putClass(ANY_NAME_2, new byte[0]);
            environment.putClass(ANY_NAME_2, AnyClassWriter.construct(new AnyDefinitionInt(environment), ANY_NAME, Type.INT_TYPE));
        }
        
        return ANY_NAME;
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionInt(position, 0, INT);
    }
    
    private class AnyDefinitionInt implements IAnyDefinition {
        
        private final IEnvironmentGlobal environment;
        
        public AnyDefinitionInt(IEnvironmentGlobal environment) {
            this.environment = environment;
        }
        
        @Override
        public void defineMembers(ClassVisitor output) {
            output.visitField(Opcodes.ACC_PRIVATE, "value", "I", null, null);
            
            MethodOutput valueOf = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "valueOf", "(I)" + signature(IAny.class), null, null);
            valueOf.start();
            valueOf.newObject(ANY_NAME);
            valueOf.dup();
            valueOf.load(Type.INT_TYPE, 0);
            valueOf.construct(ANY_NAME, "I");
            valueOf.returnObject();
            valueOf.end();
            
            MethodOutput constructor = new MethodOutput(output, Opcodes.ACC_PUBLIC, "<init>", "(I)V", null, null);
            constructor.start();
            constructor.loadObject(0);
            constructor.invokeSpecial(internal(Object.class), "<init>", "()V");
            constructor.loadObject(0);
            constructor.load(Type.INT_TYPE, 1);
            constructor.putField(ANY_NAME, "value", "I");
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
            if(expansion != null) {
                expansion.compileAnyCanCastImplicit(INT, output, environment, 0);
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
            if(expansion != null) {
                expansion.compileAnyCast(INT, output, environment, 0, 1);
            }
            
            throwCastException(output, "int", 1);
        }
        
        @Override
        public void defineNot(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.iNot();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineNeg(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.iNeg();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineAdd(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.iAdd();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineSub(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.iSub();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
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
            METHOD_ASINT.invokeVirtual(output);
            output.iMul();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineDiv(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.iDiv();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineMod(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.iRem();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineAnd(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.iAnd();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineOr(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.iOr();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
            output.returnObject();
        }
        
        @Override
        public void defineXor(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.iXor();
            output.invokeSpecial(ANY_NAME, "<init>", "(I)V");
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
            METHOD_ASINT.invokeVirtual(output);
            output.iSub();
            output.returnInt();
        }
        
        @Override
        public void defineContains(MethodOutput output) {
            throwUnsupportedException(output, "int", "in");
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
            throwUnsupportedException(output, "int", "get []");
        }
        
        @Override
        public void defineIndexSet(MethodOutput output) {
            throwUnsupportedException(output, "int", "set []");
        }
        
        @Override
        public void defineCall(MethodOutput output) {
            throwUnsupportedException(output, "int", "call");
        }
        
        @Override
        public void defineAsBool(MethodOutput output) {
            throwCastException(output, ANY_NAME, "bool");
        }
        
        @Override
        public void defineAsByte(MethodOutput output) {
            getValue(output);
            output.i2b();
            output.returnType(Type.BYTE_TYPE);
        }
        
        @Override
        public void defineAsShort(MethodOutput output) {
            getValue(output);
            output.i2s();
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
            output.invokeStatic(Integer.class, "toString", String.class, int.class);
            output.returnObject();
        }
        
        @Override
        public void defineAs(MethodOutput output) {
            int localValue = output.local(Type.INT_TYPE);
            
            getValue(output);
            output.store(Type.INT_TYPE, localValue);
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.compileAnyCast(INT, output, environment, localValue, 1);
            }
            
            throwCastException(output, "int", 1);
        }
        
        @Override
        public void defineIs(MethodOutput output) {
            Label lblEq = new Label();
            
            output.loadObject(1);
            output.constant(Type.INT_TYPE);
            output.ifACmpEq(lblEq);
            output.iConst0();
            output.returnInt();
            output.label(lblEq);
            output.iConst1();
            output.returnInt();
        }
        
        @Override
        public void defineGetNumberType(MethodOutput output) {
            output.constant(IAny.NUM_INT);
            output.returnInt();
        }
        
        @Override
        public void defineIteratorSingle(MethodOutput output) {
            throwUnsupportedException(output, "int", "iterator");
        }
        
        @Override
        public void defineIteratorMulti(MethodOutput output) {
            throwUnsupportedException(output, "int", "iterator");
        }
        
        private void getValue(MethodOutput output) {
            output.loadObject(0);
            output.getField(ANY_NAME, "value", "I");
        }
        
        @Override
        public void defineEquals(MethodOutput output) {
            Label lblNope = new Label();
            
            output.loadObject(1);
            output.instanceOf(IAny.NAME);
            output.ifEQ(lblNope);
            
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
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
    }
}
