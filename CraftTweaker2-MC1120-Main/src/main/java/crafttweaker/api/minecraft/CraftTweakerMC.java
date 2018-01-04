package crafttweaker.api.minecraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.*;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.block.*;
import crafttweaker.mc1120.creativetabs.MCCreativeTab;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import crafttweaker.mc1120.player.MCPlayer;
import crafttweaker.mc1120.world.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

/**
 * CraftTweaker - MineCraft API bridge.
 *
 * @author Stan Hebben
 */
public class CraftTweakerMC {
    
    public static final IBiome[] biomes;
    public static final ICreativeTab[] creativeTabs;
    private static final Map<Block, MCBlockDefinition> blockDefinitions = new HashMap<>();
    private static final HashMap<List, IOreDictEntry> oreDictArrays = new HashMap<>();
    static {
        biomes = new IBiome[Biome.REGISTRY.getKeys().size()];
        for(int i = 0; i < Biome.REGISTRY.getKeys().size(); i++) {
            if(Biome.REGISTRY.getObjectById(i) != null)
                biomes[i] = new MCBiome(Biome.REGISTRY.getObjectById(i));
        }
        
        creativeTabs = new ICreativeTab[CreativeTabs.CREATIVE_TAB_ARRAY.length];
        for(int i = 0; i < creativeTabs.length; i++) {
            creativeTabs[i] = new MCCreativeTab(CreativeTabs.CREATIVE_TAB_ARRAY[i]);
        }
    }
    
    private CraftTweakerMC() {
    }
    
    /**
     * Returns the Minecraft item for this CraftTweaker item.
     *
     * @param item crafttweaker item stack
     *
     * @return minecraft item stack
     */
    public static ItemStack getItemStack(IItemStack item) {
        if(item == null)
            return ItemStack.EMPTY;
        
        Object internal = item.getInternal();
        if(internal == null || !(internal instanceof ItemStack)) {
            CraftTweakerAPI.logError("Not a valid item stack: " + item);
        }
        return (ItemStack) internal;
    }
    
    /**
     * Returns the Minecraft ingredient for this ingredient. This method is only
     * useful for ingredients that represent a single item stack.
     *
     * @param ingredient item ingredient
     *
     * @return minecraft item stack
     */
    public static ItemStack getItemStack(IIngredient ingredient) {
        if(ingredient == null)
            return ItemStack.EMPTY;
        
        List<IItemStack> items = ingredient.getItems();
        if(items.size() != 1) {
            CraftTweakerAPI.logError("Not an ingredient with a single item: " + ingredient);
        }
        return getItemStack(items.get(0));
    }
    
    /**
     * Returns the CraftTweaker item stack for this item.
     *
     * @param item minecraft item stack
     *
     * @return crafttweaker item stack
     */
    public static IItemStack getIItemStack(ItemStack item) {
        if(item == null || item.isEmpty())
            return null;
        
        return new MCItemStack(item);
    }
    
    /**
     * Constructs an item stack with wildcard size.
     *
     * @param item minecraft item stack
     *
     * @return crafttweaker stack
     */
    public static IItemStack getIItemStackWildcardSize(ItemStack item) {
        if(item.isEmpty())
            return null;
        
        return new MCItemStack(item, true);
    }
    
    /**
     * Constructs an item stack with wildcard size.
     *
     * @param item
     * @param meta
     *
     * @return crafttweaker stack
     */
    public static IItemStack getIItemStackWildcardSize(Item item, int meta) {
        if(item == null)
            return null;
        return new MCItemStack(new ItemStack(item, 1, meta), true);
    }
    
