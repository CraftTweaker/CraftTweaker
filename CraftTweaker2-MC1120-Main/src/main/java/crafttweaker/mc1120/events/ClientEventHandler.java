package crafttweaker.mc1120.events;

import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tooltip.IngredientTooltips;
import crafttweaker.mc1120.formatting.IMCFormattedString;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ClientEventHandler {
    
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent ev) {
        if(!ev.getItemStack().isEmpty()) {
            IItemStack itemStack = CraftTweakerMC.getIItemStack(ev.getItemStack());
            for(IFormattedText tooltip : IngredientTooltips.getTooltips(itemStack)) {
                ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
            }
            if(!Keyboard.isCreated()) {
                return;
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                for(IFormattedText tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                    ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
                }
            }
        }
    }
}
