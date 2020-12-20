package com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.*;

public class SingletonDependencyContainer implements DependencyContainer {
    
    private final Map<Class<?>, Object> alreadyExistingInstances = new HashMap<>();
    
    @Override
    public <Type> Type getInstanceOfClass(Class<Type> cls) {
        initializeInstanceIfNotExists(cls);
        return getInstance(cls);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <Type> void addInstance(Type instance) {
        addInstanceAs(instance, (Class<Type>) instance.getClass());
    }
    
    @Override
    public <Type, Instance extends Type> void addInstanceAs(Instance instance, Class<Type> as) {
        validateNoDuplicate(as);
        insertInstanceAs(instance, as);
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class<T> cls) {
        return (T) alreadyExistingInstances.get(cls);
    }
    
    private <T> void initializeInstanceIfNotExists(Class<T> cls) {
        if(!instanceAlreadyExists(cls)) {
            initializeInstance(cls);
        }
    }
    
    private <T> void initializeInstance(Class<T> cls) {
        final Optional<T> instance = Arrays.stream(cls.getConstructors())
                .<T> map(this::tryCreateInstanceWith)
                .filter(Objects::nonNull)
                .findFirst();
        
        if(instance.isPresent()) {
            insertInstanceAs(instance.get(), cls);
        } else {
            throw new IllegalArgumentException("Could not initialize class: " + cls.getCanonicalName());
        }
    }
    
    @SuppressWarnings("unchecked")
    private <T> T tryCreateInstanceWith(Constructor<?> constructor) {
        try {
            final Object[] arguments = getArguments(constructor);
            return (T) constructor.newInstance(arguments);
        } catch(Exception ignored) {
            return null;
        }
    }
    
    @Nonnull
    private Object[] getArguments(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                .map(this::getInstanceOfClass)
                .toArray(Object[]::new);
    }
    
    private <T> void insertInstanceAs(T instance, Class<T> as) {
        alreadyExistingInstances.put(as, instance);
    }
    
    private <T> void validateNoDuplicate(Class<T> instance) {
        if(instanceAlreadyExists(instance)) {
            throw new IllegalStateException("Trying to register duplicate!");
        }
    }
    
    private boolean instanceAlreadyExists(Class<?> instanceClass) {
        return alreadyExistingInstances.containsKey(instanceClass);
    }
}
