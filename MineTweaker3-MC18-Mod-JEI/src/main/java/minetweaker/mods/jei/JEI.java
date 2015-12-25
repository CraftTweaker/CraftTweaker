package minetweaker.mods.jei;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * MineTweaker NEI support.
 * 
 * Enables hiding NEI items as well as adding new item stacks. These item stacks
 * can then show a custom message or contain NBT data. Can be used to show a
 * custom message or lore with certain items, or to provide spawnable items with
 * specific NBT tags.
 * 
 * @author Stan Hebben
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
	//
	// /**
	// * Adds a NEI entry. The item stack can contain damage values and NBT
	// tags.
	// *
	// * @param stack
	// * item stack to be added
	// */
	// @ZenMethod
	// public static void addEntry(@NotNull IItemStack stack) {
	// MineTweakerAPI.apply(new NEIAddEntryAction(getItemStack(stack)));
	// }

	// /**
	// * Overrides a name in NEI. Note that this will not change the original
	// * display name, but it will change the way it is displayed in NEI.
	// *
	// * @param item
	// * item
	// * @param name
	// * item name
	// */
	// @ZenMethod
	// public static void overrideName(@NotNull IItemStack item, @NotNull String
	// name) {
	// MineTweakerAPI.apply(new NEIOverrideNameAction(getItemStack(item),
	// name));
	// }

	// #############################
	// ### Private inner classes ###
	// #############################

	// private static class NEIAddEntryAction implements IUndoableAction {
	// private final ItemStack item;
	//
	// public NEIAddEntryAction(ItemStack item) {
	// this.item = item;
	// }
	//
	// @Override
	// public void apply() {
	// JEIAddonPlugin.jeiHelpers.getItemBlacklist().addItemToBlacklist(item);
	// // API.addItemListEntry(item);
	// }
	//
	// @Override
	// public boolean canUndo() {
	// return true;
	// }
	//
	// @Override
	// public void undo() {
	// API.hideItem(item);
	// }
	//
	// @Override
	// public String describe() {
	// return "Adding " + item.getDisplayName() + " as NEI entry";
	// }
	//
	// @Override
	// public String describeUndo() {
	// return "Removing " + item.getDisplayName() + " as NEI entry";
	// }
	//
	// @Override
	// public Object getOverrideKey() {
	// return null;
	// }
	// }

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
