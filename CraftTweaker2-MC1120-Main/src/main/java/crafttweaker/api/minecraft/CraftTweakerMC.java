package crafttweaker.api.minecraft;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.command.ICommand;
import crafttweaker.api.command.ICommandSender;
import crafttweaker.api.container.IContainer;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.data.IData;
import crafttweaker.api.dispenser.IBlockSource;
import crafttweaker.api.entity.*;
import crafttweaker.api.entity.attribute.IEntityAttribute;
import crafttweaker.api.entity.attribute.IEntityAttributeInstance;
import crafttweaker.api.entity.attribute.IEntityAttributeModifier;
import crafttweaker.api.game.ITeam;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.potions.IPotionEffect;
import crafttweaker.api.potions.IPotionType;
import crafttweaker.api.server.IServer;
import crafttweaker.api.text.IStyle;
import crafttweaker.api.text.ITextComponent;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.block.*;
import crafttweaker.mc1120.command.MCCommand;
import crafttweaker.mc1120.command.MCCommandSender;
import crafttweaker.mc1120.container.MCContainer;
import crafttweaker.mc1120.creativetabs.MCCreativeTab;
import crafttweaker.mc1120.damage.MCDamageSource;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.dispenser.MCBlockSource;
import crafttweaker.mc1120.entity.*;
import crafttweaker.mc1120.entity.attribute.MCEntityAttribute;
import crafttweaker.mc1120.entity.attribute.MCEntityAttributeInstance;
import crafttweaker.mc1120.entity.attribute.MCEntityAttributeModifier;
import crafttweaker.mc1120.entity.expand.ExpandEntityEquipmentSlot;
import crafttweaker.mc1120.game.MCTeam;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.item.MCMutableItemStack;
import crafttweaker.mc1120.item.VanillaIngredient;
import crafttweaker.mc1120.liquid.MCLiquidDefinition;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import crafttweaker.mc1120.player.MCPlayer;
import crafttweaker.mc1120.potions.MCPotion;
import crafttweaker.mc1120.potions.MCPotionEffect;
import crafttweaker.mc1120.potions.MCPotionType;
import crafttweaker.mc1120.text.MCStyle;
import crafttweaker.mc1120.text.MCTextComponent;
import crafttweaker.mc1120.util.MCAxisAlignedBB;
import crafttweaker.mc1120.world.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import java.util.*;

/**
 * CraftTweaker - MineCraft API bridge.
 *
 * @author Stan Hebben
 */
@SuppressWarnings({"rawtypes"})
public class CraftTweakerMC {
    
