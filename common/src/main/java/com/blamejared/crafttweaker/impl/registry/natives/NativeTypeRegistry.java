package com.blamejared.crafttweaker.impl.registry.natives;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.natives.IBakedTypeInfo;
import com.blamejared.crafttweaker.api.natives.IExecutableReferenceInfo;
import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class NativeTypeRegistry implements INativeTypeRegistry {
    
    private final Map<Class<?>, BakedTypeInfo> info = new HashMap<>();
    private final Collection<IBakedTypeInfo> valuesView = Collections.unmodifiableCollection(this.info.values());
    
    private static Class<?>[] convertConstructorToClassArray(final NativeTypeInfo.Constructor constructorInfo) {
        
        return convertParametersToClassArray(constructorInfo.parameters());
    }
    
    private static Class<?>[] convertMethodToClassArray(final NativeTypeInfo.Method methodInfo) {
        
        return convertParametersToClassArray(methodInfo.parameters());
    }
    
    private static Class<?>[] convertParametersToClassArray(final NativeTypeInfo.Parameter... parametersInfo) {
        
        return Arrays.stream(parametersInfo)
                .map(NativeTypeInfo.Parameter::type)
                .toArray(Class<?>[]::new);
    }
    
    public void addNativeType(final NativeTypeInfo info) {
        
        final Class<?> targetedType = info.targetedType();
        final String zenName = info.name();
        final BakedTypeInfo knownData = this.info.get(targetedType);
        
        if(knownData != null && !knownData.zenName().equals(zenName)) {
            CraftTweakerCommon.logger().error(
                    "Trying to register native type '{}' twice with names (old) '{}' and (new) '{}'",
                    targetedType.getName(),
                    knownData.zenName(),
                    zenName
            );
            return;
        }
        
        final Map<String, ExecutableReferenceGroupInfo> executables = this.getExecutableInfo(info);
        this.info.put(targetedType, new BakedTypeInfo(zenName, targetedType, executables));
    }
    
    public void inheritFrom(final NativeTypeRegistry other) {
        
        other.info.forEach((clazz, info) -> {
            if(this.info.containsKey(clazz)) {
                // TODO("Maybe merge executables?")
                throw new IllegalStateException("Unable to duplicate native type data for " + clazz);
            }
            this.info.put(clazz, info);
        });
    }
    
    private Map<String, ExecutableReferenceGroupInfo> getExecutableInfo(final NativeTypeInfo info) {
        
        final HashMap<String, ExecutableReferenceGroupInfo> result = new HashMap<>();
        
        Arrays.stream(info.constructors()).forEach(it -> {
            final Class<?>[] parameters = convertConstructorToClassArray(it);
            result.computeIfAbsent("<init>", i -> new ExecutableReferenceGroupInfo())
                    .getOrCreateFor(parameters, ExecutableReferenceInfo.AnnotationCreator::appendConstructorAnnotation);
        });
        
        Arrays.stream(info.methods()).forEach(it -> {
            result.computeIfAbsent(it.name(), i -> new ExecutableReferenceGroupInfo())
                    .getOrCreateFor(convertMethodToClassArray(it), cons -> {
                        cons.appendGetterAnnotation(it.getter());
                        cons.appendSetterAnnotation(it.setter());
                        cons.appendMethodAnnotation();
                    });
        });
        
        return result;
    }
    
    @Override
    public Optional<String> getZenNameFor(final Class<?> clazz) {
        
        return this.getBakedTypeInfoFor(clazz).map(IBakedTypeInfo::zenName);
    }
    
    @Override
    public Collection<IBakedTypeInfo> getBakedTypeInfo() {
        
        return this.valuesView;
    }
    
    @Override
    public Optional<IBakedTypeInfo> getBakedTypeInfoFor(final Class<?> clazz) {
        
        return Optional.ofNullable(this.info.get(clazz));
    }
    
    @Override
    public Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(final Constructor<?> constructor) {
        
        return this.getBakedTypeInfoFor(constructor.getDeclaringClass()).flatMap(it -> it.findMethod(constructor));
    }
    
    @Override
    public Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(final Method method) {
        
        return this.getBakedTypeInfoFor(method.getDeclaringClass()).flatMap(it -> it.findMethod(method));
    }
    
}
