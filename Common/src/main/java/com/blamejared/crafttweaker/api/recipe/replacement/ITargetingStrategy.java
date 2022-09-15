package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@Document("vanilla/api/recipe/replacement/ITargetingStrategy")
@FunctionalInterface
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.ITargetingStrategy")
@ZenRegister
public interface ITargetingStrategy extends CommandStringDisplayable {
    
    ResourceLocation DEFAULT_STRATEGY_ID = CraftTweakerConstants.rl("default");
    
    static ITargetingStrategy find(final ResourceLocation id) {
        
        return CraftTweakerAPI.getRegistry().getReplacerRegistry().findStrategy(id);
    }
    
    @ZenCodeType.Nullable <T> T castStrategy(final IRecipeComponent<T> component, final T object, final Function<T, @ZenCodeType.Nullable T> replacer);
    
    @Override
    default String getCommandString() {
        
        final IReplacerRegistry registry = CraftTweakerAPI.getRegistry().getReplacerRegistry();
        return registry.allStrategyNames()
                .stream()
                .filter(it -> registry.findStrategy(it) == this)
                .findFirst()
                .map(ResourceLocation::toString)
                .map("<targetingstrategy:%s>"::formatted)
                .orElseThrow();
    }
    
}
