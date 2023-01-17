package com.blamejared.crafttweaker.api.util;


import com.blamejared.crafttweaker.platform.Services;
import com.dwarveddonuts.neverwinter.handle.AccessType;
import com.dwarveddonuts.neverwinter.handle.HandleInvoker;
import com.dwarveddonuts.neverwinter.handle.Handles;
import com.dwarveddonuts.neverwinter.handle.RequestType;
import org.apache.logging.log4j.core.util.ObjectArrayIterator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Holds various utilities to deal with {@link MethodHandle}s and {@link VarHandle}s for reflection purposes.
 *
 * <p>All handles returned by methods in this class are fully privileged, meaning that they can perform any desired
 * operation on the target, as long as it makes sense. For example, it is allowed for a {@code VarHandle} returned by
 * this class to alter the value of a {@code final} field in a class.</p>
 *
 * <p>For maximum performance, it is suggested to invoke this method once and cache the result in a {@code static final}
 * field. The JVM will then be able to inline the call and replace it with a standard opcode instruction.</p>
 *
 * @since 9.0.0
 */
public final class HandleUtil {
    
    
    /**
     * Indicates that an error occurred while attempting to build a {@link MethodHandle} or {@link VarHandle}.
     *
     * @since 9.0.0
     */
    public static final class UnableToLinkHandleException extends RuntimeException {
        
        private UnableToLinkHandleException(final String message, final Throwable cause) {
            
            super(message, cause);
        }
        
    }
    
    /**
     * Indicates that an error occurred while attempting to invoke a specific {@link MethodHandle}.
     *
     * @since 9.0.0
     */
    public static final class FailedInvocationException extends RuntimeException {
        
        private FailedInvocationException(final String message, final Throwable cause) {
            
            super(message, cause);
        }
        
    }
    
    /**
     * Holds the names that are supposed to be used to locate the target.
     *
     * <p>This is useful in case of mixed mappings, where one platform might require a name such as {@code field_123},
     * whereas other platforms prefer {@code f_456_}. An instance of this class can be obtained through the static
     * method {@link #of(String...)}.</p>
     *
     * @since 10.0.0
     */
    public static final class Names implements Iterable<String> {
        
        private final String[] names;
        
        private Names(final String... names) {
            
            this.names = names;
        }
        
        /**
         * Obtains a new instance of this class wrapping the defined names.
         *
         * <p>Name duplicates are automatically removed.</p>
         *
         * @param names The names to wrap.
         *
         * @return An instance of this class.
         *
         * @since 10.0.0
         */
        public static Names of(final String... names) {
            
            return new Names(Set.of(names).toArray(String[]::new));
        }
        
        @Override
        public Iterator<String> iterator() {
            
            return new ObjectArrayIterator<>(this.names);
        }
        
        @Override
        public String toString() {
            
            return Arrays.toString(this.names);
        }
        
    }
    
    private HandleUtil() {}
    
    /**
     * Obtains a new {@link MethodHandle} to the specified method with the given {@link AccessType}.
     *
     * <p>If more than one name needs to be specified, refer to
     * {@link #linkMethod(Class, AccessType, Names, Class, Class[])} instead.</p>
     *
     * <p>Mapping resolution is automatic, meaning that the deobfuscation from one mapping set to the current one is
     * automatically attempted.</p>
     *
     * @param type       The {@link Class} which defines the method to invoke.
     * @param accessType The {@linkplain AccessType type of access} that the method handle should attempt
     * @param methodName The name of the method that needs to be invoked. The name should be obfuscated.
     * @param returnType The {@link Class} that represents the return type of the target method.
     * @param arguments  An array holding various {@link Class}es, each representing the type of method parameter.
     *
     * @return A {@link MethodHandle} that links to the method specified, if it exists.
     *
     * @throws UnableToLinkHandleException If linkage fails due to no method found with the given name or the method
     *                                     was found, but it was unable to be accessed. The latter situation should
     *                                     never occur.
     * @since 10.1.0
     */
    public static MethodHandle linkMethod(final Class<?> type, final AccessType accessType, final String methodName, final Class<?> returnType, final Class<?>... arguments) {
        
        return linkMethod(type, accessType, Names.of(methodName), returnType, arguments);
    }
    
