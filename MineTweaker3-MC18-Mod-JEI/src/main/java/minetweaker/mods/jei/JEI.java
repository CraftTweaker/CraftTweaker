package minetweaker.mods.jei;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * MineTweaker JEI support.
 * 
 * Adds support to hide Items in JEI
 * 
 * @author Jared
 */
@ZenClass("mods.jei.JEI")
public class JEI {
	/**
	 * Hides a specific item in NEI. Will take into account metadata values, if
	 * any.
	 * 
	 * @param item
	 *            item to be hidden
	 */
	@ZenMethod
	public static void hide(@NotNull IItemStack item) {
		MineTweakerAPI.apply(new JEIHideItemAction(item));
	}

	private static class JEIHideItemAction implements IUndoableAction {
		private final IItemStack stack;

		public JEIHideItemAction(IItemStack stack) {
			this.stack = stack;
		}

		@Override
		public void apply() {
				JEIAddonPlugin.jeiHelpers.getItemBlacklist().addItemToBlacklist(getItemStack(stack));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			JEIAddonPlugin.jeiHelpers.getItemBlacklist().removeItemFromBlacklist(getItemStack(stack));
		}

		@Override
		public String describe() {
			return "Hiding " + stack + " in JEI";
		}

		@Override
		public String describeUndo() {
			return "Displaying " + stack + " in JEI";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	// private static class NEIOverrideNameAction extends OneWayAction {
	// private final ItemStack item;
	// private final String name;
	//
	// public NEIOverrideNameAction(ItemStack item, String name) {
	// this.item = item;
	// this.name = name;
	// }
	//
	// @Override
	// public void apply() {
	// API.setOverrideName(item, name);
	// }
	//
	// @Override
	// public String describe() {
	// return "Overriding NEI item name of " + item.getUnlocalizedName() + " to
	// " + name;
	// }
	//
	// @Override
	// public Object getOverrideKey() {
	// return item;
	// }
	// }
}
