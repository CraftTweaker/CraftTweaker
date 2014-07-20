/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type;

import org.objectweb.asm.Type;
import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ZenTypeVoid extends ZenType {
	public static final ZenTypeVoid INSTANCE = new ZenTypeVoid();
	
	private ZenTypeVoid() {}
	
	@Override
	public IPartialExpression getMember(
			ZenPosition position,
			IEnvironmentGlobal environment,
			IPartialExpression value,
			String name) {
		environment.error(position, "void doesn't have members");
		return new ExpressionInvalid(position, this);
	}

	@Override
	public IPartialExpression getStaticMember(
			ZenPosition position,
			IEnvironmentGlobal environment,
			String name) {
		environment.error(position, "void doesn't have static members");
		return new ExpressionInvalid(position, this);
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return null;
	}

	@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this || canCastExpansion(environment, type);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type == this || canCastExpansion(environment, type);
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		environment.error(position, "cannot cast void to other type");
		return new ExpressionInvalid(position, type);
	}

	@Override
	public Type toASMType() {
		return Type.VOID_TYPE;
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public String getSignature() {
		return "V";
	}

	@Override
	public boolean isPointer() {
		return false;
	}

	@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		if (type == this) {
			// nothing to do
		} else {
			if (!compileCastExpansion(position, environment, type)) {
				environment.error(position, "cannot cast " + this + " to " + type);
			}
		}
	}
	
	@Override
	public Expression unary(
			ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		environment.error(position, "void does not have operators");
		return new ExpressionInvalid(position, this);
	}

	@Override
	public Expression binary(
			ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		environment.error(position, "void does not have operators");
		return new ExpressionInvalid(position, this);
	}

	@Override
	public Expression trinary(
			ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		environment.error(position, "void does not have operators");
		return new ExpressionInvalid(position, this);
	}
	
	@Override
	public Expression compare(
			ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		environment.error(position, "void does not have operators");
		return new ExpressionInvalid(position, this);
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "cannot call a void");
		return new ExpressionInvalid(position, this);
	}

	@Override
	public Class toJavaClass() {
		return void.class;
	}

	@Override
	public String getName() {
		return "void";
	}
	
	@Override
	public String getAnyClassName(IEnvironmentGlobal environment) {
		throw new UnsupportedOperationException("Cannot convert void to anything, not even any");
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		throw new RuntimeException("void has no default value");
	}
}
