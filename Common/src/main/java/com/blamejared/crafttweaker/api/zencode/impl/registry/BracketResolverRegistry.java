package com.blamejared.crafttweaker.api.zencode.impl.registry;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.command.type.BracketDumperInfo;
import com.blamejared.crafttweaker.api.zencode.bracket.ValidatedEscapableBracketParser;
import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contains all info on Bracket resolvers, validators and dumpers
 */
public class BracketResolverRegistry {
    
    //Name to BEP methods
    private final Map<String, Method> bracketResolvers = new HashMap<>();
    //Root package to BEP Names
    private final Map<String, Collection<String>> bracketNamesByRootPackage = new HashMap<>();
    //BEP name to BEP validator method
    private final Map<String, Method> bracketValidators = new HashMap<>();
    //BEP names of BEP validators without BEP Method. Needed when custom BEPs are registered
    private final Set<String> advancedBepNames = new HashSet<>();
    private final Map<String, BracketDumperInfo> bracketDumpers = new HashMap<>();
    
    public void validateBrackets() {
        
        for(String validatedBep : bracketValidators.keySet()) {
            if(!bracketResolvers.containsKey(validatedBep) && !advancedBepNames.contains(validatedBep)) {
                CraftTweakerAPI.LOGGER.info("BEP '{}' has a validator but no BEP method", validatedBep);
            }
        }
    }
    
    public void addClasses(List<Class<?>> clsList) {
        
        for(Class<?> cls : clsList) {
            for(Method method : cls.getMethods()) {
                addMethod(method);
            }
        }
    }
    
    public void addMethod(Method method) {
        
        if(method.isAnnotationPresent(BracketResolver.class)) {
            addBracketResolver(method);
        }
        if(method.isAnnotationPresent(BracketDumper.class)) {
            addBracketDumper(method);
        }
        if(method.isAnnotationPresent(BracketValidator.class)) {
            addBracketValidator(method);
        }
    }
    
    public Method getBracketValidator(String bepName) {
        
        return bracketValidators.getOrDefault(bepName, null);
    }
    
    public void addAdvancedBEPName(String name) {
        
        advancedBepNames.add(name);
    }
    
    public Map<String, BracketDumperInfo> getBracketDumpers() {
        
        return ImmutableMap.copyOf(bracketDumpers);
    }
    
    /**
     * Gets the Bracket Resolver list.
     *
     * @return ImmutableList of the Bracket Resolvers
     */
    public List<ValidatedEscapableBracketParser> getBracketResolvers(String name, ScriptingEngine scriptingEngine, JavaNativeModule owningModule) {
        
        final List<ValidatedEscapableBracketParser> validatedEscapableBracketParsers = new ArrayList<>();
        for(String bepName : bracketNamesByRootPackage.getOrDefault(name, Collections.emptyList())) {
            final Method parserMethod = bracketResolvers.get(bepName);
            final Method validatorMethod = getBracketValidator(bepName);
            final FunctionalMemberRef functionalMemberRef = owningModule.loadStaticMethod(parserMethod);
            final ValidatedEscapableBracketParser validated = new ValidatedEscapableBracketParser(bepName, functionalMemberRef, validatorMethod, scriptingEngine.registry);
            validatedEscapableBracketParsers.add(validated);
        }
        return validatedEscapableBracketParsers;
    }
    
    private void addBracketResolver(Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as a BracketResolver, but it is not public and static.", method.toString());
            return;
        }
        
        boolean isValid = true;
        Class<?>[] parameters = method.getParameterTypes();
        final String name = method.getAnnotation(BracketResolver.class).value();
        
        if(parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerAPI.LOGGER.error("Method '{}' is marked as a BracketResolver, but it does not have a String as it's only parameter.", method.toString());
            isValid = false;
        }
        
        
        if(!bracketResolvers.getOrDefault(name, method).equals(method)) {
            final Method other = bracketResolvers.get(name);
            CraftTweakerAPI.LOGGER.error("BracketResolve '{}' was registered twice: '{}' and '{}'", name, other, method);
            isValid = false;
        }
        
        if(isValid) {
            bracketResolvers.put(name, method);
            
            final Class<?> cls = method.getDeclaringClass();
            final String clsName = cls.isAnnotationPresent(ZenCodeType.Name.class)
                    ? cls.getAnnotation(ZenCodeType.Name.class).value()
                    : cls.getCanonicalName();
            
            bracketNamesByRootPackage.computeIfAbsent(clsName.split("[.]", 2)[0], s -> new ArrayList<>())
                    .add(name);
        }
    }
    
    private void addBracketDumper(Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as a BracketDumper, but it is not public and static.", method.toString());
            return;
        }
        
        if(method.getParameterCount() != 0) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as BracketDumper but does not have 0 parameters.", method.toString());
            return;
        }
        
        if(!Collection.class.isAssignableFrom(method.getReturnType()) || ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0] != String.class) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as BracketDumper but does not have 'Collection<String>' as return type.", method.toGenericString());
            return;
        }
        
        final BracketDumper annotation = method.getAnnotation(BracketDumper.class);
        
        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(method);
            final BracketDumperInfo dumperInfo = bracketDumpers.computeIfAbsent(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, annotation.value()), bepName -> new BracketDumperInfo(bepName, annotation
                    .subCommandName(), annotation.fileName()));
            dumperInfo.addMethodHandle(methodHandle);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    private void addBracketValidator(Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as a BracketValidator, but it is not public and static.", method.toString());
            return;
        }
        boolean valid = true;
        
        final String value = method.getAnnotation(BracketValidator.class).value();
        final Class<?>[] parameters = method.getParameterTypes();
        if(parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerAPI.LOGGER.error("Method '{}' is marked as a BracketValidator, but it does not have a String as it's only parameter.", method.toString());
            valid = false;
        }
        
        if(bracketValidators.containsKey(value)) {
            CraftTweakerAPI.LOGGER.error("Bracket validator for bep name '{}' was found twice: '{}' and '{}'", value, bracketValidators
                    .get(value), method);
            valid = false;
        }
        
        if(method.getReturnType() != boolean.class) {
            CraftTweakerAPI.LOGGER.error("Method '{}' is marked as a BracketValidator, so it must return a boolean", method);
            valid = false;
        }
        
        if(valid) {
            bracketValidators.put(value, method);
        }
    }
    
}
