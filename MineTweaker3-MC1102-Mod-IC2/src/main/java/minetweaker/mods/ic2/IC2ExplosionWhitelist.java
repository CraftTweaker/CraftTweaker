package minetweaker.mods.ic2;

import ic2.api.tile.ExplosionWhitelist;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Manages the explosion whitelist in IC2. Blocks on this whitelist will not resist
 * an explosion but won't be destroyed.
 * 
 * The explosion code by default ignores blocks which absorb more than 1000 explosion power to
 * prevent abusing personal safes, Trade-O-Mats and other blocks to serve as a cheap and
 * invulnerable reactor chambers. Said blocks will not shield the explosion and won't get
 * destroyed.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.ic2.ExplosionWhitelist")
@ModOnly("IC2")
public class IC2ExplosionWhitelist {
	/**
	 * Adds a block to the explosion whitelist.
	 * 
	 * @param item item to add
	 */
	@ZenMethod
	public static void add(IItemStack item) {
		ItemStack iStack = getItemStack(item);
		Block block = Block.getBlockFromItem(iStack.getItem());
		if (block == null) {
			MineTweakerAPI.logError("This item is not a block");
		} else {
			MineTweakerAPI.apply(new AddAction(block));
		}
	}
	
	/**
	 * Removes a block from the explosion whitelist.
	 * 
	 * @param item item to remove
	 */
	@ZenMethod
	public static void remove(IItemStack item) {
		ItemStack iStack = getItemStack(item);
		Block block = Block.getBlockFromItem(iStack.getItem());
		if (block == null) {
			MineTweakerAPI.logError("This item is not a block");
		} else {
			if (ExplosionWhitelist.isBlockWhitelisted(block)) {
				MineTweakerAPI.logWarning("This block is not in the whitelist");
			} else {
				MineTweakerAPI.apply(new RemoveAction(block));
			}
		}
	}
	
	/**
	 * Checks if the given item is already whitelisted.
	 * 
	 * @param item item to check
	 * @return true if whitelisted
	 */
	@ZenMethod
	public static boolean isWhitelisted(IItemStack item) {
		ItemStack iStack = getItemStack(item);
		Block block = Block.getBlockFromItem(iStack.getItem());
		if (block == null) {
			MineTweakerAPI.logWarning("This item is not a block");
			return false;
		} else {
			return ExplosionWhitelist.isBlockWhitelisted(block);
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddAction implements IUndoableAction {
		private final Block block;
		
		public AddAction(Block block) {
			this.block = block;
		}
		
		@Override
		public void apply() {
			ExplosionWhitelist.addWhitelistedBlock(block);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ExplosionWhitelist.removeWhitelistedBlock(block);
		}

		@Override
		public String describe() {
			return "Adding block to IC2 explosion whitelist: " + block.getLocalizedName();
		}

		@Override
		public String describeUndo() {
			return "Removing block from IC2 explosion whitelist: " + block.getLocalizedName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveAction implements IUndoableAction {
		private final Block block;
		
		public RemoveAction(Block block) {
			this.block = block;
		}
		
		@Override
		public void apply() {
			ExplosionWhitelist.removeWhitelistedBlock(block);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ExplosionWhitelist.addWhitelistedBlock(block);
		}

		@Override
		public String describe() {
			return "Removing block from IC2 explosion whitelist: " + block.getLocalizedName();
		}

		@Override
		public String describeUndo() {
			return "Adding block to IC2 explosion whitelist: " + block.getLocalizedName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
