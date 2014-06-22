/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.natives;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.ITypeRegistry;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionArray;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.ExpressionJavaCallStatic;
import stanhebben.zenscript.expression.ExpressionJavaCallVirtual;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeArray;
import stanhebben.zenscript.type.ZenTypeArrayBasic;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class JavaMethod {
	public static final int PRIORITY_INVALID = -1;
	public static final int PRIORITY_LOW = 1;
	public static final int PRIORITY_MEDIUM = 2;
	public static final int PRIORITY_HIGH = 3;
	
	public static JavaMethod get(ITypeRegistry types, Class cls, String name, Class... parameterTypes) {
		try {
			Method method = cls.getMethod(name, parameterTypes);
			return new JavaMethod(cls, method, types);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException("method " + name + " not found in class " + cls.getName(), ex);
		} catch (SecurityException ex) {
			throw new RuntimeException("method retrieval not permitted", ex);
		}
	}
	
	public static JavaMethod select(boolean doStatic, List<JavaMethod> methods, IEnvironmentGlobal environment, Expression... arguments) {
		int bestPriority = PRIORITY_INVALID;
		JavaMethod bestMethod = null;
		boolean isValid = false;
		
		for (JavaMethod method : methods) {
			if (method.isStatic() != doStatic) continue;
			
			int priority = method.getPriority(environment, arguments);
			if (priority == bestPriority) {
				isValid = false;
			} else if (priority > bestPriority) {
				isValid = true;
				bestMethod = method;
				bestPriority = priority;
			}
		}
		
		return isValid ? bestMethod : null;
	}
	
	public static ZenType[] predict(List<JavaMethod> methods, int numArguments, IEnvironmentGlobal environment) {
		ZenType[] results = new ZenType[numArguments];
		boolean[] ambiguous = new boolean[numArguments];
		
		for (JavaMethod method : methods) {
			if (method.accepts(numArguments)) {
				for (int i = 0; i < numArguments; i++) {
					if (i >= method.parameterTypes.length - 1 && method.method.isVarArgs()) {
						if (numArguments == method.parameterTypes.length) {
							ambiguous[i] = true;
						} else if (numArguments > method.parameterTypes.length) {
							if (results[i] != null) {
								ambiguous[i] = true;
							} else {
								results[i] = ((ZenTypeArray) method.parameterTypes[method.parameterTypes.length - 1]).getBaseType();
							}
						}
					}
					
					if (results[i] != null) {
						ambiguous[i] = true;
					} else {
						results[i] = method.parameterTypes[i];
					}
				}
			}
		}
		
		for (int i = 0; i < results.length; i++) {
			if (ambiguous[i]) {
				results[i] = null;
			}
		}
		
		return results;
	}
	
	private final Class owner;
	private final Method method;
	
	private final ZenType[] parameterTypes;
	private final boolean[] optional;
	private final ZenType returnType;
	
	public JavaMethod(Class owner, Method method, ITypeRegistry types) {
		this.owner = owner;
		this.method = method;
		
		returnType = types.getType(method.getGenericReturnType());
		parameterTypes = new ZenType[method.getParameterTypes().length];
		optional = new boolean[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = types.getType(method.getGenericParameterTypes()[i]);
			
			optional[i] = false;
			for (Annotation annotation : method.getParameterAnnotations()[i]) {
				if (annotation instanceof Optional) {
					optional[i] = true;
				}
			}
		}
		
		if (method.isVarArgs()) {
			optional[parameterTypes.length - 1] = true;
		}
	}
	
	public boolean isStatic() {
		return (method.getModifiers() & Modifier.STATIC) > 0;
	}
	
	public ZenType getReturnType() {
		return returnType;
	}
	
	public ZenType[] getParameterTypes() {
		return parameterTypes;
	}
	
	public Class getOwner() {
		return owner;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public boolean accepts(IEnvironmentGlobal environment, Expression... arguments) {
		return getPriority(environment, arguments) > 0;
	}
	
	public boolean accepts(int numArguments) {
		if (numArguments > parameterTypes.length) {
			return method.isVarArgs();
		} if (numArguments == parameterTypes.length) {
			return true;
		} else {
			for (int i = numArguments; i < parameterTypes.length; i++) {
				if (!optional[i]) return false;
			}
			return true;
		}
	}
	
	public int getPriority(IEnvironmentGlobal environment, Expression... arguments) {
		int result = PRIORITY_HIGH;
		if (arguments.length > parameterTypes.length) {
			if (method.isVarArgs()) {
				ZenType arrayType = parameterTypes[method.getParameterTypes().length - 1];
				ZenType baseType = ((ZenTypeArray) arrayType).getBaseType();
				for (int i = parameterTypes.length - 1; i < arguments.length; i++) {
					ZenType argType = arguments[i].getType();
					if (argType.equals(baseType)) {
						// OK
					} else if (argType.canCastImplicit(baseType, environment)) {
						result = Math.min(result, PRIORITY_LOW);
					} else {
						return PRIORITY_INVALID;
					}
				}
			} else {
				return PRIORITY_INVALID;
			}
		} else if (arguments.length < parameterTypes.length) {
			result = PRIORITY_MEDIUM;
			
			int checkUntil = parameterTypes.length;
			if (method.isVarArgs()) {
				checkUntil--;
			}
			
			checkOptional: for (int i = arguments.length; i < checkUntil; i++) {
				if (!optional[i]) {
					return PRIORITY_INVALID;
				}
			}
		}
		
		int checkUntil = arguments.length;
		if (arguments.length == parameterTypes.length && method.isVarArgs()) {
			ZenType arrayType = parameterTypes[method.getParameterTypes().length - 1];
			ZenType baseType = ((ZenTypeArray) arrayType).getBaseType();
			ZenType argType = arguments[arguments.length - 1].getType();
			
			if (argType.equals(arrayType)) {
				// OK
			} else if (argType.equals(baseType)) {
				// OK
			} else if (argType.canCastImplicit(arrayType, environment)) {
				result = Math.min(result, PRIORITY_LOW);
			} else if (argType.canCastImplicit(baseType, environment)) {
				result = Math.min(result, PRIORITY_LOW);
			} else {
				return PRIORITY_INVALID;
			}
			
			checkUntil = arguments.length - 1;
		}
		
		for (int i = 0; i < checkUntil; i++) {
			ZenType argType = arguments[i].getType();
			ZenType paramType = parameterTypes[i];
			if (!argType.equals(paramType)) {
				if (argType.canCastImplicit(paramType, environment)) {
					result = Math.min(result, PRIORITY_LOW);
				} else {
					return PRIORITY_INVALID;
				}
			}
		}
		
		return result;
	}
	
	public Expression callVirtual(ZenPosition position, IEnvironmentGlobal environment, Expression receiver, Expression... arguments) {
		if (isStatic()) {
			environment.error(position, "trying to call a static method dynamically");
			return new ExpressionInvalid(position, environment.getType(method.getReturnType()));
		} else {
			Expression[] actual = rematch(position, environment, arguments);
			return new ExpressionJavaCallVirtual(position, this, receiver, actual);
		}
	}
	
	public Expression callStatic(ZenPosition position, IEnvironmentGlobal environment, Expression... arguments) {
		if (isStatic()) {
			Expression[] actual = rematch(position, environment, arguments);
			return new ExpressionJavaCallStatic(position, this, actual);
		} else {
			environment.error(position, "trying to call a non-static method statically");
			return new ExpressionInvalid(position, environment.getType(method.getReturnType()));
		}
	}
	
	private Expression[] rematch(ZenPosition position, IEnvironmentGlobal environment, Expression... arguments) {
		// small optimization - don't run through this all if not necessary
		if (arguments.length == 0 && parameterTypes.length == 0) {
			return arguments;
		}
		
		Expression[] result = new Expression[method.getParameterTypes().length];
		for (int i = arguments.length; i < method.getParameterTypes().length; i++) {
			result[i] = parameterTypes[i].defaultValue(position);
		}
		
		int doUntil = parameterTypes.length;
		if (method.isVarArgs()) {
			doUntil = arguments.length - 1;
			ZenType paramType = parameterTypes[parameterTypes.length - 1];
			ZenType baseType = ((ZenTypeArray) paramType).getBaseType();
			
			if (arguments.length == parameterTypes.length) {
				ZenType argType = arguments[arguments.length - 1].getType();
				
				if (argType.equals(paramType)) {
					result[arguments.length - 1] = arguments[arguments.length - 1];
				} else if (argType.equals(baseType)) {
					result[arguments.length - 1] = new ExpressionArray(position, arguments[arguments.length - 1]);
				} else if (argType.canCastImplicit(paramType, environment)) {
					result[arguments.length - 1] = arguments[arguments.length - 1].cast(position, environment, paramType);
				} else {
					result[arguments.length - 1] = new ExpressionArray(position, arguments[arguments.length - 1]).cast(position, environment, paramType);
				}
			} else if (arguments.length > parameterTypes.length) {
				int offset = parameterTypes.length - 1;
				Expression[] values = new Expression[arguments.length - offset];
				for (int i = 0; i < values.length; i++) {
					values[i] = arguments[offset + i].cast(position, environment, baseType);
				}
				result[arguments.length - 1] = new ExpressionArray(position, values, (ZenTypeArrayBasic) paramType);
			}
		}
		
		for (int i = arguments.length; i < doUntil; i++) {
			result[i] = parameterTypes[i].defaultValue(position);
		}
		for (int i = 0; i < Math.min(arguments.length, doUntil); i++) {
			result[i] = arguments[i].cast(position, environment, parameterTypes[i]);
		}
		
		return result;
	}
}
