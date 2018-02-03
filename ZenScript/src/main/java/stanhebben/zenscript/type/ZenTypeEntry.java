package stanhebben.zenscript.type;

import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Map;

import static stanhebben.zenscript.util.ZenTypeUtil.signature;

public class ZenTypeEntry extends ZenType {
    
    
    private final ZenType keyType, valueType;
    
    public ZenTypeEntry(ZenType keyType, ZenType valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }
    
    public ZenTypeEntry(ZenTypeAssociative mapType) {
        this.keyType = mapType.getKeyType();
        this.valueType = mapType.getValueType();
    }
    
    @Override
    public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
        Expression result = unaryExpansion(position, environment, value, operator);
        if(result == null) {
            environment.error(position, "Entries don't have unary operators");
            return new ExpressionInvalid(position);
        }
        return result;
    }
    
    @Override
    public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
        Expression result = binaryExpansion(position, environment, left, right, operator);
        if(result == null) {
            environment.error(position, "Entries don't have binary operators");
            return new ExpressionInvalid(position);
        }
        return result;
    }
    
    @Override
    public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        Expression result = trinaryExpansion(position, environment, first, second, third, operator);
        if(result == null) {
            environment.error(position, "Entries don't have ternary operators");
            return new ExpressionInvalid(position);
        }
        return result;
    }
    
    @Override
    public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
        return new ExpressionBool(position, false);
    }
    
    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
        if(name.equals("key") || name.equals("value"))
            return new ExpressionEntryGet(position, value.eval(environment), name.equals("key"));
        IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
        if(result == null) {
            environment.error(position, "Entries don't have a member called '" + name + "'!");
            return new ExpressionInvalid(position);
        }
        return result;
    }
    
    @Override
    public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        IPartialExpression result = staticMemberExpansion(position, environment, name);
        if(result == null) {
            environment.error(position, "Entries don't have a static member called '" + name + "'!");
            return new ExpressionInvalid(position);
        }
        return result;
    }
    
    @Override
    public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
        environment.error(position, "Cannot call an Entry");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
        if(followCasters)
            constructExpansionCastingRules(environment, rules);
    }
    
    @Override
    public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
        return null;
    }
    
    @Override
    public Class toJavaClass() {
        return Map.Entry.class;
    }
    
    @Override
    public Type toASMType() {
        return Type.getType(toJavaClass());
    }
    
    @Override
    public int getNumberType() {
        return 0;
    }
    
    @Override
    public String getSignature() {
        return signature(toJavaClass());
    }
    
    @Override
    public boolean isPointer() {
        return false;
    }
    
    @Override
    public String getAnyClassName(IEnvironmentGlobal environment) {
        return null;
    }
    
    @Override
    public String getName() {
        return null;
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionNull(position);
    }
    
    public ZenType getKeyType() {
        return keyType;
    }
    
    public ZenType getValueType() {
        return valueType;
    }
}
