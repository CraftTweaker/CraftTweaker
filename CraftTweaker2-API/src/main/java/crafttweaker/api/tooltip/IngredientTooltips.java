package crafttweaker.api.tooltip;

import crafttweaker.*;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.*;
import crafttweaker.api.util.IngredientMap;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.util.Pair;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Stan
 */
@ZenExpansion("crafttweaker.item.IIngredient")
@ZenRegister
public class IngredientTooltips {
    
    private static final IngredientMap<Pair<IFormattedText, IFormattedText>> TOOLTIPS = new IngredientMap<>();
    private static final IngredientMap<Pair<IFormattedText, IFormattedText>> SHIFT_TOOLTIPS = new IngredientMap<>();
    private static final IngredientMap<Boolean> CLEARED_TOOLTIPS = new IngredientMap<>();
    private static final IngredientMap<Pair<ITooltipFunction, ITooltipFunction>> TOOLTIP_FUNCTIONS = new IngredientMap<>();
    private static final IngredientMap<Pair<ITooltipFunction, ITooltipFunction>> SHIFT_TOOLTIP_FUNCTIONS = new IngredientMap<>();
    private static final IngredientMap<Pattern> REMOVED_TOOLTIPS = new IngredientMap<>();
    private static final IngredientMap<Integer> REMOVED_TOOLTIPS_LINE = new IngredientMap<>();
    
