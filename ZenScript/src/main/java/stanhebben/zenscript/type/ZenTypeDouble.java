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

public class ZenTypeDouble extends ZenType {
    
    public static final ZenTypeDouble INSTANCE = new ZenTypeDouble();
    
    // private static final JavaMethod DOUBLE_TOSTRING =
    // JavaMethod.get(EMPTY_REGISTRY, Double.class, "toString", double.class);
    private static final String ANY_NAME = "any/AnyDouble";
    private static final String ANY_NAME_2 = "any.AnyDouble";
    
    private ZenTypeDouble() {
    }
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        return null;
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        rules.registerCastingRule(BYTE, new CastingRuleI2B(new CastingRuleD2I(null)));
        rules.registerCastingRule(BYTEOBJECT, new CastingRuleStaticMethod(BYTE_VALUEOF, new CastingRuleI2B(new CastingRuleD2I(null))));
        rules.registerCastingRule(SHORT, new CastingRuleI2S(new CastingRuleD2I(null)));
        rules.registerCastingRule(SHORTOBJECT, new CastingRuleStaticMethod(SHORT_VALUEOF, new CastingRuleI2S(new CastingRuleD2I(null))));
        rules.registerCastingRule(INT, new CastingRuleD2I(null));
        rules.registerCastingRule(INTOBJECT, new CastingRuleStaticMethod(INT_VALUEOF, new CastingRuleD2I(null)));
        rules.registerCastingRule(LONG, new CastingRuleD2L(null));
        rules.registerCastingRule(LONGOBJECT, new CastingRuleStaticMethod(LONG_VALUEOF, new CastingRuleD2L(null)));
        rules.registerCastingRule(FLOAT, new CastingRuleD2F(null));
        rules.registerCastingRule(FLOATOBJECT, new CastingRuleStaticMethod(FLOAT_VALUEOF, new CastingRuleD2F(null)));
        rules.registerCastingRule(DOUBLEOBJECT, new CastingRuleStaticMethod(DOUBLE_VALUEOF));
        
        rules.registerCastingRule(STRING, new CastingRuleStaticMethod(DOUBLE_TOSTRING_STATIC));
        rules.registerCastingRule(ANY, new CastingRuleStaticMethod(JavaMethod.getStatic(getAnyClassName(environment), "valueOf", ANY, DOUBLE)));
        
