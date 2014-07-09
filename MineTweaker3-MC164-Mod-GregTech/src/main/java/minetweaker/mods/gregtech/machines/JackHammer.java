package minetweaker.mods.gregtech.machines;

import gregtechmod.api.GregTech_API;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Access point to jack hammer whitelist.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.JackHammer")
@ModOnly("gregtech_addon")
public class JackHammer {
	/**
	 * Adds a block to be minable. The given item must be a block.
	 * 
	 * @param item block
	 */
	@ZenMethod
	public static void addMinableBlock(IItemStack item) {
		Block block = getBlock(item);
		if (block == null) {
			MineTweakerAPI.logError("Not a block: " + item);
		} else {
			MineTweakerAPI.apply(new AddBlockAction(block, false));
		}
	}
	
	/**
	 * Adds a block to be minable, but only with the diamond tools. The given
	 * item must be a block.
	 * 
	 * @param item block
	 */
	@ZenMethod
	public static void addDiamondMinableBlock(IItemStack item) {
		Block block = getBlock(item);
		if (block == null) {
			MineTweakerAPI.logError("Not a block: " + item);
		} else {
			MineTweakerAPI.apply(new AddBlockAction(block, true));
		}
	}
	
	// #######################
	// ### Private Methods ###
	// #######################
	
	private static Block getBlock(IItemStack item) {
		ItemStack itemStack = MineTweakerMC.getItemStack(item);
		return itemStack.itemID > Block.blocksList.length ? null : Block.blocksList[itemStack.itemID];
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddBlockAction extends OneWayAction {
		private final Block block;
		private final boolean diamondOnly;
		
		public AddBlockAction(Block block, boolean diamondOnly) {
			this.block = block;
			this.diamondOnly = diamondOnly;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addJackHammerMinableBlock(block, diamondOnly);
		}

		@Override
		public String describe() {
			return "Adding jackhammer mineable block " + block;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 83 * hash + (this.block != null ? this.block.hashCode() : 0);
			hash = 83 * hash + (this.diamondOnly ? 1 : 0);
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final AddBlockAction other = (AddBlockAction) obj;
			if (this.block != other.block) {
				return false;
			}
			if (this.diamondOnly != other.diamondOnly) {
				return false;
			}
			return true;
		}
	}
}
