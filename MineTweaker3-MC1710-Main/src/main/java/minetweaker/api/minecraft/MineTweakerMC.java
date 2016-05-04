package minetweaker.api.minecraft;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minetweaker.MineTweakerAPI;
import minetweaker.api.block.IBlock;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.data.IData;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientUnknown;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.player.IPlayer;
import minetweaker.api.world.IBiome;
import minetweaker.api.world.IDimension;
import minetweaker.mc1710.block.MCBlockDefinition;
import minetweaker.mc1710.block.MCSpecificBlock;
import minetweaker.mc1710.data.NBTConverter;
import minetweaker.mc1710.item.MCItemStack;
import minetweaker.mc1710.oredict.MCOreDictEntry;
import minetweaker.mc1710.player.MCPlayer;
import minetweaker.mc1710.world.MCDimension;
import minetweaker.mc1710.block.MCWorldBlock;
import minetweaker.mc1710.liquid.MCLiquidStack;
import minetweaker.mc1710.world.MCBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * MineTweaker - MineCraft API bridge.
 * 
 * @author Stan Hebben
 */
public class MineTweakerMC {
	private static final Map<Block, MCBlockDefinition> blockDefinitions = new HashMap<Block, MCBlockDefinition>();
	public static final IBiome[] biomes;

