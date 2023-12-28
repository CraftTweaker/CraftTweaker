package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
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
        
        Stream.concat(this.find(IRecipeHandler.For.class), this.find(IRecipeHandler.For.Container.class))
                .distinct()
                .forEach(it -> this.tryRegister(it, handler));
    }
    
    private Stream<? extends Class<?>> find(final Class<? extends Annotation> annotation) {
        
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
        if(IRecipeManager.class.isAssignableFrom(clazz)) {
            CraftTweakerCommon.logger()
                    .warn("Recipe manager " + clazz.getName() + " doubles as a recipe handler: this is discouraged");
        }
        Arrays.stream(clazz.getAnnotationsByType(IRecipeHandler.For.class))
                .map(IRecipeHandler.For::value)
                .map(it -> this.verify(it, clazz))
                .filter(it -> it != Recipe.class)
                .forEach(it -> handler.registerRecipeHandler(GenericUtil.uncheck(it), (IRecipeHandler<?>) InstantiationUtil.getOrCreateInstance(clazz)));
    }
    
    private <T> Class<?> verify(final Class<T> type, final Class<?> target) {
        
        if(type == Recipe.class) {
            throw new IllegalArgumentException("Class " + target.getName() + " wants to be a global handler: this is not allowed");
        }
        return type;
    }
    
}
