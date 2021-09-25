package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An ItemGroup is a group of Items that is shown in the Creative mode inventory.
 *
 * @docShortDescription An ItemGroup is a group of Items that is shown in the Creative mode inventory.
 * @docParam this <itemgroup:misc>
 */
@ZenRegister
@Document("vanilla/api/item/ItemGroup")
@NativeTypeRegistration(value = ItemGroup.class, zenCodeName = "crafttweaker.api.item.ItemGroup")
public class ExpandItemGroup {
    
    /**
     * Gets the path of this ItemGroup.
     * The path is the name of the ItemGroup.
     *
     * @return The path of this ItemGroup.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("path")
    public static String getPath(ItemGroup internal) {
        
        return internal.getPath();
    }
    
    /**
     * Gets the translated ItemGroup name.
     *
     * @return The translated ItemGroup name.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("groupName")
    public static MCTextComponent getGroupName(ItemGroup internal) {
        
        return new MCTextComponent(internal.groupName);
    }
    
    /**
     * Gets the relevant EnchantmentTypes of this ItemGroup.
     * The EnchantmentTypes are used to determine what enchanted books should be put in the tab.
     *
     * @return This ItemGroup's enchantment types.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("relevantEnchantmentTypes")
    public static EnchantmentType[] getRelevantEnchantmentTypes(ItemGroup internal) {
        
        return internal.getRelevantEnchantmentTypes();
    }
    
    /**
     * Checks if this ItemGroup has the given EnchantmentType.
     *
     * @param enchantmentType The EnchantmentType to check.
     *
     * @return True if it contains the EnchantmentType. False otherwise.
     *
     * @docParam enchantmentType EnchantmentType.ARMOR
     */
    @ZenCodeType.Method
    public static boolean hasRelevantEnchantmentType(ItemGroup internal, @ZenCodeType.Nullable EnchantmentType enchantmentType) {
        
        return internal.hasRelevantEnchantmentType(enchantmentType);
    }
    
    /**
     * Fills the given List with this ItemGroup's Items.
     *
     * @param items The list to fill.
     *
     * @docParam items new List<IItemStack>();
     */
    @ZenCodeType.Method
    public static void fill(ItemGroup internal, List<IItemStack> items) {
        
        NonNullList<ItemStack> nnList = NonNullList.create();
        for(Item item : Registry.ITEM) {
            item.fillItemGroup(internal, nnList);
        }
        nnList.stream().map(MCItemStack::new).forEach(items::add);
    }
    
    /**
     * Gets the items in this ItemGroup.
     *
     * @return A list containing all the items in this ItemGroup.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("items")
    public static List<IItemStack> getItems(ItemGroup internal) {
        
        NonNullList<ItemStack> nnList = NonNullList.create();
        for(Item item : Registry.ITEM) {
            item.fillItemGroup(internal, nnList);
        }
        return nnList.stream().map(MCItemStack::new).collect(Collectors.toList());
    }
    
    /**
     * Gets the page in the Creative Menu that this ItemGroup is on.
     *
     * @return The page that this ItemGroup is on.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("page")
    public static int getTabPage(ItemGroup internal) {
        
        return internal.getTabPage();
    }
    
    /**
     * Checks if this ItemGroup has a search bar.
     *
     * @return True if this ItemGroup has a search bar. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("searchBar")
    public static boolean hasSearchBar(ItemGroup internal) {
        
        return internal.hasSearchBar();
    }
    
    /**
     * Gets the width of the searchbar.
     *
     * @return The width of the searchbar.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("searchBarWidth")
    public static int getSearchbarWidth(ItemGroup internal) {
        
        return internal.getSearchbarWidth();
    }
    
    /**
     * Gets the color of the ItemGroup's name.
     *
     * @return The color of the ItemGroup's name.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("labelColor")
    public static int getLabelColor(ItemGroup internal) {
        
        return internal.getLabelColor();
    }
    
    /**
     * Gets the slot color that this ItemGroup uses.
     *
     * @return The slot color that this ItemGroup uses.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("slotColor")
    public static int getSlotColor(ItemGroup internal) {
        
        return internal.getSlotColor();
    }
    
    /**
     * Sets the background image of this ItemGroup.
     *
     * @param texture The new texture of the group
     *
     * @return This ItemGroup with the changed value.
     *
     * @docParam texture "item_search.png"
     * @deprecated Use `setBackgroundImage(ResourceLocation texture)` instead!
     */
    @Deprecated
    public static ItemGroup setBackgroundImageName(ItemGroup internal, String texture) {
        
        return internal.setBackgroundImageName(texture);
    }
    
    /**
     * Sets the background image of this ItemGroup.
     *
     * @param texture The new texture of the group
     *
     * @return This ItemGroup with the changed value.
     *
     * @docParam texture <resource:minecraft:item_search>
     */
    public static ItemGroup setBackgroundImage(ItemGroup internal, ResourceLocation texture) {
        
        return internal.setBackgroundImage(texture);
    }
    
    public static ItemGroup setNoTitle(ItemGroup internal) {
        
        return internal.setNoTitle();
    }
    
    /**
     * Removes the scrollbar from the ItemGroup.
     *
     * @return This ItemGroup with the changed value.
     */
    public static ItemGroup setNoScrollbar(ItemGroup internal) {
        
        return internal.setNoScrollbar();
    }
    
    /**
     * Sets the relevant enchantment types of this ItemGroup.
     * The enchantment types are used to fill the ItemGroup with relevant enchanted books.
     *
     * @return This ItemGroup with the changed value.
     *
     * @docParam types EnchantmentType.ARMOR
     */
    public static ItemGroup setRelevantEnchantmentTypes(ItemGroup internal, EnchantmentType... types) {
        
        return internal.setRelevantEnchantmentTypes(types);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(ItemGroup internal) {
        
        return "<itemgroup:" + getPath(internal) + ">";
    }
    
}
