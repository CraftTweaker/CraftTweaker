package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import net.minecraft.world.item.crafting.Recipe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

final class RecipeHandlerGatherer {
    
    void gatherAndRegisterHandlers(final IRecipeHandlerRegistrationHandler handler) {
        
        Stream.concat(this.findWithAnnotation(IRecipeHandler.For.class), this.findWithAnnotation(IRecipeHandler.For.Container.class))
                .distinct()
                .forEach(it -> this.tryRegister(it, handler));
    }
    
    private Stream<? extends Class<?>> findWithAnnotation(final Class<? extends Annotation> annotation) {
        
        return ClassUtil.findClassesWithAnnotation(annotation);
    }
    
    private void tryRegister(final Class<?> clazz, final IRecipeHandlerRegistrationHandler handler) {
        
        if(!IRecipeHandler.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not implement IRecipeHandler");
        }
        if(clazz.isInterface()) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is an interface and cannot be annotated with @IRecipeHandler.For");
        }
        if(Modifier.isAbstract(clazz.getModifiers())) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is an abstract class and cannot be annotated with @IRecipeHandler.For");
        }
        Arrays.stream(clazz.getAnnotationsByType(IRecipeHandler.For.class))
                .map(IRecipeHandler.For::value)
                .map(it -> (Class<?>) it)
                .filter(it -> it != Recipe.class)
                .forEach(it -> handler.registerRecipeHandler(GenericUtil.uncheck(it), (IRecipeHandler<?>) InstantiationUtil.getOrCreateInstance(clazz)));
    }
    
    
}
