/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeArray;
import static stanhebben.zenscript.type.natives.JavaMethod.PRIORITY_HIGH;
import static stanhebben.zenscript.type.natives.JavaMethod.PRIORITY_INVALID;
import static stanhebben.zenscript.type.natives.JavaMethod.PRIORITY_LOW;
import static stanhebben.zenscript.type.natives.JavaMethod.PRIORITY_MEDIUM;
import stanhebben.zenscript.util.MethodOutput;

/**
 *
 * @author Stan
 */
public class JavaMethodGenerated implements IJavaMethod {
	private final boolean isStatic;
	private final boolean isInterface;
	private final boolean isVarargs;
	private final String owner;
	private final String name;
	
	private final ZenType[] parameterTypes;
	private final boolean[] optional;
	private final ZenType returnType;
	
	private final String descriptor;

	public JavaMethodGenerated(
			boolean isStatic,
			boolean isInterface,
			boolean isVarargs,
			String owner,
			String name,
			ZenType returnType,
			ZenType[] arguments,
			boolean[] optional) {
		this.isStatic = isStatic;
		this.isInterface = isInterface;
		this.isVarargs = isVarargs;
		this.owner = owner;
		this.name = name;
		
		this.returnType = returnType;
		this.parameterTypes = arguments;
		this.optional = optional;
		
		StringBuilder descriptorString = new StringBuilder();
		descriptorString.append('(');
		for (ZenType argument : arguments) {
			descriptorString.append(argument.getSignature());
		}
		descriptorString.append(')');
		descriptorString.append(returnType.getSignature());
		descriptor = descriptorString.toString();
	}
	
	@Override
	public boolean isStatic() {
		return isStatic;
	}
	
	@Override
	public boolean isVarargs() {
		return isVarargs;
	}
	
	@Override
	public boolean accepts(int numArguments) {
		if (numArguments > parameterTypes.length) {
			return isVarargs;
		} if (numArguments == parameterTypes.length) {
			return true;
		} else {
			for (int i = numArguments; i < parameterTypes.length; i++) {
				if (!optional[i]) return false;
			}
			return true;
		}
	}
	
	@Override
	public boolean accepts(IEnvironmentGlobal environment, Expression... arguments) {
		return getPriority(environment, arguments) > 0;
	}

	@Override
	public int getPriority(IEnvironmentGlobal environment, Expression... arguments) {
		int result = PRIORITY_HIGH;
		if (arguments.length > parameterTypes.length) {
			if (isVarargs) {
				ZenType arrayType = parameterTypes[parameterTypes.length - 1];
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
			if (isVarargs) {
				checkUntil--;
			}
			
			checkOptional: for (int i = arguments.length; i < checkUntil; i++) {
				if (!optional[i]) {
					return PRIORITY_INVALID;
				}
			}
		}
		
		int checkUntil = arguments.length;
		if (arguments.length == parameterTypes.length && isVarargs) {
			ZenType arrayType = parameterTypes[parameterTypes.length - 1];
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

	@Override
	public void invokeVirtual(MethodOutput output) {
		if (isStatic) {
			throw new UnsupportedOperationException("Not supported yet.");
		} else {
			if (isInterface) {
				output.invokeInterface(owner, name, descriptor);
			} else {
				output.invokeVirtual(owner, name, descriptor);
			}
		}
	}

	@Override
	public void invokeStatic(MethodOutput output) {
		if (!isStatic) {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		} else {
			output.invokeStatic(owner, name, descriptor);
		}
	}

	@Override
	public ZenType getReturnType() {
		return returnType;
	}
	
	@Override
	public ZenType[] getParameterTypes() {
		return parameterTypes;
	}
}
