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
        List<String> tooltipList = ev.getToolTip();
        if(!ev.getItemStack().isEmpty()) {
            IItemStack itemStack = CraftTweakerMC.getIItemStackForMatching(ev.getItemStack());
            Boolean clearTooltipFlag = IngredientTooltips.shouldClearToolTip(itemStack);
            if(clearTooltipFlag != null) {
                (clearTooltipFlag ? tooltipList.subList(1, tooltipList.size()) : tooltipList).clear();
            }
            
            List<String> toRemove = new ArrayList<>();
            for (Integer line : IngredientTooltips.getTooltipLinesToRemove(itemStack)) {
                if (line > 0 && line < tooltipList.size()) {
                    toRemove.add(tooltipList.get(line));
                }
            }
            for(Pattern regex : IngredientTooltips.getTooltipsToRemove(itemStack)) {
                for(String s : tooltipList) {
                    if(regex.matcher(s).find()) {
                        toRemove.add(s);
                    }
                }
            }
            tooltipList.removeAll(toRemove);
            
            for(Pair<IFormattedText, IFormattedText> tooltip : IngredientTooltips.getTooltips(itemStack)) {
                tooltipList.add(((IMCFormattedString) tooltip.getKey()).getTooltipString());
            }
            for(Pair<ITooltipFunction, ITooltipFunction> tooltip : IngredientTooltips.getAdvancedTooltips(itemStack)) {
                String s = tooltip.getKey().process(itemStack);
                if (s != null) {
                    tooltipList.add(s);
                }
            }
            
            boolean pressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            for(Pair<IFormattedText, IFormattedText> tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                if(pressed) {
                    tooltipList.add(((IMCFormattedString) tooltip.getKey()).getTooltipString());
                } else if(tooltip.getValue() != null) {
                    tooltipList.add(((IMCFormattedString) tooltip.getValue()).getTooltipString());
                }
            }
            for(Pair<ITooltipFunction, ITooltipFunction> tooltip : IngredientTooltips.getAdvancedShiftTooltips(itemStack)) {
                if(pressed) {
                    String s = tooltip.getKey().process(itemStack);
                    if (s != null) {
                        tooltipList.add(s);
                    }
                } else if(tooltip.getValue() != null) {
                    String s = tooltip.getValue().process(itemStack);
                    if (s != null) {
                        tooltipList.add(s);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent ev) {
    
    }
}
