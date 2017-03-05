package stanhebben.zenscript.type;

import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.*;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;
import stanhebben.zenscript.value.IAny;

import static stanhebben.zenscript.util.AnyClassWriter.*;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 * @author Stanneke
 */
public class ZenTypeAny extends ZenType {
    
    public static final ZenTypeAny INSTANCE = new ZenTypeAny();
    
    private ZenTypeAny() {
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
        environment.error(position, "any values not yet supported");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        environment.error(position, "any values not yet supported");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        return null; // TODO: handle iteration on any-values
    }
    
    @Override
    public ICastingRule getCastingRule(ZenType type, IEnvironmentGlobal environment) {
        ICastingRule base = super.getCastingRule(type, environment);
        if(base == null) {
            return new CastingRuleAnyAs(type);
        } else {
            return base;
        }
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        rules.registerCastingRule(BOOL, CastingAnyBool.INSTANCE);
        rules.registerCastingRule(BOOLOBJECT, new CastingRuleNullableStaticMethod(BOOL_VALUEOF, CastingAnyBool.INSTANCE));
        rules.registerCastingRule(BYTE, CastingAnyByte.INSTANCE);
        rules.registerCastingRule(BYTEOBJECT, new CastingRuleNullableStaticMethod(BYTE_VALUEOF, CastingAnyByte.INSTANCE));
        rules.registerCastingRule(SHORT, CastingAnyShort.INSTANCE);
        rules.registerCastingRule(SHORTOBJECT, new CastingRuleNullableStaticMethod(SHORT_VALUEOF, CastingAnyShort.INSTANCE));
        rules.registerCastingRule(INT, CastingAnyInt.INSTANCE);
        rules.registerCastingRule(INTOBJECT, new CastingRuleNullableStaticMethod(INT_VALUEOF, CastingAnyInt.INSTANCE));
        rules.registerCastingRule(LONG, CastingAnyLong.INSTANCE);
        rules.registerCastingRule(LONGOBJECT, new CastingRuleNullableStaticMethod(LONG_VALUEOF, CastingAnyLong.INSTANCE));
        rules.registerCastingRule(FLOAT, CastingAnyFloat.INSTANCE);
        rules.registerCastingRule(FLOATOBJECT, new CastingRuleNullableStaticMethod(FLOAT_VALUEOF, CastingAnyFloat.INSTANCE));
        rules.registerCastingRule(DOUBLE, CastingAnyDouble.INSTANCE);
        rules.registerCastingRule(DOUBLEOBJECT, new CastingRuleNullableStaticMethod(DOUBLE_VALUEOF, CastingAnyDouble.INSTANCE));
        rules.registerCastingRule(STRING, CastingAnyString.INSTANCE);
        
        if(followCasters) {
            constructExpansionCastingRules(environment, rules);
        }
    }
    
    @Override
    public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
        return true;
    }
    
    @Override
    public Class toJavaClass() {
        return IAny.class;
    }
    
    @Override
    public Type toASMType() {
        return Type.getType(IAny.class);
    }
    
    @Override
    public int getNumberType() {
        return 0;
    }
    
    @Override
    public String getSignature() {
        return signature(IAny.class);
    }
    
    @Override
    public boolean isPointer() {
        return true;
    }
    
    @Override
    public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
        switch(operator) {
            case NEG:
                return new ExpressionCallVirtual(position, environment, METHOD_NEG, value);
            case NOT:
                return new ExpressionCallVirtual(position, environment, METHOD_NOT, value);
            default:
                return new ExpressionInvalid(position, ZenTypeAny.INSTANCE);
        }
    }
    
    @Override
    public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
        switch(operator) {
            case ADD:
                return new ExpressionCallVirtual(position, environment, METHOD_ADD, left, right.cast(position, environment, ANY));
            case CAT:
                return new ExpressionCallVirtual(position, environment, METHOD_CAT, left, right.cast(position, environment, ANY));
            case SUB:
                return new ExpressionCallVirtual(position, environment, METHOD_SUB, left, right.cast(position, environment, ANY));
            case MUL:
                return new ExpressionCallVirtual(position, environment, METHOD_MUL, left, right.cast(position, environment, ANY));
            case DIV:
                return new ExpressionCallVirtual(position, environment, METHOD_DIV, left, right.cast(position, environment, ANY));
            case MOD:
                return new ExpressionCallVirtual(position, environment, METHOD_MOD, left, right.cast(position, environment, ANY));
            case AND:
                return new ExpressionCallVirtual(position, environment, METHOD_AND, left, right.cast(position, environment, ANY));
            case OR:
                return new ExpressionCallVirtual(position, environment, METHOD_OR, left, right.cast(position, environment, ANY));
            case XOR:
                return new ExpressionCallVirtual(position, environment, METHOD_XOR, left, right.cast(position, environment, ANY));
            case CONTAINS:
                return new ExpressionCallVirtual(position, environment, METHOD_CONTAINS, left, right.cast(position, environment, ANY));
            case INDEXGET:
                return new ExpressionCallVirtual(position, environment, METHOD_INDEXGET, left, right.cast(position, environment, ANY));
            case RANGE:
                return new ExpressionCallVirtual(position, environment, METHOD_RANGE, left, right.cast(position, environment, ANY));
            case COMPARE:
                return new ExpressionCallVirtual(position, environment, METHOD_COMPARETO, left, right.cast(position, environment, ANY));
            case MEMBERGETTER:
                return new ExpressionCallVirtual(position, environment, METHOD_MEMBERGET, left, right.cast(position, environment, STRING));
            default:
                return new ExpressionInvalid(position, ANY);
        }
    }
    
    @Override
    public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        switch(operator) {
            case INDEXSET:
                return new ExpressionCallVirtual(position, environment, METHOD_INDEXSET, first, second.cast(position, environment, ANY), third.cast(position, environment, ANY));
            case MEMBERSETTER:
                return new ExpressionCallVirtual(position, environment, METHOD_MEMBERSET, first, second.cast(position, environment, STRING), third.cast(position, environment, ANY));
            default:
                return new ExpressionInvalid(position, ANY);
        }
    }
    
    @Override
    public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
        Expression comparator = new ExpressionCallVirtual(position, environment, JavaMethod.get(environment, IAny.class, "compareTo", IAny.class), left, right);
        
        return new ExpressionCompareGeneric(position, comparator, type);
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
        return new ExpressionCallVirtual(position, environment, JavaMethod.get(environment, IAny.class, "call", IAny[].class), receiver, arguments);
    }
    
    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        ZenType[] result = new ZenType[numArguments];
        for(int i = 0; i < result.length; i++) {
            result[i] = ANY;
        }
        return result;
    }
    
    @Override
    public String getName() {
        return "any";
    }
    
    @Override
    public String getAnyClassName(IEnvironmentGlobal environment) {
        throw new UnsupportedOperationException("Cannot get any class name from the any type. That's like trying to stuff a freezer into a freezer.");
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionNull(position);
    }
}
