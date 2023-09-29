package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@Document("vanilla/api/predicate/ItemPredicate")
@NativeTypeRegistration(value = ItemPredicate.class, zenCodeName = "crafttweaker.api.predicate.ItemPredicate")
public final class ExpandItemPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static ItemPredicate.Builder create() {
        
        return ItemPredicate.Builder.item();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static ItemPredicate.Builder create(final Item... items) {
        
        return create().of(items);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static ItemPredicate.Builder create(final IItemStack... items) {
        
        return create(Arrays.stream(items).map(IItemStack::getInternal).map(ItemStack::getItem).toArray(Item[]::new));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static ItemPredicate.Builder create(final KnownTag<Item> tag) {

        return create().of(tag.getTagKey());
    }
    
}
