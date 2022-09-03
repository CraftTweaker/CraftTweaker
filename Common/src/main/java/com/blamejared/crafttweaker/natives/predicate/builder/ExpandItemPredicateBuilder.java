package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@Document("vanilla/api/predicate/builder/ItemPredicateBuilder")
@NativeTypeRegistration(value = ItemPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.ItemPredicateBuilder")
public final class ExpandItemPredicateBuilder {
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder items(final ItemPredicate.Builder internal, final Item... items) {
        
        return internal.of(items);
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder items(final ItemPredicate.Builder internal, final IItemStack... items) {
        
        return items(internal, Arrays.stream(items)
                .map(IItemStack::getInternal)
                .map(ItemStack::getItem)
                .toArray(Item[]::new));
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder tag(final ItemPredicate.Builder internal, final KnownTag<Item> tag) {

        return internal.of(tag.getTagKey());
    }

    @ZenCodeType.Method
    public static ItemPredicate.Builder amount(final ItemPredicate.Builder internal, final MinMaxBounds.Ints amount) {
        
        return internal.withCount(amount);
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder durability(final ItemPredicate.Builder internal, final MinMaxBounds.Ints durability) {
        
        return internal.hasDurability(durability);
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder potion(final ItemPredicate.Builder internal, final Potion potion) {
        
        return internal.isPotion(potion);
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder nbt(final ItemPredicate.Builder internal, final MapData nbt) {
        
        return internal.hasNbt(nbt.getInternal());
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder nbt(final ItemPredicate.Builder internal, final IData nbt) {
        
        return nbt(internal, new MapData(nbt.asMap()));
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder enchantedWith(final ItemPredicate.Builder internal, final EnchantmentPredicate predicate) {
        
        return internal.hasEnchantment(predicate);
    }
    
    @ZenCodeType.Method
    public static ItemPredicate.Builder storingEnchantment(final ItemPredicate.Builder internal, final EnchantmentPredicate predicate) {
        
        return internal.hasStoredEnchantment(predicate);
    }
    
    @ZenCodeType.Method
    public static ItemPredicate build(final ItemPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
