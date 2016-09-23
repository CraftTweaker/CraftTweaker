/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.expand;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.ArrayList;
import java.util.List;

import static stanhebben.zenscript.util.StringUtil.methodMatchingError;

/**
 *
 * @author Stan
 */
public class ZenExpandMember {
	private final String type;
	private final String name;
	private IJavaMethod getter;
	private IJavaMethod setter;
	private final List<IJavaMethod> methods = new ArrayList<IJavaMethod>();

	public ZenExpandMember(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public IPartialExpression instance(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value) {
		return new InstanceGetValue(position, value);
	}

	public IPartialExpression instance(ZenPosition position, IEnvironmentGlobal environment) {
		return new StaticGetValue(position);
	}

	public void setGetter(IJavaMethod getter) {
		if (this.getter != null) {
			throw new RuntimeException(type + "." + name + " already has a getter");
		} else {
			this.getter = getter;
		}
	}

	public void setSetter(IJavaMethod setter) {
		if (this.setter != null) {
			throw new RuntimeException(type + "." + name + " already has a setter");
		} else {
			this.setter = setter;
		}
	}

	public void addMethod(IJavaMethod method) {
		methods.add(method);
	}

	private class InstanceGetValue implements IPartialExpression {
		private final ZenPosition position;
		private final IPartialExpression value;

		public InstanceGetValue(ZenPosition position, IPartialExpression value) {
			this.position = position;
			this.value = value;
		}

		@Override
		public Expression eval(IEnvironmentGlobal environment) {
			return new ExpressionCallStatic(position, environment, getter, value.eval(environment));
		}

		@Override
		public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
			return new ExpressionCallStatic(position, environment, setter, value.eval(environment), other);
		}

		@Override
		public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
			return getter.getReturnType().getMember(position, environment, this, name);
		}

		@Override
		public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
			Expression[] newValues = new Expression[values.length + 1];
			newValues[0] = value.eval(environment);
			System.arraycopy(values, 0, newValues, 1, values.length);
			IJavaMethod method = JavaMethod.select(true, methods, environment, newValues);
			if (method == null) {
				environment.error(position, methodMatchingError(methods, values));
				return new ExpressionInvalid(position);
			} else {
				return new ExpressionCallStatic(position, environment, method, newValues);
			}
		}

		@Override
		public IZenSymbol toSymbol() {
			return null;
		}

		@Override
		public ZenType getType() {
			return getter.getReturnType();
		}

		@Override
		public ZenType toType(IEnvironmentGlobal environment) {
			environment.error(position, "not a valid type");
			return ZenType.ANY;
		}

		@Override
		public ZenType[] predictCallTypes(int numArguments) {
			ZenType[] base = JavaMethod.predict(methods, numArguments + 1);
			ZenType[] result = new ZenType[base.length - 1];
			for (int i = 0; i < result.length; i++) {
				result[i] = base[i + 1];
			}

			return result;
		}
	}

	private class StaticGetValue implements IPartialExpression {
		private final ZenPosition position;

		public StaticGetValue(ZenPosition position) {
			this.position = position;
		}

		@Override
		public Expression eval(IEnvironmentGlobal environment) {
			return new ExpressionCallStatic(position, environment, getter);
		}

		@Override
		public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
			return new ExpressionCallStatic(position, environment, setter, other);
		}

		@Override
		public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
			return getter.getReturnType().getMember(position, environment, this, name);
		}

		@Override
		public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
			IJavaMethod method = JavaMethod.select(true, methods, environment, values);
			if (method == null) {
				environment.error(position, methodMatchingError(methods, values));
				return new ExpressionInvalid(position);
			} else {
				return new ExpressionCallStatic(position, environment, method, values);
			}
		}

		@Override
		public IZenSymbol toSymbol() {
			return new StaticSymbol();
		}

		@Override
		public ZenType getType() {
			return getter.getReturnType();
		}

		@Override
		public ZenType toType(IEnvironmentGlobal environment) {
			environment.error(position, "not a valid type");
			return ZenType.ANY;
		}

		@Override
		public ZenType[] predictCallTypes(int numArguments) {
			return JavaMethod.predict(methods, numArguments);
		}
	}

	private class StaticSymbol implements IZenSymbol {
		@Override
		public IPartialExpression instance(ZenPosition position) {
			return new StaticGetValue(position);
		}
	}
}
