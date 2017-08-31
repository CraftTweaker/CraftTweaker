package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;

/**
 * @author Stan
 */
public class JavaMethod implements IJavaMethod {
    
    public static final int PRIORITY_INVALID = -1;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_HIGH = 3;
    private final Method method;
    private final ZenType ownerType;
    private final ZenType[] parameterTypes;
    private final boolean[] optional;
    private final ZenType returnType;
    
    public JavaMethod(Method method, ITypeRegistry types) {
        this.method = method;
        
        ownerType = types.getType(method.getDeclaringClass());
        returnType = types.getType(method.getGenericReturnType());
        parameterTypes = new ZenType[method.getParameterTypes().length];
        optional = new boolean[parameterTypes.length];
        for(int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = types.getType(method.getGenericParameterTypes()[i]);
            optional[i] = false;
            for(Annotation annotation : method.getParameterAnnotations()[i]) {
                if(annotation instanceof Optional) {
                    optional[i] = true;
                }
            }
        }
        
        if(method.isVarArgs()) {
            optional[parameterTypes.length - 1] = true;
        }
    }
    
    public static IJavaMethod get(ITypeRegistry types, Class cls, String name, Class... parameterTypes) {
        try {
            Method method = cls.getMethod(name, parameterTypes);
            if(method == null) {
                throw new RuntimeException("method " + name + " not found in class " + cls.getName());
            }
            return new JavaMethod(method, types);
        } catch(NoSuchMethodException ex) {
            throw new RuntimeException("method " + name + " not found in class " + cls.getName(), ex);
        } catch(SecurityException ex) {
            throw new RuntimeException("method retrieval not permitted", ex);
        }
    }
    
    public static IJavaMethod getStatic(String owner, String name, ZenType returnType, ZenType... arguments) {
        return new JavaMethodGenerated(true, false, false, owner, name, returnType, arguments, new boolean[arguments.length]);
    }

    public static IJavaMethod get(ITypeRegistry types, Method method) {
        return new JavaMethod(method, types);
    }

    public static IJavaMethod select(boolean doStatic, List<IJavaMethod> methods, IEnvironmentGlobal environment, Expression... arguments) {
        int bestPriority = PRIORITY_INVALID;
        IJavaMethod bestMethod = null;
        boolean isValid = false;
        
        for(IJavaMethod method : methods) {
            if(method.isStatic() != doStatic)
                continue;
            
            int priority = method.getPriority(environment, arguments);
            if(priority == bestPriority) {
                isValid = false;
            } else if(priority > bestPriority) {
                isValid = true;
                bestMethod = method;
                bestPriority = priority;
            }
        }
        
        return isValid ? bestMethod : null;
    }

    public static ZenType[] predict(List<IJavaMethod> methods, int numArguments) {
        ZenType[] results = new ZenType[numArguments];
        boolean[] ambiguous = new boolean[numArguments];
        for(IJavaMethod method : methods) {
            if(method.accepts(numArguments)) {
                ZenType[] parameterTypes = method.getParameterTypes();
                for(int i = 0; i < numArguments; i++) {
                    if(i >= parameterTypes.length - 1 && method.isVarargs()) {
                        if(numArguments == parameterTypes.length) {
                            ambiguous[i] = true;
                        } else if(numArguments > parameterTypes.length) {
                            if(results[i] != null) {
                                ambiguous[i] = true;
                            } else {
                                results[i] = ((ZenTypeArray) parameterTypes[parameterTypes.length - 1]).getBaseType();
                            }
                        }
                    } else {
                        if(results[i] != null) {
                            ambiguous[i] = true;
                        } else {
                            results[i] = parameterTypes[i];
                        }
                    }
                }
            }
        }
        
        for(int i = 0; i < results.length; i++) {
            if(ambiguous[i]) {
                results[i] = null;
            }
        }
        
        return results;
    }
    
    public static Expression[] rematch(ZenPosition position, IJavaMethod method, IEnvironmentGlobal environment, Expression... arguments) {
        ZenType[] parameterTypes = method.getParameterTypes();
        
        // small optimization - don't run through this all if not necessary
        if(arguments.length == 0 && parameterTypes.length == 0) {
            return arguments;
        }
        
        Expression[] result = new Expression[method.getParameterTypes().length];
        for(int i = arguments.length; i < method.getParameterTypes().length; i++) {
            result[i] = parameterTypes[i].defaultValue(position);
        }
        
        int doUntil = parameterTypes.length;
        if(method.isVarargs()) {
            doUntil = arguments.length - 1;
            ZenType paramType = parameterTypes[parameterTypes.length - 1];
            ZenType baseType = ((ZenTypeArray) paramType).getBaseType();
            
            if(arguments.length == parameterTypes.length) {
                ZenType argType = arguments[arguments.length - 1].getType();
                
                if(argType.equals(paramType)) {
                    result[arguments.length - 1] = arguments[arguments.length - 1];
                } else if(argType.equals(baseType)) {
                    result[arguments.length - 1] = new ExpressionArray(position, (ZenTypeArrayBasic) paramType, arguments[arguments.length - 1]);
                } else if(argType.canCastImplicit(paramType, environment)) {
                    result[arguments.length - 1] = arguments[arguments.length - 1].cast(position, environment, paramType);
                } else {
                    result[arguments.length - 1] = new ExpressionArray(position, (ZenTypeArrayBasic) paramType, arguments[arguments.length - 1]).cast(position, environment, paramType);
                }
            } else if(arguments.length > parameterTypes.length) {
                int offset = parameterTypes.length - 1;
                Expression[] values = new Expression[arguments.length - offset];
                for(int i = 0; i < values.length; i++) {
                    values[i] = arguments[offset + i].cast(position, environment, baseType);
                }
                result[arguments.length - 1] = new ExpressionArray(position, (ZenTypeArrayBasic) paramType, values);
            }
        }
        
        for(int i = arguments.length; i < doUntil; i++) {
            result[i] = parameterTypes[i].defaultValue(position);
        }
        for(int i = 0; i < Math.min(arguments.length, doUntil); i++) {
            result[i] = arguments[i].cast(position, environment, parameterTypes[i]);
        }
        
        return result;
    }
    
