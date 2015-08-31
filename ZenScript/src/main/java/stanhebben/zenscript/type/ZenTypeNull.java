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
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.CastingRuleNone;
import stanhebben.zenscript.type.casting.ICastingRule;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ZenTypeNull extends ZenType {
	public static final ZenTypeNull INSTANCE = new ZenTypeNull();
	private static final Type TYPE = Type.getType(Object.class);

	private ZenTypeNull() {

	}

	@Override
	public ICastingRule getCastingRule(ZenType type, IEnvironmentGlobal environment) {
		if (type.isPointer()) {
			return new CastingRuleNone(this, type);
		} else {
			return null;
		}
	}

	@Override
	public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {

	}

	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		environment.error(position, "null has not operators");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		environment.error(position, "null has not operators");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		environment.error(position, "null has not operators");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		environment.error(position, "null has not operators");
		return new ExpressionInvalid(position);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		environment.error(position, "null doesn't have members");
		return new ExpressionInvalid(position);
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		environment.error(position, "null doesn't have static members");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression call(
			ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		environment.error(position, "cannot call null values");
		return new ExpressionInvalid(position);
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod environment) {
		return null;
	}

	/*
	 * @Override public boolean canCastImplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return type.isPointer(); }
	 */

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return type.isPointer();
	}

	/*
	 * @Override public Expression cast(ZenPosition position, IEnvironmentGlobal
	 * environment, Expression value, ZenType type) { return value; }
	 */

	@Override
	public Class toJavaClass() {
		return Object.class;
	}

	@Override
	public Type toASMType() {
		return TYPE;
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public String getSignature() {
		return "Ljava/lang/Object;";
	}

	@Override
	public boolean isPointer() {
		return true;
	}

	/*
	 * @Override public void compileCast(ZenPosition position,
	 * IEnvironmentMethod environment, ZenType type) { // nothing to do }
	 */

	@Override
	public String getName() {
		return "null";
	}

	@Override
	public String getAnyClassName(IEnvironmentGlobal environment) {
		throw new UnsupportedOperationException("The null type does not have an any type");
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
}
