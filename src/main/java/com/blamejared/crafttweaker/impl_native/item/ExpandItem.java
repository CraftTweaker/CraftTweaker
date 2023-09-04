package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerItem;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/item/MCItemDefinition")
@NativeTypeRegistration(value = Item.class, zenCodeName = "crafttweaker.api.item.MCItemDefinition")
public class ExpandItem {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultInstance")
    @ZenCodeType.Caster(implicit = true)
    public static IItemStack getDefaultInstance(Item internal) {
        
        return new MCItemStack(internal.getDefaultInstance());
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Item internal) {
        
        return "<item:" + internal.getRegistryName() + ">.definition";
    }
    
    /**
     * Checks if this item is in the given tag.
     *
     * @param tag The tag to check against.
     *
     * @return True if the tag contains this item.
     *
     * @docParam tag <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    public static boolean isIn(Item internal, MCTag<Item> tag) {
        
        return internal.isIn(tag.getInternalRaw());
    }
    
    /**
     * Gets the tags that contain this item.
     *
     * @return a list of tags that contain this item.
     */
    @ZenCodeType.Method
    public static List<MCTag<Item>> getTags(Item internal) {
        
        return TagManagerItem.INSTANCE.getAllTagsFor(internal);
    }
    
}