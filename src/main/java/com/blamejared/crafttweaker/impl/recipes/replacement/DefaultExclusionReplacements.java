package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.GatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CraftTweaker.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DefaultExclusionReplacements {
    
    // Note: we are using a lazy here to avoid initializing the recipe type list statically and out of order
    private static final Supplier<Set<IRecipeType<?>>> VANILLA_RECIPE_TYPES = Lazy.concurrentOf(
            () -> ImmutableSet.of(
                    IRecipeType.CRAFTING,
                    IRecipeType.SMELTING,
                    IRecipeType.BLASTING,
                    IRecipeType.SMOKING,
                    IRecipeType.CAMPFIRE_COOKING,
                    IRecipeType.STONECUTTING,
                    IRecipeType.SMITHING
            )
    );
    
    
    @SubscribeEvent
    public static void onGatherReplacementExclusionEvent(final GatherReplacementExclusionEvent event) {
        
        final IRecipeManager manager = event.getTargetedManager();
        
        if(VANILLA_RECIPE_TYPES.get().contains(manager.getRecipeType())) {
            manager.getAllRecipes()
                    .stream()
                    .map(WrapperRecipe::getRecipe)
                    .filter(IRecipe::isDynamic)
                    .forEach(event::addExclusion);
        }
        
        if(manager.getRecipeType() == CraftTweaker.RECIPE_TYPE_SCRIPTS) {
            manager.getAllRecipes()
                    .stream()
                    .map(WrapperRecipe::getRecipe)
                    .forEach(event::addExclusion);
        }
    }
    
}