    public static final IBiome[] biomes;
    public static final Map<String, ICreativeTab> creativeTabs = new HashMap<>();
    private static final Map<Block, MCBlockDefinition> blockDefinitions = new HashMap<>();
    private static final HashMap<List, IOreDictEntry> oreDictArrays = new HashMap<>();
    static {
        biomes = new IBiome[Biome.REGISTRY.getKeys().size()];
        for(int i = 0; i < Biome.REGISTRY.getKeys().size(); i++) {
            if(Biome.REGISTRY.getObjectById(i) != null)
                biomes[i] = new MCBiome(Biome.REGISTRY.getObjectById(i));
        }
        
        for(CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tab == null)
                continue;
            String label;
            label = tab.tabLabel;
            creativeTabs.put(label, new MCCreativeTab(tab, label));
        }
    }
    
    private CraftTweakerMC() {
    
    }
    
    /**
     * Returns a copy of the Minecraft item for this CraftTweaker item.
     *
     * @param item crafttweaker item stack
     *
     * @return copy of minecraft item stack
     */
    public static ItemStack getItemStack(IItemStack item) {
        if(item == null)
            return ItemStack.EMPTY;
        
        Object internal = item.getInternal();
        if(!(internal instanceof ItemStack)) {
            CraftTweakerAPI.logError("Not a valid item stack: " + item);
            throw new IllegalArgumentException("Not a valid item stack: " + item);
        }
        return ((ItemStack) internal).copy();
    }
    
    /**
     * Returns the original Minecraft item for this CraftTweaker mutable item.
     *
     * @param item crafttweaker mutable item stack
     *
     * @return original minecraft item stack
     */
    public static ItemStack getItemStack(IMutableItemStack item) {
        if(item == null)
            return ItemStack.EMPTY;
        
        Object internal = item.getInternal();
        if(!(internal instanceof ItemStack)) {
            CraftTweakerAPI.logError("Not a valid item stack: " + item);
            throw new IllegalArgumentException("Not a valid item stack: " + item);
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
        if(items.isEmpty())
            return ItemStack.EMPTY;
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
        if(item == null || item.isEmpty())
            return null;
        
        return new MCItemStack(item, true);
    }

    /**
     * Returns the CraftTweaker mutable item stack for this item.
     *
     * @param item minecraft item stack
     *
     * @return crafttweaker mutable item stack
     */
    public static IItemStack getIItemStackMutable(ItemStack item) {
        if (item == null || item.isEmpty())
            return null;

        return new MCMutableItemStack(item);
    }

    /**
     * Constructs a mutable item stack with wildcard size.
     *
     * @param item minecraft item stack
     *
     * @return crafttweaker mutable item stack
     */
    public static IItemStack getIItemStackMutableWildcardSize(ItemStack item) {
        if (item == null || item.isEmpty())
            return null;

        return new MCMutableItemStack(item, true);
    }

    /**
     * Constructs an item stack for matching. Less performance-heavy than normal getIItemStack.
     * 
     * @param item minecraft item stack
     * 
     * @return crafttweaker item stack for matching
     */
    public static IItemStack getIItemStackForMatching(ItemStack item) {
        return getIItemStackForMatching(item, false);
    }

    /**
     * Constructs an item stack for matching with wildcard size. Less performance-heavy than normal getIItemStack.
     * 
     * @param item minecraft item stack
     * 
     * @return crafttweaker item stack for matching
     */
    public static IItemStack getIItemStackForMatching(ItemStack item, boolean wildcardSize) {
        return wildcardSize ? getIItemStackMutableWildcardSize(item) : getIItemStackMutable(item);
    }
    
    /**
     * Constructs an item stack with wildcard size.
     *
     * @param item stack item
     * @param meta stack meta
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
            if(items[i] == null) {
                output[i] = ItemStack.EMPTY;
            } else {
                Object internal = items[i].getInternal();
                if(internal instanceof ItemStack && !((ItemStack) internal).isEmpty()) {
                    output[i] = (ItemStack) internal;
                }
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
            if(items.get(i) == null)
                output[i] = ItemStack.EMPTY;
            else {
                Object internal = items.get(i).getInternal();
                if(internal instanceof ItemStack && !((ItemStack) internal).isEmpty()) {
                    output[i] = (ItemStack) internal;
                } else {
                    CraftTweakerAPI.logError("Invalid item stack: " + items.get(i));
                }
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
            ItemStack itemStack = items[i];
            if(itemStack != null && !itemStack.isEmpty()) {
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
            ItemStack itemStack = items.get(i);
            if(itemStack != null && !itemStack.isEmpty()) {
                result[i] = new MCItemStack(itemStack);
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
            return null;
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
        if(block == null)
            return null;
        
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
        if(block == null)
            return null;
        return new MCSpecificBlock(block, meta);
    }
    
    /**
     * Retrieves the block from an item stack.
     *
     * @param itemStack the itemstack to retrieve the block from
     *
     * @return the block from the itemstack
     */
    public static Block getBlock(IItemStack itemStack) {
        if(itemStack == null)
            return null;
        return ((MCBlockDefinition) itemStack.asBlock().getDefinition()).getInternalBlock();
    }
    
    public static Block getBlock(IBlock block) {
        if(block == null)
            return null;
        return ((MCBlockDefinition) block.getDefinition()).getInternalBlock();
    }
    
    public static Block getBlock(IBlockDefinition blockDefinition) {
        if(blockDefinition instanceof MCBlockDefinition)
            return ((MCBlockDefinition) blockDefinition).getInternalBlock();
        Object block = blockDefinition.getInternal();
        return (block instanceof Block) ? (Block) block : null;
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
    
    /**
     * Retrieves the CT liquid stack of the given stack.
     *
     * @param stack MCF liquid stack
     *
     * @return CrT fluid stack
     */
    public static ILiquidStack getILiquidStack(FluidStack stack) {
        if(stack == null)
            return null;
        return new MCLiquidStack(stack);
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
            if(ingredient instanceof OreIngredient)
                return getOreDict((OreIngredient) ingredient);
            if(ingredient instanceof CompoundIngredient) {
                return mergeIngredients(((CompoundIngredient) ingredient).getChildren().stream()
                        .map(CraftTweakerMC::getIIngredient)
                        .toArray(IIngredient[]::new));
                        
            }
            
            ItemStack[] matchingStacks = ((Ingredient) ingredient).matchingStacks;
            
            if(ingredient == Ingredient.EMPTY || matchingStacks.length <= 0 || ((Ingredient) ingredient).apply(ItemStack.EMPTY)) {
                return null;
            } else {
                return mergeIngredients(getIItemStacks(matchingStacks));
            }
        } else {
            throw new IllegalArgumentException("Not a valid ingredient: " + ingredient);
        }
    }
    
    public static IIngredient mergeIngredients(IIngredient... ingredients) {
        if(ingredients == null || ingredients.length == 0)
            return null;
        final IIngredient[] objects = Arrays.stream(ingredients).filter(i -> i != IngredientUnknown.INSTANCE).toArray(IIngredient[]::new);
        if(objects.length == 0)
            return null;
        if(objects.length == 1)
            return objects[0];
        return new IngredientOr(objects);
    }
    
    public static Ingredient getIngredient(IIngredient ingredient) {
        if(ingredient == null)
            return Ingredient.EMPTY;
        if(ingredient instanceof IOreDictEntry)
            return new OreIngredient(((IOreDictEntry) ingredient).getName());
        if(ingredient instanceof IItemStack)
            if(((IItemStack) ingredient).hasTag() && ((IItemStack) ingredient).getMatchTagExact())
                return new IngredientNBT(getItemStack(ingredient)){};
            else
                return Ingredient.fromStacks(getItemStack((IItemStack) ingredient));
                
        return new VanillaIngredient(ingredient);
    }
    
    private static IOreDictEntry getOreDict(OreIngredient oreIngredient) {
        return MCOreDictEntry.getFromIngredient(oreIngredient);
    }
    
    public static IWorld getWorldByID(int id) {
        return new MCWorld(DimensionManager.getWorld(id));
    }
    
    public static IWorld getIWorld(World world) {
        return world == null ? null : new MCWorld(world);
    }
    
    public static World getWorld(IWorld world) {
        return world == null ? null : (World) world.getInternal();
    }
    
    public static boolean matches(IItemStack iitem, ItemStack stack, boolean wildcardsize) {
        ItemStack internal = ItemStack.EMPTY;
        if(iitem != null) {
            internal = (ItemStack) iitem.getInternal();
            
            if(stack.hasTagCompound()) {
                return matchesExact(iitem, stack);
            }
        }
        return !internal.isEmpty() && !stack.isEmpty() && internal.getItem() == stack.getItem() && (wildcardsize || internal.getCount() >= stack.getCount()) && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == internal.getItemDamage() || (!stack.getHasSubtypes() && !stack.getItem().isDamageable()));
    }
    
    public static boolean matches(IItemStack iitem, ItemStack stack) {
        return matches(iitem, stack, true);
    }
    
    public static boolean matchesExact(IItemStack item, ItemStack stack) {
        ItemStack internal = getItemStack(item);
        if(internal.getTagCompound() != null && stack.getTagCompound() == null) {
            return false;
        }
        if(internal.getTagCompound() == null && stack.getTagCompound() != null) {
            return false;
        }
        if(internal.getTagCompound() == null) {
            return stack.getItem() == internal.getItem() && (internal.getMetadata() == 32767 || stack.getMetadata() == internal.getMetadata());
        }
        if(internal.getTagCompound().getKeySet().equals(stack.getTagCompound().getKeySet())) {
            for(String s : internal.getTagCompound().getKeySet()) {
                if(!internal.getTagCompound().getTag(s).equals(stack.getTagCompound().getTag(s))) {
                    return false;
                }
            }
        }
        return stack.getItem() == internal.getItem() && (internal.getMetadata() == 32767 || stack.getMetadata() == internal.getMetadata());
    }
    
    public static IBlockState getBlockState(net.minecraft.block.state.IBlockState block) {
        return block == null ? null : new MCBlockState(block);
    }
    
    public static net.minecraft.block.state.IBlockState getBlockState(IBlockState block) {
        return block == null ? null : (net.minecraft.block.state.IBlockState) block.getInternal();
    }
    
    public static IEntity getIEntity(Entity entity) {
        if(entity == null)
            return null;
        else if(entity instanceof EntityLivingBase)
            return getIEntityLivingBase((EntityLivingBase) entity);
        else if(entity instanceof EntityItem)
            return new MCEntityItem((EntityItem) entity);
        else if(entity instanceof EntityXPOrb)
            return new MCEntityXp((EntityXPOrb) entity);
        else if(entity instanceof EntityFishHook)
            return new MCEntityFishHook((EntityFishHook) entity);
        else if(entity instanceof EntityArrow)
            return getIEntityArrow((EntityArrow) entity);
        else if(entity instanceof EntityThrowable)
            return new MCEntityThrowable((EntityThrowable) entity);
        else
            return new MCEntity(entity);
    }

    public static IEntityXp getIEntityXp(EntityXPOrb entityXPOrb) {
        return entityXPOrb == null ? null : new MCEntityXp(entityXPOrb);
    }
    
    public static IEntityItem getIEntityItem(EntityItem entityItem) {
        return entityItem == null ? null : new MCEntityItem(entityItem);
    }
    
    public static Entity getEntity(IEntity entity) {
        return entity == null ? null : (Entity) entity.getInternal();
    }
    
    public static IEntityLivingBase getIEntityLivingBase(EntityLivingBase entityLivingBase) {
        if(entityLivingBase == null)
            return null;
        if(entityLivingBase instanceof EntityPlayer)
            return getIPlayer((EntityPlayer) entityLivingBase);
        else if(entityLivingBase instanceof EntityLiving)
            return getIEntityLiving((EntityLiving) entityLivingBase);
        return new MCEntityLivingBase(entityLivingBase);
    }
    
    public static IEntityLiving getIEntityLiving(EntityLiving entityLiving) {
        if(entityLiving == null)
            return null;
        if(entityLiving instanceof EntityCreature)
            return getIEntityCreature((EntityCreature) entityLiving);
        return new MCEntityLiving(entityLiving);
    }
    
    public static IEntityCreature getIEntityCreature(EntityCreature entityCreature) {
        if(entityCreature == null)
            return null;
        if(entityCreature instanceof EntityAgeable)
            return getIEntityAgeable((EntityAgeable) entityCreature);
        else if(entityCreature instanceof EntityMob) {
            return getIEntityMob((EntityMob) entityCreature);
        }
        return new MCEntityCreature(entityCreature);
    }
    
    public static IEntityAgeable getIEntityAgeable(EntityAgeable entityAgeable) {
        if(entityAgeable == null)
            return null;
        if(entityAgeable instanceof EntityAnimal)
            return getIEntityAnimal((EntityAnimal) entityAgeable);
        return new MCEntityAgeable(entityAgeable);
    }
    
    public static IEntityAnimal getIEntityAnimal(EntityAnimal entityAnimal) {
        return entityAnimal == null ? null : new MCEntityAnimal(entityAnimal);
    }
    
    public static IEntityMob getIEntityMob(EntityMob entityMob) {
        return entityMob == null ? null : new MCEntityMob(entityMob);
    }
    
    
    public static IBlockPos getIBlockPos(BlockPos pos) {
        return pos == null ? null : new MCBlockPos(pos);
    }
    
    public static BlockPos getBlockPos(IBlockPos pos) {
        return pos == null ? null : (BlockPos) pos.getInternal();
    }
    
    public static ITeam getITeam(Team team) {
        return team == null ? null : new MCTeam(team);
    }
    
    public static Team getTeam(ITeam team) {
        return team == null ? null : (Team) team.getInternal();
    }
    
    public static IDamageSource getIDamageSource(DamageSource source) {
        return source == null ? null : new MCDamageSource(source);
    }
    
    public static DamageSource getDamageSource(IDamageSource source) {
        return source == null ? null : (DamageSource) source.getInternal();
    }
    
    public static IMaterial getIMaterial(Material material) {
        return material == null ? null : new MCMaterial(material);
    }
    
    public static Material getMaterial(IMaterial material) {
        return material == null ? null : (Material) material.getInternal();
    }
    
    public static EntityAnimal getEntityAnimal(IEntityAnimal entityAnimal) {
        return entityAnimal == null ? null : (EntityAnimal) entityAnimal.getInternal();
    }
    
    public static IEntityEquipmentSlot getIEntityEquipmentSlot(EntityEquipmentSlot slot) {
        return slot == null ? null : new MCEntityEquipmentSlot(slot);
    }
    
    public static EntityEquipmentSlot getEntityEquipmentSlot(IEntityEquipmentSlot slot) {
        return slot == null ? null : (EntityEquipmentSlot) slot.getInternal();
    }
    
    public static EntityLivingBase getEntityLivingBase(IEntityLivingBase entityLivingBase) {
        return entityLivingBase == null ? null : (EntityLivingBase) entityLivingBase.getInternal();
    }
    
    public static IPotion getIPotion(Potion potion) {
        return potion == null ? null : new MCPotion(potion);
    }
    
    public static Potion getPotion(IPotion potion) {
        return potion == null ? null : (Potion) potion.getInternal();
    }
    
    public static IPotionEffect getIPotionEffect(PotionEffect potionEffect) {
        return potionEffect == null ? null : new MCPotionEffect(potionEffect);
    }
    
    public static PotionEffect getPotionEffect(IPotionEffect potionEffect) {
        return potionEffect == null ? null : (PotionEffect) potionEffect.getInternal();
    }
    
    public static IIngredient[] getIIngredients(List<Ingredient> ingredientList) {
        if(ingredientList == null)
            return new IIngredient[0];
        
        IIngredient[] out = new IIngredient[ingredientList.size()];
        for(int index = 0; index < out.length; index++) {
            out[index] = CraftTweakerMC.getIIngredient(ingredientList.get(index));
        }
        return out;
    }
    
    public static EntityItem getEntityItem(IEntityItem entityItem) {
        return entityItem == null ? null : (EntityItem) entityItem.getInternal();
    }
    
    public static IRayTraceResult getIRayTraceResult(RayTraceResult rayTraceResult) {
        return rayTraceResult == null ? null : new MCRayTraceResult(rayTraceResult);
    }
    
    public static RayTraceResult getRayTraceResult(IRayTraceResult rayTraceResult) {
        return rayTraceResult == null ? null : (RayTraceResult) rayTraceResult.getInternal();
    }
    
    public static IContainer getIContainer(Container container) {
        return container == null ? null : new MCContainer(container);
    }
    
    public static Container getContainer(IContainer container) {
        return container == null ? null : (Container) container.getInternal();
    }

    public static IFacing getIFacing(EnumFacing sideHit) {
        return sideHit == null ? null : new MCFacing(sideHit);
    }
    
    public static CreativeTabs getCreativeTabs(ICreativeTab tab) {
        return tab == null ? null : (CreativeTabs) tab.getInternal();
    }
    
    public static net.minecraft.command.ICommandSender getICommandSender(ICommandSender sender) {
        return sender == null ? null : (net.minecraft.command.ICommandSender) sender.getInternal();
    }
    
    public static ICommandSender getICommandSender(net.minecraft.command.ICommandSender sender) {
        if(sender instanceof Entity)
            return getIEntity((Entity) sender);
        return sender == null ? null : new MCCommandSender(sender);
    }
    
    public static ICommand getICommand(net.minecraft.command.ICommand iCommand) {
        return iCommand == null ? null : new MCCommand(iCommand);
    }
    
    public static net.minecraft.command.ICommand getICommand(ICommand command) {
        return command == null ? null : (net.minecraft.command.ICommand) command.getInternal();
    }
    
    public static MinecraftServer getMCServer(IServer server) {
        return server == null ? null : (MinecraftServer) server.getInternal();
    }
    
    public static Vec3d getVec3d(IVector3d vector3d) {
        return vector3d == null ? null : (Vec3d) vector3d.getInternal();
    }
    
    public static IVector3d getIVector3d(Vec3d vec3d) {
        return vec3d == null ? null : new MCVector3d(vec3d);
    }
    
    public static List<IItemStack> getIItemStackList(List<ItemStack> items) {
        List<IItemStack> out = new ArrayList<>();
        
        if(items == null || items.isEmpty()) {
            return out;
        }
        
        for(ItemStack itemStack : items) {
            IItemStack stack = getIItemStack(itemStack);
            if(stack != null)
                out.add(stack);
        }
        return out;
    }
    
    public static List<IItemStack> getIItemStacks(Collection<ItemStack> items) {
        List<IItemStack> convertedOutputs = new ArrayList<>();
        
        if(items != null && !items.isEmpty()) {
            
            for(ItemStack itemStack : items) {
                
                IItemStack stack = CraftTweakerMC.getIItemStack(itemStack);
                
                if(stack != null && !stack.isEmpty()) {
                    
                    convertedOutputs.add(stack);
                }
            }
        }
        
        return convertedOutputs;
    }
    
    public static List<WeightedItemStack> getWeightedItemStackList(List<ItemStack> items) {
        List<WeightedItemStack> out = new ArrayList<>();
        
        if(items == null || items.isEmpty()) {
            return out;
        }
        
        for(ItemStack itemStack : items) {
            IItemStack stack = getIItemStack(itemStack);
            if(stack != null)
                out.add(new WeightedItemStack(stack, 100));
        }
        return out;
    }
    
    public static Fluid getFluid(ILiquidDefinition liquidDefinition) {
        return liquidDefinition == null ? null : (Fluid) liquidDefinition.getInternal();
    }
    
    public static ILiquidDefinition getILiquidDefinition(Fluid fluid) {
        return fluid == null ? null : new MCLiquidDefinition(fluid);
    }
    
    public static Biome getBiome(IBiome biome) {
        return biome == null ? null : biome instanceof MCBiome ? ((MCBiome) biome).getInternal() : Biome.REGISTRY.getObject(new ResourceLocation(biome.getId()));
    }

    public static IExplosion getIExplosion(Explosion explosion) {
        return explosion == null ? null : new MCExplosion(explosion);
    }

    public static Explosion getExplosion(IExplosion explosion) {
        return explosion instanceof MCExplosion ? (Explosion) explosion.getInternal() : null;
    }

    public static IEntityFishHook getIEntityFishHook(EntityFishHook entity) {
		return entity == null ? null : new MCEntityFishHook(entity);
	}

	public static AttributeModifier getAttributeModifier(IEntityAttributeModifier modifier) {
		return modifier == null ? null : (AttributeModifier) modifier.getInternal();
	}

    public static IEntityAttributeModifier getIEntityAttributeModifier(AttributeModifier modifier) {
		return modifier == null ? null : new MCEntityAttributeModifier(modifier);
	}

    public static IEntityArrow getIEntityArrow(EntityArrow entity) {
        if(entity == null)
            return null;
        else if(entity instanceof EntityTippedArrow)
            return new MCEntityArrowTipped((EntityTippedArrow) entity);
        else
            return new MCEntityArrow(entity);
	}

    public static IEntityArrowTipped getIEntityArrowTipped(EntityTippedArrow entity) {
        return entity == null ? null : new MCEntityArrowTipped(entity);
    }

    public static IEntityThrowable getIEntityThrowable(EntityThrowable entity) {
		return entity == null ? null : new MCEntityThrowable(entity);
	}

    public static IPotionEffect[] getIPotionEffects(List<PotionEffect> potionEffects) {
        if(potionEffects == null)
            return null;

        IPotionEffect[] result = new IPotionEffect[potionEffects.size()];
        for(int i = 0; i < result.length; i++) {
            PotionEffect potionEffect = potionEffects.get(i);
            if(potionEffect != null) {
                result[i] = new MCPotionEffect(potionEffect);
            }
        }
        return result;
    }

    public static PotionType getPotionType(IPotionType potionType) {
        return potionType == null ? null : (PotionType) potionType.getInternal();
    }

    public static IPotionType getIPotionType(PotionType potionType) {
        return potionType == null ? null : new MCPotionType(potionType);
    }

    public static AxisAlignedBB getAxisAlignedBB(IAxisAlignedBB aabb) {
        return aabb == null ? null : (AxisAlignedBB) aabb.getInternal();
    }

    public static IAxisAlignedBB getIAxisAlignedBB(AxisAlignedBB aabb) {
        return aabb == null ? null : new MCAxisAlignedBB(aabb);
    }

    public static IEntityAttribute getIEntityAttribute(IAttribute attribute) {
        return attribute == null ? null : new MCEntityAttribute(attribute);
    }

    public static IEntityAttributeInstance getIEntityAttributeInstance(IAttributeInstance attribute) {
        return attribute == null ? null : new MCEntityAttributeInstance(attribute);
    }

    public static EnumHand getHand(IEntityEquipmentSlot hand) {
        if (hand == null) return null;
        switch (getEntityEquipmentSlot(hand)) {
            case MAINHAND: return EnumHand.MAIN_HAND;
            case OFFHAND: return EnumHand.OFF_HAND;
            default: return null;
        }
    }

    public static IEntityEquipmentSlot getIEntityEquipmentSlot(EnumHand hand) {
        if (hand == null) return null;
        switch (hand) {
            case MAIN_HAND: return ExpandEntityEquipmentSlot.mainHand();
            case OFF_HAND: return ExpandEntityEquipmentSlot.offhand();
            default: return null;
        }
    }

    public static EnumFacing getFacing(IFacing facing) {
        return facing == null ? null : (EnumFacing) facing.getInternal();
    }

    public static Item getItem(IItemDefinition itemDefinition) {
        return itemDefinition == null ? null : (Item) itemDefinition.getInternal();
    }

    public static BiomeDictionary.Type getBiomeType(IBiomeType biomeType) {
        return biomeType == null ? null : (BiomeDictionary.Type) biomeType.getInternal();
    }

    public static IStyle getIStyle(Style style) {
        return style == null ? null : new MCStyle(style);
    }

    public static Style getStyle(IStyle style) {
        return style == null ? null : (Style) style.getInternal();
    }

    public static ITextComponent getITextComponent(net.minecraft.util.text.ITextComponent textComponent) {
        return textComponent == null ? null : new MCTextComponent(textComponent);
    }

    public static net.minecraft.util.text.ITextComponent getITextComponent(ITextComponent textComponent) {
        return textComponent == null ? null : (net.minecraft.util.text.ITextComponent) textComponent.getInternal();
    }

    public static IBlockSource getBlockSource(net.minecraft.dispenser.IBlockSource blockSource) {
        return blockSource == null ? null : new MCBlockSource(blockSource);
    }

    public static net.minecraft.dispenser.IBlockSource getBlockSource(IBlockSource blockSource) {
        return blockSource == null ? null : ((net.minecraft.dispenser.IBlockSource) blockSource.getInternal());
    }
}
