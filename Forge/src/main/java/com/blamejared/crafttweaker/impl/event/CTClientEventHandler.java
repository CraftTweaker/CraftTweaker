package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.entity.NamePlateResult;
import com.blamejared.crafttweaker.impl.script.RecipeManagerScriptLoader;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CraftTweakerConstants.MOD_ID)
public class CTClientEventHandler {
    
    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        
        RecipeManagerScriptLoader.updateState(RecipeManagerScriptLoader.UpdatedState.RECIPES, event::getRecipeManager);
    }
    
    @SubscribeEvent
    public static void handleTooltips(ItemTooltipEvent e) {
        
        Services.CLIENT.applyTooltips(e.getItemStack(), e.getFlags(), e.getToolTip());
    }
    
    @SubscribeEvent
    public static void nameplate(RenderNameplateEvent e) {
    
        Entity entity = e.getEntity();
        Event.Result eventResult = e.getResult();
        Boolean result = eventResult == Event.Result.DEFAULT ? null : eventResult == Event.Result.ALLOW;
        Component content = e.getContent();
        Component originalContent = e.getOriginalContent();
        for(Predicate<Entity> predicate : Services.CLIENT.NAMEPLATES.keySet()) {
            if(predicate.test(entity)) {
                try {
                    NamePlateResult namePlateResult = new NamePlateResult(result, content, originalContent);
                    Services.CLIENT.NAMEPLATES.get(predicate).apply(entity, namePlateResult);
                    e.setResult(namePlateResult.getResult() == null ? Event.Result.DEFAULT : namePlateResult.getResult() ? Event.Result.ALLOW : Event.Result.DENY);
                    e.setContent(namePlateResult.getContent());
                } catch(final Exception exception) {
                    CraftTweakerAPI.LOGGER.error(
                            "Unable to run one of the nameplate functions for {} due to an error (for experts, refer to {})",
                            ExpandEntityType.getCommandString(entity.getType()),
                            Services.CLIENT.NAMEPLATES.get(predicate).getClass().getName()
                            , exception
                    );
                }
            }
        }
    }
    
}
