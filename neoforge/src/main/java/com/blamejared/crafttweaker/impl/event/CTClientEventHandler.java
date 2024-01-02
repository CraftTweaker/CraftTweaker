package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.entity.NameTagResult;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.script.RecipeManagerScriptLoader;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

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
    public static void nameTag(RenderNameTagEvent e) {
        
        Entity entity = e.getEntity();
        Event.Result eventResult = e.getResult();
        Boolean result = eventResult == Event.Result.DEFAULT ? null : eventResult == Event.Result.ALLOW;
        Component content = e.getContent();
        Component originalContent = e.getOriginalContent();
        for(Predicate<Entity> predicate : Services.CLIENT.NAMETAGS.keySet()) {
            if(predicate.test(entity)) {
                try {
                    NameTagResult nameTagResult = new NameTagResult(result, content, originalContent);
                    Services.CLIENT.NAMETAGS.get(predicate).apply(entity, nameTagResult);
                    e.setResult(nameTagResult.getResult() == null ? Event.Result.DEFAULT : nameTagResult.getResult() ? Event.Result.ALLOW : Event.Result.DENY);
                    e.setContent(nameTagResult.getContent());
                } catch(final Exception exception) {
                    CommonLoggers.api().error(
                            "Unable to run one of the name tag functions for {} due to an error (for experts, refer to {})",
                            ExpandEntityType.getCommandString(GenericUtil.uncheck(entity.getType())),
                            Services.CLIENT.NAMETAGS.get(predicate).getClass().getName()
                            , exception
                    );
                }
            }
        }
    }
    
}
