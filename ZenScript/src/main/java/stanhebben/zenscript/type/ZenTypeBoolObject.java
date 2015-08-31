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
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import static stanhebben.zenscript.type.ZenType.BOOL;
import stanhebben.zenscript.type.casting.CastingRuleNullableStaticMethod;
import stanhebben.zenscript.type.casting.CastingRuleNullableVirtualMethod;
import stanhebben.zenscript.type.casting.CastingRuleVirtualMethod;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 *
 * @author Stan
 */
public class ZenTypeBoolObject extends ZenType {
	public static final ZenTypeBoolObject INSTANCE = new ZenTypeBoolObject();

	private ZenTypeBoolObject() {
	}

	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		return BOOL.unary(position, environment, value.cast(position, environment, ZenType.BOOL), operator);
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		return BOOL.binary(position, environment, left.cast(position, environment, BOOL), right, operator);
	}

	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		return BOOL.trinary(position, environment, first.cast(position, environment, BOOL), second, third, operator);
	}

	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		return BOOL.compare(position, environment, left.cast(position, environment, BOOL), right, type);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		return BOOL.getMember(position, environment, value.eval(environment).cast(position, environment, BOOL), name);
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return BOOL.getStaticMember(position, environment, name);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		return BOOL.call(position, environment, receiver.cast(position, environment, BOOL), arguments);
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		return BOOL.makeIterator(numValues, methodOutput);
	}

	@Override
	public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
		rules.registerCastingRule(BOOL, new CastingRuleVirtualMethod(BOOL_VALUE));
		rules.registerCastingRule(STRING, new CastingRuleNullableVirtualMethod(BOOL, BOOL_TOSTRING));
		rules.registerCastingRule(ANY, new CastingRuleNullableStaticMethod(JavaMethod.getStatic(
				BOOL.getAnyClassName(environment),
				"valueOf",
				ANY,
				BOOL
			), new CastingRuleVirtualMethod(BOOL_VALUE)));
	}

	/*
	 * @Override public boolean canCastImplicit(ZenType type, IEnvironmentGlobal
	 * environment) { return BOOL.canCastImplicit(type, environment); }
	 */

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return canCastImplicit(type, environment);
	}

	@Override
	public Class toJavaClass() {
		return Boolean.class;
	}

	@Override
	public Type toASMType() {
		return Type.getType(Boolean.class);
	}

	@Override
	public int getNumberType() {
		return 0;
	}

	@Override
	public String getSignature() {
		return signature(Boolean.class);
	}

	@Override
	public boolean isPointer() {
		return true;
	}

	/*
	 * @Override public void compileCast(ZenPosition position,
	 * IEnvironmentMethod environment, ZenType type) { if (type == this) { //
	 * nothing to do } else if (type == BOOL) {
	 * environment.getOutput().invokeVirtual(Boolean.class, "booleanValue",
	 * boolean.class); } else if (type == STRING) {
	 * environment.getOutput().invokeVirtual(Boolean.class, "toString",
	 * String.class); } else if (type == ANY) { MethodOutput output =
	 * environment.getOutput();
	 * 
	 * Label lblNotNull = new Label(); Label lblAfter = new Label();
	 * 
	 * output.dup(); output.ifNonNull(lblNotNull); output.aConstNull();
	 * output.goTo(lblAfter);
	 * 
	 * output.label(lblNotNull); output.invokeVirtual(Boolean.class,
	 * "booleanValue", boolean.class);
	 * output.invokeStatic(BOOL.getAnyClassName(environment), "valueOf", "(Z)" +
	 * signature(IAny.class));
	 * 
	 * output.label(lblAfter); } else {
	 * environment.getOutput().invokeVirtual(Boolean.class, "booleanValue",
	 * boolean.class); BOOL.compileCast(position, environment, type); } }
	 */

	@Override
	public String getName() {
		return "bool?";
	}

	@Override
	public String getAnyClassName(IEnvironmentGlobal environment) {
		return BOOL.getAnyClassName(environment);
	}

	/*
	 * @Override public Expression cast(ZenPosition position, IEnvironmentGlobal
	 * environment, Expression value, ZenType type) { if (type == BOOL || type
	 * == ZenTypeBoolObject.INSTANCE || type == STRING) { return new
	 * ExpressionAs(position, value, type); } else if
	 * (canCastExpansion(environment, type)) { return castExpansion(position,
	 * environment, value, type); } else { return new ExpressionAs(position,
	 * value, type); } }
	 */

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
}
