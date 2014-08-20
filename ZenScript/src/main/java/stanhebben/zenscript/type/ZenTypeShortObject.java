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
import static stanhebben.zenscript.type.ZenType.ANY;
import static stanhebben.zenscript.type.ZenType.BYTE;
import static stanhebben.zenscript.type.ZenType.BYTE_VALUE;
import static stanhebben.zenscript.type.ZenType.STRING;
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
public class ZenTypeShortObject extends ZenType {
	public static final ZenTypeShortObject INSTANCE = new ZenTypeShortObject();
	
	private ZenTypeShortObject() {}

	@Override
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		return SHORT.unary(position, environment, value.cast(position, environment, SHORT), operator);
	}

	@Override
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		return SHORT.binary(position, environment, left.cast(position, environment, SHORT), right, operator);
	}

	@Override
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		return SHORT.trinary(position, environment, first.cast(position, environment, SHORT), second, third, operator);
	}

	@Override
	public Expression compare(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, CompareType type) {
		return SHORT.compare(position, environment, left.cast(position, environment, SHORT), right, type);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value, String name) {
		return SHORT.getMember(position, environment, value.eval(environment).cast(position, environment, SHORT), name);
	}

	@Override
	public IPartialExpression getStaticMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return SHORT.getStaticMember(position, environment, name);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		return SHORT.call(position, environment, receiver.cast(position, environment, SHORT), arguments);
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod environment) {
		return SHORT.makeIterator(numValues, environment);
	}

	@Override
	public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
		rules.registerCastingRule(BYTE, new CastingRuleVirtualMethod(BYTE_VALUE));
		rules.registerCastingRule(BYTEOBJECT, new CastingRuleNullableStaticMethod(
				BYTE_VALUEOF,
				new CastingRuleVirtualMethod(BYTE_VALUE)));
		rules.registerCastingRule(SHORT, new CastingRuleVirtualMethod(SHORT_VALUE));
		rules.registerCastingRule(SHORTOBJECT, new CastingRuleNullableStaticMethod(
				SHORT_VALUEOF,
				new CastingRuleVirtualMethod(SHORT_VALUE)));
		rules.registerCastingRule(INT, new CastingRuleVirtualMethod(INT_VALUE));
		rules.registerCastingRule(INTOBJECT, new CastingRuleNullableStaticMethod(
				INT_VALUEOF,
				new CastingRuleVirtualMethod(INT_VALUE)));
		rules.registerCastingRule(LONG, new CastingRuleVirtualMethod(LONG_VALUE));
		rules.registerCastingRule(LONGOBJECT, new CastingRuleNullableStaticMethod(
				LONG_VALUEOF,
				new CastingRuleVirtualMethod(LONG_VALUE)));
		rules.registerCastingRule(FLOAT, new CastingRuleVirtualMethod(FLOAT_VALUE));
		rules.registerCastingRule(FLOATOBJECT, new CastingRuleNullableStaticMethod(
				FLOAT_VALUEOF,
				new CastingRuleVirtualMethod(FLOAT_VALUE)));
		rules.registerCastingRule(DOUBLE, new CastingRuleVirtualMethod(DOUBLE_VALUE));
		rules.registerCastingRule(DOUBLEOBJECT, new CastingRuleNullableStaticMethod(
				DOUBLE_VALUEOF,
				new CastingRuleVirtualMethod(DOUBLE_VALUE)));
		
		rules.registerCastingRule(STRING, new CastingRuleNullableVirtualMethod(SHORTOBJECT, SHORT_TOSTRING));
		rules.registerCastingRule(ANY, new CastingRuleNullableStaticMethod(
				JavaMethod.getStatic(getAnyClassName(environment), "valueOf", ANY, SHORT),
				new CastingRuleVirtualMethod(SHORT_VALUE)));
		
		if (followCasters) {
			constructExpansionCastingRules(environment, rules);
		}
	}

	/*@Override
	public boolean canCastImplicit(ZenType type, IEnvironmentGlobal environment) {
		return SHORT.canCastImplicit(type, environment);
	}

	@Override
	public boolean canCastExplicit(ZenType type, IEnvironmentGlobal environment) {
		return SHORT.canCastExplicit(type, environment);
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression value, ZenType type) {
		if (type.getNumberType() > 0 || type == STRING) {
			return new ExpressionAs(position, value, type);
		} else if (canCastExpansion(environment, type)) {
			return castExpansion(position, environment, value, type);
		} else {
			return new ExpressionAs(position, value, type);
		}
	}*/

	@Override
	public Class toJavaClass() {
		return Short.class;
	}

	@Override
	public Type toASMType() {
		return Type.getType(Short.class);
	}

	@Override
	public int getNumberType() {
		return NUM_SHORT;
	}

	@Override
	public String getSignature() {
		return signature(Short.class);
	}

	@Override
	public boolean isPointer() {
		return true;
	}

	/*@Override
	public void compileCast(ZenPosition position, IEnvironmentMethod environment, ZenType type) {
		if (type == this) {
			// nothing to do
		} else if (type == SHORT) {
			environment.getOutput().invokeVirtual(Short.class, "shortValue", short.class);
		} else if (type == STRING) {
			environment.getOutput().invokeVirtual(Short.class, "toString", String.class);
		} else if (type == ANY) {
			MethodOutput output = environment.getOutput();
			
			Label lblNotNull = new Label();
			Label lblAfter = new Label();
			
			output.dup();
			output.ifNonNull(lblNotNull);
			output.aConstNull();
			output.goTo(lblAfter);
			
			output.label(lblNotNull);
			output.invokeVirtual(Short.class, "shortValue", short.class);
			output.invokeStatic(SHORT.getAnyClassName(environment), "valueOf", "(S)" + signature(IAny.class));
			
			output.label(lblAfter);
		} else {
			environment.getOutput().invokeVirtual(Short.class, "shortValue", short.class);
			SHORT.compileCast(position, environment, type);
		}
	}*/

	@Override
	public String getName() {
		return "short";
	}
	
	@Override
	public String getAnyClassName(IEnvironmentGlobal environment) {
		return SHORT.getAnyClassName(environment);
	}

	@Override
	public Expression defaultValue(ZenPosition position) {
		return new ExpressionNull(position);
	}
}
