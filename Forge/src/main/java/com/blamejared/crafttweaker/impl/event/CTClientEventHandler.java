package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.script.RecipeManagerScriptLoader;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CraftTweakerConstants.MOD_ID)
public class CTClientEventHandler {
    
    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
    
        RecipeManagerScriptLoader.updateState(RecipeManagerScriptLoader.UpdatedState.RECIPES, event::getRecipeManager);
    }
    
    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
    
        CraftTweakerAPI.getAccessibleElementsProvider().client().registryAccess(event.getTagManager());
        RecipeManagerScriptLoader.updateState(RecipeManagerScriptLoader.UpdatedState.TAGS, null);
    } 
    
    @SubscribeEvent
    public static void handleTooltips(ItemTooltipEvent e) {
        
        Services.CLIENT.applyTooltips(e.getItemStack(), e.getFlags(), e.getToolTip());
    }
    
}