    /**
     * Constructs an item stack with wildcard damage.
     *
     * @param item   stack item
     * @param amount stack size
     *
     * @return crafttweaker item stack
     */
    public static IItemStack getIItemStackWildcard(Item item, int amount) {
        if(item == null)
            return null;
        
        return new MCItemStack(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
    }
    
    public static ItemStack[] getExamples(IIngredient ingredient) {
        List<IItemStack> examples = ingredient.getItems();
        ItemStack[] result = new ItemStack[examples.size()];
        for(int i = 0; i < examples.size(); i++) {
            result[i] = CraftTweakerMC.getItemStack(examples.get(i));
        }
        return result;
    }
    
    /**
     * Constructs an item stack with given item, damage and amount.
     *
     * @param item   stack item
     * @param damage stack damage
     * @param amount stack amount
     *
     * @return crafttweaker item stack
     */
    public static IItemStack getItemStack(Item item, int amount, int damage) {
        if(item == null)
            return null;
        
        return new MCItemStack(new ItemStack(item, amount, damage));
    }
    
    /**
     * Converts an array of crafttweaker item stacks into an array of minecraft
     * item stacks.
     *
     * @param items minetweker item stacks
     *
     * @return minecraft item stacks
     */
    public static ItemStack[] getItemStacks(IItemStack... items) {
        if(items == null)
            return null;
        
        ItemStack[] output = new ItemStack[items.length];
        for(int i = 0; i < items.length; i++) {
            Object internal = items[i].getInternal();
            if(internal instanceof ItemStack && !((ItemStack) internal).isEmpty()) {
                output[i] = (ItemStack) internal;
            }
        }
        return output;
    }
    
    /**
     * Converts a list of crafttweaker item stacks into an array of minecraft
     * item stacks.
     *
     * @param items crafttweaker items
     *
     * @return minecraft items
     */
    public static ItemStack[] getItemStacks(List<IItemStack> items) {
        if(items == null)
            return null;
        
        ItemStack[] output = new ItemStack[items.size()];
        for(int i = 0; i < items.size(); i++) {
            Object internal = items.get(i).getInternal();
            if(internal instanceof ItemStack && !((ItemStack) internal).isEmpty()) {
                output[i] = (ItemStack) internal;
            } else {
                CraftTweakerAPI.logError("Invalid item stack: " + items.get(i));
            }
        }
        return output;
    }
    
    /**
     * Converts an array of minecraft item stacks into an array of crafttweaker
     * item stacks.
     *
     * @param items minecraft item stacks
     *
     * @return crafttweaker item stacks
     */
    public static IItemStack[] getIItemStacks(ItemStack... items) {
        if(items == null)
            return null;
        
        IItemStack[] result = new IItemStack[items.length];
        for(int i = 0; i < items.length; i++) {
            if(!items[i].isEmpty()) {
                result[i] = new MCItemStack(items[i]);
            }
        }
        return result;
    }
    
    /**
     * Converts a list of minecraft item stacks into an array of crafttweaker
     * item stacks.
     *
     * @param items minecraft item stacks
     *
     * @return crafttweaker item stacks
     */
    public static IItemStack[] getIItemStacks(List<ItemStack> items) {
        if(items == null)
            return null;
        
        IItemStack[] result = new IItemStack[items.size()];
        for(int i = 0; i < result.length; i++) {
            if(!items.get(i).isEmpty()) {
                result[i] = new MCItemStack(items.get(i));
            }
        }
        return result;
    }
    
    /**
     * Gets the ore dictionary entry with the given name. If the entry didn't
     * exist yet, it will create it and return an empty entry.
     *
     * @param name ore entry name
     *
     * @return ore dictionary entry
     */
    public static IOreDictEntry getOreDict(String name) {
        return new MCOreDictEntry(name);
    }
    
    /**
     * Converts a Minecraft player into a CraftTweaker player.
     *
     * @param player minecraft player
     *
     * @return crafttweaker player
     */
    public static IPlayer getIPlayer(EntityPlayer player) {
        if(player == null)
            return null;
        
        return new MCPlayer(player);
    }
    
    /**
     * Converts a CraftTweaker player into a Minecraft player.
     *
     * @param player crafttweaker player
     *
     * @return minecraft player
     */
    public static EntityPlayer getPlayer(IPlayer player) {
        if(player == null)
            return null;
        
        if(!(player instanceof MCPlayer)) {
            CraftTweakerAPI.logError("Invalid player: " + player);
        }
        
        return ((MCPlayer) player).getInternal();
    }
    
    /**
     * Converts a Minecraft NBT to an IData instance. The IData instance is
     * immutable (not modifyiable).
     *
     * @param nbt nbt data
     *
     * @return IData value
     */
    public static IData getIData(NBTBase nbt) {
        if(nbt == null)
            return null;
        
        return NBTConverter.from(nbt, true);
    }
    
    /**
     * Converts a Minecraft NBT to a modifyable IData instance.
     *
     * @param nbt nbt data
     *
     * @return mutable IData value
     */
    public static IData getIDataModifyable(NBTBase nbt) {
        if(nbt == null)
            return null;
        
        return NBTConverter.from(nbt, false);
    }
    
    /**
     * Converts an IData instance to an NBT value.
     *
     * @param data IData value
     *
     * @return nbt data
     */
    public static NBTBase getNBT(IData data) {
        if(data == null)
            return null;
        
        return NBTConverter.from(data);
    }
    
    /**
     * Converts an IData instance to an NBT Tag compound.
     *
     * @param data IData value
     *
     * @return nbt compound data
     */
    public static NBTTagCompound getNBTCompound(IData data) {
        if(data == null)
            return null;
        
        return (NBTTagCompound) NBTConverter.from(data);
    }
    
    /**
     * Retrieves the block at the given position.
     *
     * @param blocks block access
     * @param x      block x position
     * @param y      block y position
     * @param z      block z position
     *
     * @return block instance
     */
    public static IBlock getBlock(IBlockAccess blocks, int x, int y, int z) {
        return new MCWorldBlock(blocks, x, y, z);
    }
    
    /**
     * Retrieves the block definition for the given block.
     *
     * @param block block object
     *
     * @return block definition
     */
    public static IBlockDefinition getBlockDefinition(Block block) {
        if(!blockDefinitions.containsKey(block)) {
            blockDefinitions.put(block, new MCBlockDefinition(block));
        }
        return blockDefinitions.get(block);
    }
    
    /**
     * Returns an instance of the given block.
     *
     * @param block MC block definition
     *
     * @return MT block instance
     */
    public static IBlock getBlockAnyMeta(Block block) {
        return new MCSpecificBlock(block, OreDictionary.WILDCARD_VALUE);
    }
    
    /**
     * Returns an instance of the given block.
     *
     * @param block MC block definition
     * @param meta  block meta value
     *
     * @return MT block instance
     */
    public static IBlock getBlock(Block block, int meta) {
        return new MCSpecificBlock(block, meta);
    }
    
    /**
     * Retrieves the block from an item stack.
     *
     * @param itemStack
     *
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
     *
     * @return MCF fluid stack
     */
    public static FluidStack getLiquidStack(ILiquidStack stack) {
        if(stack == null)
            return null;
        
        return (FluidStack) stack.getInternal();
    }
    
    /**
     * Converts an array of MT liquid stacks into an array of MCF fluid stacks
     */
    public static FluidStack[] getLiquidStacks(ILiquidStack[] stacks) {
        if(stacks == null) {
            return null;
        }
        
        FluidStack[] res = new FluidStack[stacks.length];
        
        for(int i = 0; i < stacks.length; i++) {
            ILiquidStack liquidStack = stacks[i];
            res[i] = getLiquidStack(liquidStack);
        }
        return res;
    }
    
    public static IOreDictEntry getOreDictEntryFromArray(List array) {
        if(!oreDictArrays.containsKey(array)) {
            for(String ore : OreDictionary.getOreNames()) {
                if(OreDictionary.getOres(ore) == array) {
                    oreDictArrays.put(array, CraftTweakerAPI.oreDict.get(ore));
                }
            }
        }
        
        return oreDictArrays.get(array);
    }
    
    /**
     * Converts a Minecraft ingredient to a CraftTweaker ingredient.
     *
     * @param ingredient minecraft ingredient
     *
     * @return crafttweaker ingredient
     */
    public static IIngredient getIIngredient(Object ingredient) {
        if(ingredient == null) {
            return null;
        } else if(ingredient instanceof String) {
            return CraftTweakerAPI.oreDict.get((String) ingredient);
        } else if(ingredient instanceof Item) {
            return getIItemStack(new ItemStack((Item) ingredient, 1, 0));
        } else if(ingredient instanceof ItemStack) {
            return getIItemStack((ItemStack) ingredient);
        } else if(ingredient instanceof List) {
            IOreDictEntry entry = getOreDictEntryFromArray((List) ingredient);
            
            if(entry == null) {
                return IngredientUnknown.INSTANCE;
            }
            
            return entry;
        } else if(ingredient instanceof FluidStack) {
            return new MCLiquidStack((FluidStack) ingredient);
        } else if(ingredient instanceof Ingredient) {
            ItemStack[] matchingStacks = ((Ingredient) ingredient).getMatchingStacks();
            
            if(ingredient == Ingredient.EMPTY || matchingStacks.length <= 0 || ((Ingredient) ingredient).apply(ItemStack.EMPTY)) {
                return null;
            } else {
                return getIItemStack(matchingStacks[0]);
            }
        } else {
            throw new IllegalArgumentException("Not a valid ingredient: " + ingredient);
        }
    }
    
    public static IWorld getWorldByID(int id) {
        return new MCWorld(DimensionManager.getWorld(id));
    }
}
