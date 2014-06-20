/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.natives;

import java.util.ArrayList;
import java.util.List;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import static stanhebben.zenscript.util.StringUtil.methodMatchingError;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class ZenNativeMember {
	private JavaMethod getter;
	private JavaMethod setter;
	private final List<JavaMethod> methods = new ArrayList<JavaMethod>();
	
	public JavaMethod getGetter() {
		return getter;
	}
	
	public JavaMethod getSetter() {
		return setter;
	}
	
	public void setGetter(JavaMethod getter) {
		if (this.getter == null) {
			this.getter = getter;
		} else {
			throw new RuntimeException("already has a getter");
		}
	}
	
	public void setSetter(JavaMethod setter) {
		if (this.setter == null) {
			this.setter = setter;
		} else {
			throw new RuntimeException("already has a setter");
		}
	}

	public IPartialExpression instance(ZenPosition position, IEnvironmentGlobal environment, IPartialExpression value) {
		return new InstanceGetValue(position, value);
	}

	public IPartialExpression instance(ZenPosition position, IEnvironmentGlobal environment) {
		return new StaticGetValue(position);
	}
	
	public void addMethod(JavaMethod method) {
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
			return getter.callVirtual(position, environment, value.eval(environment));
		}

		@Override
		public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
			return setter.callVirtual(position, environment, value.eval(environment), other);
		}

		@Override
		public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
			ZenType type = environment.getType(getter.getMethod().getGenericReturnType());
			return type.getMember(position, environment, this, name);
		}

		@Override
		public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
			JavaMethod method = JavaMethod.select(false, methods, environment, values);
			if (method == null) {
				environment.error(position, methodMatchingError(methods, values));
				return new ExpressionInvalid(position);
			} else {
				return method.callVirtual(position, environment, value.eval(environment), values);
			}
		}

		@Override
		public IZenSymbol toSymbol() {
			return null;
		}

		@Override
		public ZenType toType(IEnvironmentGlobal environment) {
			environment.error(position, "not a valid type");
			return ZenType.ANY;
		}
	}
	
	private class StaticGetValue implements IPartialExpression {
		private final ZenPosition position;
		
		public StaticGetValue(ZenPosition position) {
			this.position = position;
		}
		
		@Override
		public Expression eval(IEnvironmentGlobal environment) {
			return setter.callStatic(position, environment);
		}

		@Override
		public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
			return setter.callStatic(position, environment, other);
		}

		@Override
		public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
			ZenType type = environment.getType(getter.getMethod().getGenericReturnType());
			return type.getMember(position, environment, this, name);
		}

		@Override
		public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
			JavaMethod method = JavaMethod.select(true, methods, environment, values);
			if (method == null) {
				environment.error(position, methodMatchingError(methods, values));
				return new ExpressionInvalid(position);
			} else {
				return method.callStatic(position, environment, values);
			}
		}

		@Override
		public IZenSymbol toSymbol() {
			return new StaticSymbol();
		}

		@Override
		public ZenType toType(IEnvironmentGlobal environment) {
			environment.error(position, "not a valid type");
			return ZenType.ANY;
		}
	}
	
	private class StaticSymbol implements IZenSymbol {
		@Override
		public IPartialExpression instance(ZenPosition position) {
			return new StaticGetValue(position);
		}
	}
}