    @Override
    public boolean isStatic() {
        return (method.getModifiers() & Modifier.STATIC) > 0;
    }
    
    @Override
    public boolean isVarargs() {
        return method.isVarArgs();
    }
    
    @Override
    public ZenType getReturnType() {
        return returnType;
    }
    
    @Override
    public ZenType[] getParameterTypes() {
        return parameterTypes;
    }
    
    public Class getOwner() {
        return method.getDeclaringClass();
    }
    
    public Method getMethod() {
        return method;
    }
    
    @Override
    public boolean accepts(IEnvironmentGlobal environment, Expression... arguments) {
        return getPriority(environment, arguments) > 0;
    }
    
    @Override
    public boolean accepts(int numArguments) {
        if(numArguments > parameterTypes.length) {
            return method.isVarArgs();
        }
        if(numArguments == parameterTypes.length) {
            return true;
        } else {
            for(int i = numArguments; i < parameterTypes.length; i++) {
                if(!optional[i])
                    return false;
            }
            return true;
        }
    }
    
    @Override
    public int getPriority(IEnvironmentGlobal environment, Expression... arguments) {
        int result = PRIORITY_HIGH;
        if(arguments.length > parameterTypes.length) {
            if(method.isVarArgs()) {
                ZenType arrayType = parameterTypes[method.getParameterTypes().length - 1];
                ZenType baseType = ((ZenTypeArray) arrayType).getBaseType();
                for(int i = parameterTypes.length - 1; i < arguments.length; i++) {
                    ZenType argType = arguments[i].getType();
                    if(argType.equals(baseType)) {
                        // OK
                    } else if(argType.canCastImplicit(baseType, environment)) {
                        result = Math.min(result, PRIORITY_LOW);
                    } else {
                        return PRIORITY_INVALID;
                    }
                }
            } else {
                return PRIORITY_INVALID;
            }
        } else if(arguments.length < parameterTypes.length) {
            result = PRIORITY_MEDIUM;
            
            int checkUntil = parameterTypes.length;
            if(method.isVarArgs()) {
                checkUntil--;
            }
            for(int i = arguments.length; i < checkUntil; i++) {
                if(!optional[i]) {
                    return PRIORITY_INVALID;
                }
            }
        }
        
        int checkUntil = arguments.length;
        if(arguments.length == parameterTypes.length && method.isVarArgs()) {
            ZenType arrayType = parameterTypes[method.getParameterTypes().length - 1];
            ZenType baseType = ((ZenTypeArray) arrayType).getBaseType();
            ZenType argType = arguments[arguments.length - 1].getType();
            
            if(argType.equals(arrayType) || argType.equals(baseType)) {
                // OK
            } else if(argType.canCastImplicit(arrayType, environment)) {
                result = Math.min(result, PRIORITY_LOW);
            } else if(argType.canCastImplicit(baseType, environment)) {
                result = Math.min(result, PRIORITY_LOW);
            } else {
                return PRIORITY_INVALID;
            }
            
            checkUntil = arguments.length - 1;
        }
        
        for(int i = 0; i < checkUntil; i++) {
            ZenType argType = arguments[i].getType();
            ZenType paramType = parameterTypes[i];
            if(!argType.equals(paramType)) {
                if(argType.canCastImplicit(paramType, environment)) {
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
        if(isStatic()) {
            throw new UnsupportedOperationException("Method is static");
        } else {
            if(method.getDeclaringClass().isInterface()) {
                output.invokeInterface(method.getDeclaringClass(), method.getName(), method.getReturnType(), method.getParameterTypes());
            } else {
                output.invokeVirtual(method.getDeclaringClass(), method.getName(), method.getReturnType(), method.getParameterTypes());
            }
        }
    }
    
    @Override
    public void invokeStatic(MethodOutput output) {
        if(!isStatic()) {
            throw new UnsupportedOperationException("Method is not static");
        } else {
            output.invokeStatic(method.getDeclaringClass(), method.getName(), method.getReturnType(), method.getParameterTypes());
        }
    }
    
    @Override
    public String toString() {
        return "JavaMethod: " + method.toString();
    }
}
