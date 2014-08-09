/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type;

import java.util.Map;
import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionCompareGeneric;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.ExpressionMapContains;
import stanhebben.zenscript.expression.ExpressionMapIndexGet;
import stanhebben.zenscript.expression.ExpressionMapIndexSet;
import stanhebben.zenscript.expression.ExpressionMapSize;
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.iterator.IteratorMap;
import stanhebben.zenscript.type.iterator.IteratorMapKeys;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 *
 * @author Stanneke
 */
public class ZenTypeAssociative extends ZenType {
	private final ZenType base;
	private final ZenType key;
	
	private final String name;
	
	public ZenTypeAssociative(ZenType base, ZenType key) {
		this.base = base;
		this.key = key;
		
		name = base.getName() + "[" + key.getName() + "]";
	}
	
	public ZenType getValueType() {
		return base;
	}
	
	public ZenType getKeyType() {
		return key;
	}
	
	@Override
	public String getAnyClassName(IEnvironmentGlobal global) {
		return null;
	}

	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		Expression result = unaryExpansion(position, environment, value, operator);
		if (result == null) {
			environment.error(position, "associative arrays don't have unary operators");
			return new ExpressionInvalid(position);
		} else {
			return result;
		}
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		if (operator == OperatorType.CONTAINS) {
			return new ExpressionMapContains(position, left, right.cast(position, environment, key));
		} else if (operator == OperatorType.INDEXGET) {
			return new ExpressionMapIndexGet(position, left, right.cast(position, environment, key));
		} else {
			Expression result = binaryExpansion(position, environment, left, right, operator);
			if (result == null) {
				environment.error(position, "associative arrays don't support this operation");
				return new ExpressionInvalid(position);
			} else {
				return result;
			}
		}
	}

	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		if (operator == OperatorType.INDEXSET) {
			return new ExpressionMapIndexSet(
					position,
					first,
					second.cast(position, environment, key),
					third.cast(position, environment, base));
		} else {
			Expression result = trinaryExpansion(position, environment, first, second, third, operator);
			if (result == null) {
				environment.error(position, "associative arrays don't support this operation");
				return new ExpressionInvalid(position);
			} else {
				return result;
			}
		}
	}

	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		Expression result = binaryExpansion(position, environment, left, right, OperatorType.COMPARE);
		if (result == null) {
			environment.error(position, "cannot compare associative arrays");
			return new ExpressionInvalid(position);
		} else {
			return new ExpressionCompareGeneric(position, result, type);
		}
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		if (name.equals("length")) {
			return new ExpressionMapSize(position, value.eval(environment));
		} else if (STRING.canCastImplicit(key, environment)) {
			return new ExpressionMapIndexGet(
					position,
					value.eval(environment),
					new ExpressionString(position, name).cast(position, environment, key));
		} else {
			IPartialExpression result = memberExpansion(position, environment, value.eval(environment), name);
			if (result == null) {
				environment.error(position, "this array is not indexable with strings");
				return new ExpressionInvalid(position, base);
			} else {
				return result;
			}
		}
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		IPartialExpression result = staticMemberExpansion(position, environment, name);
		if (result == null) {
			environment.error(position, "associative arrays don't have static members");
			return new ExpressionInvalid(position);
		} else {
			return result;
		}
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "cannot call associative arrays");
		return new ExpressionInvalid(position);
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type == this) {
			return value;
		}
		
		return castExpansion(position, environment, value, type);
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		if (numValues == 1) {
			return new IteratorMapKeys(methodOutput.getOutput(), this);
		} else if (numValues == 2) {
			return new IteratorMap(methodOutput.getOutput(), this);
		} else {
			return null;
		}
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this || canCastAssociative(type, environment) || canCastExpansion(environment, type);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this || canCastAssociative(type, environment) || canCastExpansion(environment, type);
	}

	@Override
	public Class toJavaClass() {
		return Map.class;
	}

	@Override
	public Type toASMType() {
		return Type.getType(Map.class);
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public String getSignature() {
		return signature(Map.class);
	}

	@Override
	public boolean isPointer() {
		return true;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		if (equals(type)) {
			// nothing to do
		} else if (!compileCastExpansion(position, environment, type)) {
			environment.error(position, "cannot cast " + this + " to " + type);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
	
	private boolean canCastAssociative(ZenType type, IEnvironmentGlobal global) {
		if (!(type instanceof ZenTypeAssociative)) {
			return false;
		}
		
		ZenTypeAssociative atype = (ZenTypeAssociative) type;
		return getKeyType().canCastImplicit(atype.getKeyType(), global)
				&& getValueType().canCastImplicit(atype.getValueType(), global);
	}
}
