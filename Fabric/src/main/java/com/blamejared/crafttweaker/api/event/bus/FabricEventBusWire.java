package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;
import net.fabricmc.fabric.api.event.Event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.stream.Stream;

public final class FabricEventBusWire<E, S> implements IEventBusWire {
    @SuppressWarnings("ClassCanBeRecord")
    private static final class PostingInvocationHandler<T> implements InvocationHandler {
        private final Method targetMethod;
        private final MethodHandle wrapper;
        private final Phase phase;
        private final MethodHandle reveal;
        private final IEventBus<T> bus;
        
        PostingInvocationHandler(
                final Method targetMethod,
                final MethodHandle wrapper,
                final Phase phase,
                final MethodHandle reveal,
                final IEventBus<T> bus
        ) {
            this.targetMethod = targetMethod;
            this.wrapper = wrapper;
            this.phase = phase;
            this.reveal = reveal;
            this.bus = bus;
        }
        
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            
            final String methodName = method.getName();
            if ("equals".equals(methodName)) {
                return this.$equals(proxy, args[0]);
            }
            if ("toString".equals(methodName)) {
                return this.$toString(proxy);
            }
            if ("hashCode".equals(methodName)) {
                return this.$hashCode(proxy);
            }
            if (method.isDefault()) {
                return InvocationHandler.invokeDefault(proxy, method, args);
            }
            if (this.targetMethod.equals(method)) {
                return this.$invoke(proxy, args);
            }
            throw new IllegalArgumentException("Unknown method to proxy " + methodName);
        }
        
        private boolean $equals(final Object $this, final Object other) {
            return $this == other;
        }
        
        private String $toString(final Object $this) {
            return $this.getClass().getName() + '@' + $this.hashCode() + "//" + this.targetMethod.getDeclaringClass().getName() + '#' + this.targetMethod.getName();
        }
        
        private int $hashCode(final Object $this) {
            return System.identityHashCode($this);
        }
        
