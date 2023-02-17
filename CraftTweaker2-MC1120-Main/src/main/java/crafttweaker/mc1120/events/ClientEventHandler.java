package crafttweaker.mc1120.events;

import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tooltip.*;
import crafttweaker.mc1120.formatting.IMCFormattedString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import org.lwjgl.input.Keyboard;
import stanhebben.zenscript.util.Pair;

import java.util.*;
import java.util.regex.*;

public class ClientEventHandler {
    
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onItemTooltip(ItemTooltipEvent ev) {
        if(!ev.getItemStack().isEmpty()) {
            IItemStack itemStack = CraftTweakerMC.getIItemStack(ev.getItemStack());
            if(IngredientTooltips.shouldClearToolTip(itemStack)) {
                ev.getToolTip().clear();
                ev.getToolTip().add(ev.getItemStack().getDisplayName());
            }
            
            List<String> toRemove = new ArrayList<>();
            for (Integer line : IngredientTooltips.getTooltipLinesToRemove(itemStack)) {
                if (line > 0 && line < ev.getToolTip().size()) {
                    toRemove.add(ev.getToolTip().get(line));
                }
            }
            for(Pattern regex : IngredientTooltips.getTooltipsToRemove(itemStack)) {
                for(String s : ev.getToolTip()) {
                    if(regex.matcher(s).find()) {
                        toRemove.add(s);
                    }
                }
            }
            ev.getToolTip().removeAll(toRemove);
            
            for(Pair<IFormattedText, IFormattedText> tooltip : IngredientTooltips.getTooltips(itemStack)) {
                ev.getToolTip().add(((IMCFormattedString) tooltip.getKey()).getTooltipString());
            }
            for(Pair<ITooltipFunction, ITooltipFunction> tooltip : IngredientTooltips.getAdvancedTooltips(itemStack)) {
                    ev.getToolTip().add(tooltip.getKey().process(itemStack));
            }
            
            boolean pressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            for(Pair<IFormattedText, IFormattedText> tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                if(pressed) {
                    ev.getToolTip().add(((IMCFormattedString) tooltip.getKey()).getTooltipString());
                } else if(tooltip.getValue() != null) {
                    ev.getToolTip().add(((IMCFormattedString) tooltip.getValue()).getTooltipString());
                }
            }
            for(Pair<ITooltipFunction, ITooltipFunction> tooltip : IngredientTooltips.getAdvancedShiftTooltips(itemStack)) {
                if(pressed) {
                    ev.getToolTip().add(tooltip.getKey().process(itemStack));
                } else if(tooltip.getValue() != null) {
                    ev.getToolTip().add(tooltip.getValue().process(itemStack));
                }
            }
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent ev) {
    
    }
}