	static {
		biomes = new IBiome[BiomeGenBase.getBiomeGenArray().length];
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			if (BiomeGenBase.getBiomeGenArray()[i] != null)
				biomes[i] = new MCBiome(BiomeGenBase.getBiomeGenArray()[i]);
		}
	}

	private MineTweakerMC() {
	}

	/**
	 * Returns the Minecraft item for this MineTweaker item.
	 * 
	 * @param item minetweaker item stack
	 * @return minecraft item stack
	 */
	public static ItemStack getItemStack(IItemStack item) {
		if (item == null)
			return null;

		Object internal = item.getInternal();
		if (internal == null || !(internal instanceof ItemStack)) {
			MineTweakerAPI.logError("Not a valid item stack: " + item);
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
		if (ingredient == null)
			return null;

		List<IItemStack> items = ingredient.getItems();
		if (items.size() != 1) {
			MineTweakerAPI.logError("Not an ingredient with a single item: " + ingredient);
		}
		return getItemStack(items.get(0));
	}

	/**
	 * Returns the MineTweaker item stack for this item.
	 * 
	 * @param item minecraft item stack
	 * @return minetweaker item stack
	 */
	public static IItemStack getIItemStack(ItemStack item) {
		if (item == null)
			return null;

		return new MCItemStack(item);
	}

	/**
	 * Constructs an item stack with wildcard size.
	 * 
	 * @param item minecraft item stack
	 * @return minetweaker stack
	 */
	public static IItemStack getIItemStackWildcardSize(ItemStack item) {
		if (item == null)
			return null;

		return new MCItemStack(item, true);
	}

	/**
	 * Constructs an item stack with wildcard size.
	 * 
	 * @param item
	 * @param meta
	 * @return minetweaker stack
	 */
	public static IItemStack getIItemStackWildcardSize(Item item, int meta) {
		if (item == null)
			return null;

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
		if (item == null)
			return null;

		return new MCItemStack(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
	}

	public static ItemStack[] getExamples(IIngredient ingredient) {
		List<IItemStack> examples = ingredient.getItems();
		ItemStack[] result = new ItemStack[examples.size()];
		for (int i = 0; i < examples.size(); i++) {
			result[i] = MineTweakerMC.getItemStack(examples.get(i));
		}
		return result;
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
		if (item == null)
			return null;

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
		if (items == null)
			return null;

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
		if (items == null)
			return null;

		ItemStack[] output = new ItemStack[items.size()];
		for (int i = 0; i < items.size(); i++) {
			Object internal = items.get(i).getInternal();
			if (internal != null && internal instanceof ItemStack) {
				output[i] = (ItemStack) internal;
			} else {
				MineTweakerAPI.logError("Invalid item stack: " + items.get(i));
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
		if (items == null)
			return null;

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
		if (items == null)
			return null;

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
		if (player == null)
			return null;

		return new MCPlayer(player);
	}

	/**
	 * Converts a MineTweaker player into a Minecraft player.
	 * 
	 * @param player minetweaker player
	 * @return minecraft player
	 */
	public static EntityPlayer getPlayer(IPlayer player) {
		if (player == null)
			return null;

		if (!(player instanceof MCPlayer)) {
			MineTweakerAPI.logError("Invalid player: " + player);
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
		if (nbt == null)
			return null;

		return NBTConverter.from(nbt, true);
	}

	/**
	 * Converts a Minecraft NBT to a modifyable IData instance.
	 * 
	 * @param nbt nbt data
	 * @return mutable IData value
	 */
	public static IData getIDataModifyable(NBTBase nbt) {
		if (nbt == null)
			return null;

		return NBTConverter.from(nbt, false);
	}

	/**
	 * Converts an IData instance to an NBT value.
	 * 
	 * @param data IData value
	 * @return nbt data
	 */
	public static NBTBase getNBT(IData data) {
		if (data == null)
			return null;

		return NBTConverter.from(data);
	}

	/**
	 * Converts an IData instance to an NBT Tag compound.
	 * 
	 * @param data IData value
	 * @return nbt compound data
	 */
	public static NBTTagCompound getNBTCompound(IData data) {
		if (data == null)
			return null;

		return (NBTTagCompound) NBTConverter.from(data);
	}

	/**
	 * Retrieves the block at the given position.
	 * 
	 * @param blocks block access
	 * @param x block x position
	 * @param y block y position
	 * @param z block z position
	 * @return block instance
	 */
	public static IBlock getBlock(IBlockAccess blocks, int x, int y, int z) {
		return new MCWorldBlock(blocks, x, y, z);
	}

	/**
	 * Retrieves the block definition for the given block.
	 * 
	 * @param block block object
	 * @return block definition
	 */
	public static IBlockDefinition getBlockDefinition(Block block) {
		if (!blockDefinitions.containsKey(block)) {
			blockDefinitions.put(block, new MCBlockDefinition(block));
		}
		return blockDefinitions.get(block);
	}

	/**
	 * Retrieves the dimension instance for a given world.
	 * 
	 * @param world world
	 * @return dimension
	 */
	public static IDimension getDimension(World world) {
		if (world == null)
			return null;

		return new MCDimension(world);
	}

	/**
	 * Returns an instance of the given block.
	 * 
	 * @param block MC block definition
	 * @return MT block instance
	 */
	public static IBlock getBlockAnyMeta(Block block) {
		return new MCSpecificBlock(block, OreDictionary.WILDCARD_VALUE);
	}

	/**
	 * Returns an instance of the given block.
	 * 
	 * @param block MC block definition
	 * @param meta block meta value
	 * @return MT block instance
	 */
	public static IBlock getBlock(Block block, int meta) {
		return new MCSpecificBlock(block, meta);
	}

	/**
	 * Retrieves the block from an item stack.
	 * 
	 * @param itemStack
	 * @return
	 */
	public static Block getBlock(IItemStack itemStack) {
		return ((MCBlockDefinition) itemStack.asBlock().getDefinition()).getInternalBlock();
	}

	public static Block getBlock(IBlock block) {
		return ((MCBlockDefinition) block.getDefinition()).getInternalBlock();
	}

	/**
	 * Retrieves the internal fluid stack of the given stack.
	 * 
	 * @param stack MT liquid stack
	 * @return MCF fluid stack
	 */
	public static FluidStack getLiquidStack(ILiquidStack stack) {
		if (stack == null)
			return null;

		return (FluidStack) stack.getInternal();
	}

	/**
	 * Converts an array of MT liquid stacks into an array of MCF fluid stacks
	 * 
	 */
	public static FluidStack[] getLiquidStacks(ILiquidStack[] stacks) {
		if (stacks == null) {
			return null;
		}

		FluidStack[] res = new FluidStack[stacks.length];

		for (int i = 0; i < stacks.length; i++) {
			ILiquidStack liquidStack = stacks[i];
			res[i] = getLiquidStack(liquidStack);
		}
		return res;
	}

	private static final HashMap<List, IOreDictEntry> oreDictArrays = new HashMap<List, IOreDictEntry>();

	public static IOreDictEntry getOreDictEntryFromArray(List array) {
		if (!oreDictArrays.containsKey(array)) {
			for (String ore : OreDictionary.getOreNames()) {
				if (OreDictionary.getOres(ore) == array) {
					oreDictArrays.put(array, MineTweakerAPI.oreDict.get(ore));
				}
			}
		}

		return oreDictArrays.get(array);
	}

	/**
	 * Converts a Minecraft ingredient to a MineTweaker ingredient.
	 * 
	 * @param ingredient minecraft ingredient
	 * @return minetweaker ingredient
	 */
	public static IIngredient getIIngredient(Object ingredient) {
		if (ingredient == null) {
			return null;
		} else if (ingredient instanceof String) {
			return MineTweakerAPI.oreDict.get((String) ingredient);
		} else if (ingredient instanceof Item) {
			return getIItemStack(new ItemStack((Item) ingredient, 1, 0));
		} else if (ingredient instanceof ItemStack) {
			return getIItemStack((ItemStack) ingredient);
		} else if (ingredient instanceof List) {
			IOreDictEntry entry = getOreDictEntryFromArray((List) ingredient);

			if (entry == null) {
				return IngredientUnknown.INSTANCE;
			}

			return entry;
		} else if (ingredient instanceof FluidStack) {
			return new MCLiquidStack((FluidStack) ingredient);
		} else {
			throw new IllegalArgumentException("Not a valid ingredient: " + ingredient);
		}
	}
}