        private Object $invoke(@SuppressWarnings("unused") final Object $this, final Object[] args) throws Throwable {
            final T object = GenericUtil.uncheck(this.wrapper.invokeExact(args));
            this.bus.post(this.phase, object);
            return this.reveal == null? null : this.reveal.invokeExact(object);
        }
        
    }
    
    private record WrapReveal(Method wrap, Method reveal) {}
    
    private static final MethodHandles.Lookup PUBLIC_LOOKUP = MethodHandles.publicLookup();
    
    private final Event<E> event;
    private final Class<E> originalEventClass;
    private final TypeToken<S> wrappedEventClass;
    private final FabricEventPhaseMapper mapper;
    private final Method functionalMethod;
    private final MethodHandle wrapper;
    private final MethodHandle reveal;
    
    private FabricEventBusWire(
            final Event<E> event,
            final Class<E> originalEventClass,
            final TypeToken<S> wrappedEventClass,
            final FabricEventPhaseMapper mapper,
            final Method functionalMethod,
            final MethodHandle wrapper,
            final MethodHandle reveal
    ) {
        this.event = event;
        this.originalEventClass = originalEventClass;
        this.wrappedEventClass = wrappedEventClass;
        this.mapper = mapper;
        this.functionalMethod = functionalMethod;
        this.wrapper = wrapper;
        this.reveal = reveal;
    }
    
    public static <E, S> FabricEventBusWire<E, S> of(
            final Event<E> event,
            final Class<E> originalEventClass,
            final Class<S> wrappedEventClass
    ) {
        
        return of(event, originalEventClass, TypeToken.of(wrappedEventClass));
    }
    
    public static <E, S> FabricEventBusWire<E, S> of(
            final Event<E> event,
            final Class<E> originalEventClass,
            final TypeToken<S> wrappedEventClass
    ) {
        
        return of(event, originalEventClass, wrappedEventClass, FabricEventPhaseMapper.of());
    }
    
    public static <E, S> FabricEventBusWire<E, S> of(
            final Event<E> event,
            final Class<E> originalEventClass,
            final Class<S> wrappedEventClass,
            final FabricEventPhaseMapper mapper
    ) {
        
        return of(event, originalEventClass, TypeToken.of(wrappedEventClass), mapper);
    }
    
    public static <E, S> FabricEventBusWire<E, S> of(
            final Event<E> event,
            final Class<E> originalEventClass,
            final TypeToken<S> wrappedEventClass,
            final FabricEventPhaseMapper mapper
    ) {
       
        Objects.requireNonNull(event, "event");
        Objects.requireNonNull(mapper, "mapper");
        final Method functionalMethod = verifyOriginal(originalEventClass);
        final WrapReveal wrapReveal = verifyWrapped(wrappedEventClass);
        compatibility(functionalMethod, wrapReveal);
        final MethodHandle wrap = wrapHandle(wrapReveal.wrap());
        final MethodHandle reveal = revealHandle(wrapReveal.reveal());
        return new FabricEventBusWire<>(event, originalEventClass, wrappedEventClass, mapper, functionalMethod, wrap, reveal);
    }
    
    private static <E> Method verifyOriginal(final Class<E> originalEventClass) {
        Objects.requireNonNull(originalEventClass, "originalEventClass");
        if (!originalEventClass.isInterface()) {
            throw new IllegalArgumentException("All Fabric events are represented by interfaces, but got non-interface " + originalEventClass.getName());
        }
        
        final Method[] methods = originalEventClass.getDeclaredMethods();
        
        Method target = null;
        for (final Method method : methods) {
            final int modifiers = method.getModifiers();
            if (!Modifier.isPublic(modifiers) || Modifier.isStatic(modifiers) || method.isDefault() || !Modifier.isAbstract(modifiers)) {
                continue;
            }
            if (target != null) {
                throw new IllegalArgumentException(originalEventClass.getName() + " is not a functional interface");
            }
            target = method;
        }
        
        if (target == null) {
            throw new IllegalArgumentException(originalEventClass.getName() + " is not a functional interface");
        }
        
        return target;
    }
    
    private static <S> WrapReveal verifyWrapped(final TypeToken<S> wrappedEventClass) {
        Objects.requireNonNull(wrappedEventClass, "wrappedEventClass");
        return verifyWrapped(wrappedEventClass.getRawType());
    }
    
    private static <S> WrapReveal verifyWrapped(final Class<S> wrappedEventClass) {
        final Method[] methods = wrappedEventClass.getDeclaredMethods();
        
        Method wrap = null;
        Method reveal = null;
        for (final Method method : methods) {
            final int modifiers = method.getModifiers();
            
            if (method.isAnnotationPresent(FabricWiredWrap.class)) {
                if (wrap != null) {
                    throw new IllegalArgumentException(wrappedEventClass.getName() + " declares two wrapping methods");
                }
                if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers)) {
                    throw new IllegalArgumentException(wrappedEventClass.getName() + " wrapper must be public and static");
                }
                if (method.getReturnType() != wrappedEventClass) {
                    throw new IllegalArgumentException(wrappedEventClass.getName() + " wrapper must return the same class");
                }
                wrap = method;
            }
            
            if (method.isAnnotationPresent(FabricWiredReveal.class)) {
                if (reveal != null) {
                    throw new IllegalArgumentException(wrappedEventClass.getName() + " declares two reveal methods");
                }
                if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers)) {
                    throw new IllegalArgumentException(wrappedEventClass.getName() + " reveal must be public and static");
                }
                if (method.getParameterCount() != 1 || method.getParameterTypes()[0] != wrappedEventClass) {
                    throw new IllegalArgumentException(wrappedEventClass.getName() + " reveal must accept its class as the single parameter");
                }
                reveal = method;
            }
        }
        
        if (wrap == null) {
            throw new IllegalArgumentException(wrappedEventClass.getName() + " is missing a wrapper");
        }
        
        return new WrapReveal(wrap, reveal);
    }
    
    private static void compatibility(final Method functionalMethod, final WrapReveal wrapReveal) {
        compatibleArguments(functionalMethod, wrapReveal.wrap());
        compatibleReturnType(functionalMethod, wrapReveal.reveal());
    }
    
    private static void compatibleArguments(final Method functional, final Method wrap) {
        final Parameter[] functionalParameters = functional.getParameters();
        final Parameter[] wrapParameters = wrap.getParameters();
        final int length = functionalParameters.length;
        
        if (length != wrapParameters.length) {
            throw new IllegalArgumentException("Incompatible wrapper: expected " + length + " parameters, but got " + wrapParameters.length);
        }
        
        for (int i = 0; i < length; ++i) {
            final Parameter functionalParameter = functionalParameters[i];
            final Parameter wrapParameter = wrapParameters[i];
            
            if (functionalParameter.getType() != wrapParameter.getType()) {
                final String message = "Incompatible wrapper: type mismatch at parameter " + i + "; got " + wrapParameter + ", expected " + functionalParameter;
                throw new IllegalArgumentException(message);
            }
        }
    }
    
    private static void compatibleReturnType(final Method functionalMethod, final Method reveal) {
        final Class<?> returnType = functionalMethod.getReturnType();
        
        if (returnType.equals(void.class)) {
            if (reveal != null) {
                throw new IllegalArgumentException("Incompatible reveal: functional method is void, so no reveal expected");
            }
            return;
        }
        
        if (reveal == null) {
            throw new IllegalArgumentException("Incompatible reveal: functional method is not void, so reveal expected");
        }
        
        final Class<?> revealType = reveal.getReturnType();
        if (returnType != revealType) {
            throw new IllegalArgumentException("Incompatible reveal: invalid return type; expected " + returnType.getName() + ", got " + revealType.getName());
        }
    }
    
    private static MethodHandle wrapHandle(final Method wrap) {
        final MethodHandle handle = handle(wrap);
        final MethodHandle objectReturning = handle.asType(handle.type().changeReturnType(Object.class));
        final MethodHandle spreader = objectReturning.asSpreader(Object[].class, objectReturning.type().parameterCount());
        return spreader.asType(MethodType.methodType(Object.class, Object[].class));
    }
    
    private static MethodHandle revealHandle(final Method reveal) {
        final MethodHandle handle = handle(reveal);
        return handle == null? null : handle.asType(MethodType.methodType(Object.class, Object.class));
    }
    
    private static MethodHandle handle(final Method method) {
        if (method == null) {
            return null;
        }
        
        try {
            final Class<?> clazz = method.getDeclaringClass();
            final MethodType type = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
            final String name = method.getName();
            return PUBLIC_LOOKUP.findStatic(clazz, name, type);
        } catch(final IllegalAccessException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }
    
    @Override
    public <T> void registerBusForDispatch(final TypeToken<T> eventType, final IEventBus<T> bus) {
        if (!this.wrappedEventClass.equals(eventType)) {
            throw new IllegalStateException("Invalid event type");
        }
        
        final Class<?>[] classes = Stream.of(this.originalEventClass).toArray(Class<?>[]::new);
        this.mapper.phases().forEach(phase -> {
            final InvocationHandler handler = new PostingInvocationHandler<T>(
                    this.functionalMethod,
                    this.wrapper,
                    this.mapper.apply(phase),
                    this.reveal,
                    bus
            );
            final E proxy = this.originalEventClass.cast(Proxy.newProxyInstance(this.getClass().getClassLoader(), classes, handler));
            this.event.register(phase, proxy);
        });
    }
    
}
