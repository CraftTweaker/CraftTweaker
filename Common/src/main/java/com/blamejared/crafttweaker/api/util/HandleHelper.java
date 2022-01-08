package com.blamejared.crafttweaker.api.util;


import com.blamejared.crafttweaker.platform.Services;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class HandleHelper {
    
    public static final class UnableToLinkHandleException extends RuntimeException {
        
        public UnableToLinkHandleException(final String message, final Throwable cause) {
            
            super(message, cause);
        }
        
    }
    
    public static final class FailedInvocationException extends RuntimeException {
        
        private FailedInvocationException(final String message, final Throwable cause) {
            
            super(message, cause);
        }
        
    }
    
    public interface MethodHandleInvoker<R> {
        
        R invoke() throws Throwable;
        
    }
    
    public interface MethodHandleVoidInvoker {
        
        void invoke() throws Throwable;
        
    }
    
    private static final MethodHandles.Lookup LOOKUP = findLookup();
    
    private HandleHelper() {}
    
    public static MethodHandle linkMethod(final Class<?> type, final String methodName, final Class<?> returnType, final Class<?>... arguments) {
        
        try {
            final Method target = Services.PLATFORM.findMethod(type, methodName, returnType, arguments);
            
            return LOOKUP.unreflect(target);
        } catch(final IllegalAccessException e) {
            throw new UnableToLinkHandleException("Unable to access method " + StringUtils.quoteAndEscape(methodName), e);
        }
    }
    
    public static VarHandle linkField(final Class<?> owner, final String fieldName, final String fieldDescription) {
        
        try {
            final Field target = Services.PLATFORM.findField(owner, fieldName, fieldDescription);
            
            return LOOKUP.unreflectVarHandle(target);
        } catch(final IllegalAccessException e) {
            throw new UnableToLinkHandleException("Unable to access field " + StringUtils.quoteAndEscape(fieldName), e);
        }
    }
    
    public static <R> R invoke(final MethodHandleInvoker<R> invoker) {
        
        try {
            return invoker.invoke();
        } catch(final WrongMethodTypeException e) {
            throw new FailedInvocationException("Unable to invoke target method handle: check your arguments", e);
        } catch(final Throwable throwable) {
            if(throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException("Invoked method threw an exception", throwable);
        }
    }
    
    public static void invokeVoid(final MethodHandleVoidInvoker invoker) {
        
        try {
            invoker.invoke();
        } catch(final WrongMethodTypeException e) {
            throw new FailedInvocationException("Unable to invoke target method handle: check your arguments", e);
        } catch(final Throwable throwable) {
            if(throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException("Invoked method threw an exception", throwable);
        }
    }
    
    private static MethodHandles.Lookup findLookup() {
        try {
            final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            final Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            final Object unsafe = unsafeField.get(null);
            final Method getObject = unsafeClass.getDeclaredMethod("getObject", Object.class, long.class);
            final Method staticFieldBase = unsafeClass.getDeclaredMethod("staticFieldBase", Field.class);
            final Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);
            
            final Class<?> methodHandlesLookupClass = MethodHandles.Lookup.class;
            final Field[] declaredFields = methodHandlesLookupClass.getDeclaredFields();
            
            for (final Field declaredField : declaredFields) {
                if (declaredField.getType() != MethodHandles.Lookup.class) continue;
                
                final Object base = staticFieldBase.invoke(unsafe, declaredField);
                final long offset = (Long) staticFieldOffset.invoke(unsafe, declaredField);
                final MethodHandles.Lookup lookup = (MethodHandles.Lookup) getObject.invoke(unsafe, base, offset);
    
                if (lookup.lookupModes() != 127) continue;
                
                return lookup;
            }
            
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to find lookup", e);
        }
        
        throw new IllegalStateException("Unable to find lookup");
    }
    
}
