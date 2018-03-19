package crafttweaker.api.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * Contains an item definition. Item definitions provide general information
 * about an item, such as its name and ID.
 * <p>
 * Item definitions themselves have no localized name, as it could be different
 * for each subitem or even depend on NBT tags.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.item.IItemDefinition")
@ZenRegister
public interface IItemDefinition {
    
    /**
     * Gets the item ID.
     *
     * @return item ID
     */
    @ZenGetter("id")
    String getId();
    
    /**
     * Gets the unlocalized item name.
     *
     * @return item name
     */
    @ZenGetter("name")
    String getName();
    
    /**
     * Gets the owner of the item.
     *
     * @return modid of the mod that adds the item
     */
    @ZenGetter("owner")
    String getOwner();
    
    /**
     * Makes an item stack from this definition.
     *
     * @param meta meta value
     *
     * @return item stack
     */
    @ZenMethod
    IItemStack makeStack(@Optional int meta);
    
    /**
     * Returns all ore entries containing this item. Also contains ore entries
     * that refer to a specific sub-item.
     *
     * @return all the ore entries containing this item.
     */
    @ZenGetter("ores")
    List<IOreDictEntry> getOres();
    
    @ZenGetter("defaultInstance")
    IItemStack getDefaultInstance();
    
    @ZenMethod
    void setHarvestLevel(String type, int level);
    
    @ZenGetter("creativeTab")
    ICreativeTab getCreativeTab();
    
    @ZenSetter("creativeTab")
    void setCreativeTab(ICreativeTab tab);
    
    @ZenGetter("creativeTabs")
    ICreativeTab[] getCreativeTabs();
    
    @ZenMethod
    void setNoRepair();
    
    @ZenGetter("canItemEditBlocks")
    boolean canItemEditBlocks();
    
    @ZenGetter("itemEnchantability")
    int getItemEnchantability();
    
    @ZenMethod
    void setContainerItem(IItemDefinition item);
    
    @ZenGetter("subItems")
    List<IItemStack> getSubItems();
    
    @ZenMethod
    List<IItemStack> getSubItems(ICreativeTab tab);
    
    @ZenMethod
    int getItemBurnTime(IItemStack itemStack);
    
    Object getInternal();
}
