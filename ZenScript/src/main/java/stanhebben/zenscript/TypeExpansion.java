/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenMethodStatic;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.ZenSetter;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.ITypeRegistry;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.expand.ZenExpandCaster;
import stanhebben.zenscript.type.expand.ZenExpandMember;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.type.natives.ZenNativeOperator;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class TypeExpansion {
	private final Map<String, ZenExpandMember> members;
	private final Map<String, ZenExpandMember> staticMembers;
	private final List<ZenExpandCaster> casters;
	private final List<ZenNativeOperator> trinaryOperators;
	private final List<ZenNativeOperator> binaryOperators;
	private final List<ZenNativeOperator> unaryOperators;
	
	public TypeExpansion() {
		members = new HashMap<String, ZenExpandMember>();
		staticMembers = new HashMap<String, ZenExpandMember>();
		casters = new ArrayList<ZenExpandCaster>();
		trinaryOperators = new ArrayList<ZenNativeOperator>();
		binaryOperators = new ArrayList<ZenNativeOperator>();
		unaryOperators = new ArrayList<ZenNativeOperator>();
	}
	
	public void expand(Class cls, ITypeRegistry types) {
		for (Method method : cls.getMethods()) {
			String methodName = method.getName();
			
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation instanceof ZenCaster) {
					checkStatic(method);
					casters.add(new ZenExpandCaster(new JavaMethod(cls, method, types)));
				} else if (annotation instanceof ZenGetter) {
					checkStatic(method);
					ZenGetter getterAnnotation = (ZenGetter) annotation;
					String name = getterAnnotation.value().length() == 0 ? method.getName() : getterAnnotation.value();
					
					if (!members.containsKey(name)) {
						members.put(name, new ZenExpandMember());
					}
					members.get(name).setGetter(new JavaMethod(cls, method, types));
				} else if (annotation instanceof ZenSetter) {
					checkStatic(method);
					ZenSetter setterAnnotation = (ZenSetter) annotation;
					String name = setterAnnotation.value().length() == 0 ? method.getName() : setterAnnotation.value();
					
					if (!members.containsKey(name)) {
						members.put(name, new ZenExpandMember());
					}
					members.get(name).setSetter(new JavaMethod(cls, method, types));
				} else if (annotation instanceof ZenOperator) {
					checkStatic(method);
					ZenOperator operatorAnnotation = (ZenOperator) annotation;
					switch (operatorAnnotation.value()) {
						case NEG:
						case NOT:
							if (method.getParameterTypes().length != 0) {
								// TODO: error
							} else {
								unaryOperators.add(new ZenNativeOperator(
										operatorAnnotation.value(),
										new JavaMethod(cls, method, types)));
							}
							break;
						case ADD:
						case SUB:
						case CAT:
						case MUL:
						case DIV:
						case MOD:
						case AND:
						case OR:
						case XOR:
						case INDEXGET:
						case RANGE:
						case CONTAINS:
						case COMPARE:
							if (method.getParameterTypes().length != 1) {
								// TODO: error
							} else {
								binaryOperators.add(new ZenNativeOperator(
										operatorAnnotation.value(),
										new JavaMethod(cls, method, types)));
							}
							break;
						case INDEXSET:
							if (method.getParameterTypes().length != 2) {
								// TODO: error
							} else {
								trinaryOperators.add(new ZenNativeOperator(
										operatorAnnotation.value(),
										new JavaMethod(cls, method, types)));
							}
							break;
					}
				} else if (annotation instanceof ZenMethod) {
					checkStatic(method);
					ZenMethod methodAnnotation = (ZenMethod) annotation;
					if (methodAnnotation.value().length() > 0) {
						methodName = methodAnnotation.value();
					}
					if (!members.containsKey(methodName)) {
						members.put(methodName, new ZenExpandMember());
					}
					members.get(methodName).addMethod(new JavaMethod(cls, method, types));
				} else if (annotation instanceof ZenMethodStatic) {
					checkStatic(method);
					ZenMethodStatic methodAnnotation = (ZenMethodStatic) annotation;
					if (methodAnnotation.value().length() > 0) {
						methodName = methodAnnotation.value();
					}
					if (!staticMembers.containsKey(methodName)) {
						staticMembers.put(methodName, new ZenExpandMember());
					}
					staticMembers.get(methodName).addMethod(new JavaMethod(cls, method, types));
				}
			}
		}
	}
	
	public ZenExpandCaster getCaster(ZenType type, IEnvironmentGlobal environment) {
		for (ZenExpandCaster caster : casters) {
			if (caster.getTarget().equals(type)) {
				return caster;
			}
		}
		for (ZenExpandCaster caster : casters) {
			if (caster.getTarget().canCastImplicit(type, environment)) {
				return caster;
			}
		}
		
		return null;
	}
	
	public Expression unary(ZenPosition position, IEnvironmentGlobal environment, Expression value, OperatorType operator) {
		for (ZenNativeOperator op : unaryOperators) {
			if (op.getOperator() == operator) {
				return op.getMethod().callStatic(position, environment, value);
			}
		}
		
		return null;
	}
	
	public Expression binary(ZenPosition position, IEnvironmentGlobal environment, Expression left, Expression right, OperatorType operator) {
		for (ZenNativeOperator op : binaryOperators) {
			if (op.getOperator() == operator) {
				return op.getMethod().callStatic(position, environment, left, right);
			}
		}
		
		return null;
	}
	
	public Expression trinary(ZenPosition position, IEnvironmentGlobal environment, Expression first, Expression second, Expression third, OperatorType operator) {
		for (ZenNativeOperator op : trinaryOperators) {
			if (op.getOperator() == operator) {
				return op.getMethod().callStatic(position, environment, first, second, third);
			}
		}
		
		return null;
	}
	
	public IPartialExpression member(ZenPosition position, IEnvironmentGlobal environment, Expression value, String member) {
		if (members.containsKey(member)) {
			return members.get(member).instance(position, environment, value);
		}
		
		return null;
	}
	
	public IPartialExpression staticMember(ZenPosition position, IEnvironmentGlobal environment, String member) {
		if (staticMembers.containsKey(member)) {
			return staticMembers.get(member).instance(position, environment);
		}
		
		return null;
	}
	
	private void checkStatic(Method method) {
		if ((method.getModifiers() & Modifier.STATIC) == 0) {
			throw new RuntimeException("Expansion method " + method.getName() + " must be static");
		}
	}
}