        if(followCasters) {
            constructExpansionCastingRules(environment, rules);
        }
    }

	/*
     * @Override public boolean canCastImplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return (type.getNumberType() != 0 && type.getNumberType()
	 * >= NUM_FLOAT) || type == STRING || type == ANY ||
	 * canCastExpansion(environment, type); }
	 * 
	 * @Override public boolean canCastExplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type.getNumberType() != 0 || type == STRING || type
	 * == ANY || canCastExpansion(environment, type); }
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
        return Type.DOUBLE_TYPE;
    }
    
    @Override
    public Class toJavaClass() {
        return double.class;
    }
    
    @Override
    public int getNumberType() {
        return NUM_DOUBLE;
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
        IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
        if(result == null) {
            environment.error(position, "double value has no members");
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
        return "D";
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
	 * if (type == BYTE) { output.d2i(); output.i2b(); } else if (type ==
	 * ZenTypeByteObject.INSTANCE) { output.d2i(); output.i2b();
	 * output.invokeStatic(Byte.class, "valueOf", Byte.class, byte.class); }
	 * else if (type == SHORT) { output.d2i(); output.i2s(); } else if (type ==
	 * ZenTypeShortObject.INSTANCE) { output.d2i(); output.i2s();
	 * output.invokeStatic(Short.class, "valueOf", Short.class, short.class); }
	 * else if (type == INT) { output.d2i(); } else if (type ==
	 * ZenTypeIntObject.INSTANCE) { output.d2i();
	 * output.invokeStatic(Integer.class, "valueOf", Integer.class, int.class);
	 * } else if (type == LONG) { output.d2l(); } else if (type ==
	 * ZenTypeLongObject.INSTANCE) { output.d2l();
	 * output.invokeStatic(Long.class, "valueOf", Long.class, long.class); }
	 * else if (type == FLOAT) { output.d2f(); } else if (type ==
	 * ZenTypeFloatObject.INSTANCE) { output.d2f();
	 * output.invokeStatic(Float.class, "valueOf", Float.class, float.class); }
	 * else if (type == DOUBLE) { // nothing to do } else if (type ==
	 * ZenTypeDoubleObject.INSTANCE) { output.invokeStatic(Double.class,
	 * "valueOf", Double.class, double.class); } else if (type == STRING) {
	 * output.invokeStatic(Double.class, "toString", String.class,
	 * double.class); } else if (type == ANY) {
	 * output.invokeStatic(getAnyClassName(environment), "valueOf", "(D)" +
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
        }
        
        return new ExpressionArithmeticBinary(position, operator, left, right.cast(position, environment, this));
    }
    
    @Override
    public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        environment.error(position, "double doesn't support this operation");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
        return new ExpressionArithmeticCompare(position, type, left, right.cast(position, environment, this));
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
        environment.error(position, "cannot call a double value");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public String getName() {
        return "double";
    }
    
    @Override
    public String getAnyClassName(IEnvironmentGlobal environment) {
        if(!environment.containsClass(ANY_NAME_2)) {
            environment.putClass(ANY_NAME_2, new byte[0]);
            environment.putClass(ANY_NAME_2, AnyClassWriter.construct(new AnyDefinitionDouble(environment), ANY_NAME, Type.DOUBLE_TYPE));
        }
        
        return ANY_NAME;
    }
    
    @Override
    public boolean isLarge() {
        return true;
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionFloat(position, 0.0, DOUBLE);
    }
    
    private class AnyDefinitionDouble implements IAnyDefinition {
        
        private final IEnvironmentGlobal environment;
        
        public AnyDefinitionDouble(IEnvironmentGlobal environment) {
            this.environment = environment;
        }
        
        @Override
        public void defineMembers(ClassVisitor output) {
            output.visitField(Opcodes.ACC_PRIVATE, "value", "D", null, null);
            
            MethodOutput valueOf = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "valueOf", "(D)" + signature(IAny.class), null, null);
            valueOf.start();
            valueOf.newObject(ANY_NAME);
            valueOf.dup();
            valueOf.load(Type.DOUBLE_TYPE, 0);
            valueOf.construct(ANY_NAME, "D");
            valueOf.returnObject();
            valueOf.end();
            
            MethodOutput constructor = new MethodOutput(output, Opcodes.ACC_PUBLIC, "<init>", "(D)V", null, null);
            constructor.start();
            constructor.loadObject(0);
            constructor.invokeSpecial(internal(Object.class), "<init>", "()V");
            constructor.loadObject(0);
            constructor.load(Type.DOUBLE_TYPE, 1);
            constructor.putField(ANY_NAME, "value", "D");
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
            if(expansion != null) {
                expansion.compileAnyCast(DOUBLE, output, environment, 0, 1);
            }
            
            throwCastException(output, "double", 1);
        }
        
        @Override
        public void defineNot(MethodOutput output) {
            throwUnsupportedException(output, "double", "not");
        }
        
        @Override
        public void defineNeg(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.dNeg();
            output.invokeSpecial(ANY_NAME, "<init>", "(D)V");
            output.returnObject();
        }
        
        @Override
        public void defineAdd(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASDOUBLE.invokeVirtual(output);
            output.dAdd();
            output.invokeSpecial(ANY_NAME, "<init>", "(D)V");
            output.returnObject();
        }
        
        @Override
        public void defineSub(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASDOUBLE.invokeVirtual(output);
            output.dSub();
            output.invokeSpecial(ANY_NAME, "<init>", "(D)V");
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
            output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, double.class);
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
            METHOD_ASDOUBLE.invokeVirtual(output);
            output.dMul();
            output.invokeSpecial(ANY_NAME, "<init>", "(D)V");
            output.returnObject();
        }
        
        @Override
        public void defineDiv(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASDOUBLE.invokeVirtual(output);
            output.dDiv();
            output.invokeSpecial(ANY_NAME, "<init>", "(D)V");
            output.returnObject();
        }
        
        @Override
        public void defineMod(MethodOutput output) {
            output.newObject(ANY_NAME);
            output.dup();
            getValue(output);
            output.loadObject(1);
            METHOD_ASDOUBLE.invokeVirtual(output);
            output.dRem();
            output.invokeSpecial(ANY_NAME, "<init>", "(D)V");
            output.returnObject();
        }
        
        @Override
        public void defineAnd(MethodOutput output) {
            throwUnsupportedException(output, "double", "and");
        }
        
        @Override
        public void defineOr(MethodOutput output) {
            throwUnsupportedException(output, "double", "or");
        }
        
        @Override
        public void defineXor(MethodOutput output) {
            throwUnsupportedException(output, "double", "xor");
        }
        
        @Override
        public void defineRange(MethodOutput output) {
            throwUnsupportedException(output, "double", "range");
        }
        
        @Override
        public void defineCompareTo(MethodOutput output) {
            // return Double.compare(x, y)
            getValue(output);
            output.loadObject(1);
            METHOD_ASDOUBLE.invokeVirtual(output);
            output.invokeStatic(Float.class, "compare", int.class, double.class, double.class);
            output.returnInt();
        }
        
        @Override
        public void defineContains(MethodOutput output) {
            throwUnsupportedException(output, "double", "in");
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
            throwUnsupportedException(output, "double", "get []");
        }
        
        @Override
        public void defineIndexSet(MethodOutput output) {
            throwUnsupportedException(output, "double", "set []");
        }
        
        @Override
        public void defineCall(MethodOutput output) {
            throwUnsupportedException(output, "double", "call");
        }
        
        @Override
        public void defineAsBool(MethodOutput output) {
            throwCastException(output, ANY_NAME, "bool");
        }
        
        @Override
        public void defineAsByte(MethodOutput output) {
            getValue(output);
            output.d2i();
            output.i2b();
            output.returnType(Type.BYTE_TYPE);
        }
        
        @Override
        public void defineAsShort(MethodOutput output) {
            getValue(output);
            output.d2i();
            output.i2s();
            output.returnType(Type.SHORT_TYPE);
        }
        
        @Override
        public void defineAsInt(MethodOutput output) {
            getValue(output);
            output.d2i();
            output.returnType(Type.INT_TYPE);
        }
        
        @Override
        public void defineAsLong(MethodOutput output) {
            getValue(output);
            output.d2l();
            output.returnType(Type.LONG_TYPE);
        }
        
        @Override
        public void defineAsFloat(MethodOutput output) {
            getValue(output);
            output.d2f();
            output.returnType(Type.FLOAT_TYPE);
        }
        
        @Override
        public void defineAsDouble(MethodOutput output) {
            getValue(output);
            output.returnType(Type.DOUBLE_TYPE);
        }
        
        @Override
        public void defineAsString(MethodOutput output) {
            getValue(output);
            output.invokeStatic(Double.class, "toString", String.class, double.class);
            output.returnObject();
        }
        
        @Override
        public void defineAs(MethodOutput output) {
            int localValue = output.local(Type.DOUBLE_TYPE);
            
            getValue(output);
            output.store(Type.DOUBLE_TYPE, localValue);
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.compileAnyCast(DOUBLE, output, environment, localValue, 1);
            }
            
            throwCastException(output, "double", 1);
        }
        
        @Override
        public void defineIs(MethodOutput output) {
            Label lblEq = new Label();
            
            output.loadObject(1);
            output.constant(Type.DOUBLE_TYPE);
            output.ifACmpEq(lblEq);
            output.iConst0();
            output.returnInt();
            output.label(lblEq);
            output.iConst1();
            output.returnInt();
        }
        
        @Override
        public void defineGetNumberType(MethodOutput output) {
            output.constant(IAny.NUM_DOUBLE);
            output.returnInt();
        }
        
        @Override
        public void defineIteratorSingle(MethodOutput output) {
            throwUnsupportedException(output, "double", "iterator");
        }
        
        @Override
        public void defineIteratorMulti(MethodOutput output) {
            throwUnsupportedException(output, "double", "iterator");
        }
        
        private void getValue(MethodOutput output) {
            output.loadObject(0);
            output.getField(ANY_NAME, "value", "D");
        }
        
        @Override
        public void defineEquals(MethodOutput output) {
            Label lblNope = new Label();
            
            output.loadObject(1);
            output.instanceOf(IAny.NAME);
            output.ifEQ(lblNope);
            
            getValue(output);
            output.loadObject(1);
            METHOD_ASDOUBLE.invokeVirtual(output);
            output.dCmp();
            output.ifNE(lblNope);
            
            output.iConst1();
            output.returnInt();
            
            output.label(lblNope);
            output.iConst0();
            output.returnInt();
        }
        
        @Override
        public void defineHashCode(MethodOutput output) {
            // long bits = Double.doubleToLongBits(value);
            // return (int) (bits ^ bits >> 32)
            int bits = output.local(Type.LONG_TYPE);
            output.invokeStatic(Double.class, "doubleToLongBits", double.class, long.class);
            output.store(Type.LONG_TYPE, bits);
            
            output.load(Type.LONG_TYPE, bits);
            output.l2i();
            output.load(Type.LONG_TYPE, bits);
            output.constant(32);
            output.lShr();
            output.l2i();
            output.iXor();
            
            output.returnInt();
        }
    }
}
