package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/item/CreativeModeTab")
@NativeTypeRegistration(value = CreativeModeTab.class, zenCodeName = "crafttweaker.api.item.CreativeModeTab")
public class ExpandCreativeModeTab {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static int getId(CreativeModeTab internal) {
        
        return internal.getId();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeFolderName")
    public static String getRecipeFolderName(CreativeModeTab internal) {
        
        return internal.getRecipeFolderName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(CreativeModeTab internal) {
        
        return internal.getDisplayName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("iconItem")
    public static IItemStack getIconItem(CreativeModeTab internal) {
        
        return IItemStack.of(internal.getIconItem());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("backgroundSuffic")
    public static String getBackgroundSuffix(CreativeModeTab internal) {
        
        return internal.getBackgroundSuffix();
    }
    
    @ZenCodeType.Method
    public static CreativeModeTab setBackgroundSuffix(CreativeModeTab internal, String prefix) {
        
        return internal.setBackgroundSuffix(prefix);
    }
    
    @ZenCodeType.Method
    public static CreativeModeTab setRecipeFolderName(CreativeModeTab internal, String recipeFolderName) {
        
        return internal.setRecipeFolderName(recipeFolderName);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("showTitle")
    public static boolean showTitle(CreativeModeTab internal) {
        
        return internal.showTitle();
    }
    
    @ZenCodeType.Method
    public static CreativeModeTab hideTitle(CreativeModeTab internal) {
        
        return internal.hideTitle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canScroll")
    public static boolean canScroll(CreativeModeTab internal) {
        
        return internal.canScroll();
    }
    
    @ZenCodeType.Method
    public static CreativeModeTab hideScroll(CreativeModeTab internal) {
        
        return internal.hideScroll();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("column")
    public static int getColumn(CreativeModeTab internal) {
        
        return internal.getColumn();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isTopRow")
    public static boolean isTopRow(CreativeModeTab internal) {
        
        return internal.isTopRow();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isAlignedRight")
    public static boolean isAlignedRight(CreativeModeTab internal) {
        
        return internal.isAlignedRight();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantmentCategories")
    public static EnchantmentCategory[] getEnchantmentCategories(CreativeModeTab internal) {
        
        return internal.getEnchantmentCategories();
    }
    
    @ZenCodeType.Method
    public static CreativeModeTab setEnchantmentCategories(CreativeModeTab internal, EnchantmentCategory... categories) {
        
        return internal.setEnchantmentCategories(categories);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    public static boolean hasEnchantmentCategory(CreativeModeTab internal, @ZenCodeType.Nullable EnchantmentCategory category) {
        
        return internal.hasEnchantmentCategory(category);
    }
    
    @ZenCodeType.Method
    public static List<IItemStack> fillItemList(CreativeModeTab internal) {
        
        NonNullList<ItemStack> stacks = NonNullList.create();
        internal.fillItemList(stacks);
        return stacks.stream().map(IItemStack::of).toList();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(CreativeModeTab internal) {
        
        return "<itemgroup:" + getRecipeFolderName(internal) + ">";
    }
    
}
