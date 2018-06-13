package crafttweaker.mc1120.events;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tooltip.IngredientTooltips;
import crafttweaker.mc1120.formatting.IMCFormattedString;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

public class ClientEventHandler {
    
    private static boolean alreadyChangedThePlayer = false;
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onItemTooltip(ItemTooltipEvent ev) {
        if(!ev.getItemStack().isEmpty()) {
            IItemStack itemStack = CraftTweakerMC.getIItemStack(ev.getItemStack());
            if(IngredientTooltips.shouldClearToolTip(itemStack)) {
                ev.getToolTip().clear();
            }
            for(IFormattedText tooltip : IngredientTooltips.getTooltips(itemStack)) {
                ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
            }
            if(GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
                for(IFormattedText tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                    ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
                }
            }
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent ev) {
        
        final Minecraft minecraft = Minecraft.getMinecraft();
        if(minecraft.player != null && !alreadyChangedThePlayer) {
            alreadyChangedThePlayer = true;
            
            RecipeBookClient.rebuildTable();
            minecraft.populateSearchTreeManager();
            
            // final SimpleReloadableResourceManager manager = ((SimpleReloadableResourceManager) minecraft.getResourceManager());
            // manager.notifyReloadListeners();
            // //for(IResourceManagerReloadListener reloadListener : manager.reloadListeners) {
            // //    //if(reloadListener instanceof SearchTreeManager)
            // //        reloadListener.onResourceManagerReload(manager);
            // //}
            
            //FIXME find better solution
            minecraft.refreshResources();
            CraftTweakerAPI.logInfo("Fixed the RecipeBook");
        }
    }
}
