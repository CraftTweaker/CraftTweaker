package com.blamejared.crafttweaker.api.util;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class MethodHandleHelper {
    public static final class UnableToLinkMethodHandleException extends RuntimeException {
        private UnableToLinkMethodHandleException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
    
    public static final class FailedInvocationException extends RuntimeException {
        private FailedInvocationException(final String message, final Throwable cause)  {
            super(message, cause);
        }
    }
    
    public interface MethodHandleInvoker<R> {
        R invoke() throws Throwable;
    }
    
    public interface MethodHandleVoidInvoker {
        void invoke() throws Throwable;
    }
    
    private MethodHandleHelper() {}
    
    public static MethodHandle linkMethod(final Class<?> type, final String methodName, final Class<?>... arguments) {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final Method target = ObfuscationReflectionHelper.findMethod(type, methodName, arguments);
            
            return lookup.unreflect(target);
        } catch (final ObfuscationReflectionHelper.UnableToFindMethodException e) {
            throw new UnableToLinkMethodHandleException("Method " + StringUtils.quoteAndEscape(methodName) + " was not found inside class " + type.getName(), e);
        } catch (final IllegalAccessException e) {
            throw new UnableToLinkMethodHandleException("Unable to access method " + StringUtils.quoteAndEscape(methodName), e);
        }
    }
    
    public static MethodHandle linkGetter(final Class<?> type, final String fieldName) {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final Field target = ObfuscationReflectionHelper.findField(castToSuperExplicitly(type), fieldName);
        
            return lookup.unreflectGetter(target);
        } catch (final ObfuscationReflectionHelper.UnableToFindFieldException e) {
            throw new UnableToLinkMethodHandleException("Field " + StringUtils.quoteAndEscape(fieldName) + " was not found inside class " + type.getName(), e);
        } catch (final IllegalAccessException e) {
            throw new UnableToLinkMethodHandleException("Unable to access field " + StringUtils.quoteAndEscape(fieldName), e);
        }
    }
    
    public static MethodHandle linkSetter(final Class<?> type, final String fieldName) {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final Field target = ObfuscationReflectionHelper.findField(castToSuperExplicitly(type), fieldName);
            
            return lookup.unreflectSetter(target);
        } catch (final ObfuscationReflectionHelper.UnableToFindFieldException e) {
            throw new UnableToLinkMethodHandleException("Field " + StringUtils.quoteAndEscape(fieldName) + " was not found inside class " + type.getName(), e);
        } catch (final IllegalAccessException e) {
            throw new UnableToLinkMethodHandleException("Unable to access field " + StringUtils.quoteAndEscape(fieldName), e);
        }
    }
    
    public static <R> R invoke(final MethodHandleInvoker<R> invoker) {
        try {
            return invoker.invoke();
        } catch (final WrongMethodTypeException e) {
            throw new FailedInvocationException("Unable to invoke target method handle: check your arguments", e);
        } catch (final Throwable throwable) {
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException("Invoked method threw an exception", throwable);
        }
    }
    
    public static void invokeVoid(final MethodHandleVoidInvoker invoker) {
        try {
            invoker.invoke();
        } catch (final WrongMethodTypeException e) {
            throw new FailedInvocationException("Unable to invoke target method handle: check your arguments", e);
        } catch (final Throwable throwable) {
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException("Invoked method threw an exception", throwable);
        }
    }
    
    // Don't ask, this makes the compiler shut up
    private static <T> Class<? super T> castToSuperExplicitly(final Class<T> t) {
        return t;
    }
}
