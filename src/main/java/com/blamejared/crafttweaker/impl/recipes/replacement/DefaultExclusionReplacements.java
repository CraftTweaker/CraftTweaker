package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.GatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CraftTweaker.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DefaultExclusionReplacements {
    @SubscribeEvent
    public static void onGatherReplacementExclusionEvent(final GatherReplacementExclusionEvent event) {
    
        final IRecipeManager manager = event.getTargetedManager();
        if (manager.getRecipeType() == IRecipeType.CRAFTING) {
            manager.getAllRecipes()
                    .stream()
                    .map(WrapperRecipe::getRecipe)
                    .filter(it -> it instanceof SpecialRecipe)
                    .forEach(event::addExclusion);
        }
    }
}
