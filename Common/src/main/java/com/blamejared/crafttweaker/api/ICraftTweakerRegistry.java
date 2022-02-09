package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.command.type.BracketDumperInfo;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ICraftTweakerRegistry {
    
    IScriptLoader findLoader(final String name);
    
    default IScriptLoader findLoader(final Class<?> clazz) {
        
        return this.findLoader(clazz.getAnnotation(ZenRegister.class).loader());
    }
    
    Collection<IScriptLoader> getAllLoaders();
    
    IZenClassRegistry getZenClassRegistry();
    
    Map<String, BracketDumperInfo> getBracketDumpers(final IScriptLoader loader);
    
    // TODO("Better API")
    List<Pair<String, BracketExpressionParser>> getBracketHandlers(final IScriptLoader loader, final String rootPackage, final ScriptingEngine engine, final JavaNativeModule ctModule);
    
    List<IPreprocessor> getPreprocessors();
    
    <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(final T recipe);
    
    <T extends Enum<T>> T getEnumBracketValue(final ResourceLocation type, final String value);
    
    <T extends Enum<T>> Optional<Class<T>> getEnumBracketFor(final ResourceLocation type);
    
    Set<String> getAllEnumsForEnumBracket();
    
}
