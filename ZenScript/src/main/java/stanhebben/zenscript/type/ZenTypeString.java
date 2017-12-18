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

import java.util.*;

import static stanhebben.zenscript.util.AnyClassWriter.*;
import static stanhebben.zenscript.util.ZenTypeUtil.*;

public class ZenTypeString extends ZenType {
    
    public static final ZenTypeString INSTANCE = new ZenTypeString();
    
    private static final String ANY_NAME = "any/AnyString";
    private static final String ANY_NAME_2 = "any.AnyString";
    
    private final Type type = Type.getType(String.class);
    
    private ZenTypeString() {
    }
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        return null;
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        rules.registerCastingRule(BOOL, new CastingRuleStaticMethod(PARSE_BOOL));
        rules.registerCastingRule(BOOLOBJECT, new CastingRuleNullableStaticMethod(PARSE_BOOL_OBJECT));
        rules.registerCastingRule(BYTE, new CastingRuleStaticMethod(PARSE_BYTE));
        rules.registerCastingRule(BYTEOBJECT, new CastingRuleNullableStaticMethod(PARSE_BYTE_OBJECT));
        rules.registerCastingRule(SHORT, new CastingRuleStaticMethod(PARSE_SHORT));
        rules.registerCastingRule(SHORTOBJECT, new CastingRuleNullableStaticMethod(PARSE_SHORT_OBJECT));
        rules.registerCastingRule(INT, new CastingRuleStaticMethod(PARSE_INT));
        rules.registerCastingRule(INTOBJECT, new CastingRuleNullableStaticMethod(PARSE_INT_OBJECT));
        rules.registerCastingRule(LONG, new CastingRuleStaticMethod(PARSE_LONG));
        rules.registerCastingRule(LONGOBJECT, new CastingRuleNullableStaticMethod(PARSE_LONG_OBJECT));
        rules.registerCastingRule(FLOAT, new CastingRuleStaticMethod(PARSE_FLOAT));
        rules.registerCastingRule(FLOATOBJECT, new CastingRuleNullableStaticMethod(PARSE_FLOAT_OBJECT));
        rules.registerCastingRule(DOUBLE, new CastingRuleStaticMethod(PARSE_DOUBLE));
        rules.registerCastingRule(DOUBLEOBJECT, new CastingRuleNullableStaticMethod(PARSE_DOUBLE_OBJECT));
        rules.registerCastingRule(ANY, new CastingRuleNullableStaticMethod(JavaMethod.getStatic(getAnyClassName(environment), "valueOf", ANY, STRING)));
        
