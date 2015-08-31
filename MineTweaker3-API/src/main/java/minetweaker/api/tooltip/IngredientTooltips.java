/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.tooltip;

import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.formatting.IFormattedText;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.util.IngredientMap;
import minetweaker.api.util.IngredientMap.IngredientMapEntry;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenExpansion("minetweaker.item.IIngredient")
public class IngredientTooltips {
	private static final IngredientMap<IFormattedText> TOOLTIPS = new IngredientMap<IFormattedText>();
	private static final IngredientMap<IFormattedText> SHIFT_TOOLTIPS = new IngredientMap<IFormattedText>();

	@ZenMethod
	public static void addTooltip(IIngredient ingredient, IFormattedText tooltip) {
		MineTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, false));
	}

	@ZenMethod
	public static void addShiftTooltip(IIngredient ingredient, IFormattedText tooltip) {
		MineTweakerAPI.apply(new AddTooltipAction(ingredient, tooltip, true));
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

	private static class AddTooltipAction implements IUndoableAction {
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
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (shift) {
				SHIFT_TOOLTIPS.unregister(entry);
			} else {
				TOOLTIPS.unregister(entry);
			}
		}

		@Override
		public String describe() {
			return "Adding tooltip for " + ingredient + ": " + tooltip;
		}

		@Override
		public String describeUndo() {
			return "Removing tooltip for " + ingredient + ": " + tooltip;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
