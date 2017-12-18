package stanhebben.zenscript.type;

import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stan
 */
public abstract class ZenTypeArray extends ZenType {
    
    private final ZenType base;
    private final String name;
    
    public ZenTypeArray(ZenType base) {
        this.base = base;
        name = base + "[]";
    }
    
    public ZenType getBaseType() {
        return base;
    }
    
    public abstract IPartialExpression getMemberLength(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value);
    
    public abstract Expression indexGet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index);
    
    public abstract Expression indexSet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index, Expression value);
    
    public abstract Expression add(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression val);
    
    @Override
    public final String getName() {
        return name;
    }
    
    @Override
    public final boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
        return equals(type) || canCastExpansion(environment, type);
    }
    
    @Override
    public final int getNumberType() {
        return 0;
    }
    
    @Override
    public final IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
        if(name.equals("length")) {
            return getMemberLength(position, environment, value);
        } else {
            IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
            if(result == null) {
                environment.error(position, "no such member in array: " + name);
                return new ExpressionInvalid(position);
            } else {
                return result;
            }
        }
    }
    
    @Override
    public final IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        IPartialExpression result = staticMemberExpansion(position, environment, name);
        if(result == null) {
            environment.error(position, "no such member in array: " + name);
            return new ExpressionInvalid(position);
        } else {
            return result;
        }
    }
    
    @Override
    public final boolean isPointer() {
        return true;
    }
    
    @Override
    public final Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
        Expression result = unaryExpansion(position, environment, value, operator);
        if(result == null) {
            environment.error(position, "Array has no unary operators");
            return new ExpressionInvalid(position);
        } else {
            return result;
        }
    }
    
    @Override
    public final Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
        if(operator == OperatorType.INDEXGET) {
            return indexGet(position, environment, left, right);
        } else if (operator == OperatorType.ADD) {
        	if (getBaseType().toJavaClass().isArray()) throw new UnsupportedOperationException("You cannot add to nested arrays!");
        	if (getBaseType().toJavaClass().isPrimitive()) throw new UnsupportedOperationException("You cannot add to primitive arrays!");
        	if (!getBaseType().equals(right.getType()) && !right.getType().canCastExplicit(getBaseType(), environment)) throw new IllegalArgumentException(String.format("Cannot add %s to %s", right.getType().toString(), toString()));
        	
        	return add(position, environment, left, right);
        } else {
            Expression result = binaryExpansion(position, environment, left, right, operator);
            if(result == null) {
                environment.error(position, getName() + " doesn't have such operator");
                return new ExpressionInvalid(position);
            } else {
                return result;
            }
        }
    }
    
    @Override
    public final Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
        if(operator == OperatorType.INDEXSET) {
            return indexSet(position, environment, first, second, third);
        } else {
            Expression result = trinaryExpansion(position, environment, first, second, third, operator);
            if(result == null) {
                environment.error(position, getName() + " doesn't have such operator");
                return new ExpressionInvalid(position);
            } else {
                return result;
            }
        }
    }
    
    @Override
    public final Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
        Expression compare = binaryExpansion(position, environment, left, right, OperatorType.COMPARE);
        if(compare == null) {
            environment.error(position, "Arrays cannot be compared");
            return new ExpressionInvalid(position, this);
        } else {
            return new ExpressionCompareGeneric(position, compare, type);
        }
    }
    
    @Override
    public final Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
        environment.error(position, "Cannot call an array");
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression defaultValue(ZenPosition position) {
        return new ExpressionNull(position);
    }
}