    /**
     * Obtains a new {@link MethodHandle} to the specified method with the given {@link AccessType}.
     *
     * <p>The various names will attempt resolution one at a time, and the first {@link MethodHandle} that links
     * successfully will be returned.</p>
     *
     * <p>Mapping resolution is automatic, meaning that the deobfuscation from one mapping set to the current one is
     * automatically attempted.</p>
     *
     * @param type        The {@link Class} which defines the method to invoke.
     * @param accessType  The {@linkplain AccessType type of access} that the method handle should attempt.
     * @param methodNames The various possible obfuscated names of the method that needs to be invoked.
     * @param returnType  The {@link Class} that represents the return type of the target method.
     * @param arguments   An array holding various {@link Class}es, each representing the type of method parameter.
     *
     * @return A {@link MethodHandle} that links to the method specified, if it exists.
     *
     * @throws UnableToLinkHandleException If linkage fails due to no method found with the given names or a method
     *                                     was found, but it was unable to be accessed. The latter situation should
     *                                     never occur.
     * @since 10.1.0
     */
    public static MethodHandle linkMethod(final Class<?> type, final AccessType accessType, final Names methodNames, final Class<?> returnType, final Class<?>... arguments) {
        
        final MethodType signature = MethodType.methodType(returnType, arguments);
        List<com.dwarveddonuts.neverwinter.handle.UnableToLinkHandleException> exceptions = null;
        
        for(final String methodName : methodNames) {
            
            try {
                final String targetName = Services.PLATFORM.findMappedMethodName(type, methodName, returnType, arguments);
                return Handles.linkMethod(RequestType.trusted(), accessType, type, targetName, signature);
            } catch(final com.dwarveddonuts.neverwinter.handle.UnableToLinkHandleException e) {
                (exceptions == null ? (exceptions = new ArrayList<>()) : exceptions).add(e);
            }
        }
        
        final Throwable cause = exceptions == null ? new NullPointerException("No method name specified") : exceptions.get(0);
        final UnableToLinkHandleException e = new UnableToLinkHandleException("No method with names " + methodNames + " found in target type " + type.getName(), cause);
        
        for(int i = 1, s = exceptions == null ? 0 : exceptions.size(); i < s; ++i) {
            e.addSuppressed(exceptions.get(i));
        }
        
        throw e;
    }
    
    /**
     * Obtains a new {@link VarHandle} to the specified field with the given {@link AccessType}.
     *
     * <p>If more than one name needs to be specified, refer to {@link #linkField(Class, AccessType, Names, Class)}
     * instead.</p>
     *
     * <p>Mapping resolution is automatic, meaning that the deobfuscation from one mapping set to the current one is
     * automatically attempted.</p>
     *
     * @param owner      The {@link Class} which defines the field that needs to be accessed.
     * @param accessType The {@linkplain AccessType type of access} that the variable handle should attempt.
     * @param fieldName  The name of the field that needs to be accessed. The name should be obfuscated.
     * @param type       The {@link Class} that represents the type of the field that needs to be accessed.
     *
     * @return A {@link VarHandle} that links to the field specified, if it exists.
     *
     * @throws UnableToLinkHandleException If linkage fails due to no field found with the given name or the field was
     *                                     found, but it was unable to be accessed. The latter situation should never
     *                                     occur.
     * @since 10.1.0
     */
    public static VarHandle linkField(final Class<?> owner, final AccessType accessType, final String fieldName, final Class<?> type) {
        
        return linkField(owner, accessType, Names.of(fieldName), type);
    }
    