    @ZenMethod
    public static void addTooltip(IIngredient ingredient, IFormattedText tooltip) {
        CraftTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, false, null));
    }
    
    @ZenMethod
    public static void addAdvancedTooltip(IIngredient ingredient, ITooltipFunction function) {
        CraftTweakerAPI.apply(new AddAdvancedTooltipAction(ingredient, function, false, null));
    }
    
    @ZenMethod
    public static void addShiftTooltip(IIngredient ingredient, IFormattedText tooltip, @Optional IFormattedText showMessage) {
        CraftTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, true, showMessage));
    }
    
    @ZenMethod
    public static void addShiftTooltip(IIngredient ingredient, ITooltipFunction function, @Optional ITooltipFunction showMessage) {
        CraftTweakerAPI.apply(new AddAdvancedTooltipAction(ingredient, function, true, showMessage));
    }

    @ZenMethod
    public static void clearTooltip(IIngredient ingredient) {
        clearTooltip(ingredient, false);
    }
    
    @ZenMethod
    public static void clearTooltip(IIngredient ingredient, boolean leaveName) {
        CraftTweakerAPI.apply(new ClearTooltipAction(ingredient, leaveName));
    }
    
    @ZenMethod
    public static void removeTooltip(IIngredient ingredient, String regex) {
        CraftTweakerAPI.apply(new RemoveTooltipAction(ingredient, regex));
    }

    @ZenMethod
    public static void removeTooltipLine(IIngredient ingredient, int line)  {
        CraftTweakerAPI.apply(new RemoveTooltipLineAction(ingredient, line));
    }
    
    public static List<Pair<IFormattedText, IFormattedText>> getTooltips(IItemStack item) {
        return TOOLTIPS.getEntries(item);
    }
    
    public static List<Pair<IFormattedText, IFormattedText>> getShiftTooltips(IItemStack item) {
        return SHIFT_TOOLTIPS.getEntries(item);
    }
    
    public static List<Pair<ITooltipFunction, ITooltipFunction>> getAdvancedTooltips(IItemStack item) {
        return TOOLTIP_FUNCTIONS.getEntries(item);
    }
    
    public static List<Pair<ITooltipFunction, ITooltipFunction>> getAdvancedShiftTooltips(IItemStack item) {
        return SHIFT_TOOLTIP_FUNCTIONS.getEntries(item);
    }
    
    public static List<Pattern> getTooltipsToRemove(IItemStack item) {
        return REMOVED_TOOLTIPS.getEntries(item);
    }

    public static List<Integer> getTooltipLinesToRemove(IItemStack item) {
        return REMOVED_TOOLTIPS_LINE.getEntries(item);
    }

    // Boolean wrapper as 3 status flag
    // null -> don't clear tooltip, FALSE -> clear tooltip, TRUE -> clear tooltip but leave name
    // a little dirty, but making an enum for the small thing is annoying
    public static Boolean shouldClearToolTip(IItemStack item) {
        List<Boolean> entries = CLEARED_TOOLTIPS.getEntries(item);
        return entries.isEmpty() ? null : entries.get(0);
    }
    
    // ######################
    // ### Action classes ###
    // ######################
    
    private static class AddTooltipAction implements IAction {
        
        private final IIngredient ingredient;
        private final IFormattedText tooltip;
        private final boolean shift;
        private final IFormattedText showMessage;
        
        public AddTooltipAction(IIngredient ingredient, IFormattedText tooltip, boolean shift, IFormattedText showMessage) {
            this.ingredient = ingredient;
            this.tooltip = tooltip;
            this.shift = shift;
            this.showMessage = showMessage;
        }
        
        @Override
        public void apply() {
            (shift ? SHIFT_TOOLTIPS : TOOLTIPS).register(ingredient, new Pair<>(tooltip, showMessage));
        }
        
        
        @Override
        public String describe() {
            return "Adding tooltip for " + ingredient + ": \"" + tooltip.getText() + "\"";
        }
        
    }
    
    private static class RemoveTooltipAction implements IAction {
        
        private final IIngredient ingredient;
        private final String regex;
        
        public RemoveTooltipAction(IIngredient ingredient, String regex) {
            this.ingredient = ingredient;
            this.regex = regex;
        }
        
        @Override
        public void apply() {
            REMOVED_TOOLTIPS.register(ingredient, Pattern.compile(regex));
        }
        
        
        @Override
        public String describe() {
            return "Removing tooltip for " + ingredient + ": \"" + regex + "\"";
        }
        
    }
    
    private static class AddAdvancedTooltipAction implements IAction {
        
        private final IIngredient ingredient;
        private final ITooltipFunction function;
        private final boolean shift;
        private final ITooltipFunction showMessage;
        
        public AddAdvancedTooltipAction(IIngredient ingredient, ITooltipFunction function, boolean shift, ITooltipFunction showMessage) {
            this.ingredient = ingredient;
            this.function = function;
            this.shift = shift;
            this.showMessage = showMessage;
        }
        
        @Override
        public void apply() {
            (shift ? SHIFT_TOOLTIP_FUNCTIONS : TOOLTIP_FUNCTIONS).register(ingredient, new Pair<>(function, showMessage));
        }
        
        
        @Override
        public String describe() {
            return "Adding advanced tooltip for " + ingredient;
        }
        
    }
    
    private static class ClearTooltipAction implements IAction {
        
        private final IIngredient ingredient;
        private final boolean leaveName;

        public ClearTooltipAction(IIngredient ingredient) {
            this(ingredient, false);
        }

        public ClearTooltipAction(IIngredient ingredient, boolean leaveItemName) {
            this.ingredient = ingredient;
            this.leaveName = leaveItemName;
        }

        @Override
        public void apply() {
            CLEARED_TOOLTIPS.register(ingredient, leaveName);
        }
        
        
        @Override
        public String describe() {
            return "Clearing tooltip for " + ingredient;
        }
        
    }

    private static class RemoveTooltipLineAction implements IAction {

        private final IIngredient ingredient;
        private final int line;

        public RemoveTooltipLineAction(IIngredient ingredient, int line) {
            this.ingredient = ingredient;
            this.line = line;
        }

        @Override
        public void apply() {
            REMOVED_TOOLTIPS_LINE.register(ingredient, line);
        }

        @Override
        public String describe() {
            return "Removing tooltip for " + ingredient + " at line " + line;
        }
    }
}
