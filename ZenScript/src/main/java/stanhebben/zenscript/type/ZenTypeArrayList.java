/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type;

import java.util.List;
import org.objectweb.asm.Type;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.casting.CastingRuleDelegateList;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.iterator.IteratorIterable;
import stanhebben.zenscript.type.iterator.IteratorList;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.signature;

/**
 *
 * @author Stan
 */
public class ZenTypeArrayList extends ZenTypeArray {
	private final Type type;

	public ZenTypeArrayList(ZenType baseType) {
		super(baseType);

		type = Type.getType(List.class);
	}

	@Override
	public IPartialExpression getMemberLength(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value) {
		return new ExpressionListLength(position, value.eval(environment));
	}

	@Override
	public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules, boolean followCasters) {
		ICastingRuleDelegate arrayRules = new CastingRuleDelegateList(rules, this);
		getBaseType().constructCastingRules(environment, arrayRules, followCasters);

		if (followCasters) {
			constructExpansionCastingRules(environment, rules);
		}
	}

	@Override
	public IZenIterator makeIterator(int numValues, IEnvironmentMethod methodOutput) {
		if (numValues == 1) {
			return new IteratorIterable(methodOutput.getOutput(), getBaseType());
		} else if (numValues == 2) {
			return new IteratorList(methodOutput.getOutput(), getBaseType());
		} else {
			return null;
		}
	}

	@Override
	public Class toJavaClass() {
		return List.class;
	}

	@Override
	public String getAnyClassName(IEnvironmentGlobal global) {
		return null;
	}

	@Override
	public Type toASMType() {
		return Type.getType(List.class);
	}

	@Override
	public String getSignature() {
		return signature(List.class);
	}

	@Override
	public Expression indexGet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index) {
		// TODO: implement
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public Expression indexSet(ZenPosition position, IEnvironmentGlobal environment, Expression array, Expression index, Expression value) {
		// TODO: implement
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	private class ExpressionListLength extends Expression {
		private final Expression value;

		public ExpressionListLength(ZenPosition position, Expression value) {
			super(position);

			this.value = value;
		}

		@Override
		public ZenType getType() {
			return ZenTypeInt.INSTANCE;
		}

		@Override
		public void compile(boolean result, IEnvironmentMethod environment) {
			value.compile(result, environment);

			if (result) {
				environment.getOutput().invokeInterface(List.class, "size", int.class);
			}
		}
	}
}
