package crafttweaker.api.tooltip;

import crafttweaker.*;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.*;
import crafttweaker.api.util.IngredientMap;
import crafttweaker.api.util.IngredientMap.IngredientMapEntry;
import stanhebben.zenscript.annotations.*;

import java.util.*;

/**
 * @author Stan
 */
@ZenExpansion("crafttweaker.item.IIngredient")
@ZenRegister
public class IngredientTooltips {
    
    private static final IngredientMap<IFormattedText> TOOLTIPS = new IngredientMap<>();
    private static final IngredientMap<IFormattedText> SHIFT_TOOLTIPS = new IngredientMap<>();
    private static final List<IIngredient> CLEARED_TOOLTIPS = new LinkedList<>();
    
    
    @ZenMethod
    public static void addTooltip(IIngredient ingredient, IFormattedText tooltip) {
        CraftTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, false));
    }
    
    @ZenMethod
    public static void addShiftTooltip(IIngredient ingredient, IFormattedText tooltip) {
        CraftTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, true));
    }
    
    @ZenMethod
    public static void clearTooltip(IIngredient ingredient) {
        CraftTweakerAPI.apply(new ClearTooltipAction(ingredient));
    }
    
    public static List<IFormattedText> getTooltips(IItemStack item) {
        return TOOLTIPS.getEntries(item);
    }
    
    public static List<IFormattedText> getShiftTooltips(IItemStack item) {
        return SHIFT_TOOLTIPS.getEntries(item);
    }
    
    public static boolean shouldClearToolTip(IItemStack item) {
        for(IIngredient cleared : CLEARED_TOOLTIPS) {
            if(cleared.matches(item)){
                return true;
            }
        }
        return false;
    }
    
    // ######################
    // ### Action classes ###
    // ######################
    
    private static class AddTooltipAction implements IAction {
        
        private final IIngredient ingredient;
        private final IFormattedText tooltip;
        private final boolean shift;
        private IngredientMapEntry<IFormattedText> entry;
        
        public AddTooltipAction(IIngredient ingredient, IFormattedText tooltip, boolean shift) {
            this.ingredient = ingredient;
            this.tooltip = tooltip;
            this.shift = shift;
        }
        
        @Override
        public void apply() {
            entry = (shift ? SHIFT_TOOLTIPS : TOOLTIPS).register(ingredient, tooltip);
        }
        
        
        @Override
        public String describe() {
            return "Adding tooltip for " + ingredient + ": " + tooltip.getText();
        }
        
    }
    
    private static class ClearTooltipAction implements IAction {
        
        private final IIngredient ingredient;
        
        public ClearTooltipAction(IIngredient ingredient) {
            this.ingredient = ingredient;
        }
        
        @Override
        public void apply() {
            ingredient.getItems().forEach(item -> CLEARED_TOOLTIPS.add(item));
        }
        
        
        @Override
        public String describe() {
            return "Clearing tooltip for " + ingredient;
        }
        
    }
}
