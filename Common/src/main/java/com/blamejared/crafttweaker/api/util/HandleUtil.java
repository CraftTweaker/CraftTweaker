package com.blamejared.crafttweaker.api.util;


import com.blamejared.crafttweaker.platform.Services;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Field;

public final class HandleUtil {
    
    public static final class UnableToLinkHandleException extends RuntimeException {
        
        private UnableToLinkHandleException(final String message, final Throwable cause) {
            
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
    
    public enum AccessType {
        VIRTUAL,
        STATIC
    }
    
    private static final MethodHandles.Lookup LOOKUP = findLookup();
    
    private HandleUtil() {}
    
    public static MethodHandle linkMethod(final Class<?> type, final AccessType accessType, final String methodName, final Class<?> returnType, final Class<?>... arguments) {
        
        try {
            final String targetName = Services.PLATFORM.findMappedMethodName(type, methodName, returnType, arguments);
            final MethodType signature = MethodType.methodType(returnType, arguments);
            
            return switch(accessType) {
                case STATIC -> LOOKUP.findStatic(type, targetName, signature);
                case VIRTUAL -> LOOKUP.findVirtual(type, targetName, signature);
            };
        } catch(final NoSuchMethodException e) {
            throw new UnableToLinkHandleException("No method named " + StringUtil.quoteAndEscape(methodName) + " found in target type " + type.getName(), e);
        } catch(final IllegalAccessException e) {
            throw new UnableToLinkHandleException("Unable to access method " + StringUtil.quoteAndEscape(methodName), e);
        }
    }
    
    public static VarHandle linkField(final Class<?> owner, final AccessType accessType, final String fieldName, final Class<?> type) {
        
        try {
            final String targetName = Services.PLATFORM.findMappedFieldName(owner, fieldName, type);
            
            return switch(accessType) {
                case STATIC -> LOOKUP.findStaticVarHandle(owner, targetName, type);
                case VIRTUAL -> LOOKUP.findVarHandle(owner, targetName, type);
            };
        } catch(final NoSuchFieldException e) {
            throw new UnableToLinkHandleException("No field named " + StringUtil.quoteAndEscape(fieldName) + " found in target type " + type.getName(), e);
        } catch(final IllegalAccessException e) {
            throw new UnableToLinkHandleException("Unable to access field " + StringUtil.quoteAndEscape(fieldName), e);
        }
    }
    
    public static <R> R invoke(final MethodHandleInvoker<R> invoker) {
        
        try {
            return invoker.invoke();
        } catch(final WrongMethodTypeException e) {
            throw new FailedInvocationException("Unable to invoke target handle: check your arguments", e);
        } catch(final Throwable throwable) {
            if(throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException("Invoked handle threw an exception", throwable);
        }
    }
    
    public static void invokeVoid(final MethodHandleVoidInvoker invoker) {
        
        try {
            invoker.invoke();
        } catch(final WrongMethodTypeException e) {
            throw new FailedInvocationException("Unable to invoke target handle: check your arguments", e);
        } catch(final Throwable throwable) {
            if(throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException("Invoked handle threw an exception", throwable);
        }
    }
    
    private static MethodHandles.Lookup findLookup() {
        
        try {
            final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            final MethodHandles.Lookup unsafeLookup = MethodHandles.privateLookupIn(unsafeClass, MethodHandles.lookup());
            final VarHandle unsafeField = unsafeLookup.findStaticVarHandle(unsafeClass, "theUnsafe", unsafeClass);
            final MethodHandle staticFieldBase = unsafeLookup.findVirtual(unsafeClass, "staticFieldBase", MethodType.methodType(Object.class, Field.class));
            final MethodHandle staticFieldOffset = unsafeLookup.findVirtual(unsafeClass, "staticFieldOffset", MethodType.methodType(long.class, Field.class));
            final MethodHandle getObject = unsafeLookup.findVirtual(unsafeClass, "getObject", MethodType.methodType(Object.class, Object.class, long.class));
            
            final Object unsafe = unsafeField.get();
            
            final Class<?> methodHandlesLookupClass = MethodHandles.Lookup.class;
            final Field[] declaredFields = methodHandlesLookupClass.getDeclaredFields();
            
            for(final Field declaredField : declaredFields) {
                if(declaredField.getType() != MethodHandles.Lookup.class) {
                    continue;
                }
                
                final Object base = staticFieldBase.invoke(unsafe, declaredField);
                final long offset = (long) staticFieldOffset.invoke(unsafe, declaredField);
                final Object lookupObject = getObject.invoke(unsafe, base, offset);
                final MethodHandles.Lookup lookup = GenericUtil.uncheck(lookupObject);
                
                if(lookup.lookupModes() != 127) {
                    continue;
                }
                
                return lookup;
            }
            
        } catch(final Throwable e) {
            throw new IllegalStateException("Unable to find lookup", e);
        }
        
        throw new IllegalStateException("Unable to find lookup");
    }
    
}
