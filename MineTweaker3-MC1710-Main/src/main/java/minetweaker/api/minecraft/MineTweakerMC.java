package minetweaker.api.minecraft;

import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.IData;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.player.IPlayer;
import minetweaker.mc1710.data.NBTConverter;
import minetweaker.mc1710.item.MCItemStack;
import minetweaker.mc1710.oredict.MCOreDictEntry;
import minetweaker.mc1710.player.MCPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * MineTweaker - MineCraft API bridge.
 * 
 * @author Stan Hebben
 */
public class MineTweakerMC {
	private MineTweakerMC() {}
	
	/**
	 * Returns the Minecraft item for this MineTweaker item.
	 * 
	 * @param item minetweaker item stack
	 * @return minecraft item stack
	 */
	public static ItemStack getItemStack(IItemStack item) {
		Object internal = item.getInternal();
		if (internal == null || !(internal instanceof ItemStack)) {
			MineTweakerAPI.logger.logError("Not a valid item stack: " + item);
		}
		return (ItemStack) internal;
	}
	
	/**
	 * Returns the Minecraft ingredient for this ingredient. This method is only
	 * useful for ingredients that represent a single item stack.
	 * 
	 * @param ingredient item ingredient
	 * @return minecraft item stack
	 */
	public static ItemStack getItemStack(IIngredient ingredient) {
		List<IItemStack> items = ingredient.getItems();
		if (items.size() != 1) {
			MineTweakerAPI.logger.logError("Not an ingredient with a single item: " + ingredient);
		}
		return getItemStack(items.get(0));
	}
	
	/**
	 * Returns the MineTweaker item stack for this item.
	 * 
	 * @param item minecraft item stack
	 * @return  minetweaker item stack
	 */
	public static IItemStack getIItemStack(ItemStack item) {
		return new MCItemStack(item);
	}
	
	/**
	 * Constructs an item stack with wildcard size.
	 * 
	 * @param item
	 * @param meta
	 * @return minetweaker stack
	 */
	public static IItemStack getIItemStackWildcardSize(Item item, int meta) {
		return new MCItemStack(new ItemStack(item, 1, meta), true);
	}
	
	/**
	 * Constructs an item stack with wildcard damage.
	 * 
	 * @param item stack item
	 * @param amount stack size
	 * @return minetweaker item stack
	 */
	public static IItemStack getIItemStackWildcard(Item item, int amount) {
		return new MCItemStack(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	/**
	 * Constructs an item stack with given item, damage and amount.
	 * 
	 * @param item stack item
	 * @param damage stack damage
	 * @param amount stack amount
	 * @return minetweaker item stack
	 */
	public static IItemStack getItemStack(Item item, int amount, int damage) {
		return new MCItemStack(new ItemStack(item, amount, damage));
	}
	
	/**
	 * Converts an array of minetweaker item stacks into an array of minecraft
	 * item stacks.
	 * 
	 * @param items minetweker item stacks
	 * @return minecraft item stacks
	 */
	public static ItemStack[] getItemStacks(IItemStack... items) {
		ItemStack[] output = new ItemStack[items.length];
		for (int i = 0; i < items.length; i++) {
			Object internal = items[i].getInternal();
			if (internal != null && internal instanceof ItemStack) {
				output[i] = (ItemStack) internal;
			}
		}
		return output;
	}
	
	/**
	 * Converts a list of minetweaker item stacks into an array of minecraft
	 * item stacks.
	 * 
	 * @param items minetweaker items
	 * @return minecraft items
	 */
	public static ItemStack[] getItemStacks(List<IItemStack> items) {
		ItemStack[] output = new ItemStack[items.size()];
		for (int i = 0; i < items.size(); i++) {
			Object internal = items.get(i).getInternal();
			if (internal != null && internal instanceof ItemStack) {
				output[i] = (ItemStack) internal;
			} else {
				MineTweakerAPI.logger.logError("Invalid item stack: " + items.get(i));
			}
		}
		return output;
	}
	
	/**
	 * Converts an array of minecraft item stacks into an array of minetweaker
	 * item stacks.
	 * 
	 * @param items minecraft item stacks
	 * @return minetweaker item stacks
	 */
	public static IItemStack[] getIItemStacks(ItemStack... items) {
		IItemStack[] result = new IItemStack[items.length];
		for (int i = 0; i < items.length; i++) {
			result[i] = new MCItemStack(items[i]);
		}
		return result;
	}
	
	/**
	 * Converts a list of minecraft item stacks into an array of minetweaker
	 * item stacks.
	 * 
	 * @param items minecraft item stacks
	 * @return minetweaker item stacks
	 */
	public static IItemStack[] getIItemStacks(List<ItemStack> items) {
		IItemStack[] result = new IItemStack[items.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = new MCItemStack(items.get(i));
		}
		return result;
	}
	
	/**
	 * Gets the ore dictionary entry with the given name. If the entry didn't
	 * exist yet, it will create it and return an empty entry.
	 * 
	 * @param name ore entry name
	 * @return ore dictionary entry
	 */
	public static IOreDictEntry getOreDict(String name) {
		return new MCOreDictEntry(name);
	}
	
	/**
	 * Converts a Minecraft player into a MineTweaker player.
	 * 
	 * @param player minecraft player
	 * @return minetweaker player
	 */
	public static IPlayer getIPlayer(EntityPlayer player) {
		return new MCPlayer(player);
	}
	
	/**
	 * Converts a MineTweaker player into a Minecraft player.
	 * 
	 * @param player minetweaker player
	 * @return minecraft player
	 */
	public static EntityPlayer getPlayer(IPlayer player) {
		if (!(player instanceof MCPlayer)) {
			MineTweakerAPI.logger.logError("Invalid player: " + player);
		}
		
		return ((MCPlayer) player).getInternal();
	}
	
	/**
	 * Converts a Minecraft NBT to an IData instance. The IData instance is
	 * immutable (not modifyiable).
	 * 
	 * @param nbt nbt data
	 * @return IData value
	 */
	public static IData getIData(NBTBase nbt) {
		return NBTConverter.from(nbt, true);
	}
	
	/**
	 * Converts a Minecraft NBT to a modifyable IData instance.
	 * 
	 * @param nbt nbt data
	 * @return mutable IData value
	 */
	public static IData getIDataModifyable(NBTBase nbt) {
		return NBTConverter.from(nbt, false);
	}
	
	/**
	 * Converts an IData instance to an NBT value.
	 * 
	 * @param data IData value
	 * @return nbt data
	 */
	public static NBTBase getNBT(IData data) {
		return NBTConverter.from(data);
	}
	
	/**
	 * Converts an IData instance to an NBT Tag compound.
	 * 
	 * @param data IData value
	 * @return nbt compound data
	 */
	public static NBTTagCompound getNBTCompound(IData data) {
		return (NBTTagCompound) NBTConverter.from(data);
	}
}