        if(followCasters) {
            constructExpansionCastingRules(environment, rules);
        }
    }
    /*
	 * @Override public boolean canCastImplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type == this || type == ANY ||
	 * canCastExpansion(environment, type); }
	 * 
	 * @Override public boolean canCastExplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type == this || type == ANY || type == BOOL ||
	 * type.getNumberType() > 0 || canCastExpansion(environment, type); }
	 * 
	 * @Override public Expression cast(ZenPosition position, IEnvironmentGlobal
	 * environment, Expression value, ZenType type) { if (type == this) { return
	 * value; } else if (type == BOOL || type.getNumberType() > 0 || type ==
	 * ANY) { return new ExpressionAs(position, value, type); } else if
	 * (canCastExpansion(environment, type)) { return castExpansion(position,
	 * environment, value, type); } else { return new ExpressionAs(position,
	 * value, type); } }
	 */
    
    @Override
    public Type toASMType() {
        return type;
    }
    
    @Override
    public int getNumberType() {
        return 0;
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
    	if(ExpressionStringMethod.hasMethod(name)) {
    		return new ExpressionStringMethod(position, value.eval(environment), name, environment);
    	}
    	
    	IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
        if(result == null) {
            environment.error(position, "string value has no such member: " + name);
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
        return "Ljava/lang/String;";
    }
    
    @Override
    public boolean isPointer() {
        return true;
    }

	/*
	 * @Override public void compileCast(ZenPosition position,
	 * IEnvironmentMethod environment, ZenType type) { MethodOutput output =
	 * environment.getOutput();
	 * 
	 * if (type == STRING) { // do nothing } else if (type == BOOL) {
	 * output.invokeStatic(Boolean.class, "parseBoolean", boolean.class,
	 * String.class); } else if (type == ZenTypeBoolObject.INSTANCE) {
	 * output.invokeStatic(Boolean.class, "valueOf", Boolean.class,
	 * String.class); } else if (type == BYTE) { output.invokeStatic(Byte.class,
	 * "parseByte", byte.class, String.class); } else if (type ==
	 * ZenTypeByteObject.INSTANCE) { output.invokeStatic(Byte.class, "valueOf",
	 * Byte.class, String.class); } else if (type == SHORT) {
	 * output.invokeStatic(Short.class, "parseShort", short.class,
	 * String.class); } else if (type == ZenTypeShortObject.INSTANCE) {
	 * output.invokeStatic(Short.class, "valueof", Short.class, String.class); }
	 * else if (type == INT) { output.invokeStatic(Integer.class, "parseInt",
	 * int.class, String.class); } else if (type == ZenTypeIntObject.INSTANCE) {
	 * output.invokeStatic(Integer.class, "valueof", Integer.class,
	 * String.class); } else if (type == LONG) { output.invokeStatic(Long.class,
	 * "parseLong", long.class, String.class); } else if (type ==
	 * ZenTypeLongObject.INSTANCE) { output.invokeStatic(Long.class, "valueof",
	 * Long.class, String.class); } else if (type == FLOAT) {
	 * output.invokeStatic(Float.class, "parseFloat", float.class,
	 * String.class); } else if (type == ZenTypeFloatObject.INSTANCE) {
	 * output.invokeStatic(Float.class, "valueOf", Float.class, String.class); }
	 * else if (type == DOUBLE) { output.invokeStatic(Double.class,
	 * "parseDouble", double.class, String.class); } else if (type ==
	 * ZenTypeDoubleObject.INSTANCE) { output.invokeStatic(Double.class,
	 * "valueOf", Double.class, String.class); } else if (type == ANY) {
	 * output.invokeStatic(getAnyClassName(environment), "valueOf",
	 * "(Ljava/lang/String;)" + signature(IAny.class)); } else if
	 * (!compileCastExpansion(position, environment, type)) {
	 * environment.error(position, "cannot cast " + this + " to " + type); } }
	 */
    
    @Override
    public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
        Expression result = unaryExpansion(position, environment, value, operator);
        if(result == null) {
            environment.error(position, "operator not supported on a string");
            return new ExpressionInvalid(position);
        } else {
            return result;
        }
    }
    
    @Override
    public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
        if(operator == OperatorType.CAT || operator == OperatorType.ADD) {
            if(left instanceof ExpressionStringConcat) {
                ((ExpressionStringConcat) left).add(right.cast(position, environment, this));
                return left;
            } else {
                if(right.getType().canCastImplicit(STRING, environment)) {
                    List<Expression> values = new ArrayList<>();
                    values.add(left);
                    values.add(right.cast(position, environment, this));
                    return new ExpressionStringConcat(position, values);
                } else {
                    Expression expanded = this.binaryExpansion(position, environment, left, right, operator);
                    if(expanded == null) {
                        environment.error(position, "cannot add " + right.getType().getName() + " to a string");
                        return new ExpressionInvalid(position, this);
                    } else {
                        return expanded;
                    }
                }
            }
        } else if(operator == OperatorType.INDEXGET) {
            return new ExpressionStringIndex(position, left, right.cast(position, environment, INT));
        } else if(operator == OperatorType.CONTAINS) {
            return new ExpressionStringContains(position, left, right.cast(position, environment, STRING));
        } else {
            Expression result = binaryExpansion(position, environment, left, right, operator);
            if(result == null) {
                environment.error(position, "operator not supported on strings");
                return new ExpressionInvalid(position, this);
            } else {
                return result;
            }
        }
    }
    
    @Override
    public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        Expression result = trinaryExpansion(position, environment, first, second, third, operator);
        if(result == null) {
            environment.error(position, "operator not supported on strings");
            return new ExpressionInvalid(position, this);
        } else {
            return result;
        }
    }
    
    @Override
    public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
        if(right.getType().canCastImplicit(STRING, environment)) {
            return new ExpressionCompareGeneric(position, new ExpressionCallVirtual(position, environment, STRING_COMPARETO, left, right.cast(position, environment, STRING)), type);
        }
        
        Expression result = binaryExpansion(position, environment, left, right, OperatorType.COMPARE);
        if(result == null) {
            environment.error(position, "cannot compare strings");
            return new ExpressionInvalid(position, BOOL);
        } else {
            return new ExpressionCompareGeneric(position, result, type);
        }
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
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
    
    /**
     * Returns the Java class name.
     *
     * @param environment environment
     *
     * @return gets the name of the class
     */
    @Override
    public String getAnyClassName(IEnvironmentGlobal environment) {
        if(!environment.containsClass(ANY_NAME_2)) {
            environment.putClass(ANY_NAME_2, new byte[0]);
            environment.putClass(ANY_NAME_2, AnyClassWriter.construct(new AnyDefinitionString(environment), ANY_NAME, type));
        }
        
        return ANY_NAME;
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionNull(position);
    }
    
    private class AnyDefinitionString implements IAnyDefinition {
        
        private final IEnvironmentGlobal environment;
        
        private AnyDefinitionString(IEnvironmentGlobal environment) {
            this.environment = environment;
        }
        
        @Override
        public void defineMembers(ClassVisitor output) {
            output.visitField(Opcodes.ACC_PRIVATE, "value", "Ljava/lang/String;", null, null);
            
            MethodOutput valueOf = new MethodOutput(output, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "valueOf", "(Ljava/lang/String;)" + signature(IAny.class), null, null);
            valueOf.start();
            valueOf.newObject(ANY_NAME);
            valueOf.dup();
            valueOf.loadObject(0);
            valueOf.construct(ANY_NAME, "Ljava/lang/String;");
            valueOf.returnObject();
            valueOf.end();
            
            MethodOutput constructor = new MethodOutput(output, Opcodes.ACC_PUBLIC, "<init>", "(Ljava/lang/String;)V", null, null);
            constructor.start();
            constructor.loadObject(0);
            constructor.invokeSpecial(internal(Object.class), "<init>", "()V");
            constructor.loadObject(0);
            constructor.load(type, 1);
            constructor.putField(ANY_NAME, "value", "Ljava/lang/String;");
            constructor.returnType(Type.VOID_TYPE);
            constructor.end();
        }
        
        @Override
        public void defineStaticCanCastImplicit(MethodOutput output) {
            Label lblOthers = new Label();
            
            output.constant(type);
            output.loadObject(0);
            output.ifACmpNe(lblOthers);
            output.iConst1();
            output.returnInt();
            
            output.label(lblOthers);
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.compileAnyCanCastImplicit(STRING, output, environment, 0);
            }
            
            output.iConst0();
            output.returnInt();
        }
        
        @Override
        public void defineStaticAs(MethodOutput output) {
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.compileAnyCast(STRING, output, environment, 0, 1);
            }
            
            throwCastException(output, "string", 1);
        }
        
        @Override
        public void defineNot(MethodOutput output) {
            throwUnsupportedException(output, "string", "not");
        }
        
        @Override
        public void defineNeg(MethodOutput output) {
            throwUnsupportedException(output, "string", "negate");
        }
        
        @Override
        public void defineAdd(MethodOutput output) {
            defineCat(output);
        }
        
        @Override
        public void defineSub(MethodOutput output) {
            throwUnsupportedException(output, "string", "-");
        }
        
        @Override
        public void defineCat(MethodOutput output) {
            output.newObject(StringBuilder.class);
            output.dup();
            output.invokeSpecial(internal(StringBuilder.class), "<init>", "()V");
            getValue(output);
            output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, String.class);
            output.loadObject(1);
            METHOD_ASSTRING.invokeVirtual(output);
            output.invokeVirtual(StringBuilder.class, "append", StringBuilder.class, String.class);
            output.invokeVirtual(StringBuilder.class, "toString", String.class);
            output.invokeStatic(ANY_NAME, "valueOf", "(Ljava/lang/String;)" + signature(IAny.class));
            output.returnObject();
        }
        
        @Override
        public void defineMul(MethodOutput output) {
            throwUnsupportedException(output, "string", "*");
        }
        
        @Override
        public void defineDiv(MethodOutput output) {
            throwUnsupportedException(output, "string", "/");
        }
        
        @Override
        public void defineMod(MethodOutput output) {
            throwUnsupportedException(output, "string", "*");
        }
        
        @Override
        public void defineAnd(MethodOutput output) {
            throwUnsupportedException(output, "string", "&");
        }
        
        @Override
        public void defineOr(MethodOutput output) {
            throwUnsupportedException(output, "string", "|");
        }
        
        @Override
        public void defineXor(MethodOutput output) {
            throwUnsupportedException(output, "string", "^");
        }
        
        @Override
        public void defineRange(MethodOutput output) {
            throwUnsupportedException(output, "string", "..");
        }
        
        @Override
        public void defineCompareTo(MethodOutput output) {
            getValue(output);
            output.loadObject(1);
            METHOD_ASSTRING.invokeVirtual(output);
            output.invokeVirtual(String.class, "compareTo", int.class, String.class);
            output.returnInt();
        }
        
        @Override
        public void defineContains(MethodOutput output) {
            getValue(output);
            output.loadObject(1);
            METHOD_ASSTRING.invokeVirtual(output);
            output.invokeVirtual(String.class, "contains", boolean.class, CharSequence.class);
            output.returnInt();
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
            // return new AnyString(String.substring(other.asInt(),
            // other.asInt() + 1))
            getValue(output);
            output.loadObject(1);
            METHOD_ASINT.invokeVirtual(output);
            output.dup();
            output.iConst1();
            output.iAdd();
            output.invokeVirtual(String.class, "substring", String.class, int.class, int.class);
            output.invokeStatic(ANY_NAME, "valueOf", "(Ljava/lang/String;)" + signature(IAny.class));
            output.returnObject();
        }
        
        @Override
        public void defineIndexSet(MethodOutput output) {
            throwUnsupportedException(output, "string", "[]=");
        }
        
        @Override
        public void defineCall(MethodOutput output) {
            throwUnsupportedException(output, "string", "call");
        }
        
        @Override
        public void defineAsBool(MethodOutput output) {
            // return Boolean.parseBoolean(value);
            getValue(output);
            output.invokeStatic(Boolean.class, "parseBoolean", boolean.class, String.class);
            output.returnInt();
        }
        
        @Override
        public void defineAsByte(MethodOutput output) {
            // return Byte.parseByte(value);
            getValue(output);
            output.invokeStatic(Byte.class, "parseByte", byte.class, String.class);
            output.returnInt();
        }
        
        @Override
        public void defineAsShort(MethodOutput output) {
            // return Short.parseShort(value);
            getValue(output);
            output.invokeStatic(Short.class, "parseShort", short.class, String.class);
            output.returnInt();
        }
        
        @Override
        public void defineAsInt(MethodOutput output) {
            // return Integer.parseInt(value);
            getValue(output);
            output.invokeStatic(Integer.class, "parseInt", int.class, String.class);
            output.returnInt();
        }
        
        @Override
        public void defineAsLong(MethodOutput output) {
            // return Integer.parseLong(value);
            getValue(output);
            output.invokeStatic(Long.class, "parseLong", long.class, String.class);
            output.returnType(Type.LONG_TYPE);
        }
        
        @Override
        public void defineAsFloat(MethodOutput output) {
            // return Float.parseFloat(value);
            getValue(output);
            output.invokeStatic(Float.class, "parseFloat", float.class, String.class);
            output.returnType(Type.FLOAT_TYPE);
        }
        
        @Override
        public void defineAsDouble(MethodOutput output) {
            // return Double.parseDouble(value);
            getValue(output);
            output.invokeStatic(Double.class, "parseDouble", double.class, String.class);
            output.returnType(Type.DOUBLE_TYPE);
        }
        
        @Override
        public void defineAsString(MethodOutput output) {
            getValue(output);
            output.returnObject();
        }
        
        @Override
        public void defineAs(MethodOutput output) {
            int localValue = output.local(type);
            
            getValue(output);
            output.store(type, localValue);
            TypeExpansion expansion = environment.getExpansion(getName());
            if(expansion != null) {
                expansion.compileAnyCast(STRING, output, environment, localValue, 1);
            }
            
            throwCastException(output, "string", 1);
        }
        
        @Override
        public void defineIs(MethodOutput output) {
            Label lblEq = new Label();
            
            output.loadObject(1);
            output.constant(type);
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
            throwUnsupportedException(output, "string", "iterator");
        }
        
        @Override
        public void defineIteratorMulti(MethodOutput output) {
            throwUnsupportedException(output, "string", "iterator");
        }
        
        private void getValue(MethodOutput output) {
            output.loadObject(0);
            output.getField(ANY_NAME, "value", "Ljava/lang/String;");
        }
        
        @Override
        public void defineEquals(MethodOutput output) {
            getValue(output);
            output.loadObject(1);
            METHOD_ASSTRING.invokeVirtual(output);
            output.invokeVirtual(String.class, "equals", boolean.class, Object.class);
            output.returnInt();
        }
        
        @Override
        public void defineHashCode(MethodOutput output) {
            getValue(output);
            output.invokeVirtual(String.class, "hashCode", int.class);
            output.returnInt();
        }
    }
}
