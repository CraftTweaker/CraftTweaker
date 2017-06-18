package crafttweaker.api.tooltip;

import crafttweaker.*;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.*;
import crafttweaker.api.util.IngredientMap;
import crafttweaker.api.util.IngredientMap.IngredientMapEntry;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenExpansion("crafttweaker.item.IIngredient")
@ZenRegister
public class IngredientTooltips {
    
    private static final IngredientMap<IFormattedText> TOOLTIPS = new IngredientMap<>();
    private static final IngredientMap<IFormattedText> SHIFT_TOOLTIPS = new IngredientMap<>();
    
    @ZenMethod
    public static void addTooltip(IIngredient ingredient, IFormattedText tooltip) {
        CraftTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, false));
    }
    
    @ZenMethod
    public static void addShiftTooltip(IIngredient ingredient, IFormattedText tooltip) {
        CraftTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, true));
    }
    
    public static List<IFormattedText> getTooltips(IItemStack item) {
        return TOOLTIPS.getEntries(item);
    }
    
    public static List<IFormattedText> getShiftTooltips(IItemStack item) {
        return SHIFT_TOOLTIPS.getEntries(item);
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
            CraftTweakerAPI.getIjeiRecipeRegistry().invalidateTooltips(ingredient);
        }
        
        
        @Override
        public String describe() {
            return "Adding tooltip for " + ingredient + ": " + tooltip;
        }
        
    }
}