    /**
     * Obtains a new {@link VarHandle} to the specified field with the given {@link AccessType}.
     *
     * <p>The various names will attempt resolution one at a time, and the first {@link VarHandle} that links
     * successfully will be returned.</p>
     *
     * <p>Mapping resolution is automatic, meaning that the deobfuscation from one mapping set to the current one is
     * automatically attempted.</p>
     *
     * @param owner      The {@link Class} which defines the field that needs to be accessed.
     * @param accessType The {@linkplain AccessType type of access} that the variable handle should attempt.
     * @param fieldNames The various possible obfuscated names of the field that needs to be accessed.
     * @param type       The {@link Class} that represents the type of the field that needs to be accessed.
     *
     * @return A {@link VarHandle} that links to the field specified, if it exists.
     *
     * @throws UnableToLinkHandleException If linkage fails due to no field found with the given names or a field was
     *                                     found, but it was unable to be accessed. The latter situation should never
     *                                     occur.
     * @since 10.1.0
     */
    public static VarHandle linkField(final Class<?> owner, final AccessType accessType, final Names fieldNames, final Class<?> type) {
        
        List<com.dwarveddonuts.neverwinter.handle.UnableToLinkHandleException> exceptions = null;
        
        for(final String fieldName : fieldNames) {
            
            try {
                final String targetName = Services.PLATFORM.findMappedFieldName(owner, fieldName, type);
                return Handles.linkField(RequestType.trusted(), accessType, owner, targetName, type);
            } catch(final com.dwarveddonuts.neverwinter.handle.UnableToLinkHandleException e) {
                (exceptions == null ? (exceptions = new ArrayList<>()) : exceptions).add(e);
            }
        }
        
        final Throwable cause = exceptions == null ? new NullPointerException("No method name specified") : exceptions.get(0);
        final UnableToLinkHandleException e = new UnableToLinkHandleException("No field with names " + fieldNames + " found in target type " + type.getName(), cause);
        
        for(int i = 1, s = exceptions == null ? 0 : exceptions.size(); i < s; ++i) {
            e.addSuppressed(exceptions.get(i));
        }
        
        throw e;
    }
    
    /**
     * Attempts invocation of a {@link MethodHandle} which will return something, catching any exceptions.
     *
     * <p>While the job of invoking the handle is still given to the caller, it is suggested to use this method as it
     * automatically catches and rethrows exceptions as needed. See further in the documentation for more information on
     * the exception contract of this method.</p>
     *
     * <p>If the method to invoke returns nothing (i.e. {@code void}), use {@link #invokeVoid(HandleInvoker.Void)}
     * instead.</p>
     *
     * @param invoker The {@link HandleInvoker} responsible for invoking the actual handle.
     * @param <R>     The type of the value returned by the invocation of the method handle.
     *
     * @return The result of the method invocation.
     *
     * @throws FailedInvocationException If the invocation fails due to a mismatch between the invocation in the
     *                                   {@code invoker} and the one expected by the handle/
     * @throws RuntimeException          If any other exception occurs. Namely, if the exception is already a subclass
     *                                   of {@code RuntimeException}, the exception will be rethrown as is. Otherwise,
     *                                   it will be wrapped in a {@code RuntimeException} and rethrown.
     * @since 10.1.0
     */
    public static <R> R invoke(final HandleInvoker<R> invoker) {
        
        return Handles.invokeHandle(invoker);
    }
    
    /**
     * Attempts invocation of a {@link MethodHandle} which does not return anything, catching any exceptions.
     *
     * <p>While the job of invoking the handle is still given to the caller, it is suggested to use this method as it
     * automatically catches and rethrows exceptions as needed. See further in the documentation for more information on
     * the exception contract of this method.</p>
     *
     * <p>If the method to invoke returns something, use {@link #invoke(HandleInvoker)} instead.</p>
     *
     * @param invoker The {@link HandleInvoker.Void} responsible for invoking the actual handle.
     *
     * @throws FailedInvocationException If the invocation fails due to a mismatch between the invocation in the
     *                                   {@code invoker} and the one expected by the handle/
     * @throws RuntimeException          If any other exception occurs. Namely, if the exception is already a subclass
     *                                   of {@code RuntimeException}, the exception will be rethrown as is. Otherwise,
     *                                   it will be wrapped in a {@code RuntimeException} and rethrown.
     * @since 10.1.0
     */
    public static void invokeVoid(final HandleInvoker.Void invoker) {
        
        Handles.invokeVoidHandle(invoker);
    }
    
}
