package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.mojang.datafixers.util.Pair;
import cpw.mods.modlauncher.api.LamdbaExceptionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public final class LootConditionManager {
    private static final Map<Class<? extends ILootConditionTypeBuilder>, Function<CTLootConditionBuilder, ? extends ILootConditionTypeBuilder>> BUILDERS = new HashMap<>();
    
    public static void handleBuilderRegistration() {
        BUILDERS.clear();
        CraftTweakerRegistry.getZenClassRegistry()
                .getImplementationsOf(ILootConditionTypeBuilder.class)
                .stream()
                .map(LootConditionManager::make)
                .filter(Objects::nonNull)
                .forEach(LootConditionManager::register);
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends ILootConditionTypeBuilder> T get(final CTLootConditionBuilder builder, final Class<T> token) {
        return Objects.requireNonNull((T) BUILDERS.get(token).apply(builder), () -> "No loot condition builder for type '" + token.getName() + "' was registered!");
    }
    
    private static <T extends ILootConditionTypeBuilder> Pair<Class<T>, Function<CTLootConditionBuilder, T>> make(final Class<T> clazz) {
        final Field instanceField = findInstanceField(clazz);
        final Pair<Constructor<T>, Constructor<T>> constructors = findConstructors(clazz);
        
        final boolean hasInstance = instanceField != null;
        final boolean hasConstructor = constructors.getFirst() != null || constructors.getSecond() != null;
        final boolean hasBothConstructors = hasConstructor && (constructors.getFirst() != null && constructors.getSecond() != null);
        
        if (hasInstance && hasConstructor) {
            CraftTweakerAPI.logWarning("Identified both 'INSTANCE' field and a valid constructor for condition '%s': INSTANCE will take precedence", clazz.getName());
        } else if (!hasInstance && hasBothConstructors) {
            CraftTweakerAPI.logWarning("Identified two valid constructors for condition '%s': no-arg will take precedence", clazz.getName());
        } else if (!hasInstance && !hasConstructor) {
            CraftTweakerAPI.logError("Condition '%s' does not specify a valid constructor nor an instance field! This is invalid", clazz.getName());
            return null;
        }
        
        return Pair.of(clazz, make(instanceField, constructors.getFirst(), constructors.getSecond()));
    }
    
    private static <T extends ILootConditionTypeBuilder> Field findInstanceField(final Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(it -> Modifier.isStatic(it.getModifiers()))
                .filter(it -> !Modifier.isPrivate(it.getModifiers()))
                .filter(it -> "INSTANCE".equals(it.getName()))
                .peek(it -> it.setAccessible(true))
                .findFirst()
                .orElse(null);
    }
    
    private static <T extends ILootConditionTypeBuilder> Pair<Constructor<T>, Constructor<T>> findConstructors(final Class<T> clazz) {
        /*mutable*/ Constructor<T> noArg = null;
        /*mutable*/ Constructor<T> singleArg = null;
        
        for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            @SuppressWarnings("unchecked") final Constructor<T> typedConstructor = (Constructor<T>) constructor;
            
            if (Modifier.isPrivate(typedConstructor.getModifiers())) continue;
            
            if (typedConstructor.getParameterCount() == 0) {
                noArg = typedConstructor; // There cannot be more than one anyway
            } else if (typedConstructor.getParameterCount() == 1 && typedConstructor.getParameters()[0].getType() == CTLootConditionBuilder.class) {
                singleArg = typedConstructor;
            }
        }
        
        if (noArg != null) noArg.setAccessible(true);
        if (singleArg != null) singleArg.setAccessible(true);
        
        return Pair.of(noArg, singleArg);
    }
    
    @SuppressWarnings("unchecked")
    private static <T extends ILootConditionTypeBuilder> Function<CTLootConditionBuilder, T> make(final Field instance, final Constructor<T> noArg, final Constructor<T> singleArg) {
        if (instance != null) {
            return ignoring(() -> LamdbaExceptionUtils.uncheck(() -> (T) instance.get(null)));
        }
        if (noArg != null) {
            // This is due to type inference breaking because varargs
            //noinspection Convert2MethodRef
            return ignoring(() -> LamdbaExceptionUtils.uncheck(() -> noArg.newInstance()));
        }
        
        return builder -> LamdbaExceptionUtils.uncheck(() -> singleArg.newInstance(builder));
    }
    
    private static <T extends ILootConditionTypeBuilder> Function<CTLootConditionBuilder, T> ignoring(final Supplier<T> maker) {
        return ignore -> maker.get();
    }
    
    private static <T extends ILootConditionTypeBuilder> void register(final Pair<Class<T>, Function<CTLootConditionBuilder, T>> pair) {
        final Class<T> typeToken = pair.getFirst();
        final Function<CTLootConditionBuilder, T> creator = pair.getSecond();
        if (BUILDERS.containsKey(typeToken)) {
            throw new IllegalStateException("A builder for the given type '" + typeToken.getName() + "' was already registered");
        }
        BUILDERS.put(typeToken, creator);
        CraftTweakerAPI.logDebug("Successfully registered loot condition type builder for '%s' as '%s'", typeToken, creator);
    }
}
