package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.loot.conditions.IntRange;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ITag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.ItemPredicate")
@Document("vanilla/api/loot/conditions/predicate/ItemPredicate")
public final class ItemPredicate {
    private final Map<Enchantment, EnchantmentData> enchantments;
    private final Map<Enchantment, EnchantmentData> storedEnchantments;

    private IntRange amount;
    private IntRange damage;
    private IData data;
    private Item item;
    private MCTag<Item> tag;
    private Potion potion;

    public ItemPredicate() {
        this.enchantments = new HashMap<>();
        this.storedEnchantments = new HashMap<>();
    }

    @ZenCodeType.Method
    public ItemPredicate withAmount(final int min, final int max) {
        this.amount = new IntRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withAmount(final int value) {
        return this.withAmount(value, value);
    }

    @ZenCodeType.Method
    public ItemPredicate withDamage(final int min, final int max) {
        this.damage = new IntRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withDamage(final int value) {
        return this.withDamage(value, value);
    }

    @ZenCodeType.Method
    public ItemPredicate withData(final IData data) {
        this.data = data;
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withItem(final Item definition) {
        this.item = definition;
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withItem(final IItemStack itemStack) {
        return this.withItem(itemStack.getDefinition());
    }

    @ZenCodeType.Method
    public ItemPredicate withTag(final MCTag<Item> tag) {
        this.tag = tag;
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withPotion(final Potion potion) {
        this.potion = potion;
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withEnchantment(final Enchantment enchantment, final Consumer<EnchantmentData> builder) {
        final EnchantmentData data = new EnchantmentData();
        builder.accept(data);
        this.enchantments.put(enchantment, data);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withStoredEnchantment(final Enchantment enchantment, final Consumer<EnchantmentData> builder) {
        final EnchantmentData data = new EnchantmentData();
        builder.accept(data);
        this.storedEnchantments.put(enchantment, data);
        return this;
    }

    // quick CraftTweaker helper
    // TODO("Booleans to exclude certain things like count or damage")
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack) {
        this.withItem(stack);
        this.withDamage(stack.getDamage());
        this.withAmount(stack.getAmount());
        final IData tag = stack.getTag();
        if (tag != null) this.withData(tag);
        return this;
    }

    boolean isAny() {
        return this.enchantments.isEmpty() && this.storedEnchantments.isEmpty() && this.amount == null && this.damage == null
                && this.data == null && this.item == null && this.tag == null && this.potion == null;
    }

    public net.minecraft.advancements.criterion.ItemPredicate toVanilla() {
        if (this.data != null && !(this.data instanceof MapData)) {
            throw new IllegalStateException("Data specified in an 'ItemPredicate' must be a map");
        }
        if (this.isAny()) return net.minecraft.advancements.criterion.ItemPredicate.ANY;
        return new net.minecraft.advancements.criterion.ItemPredicate(
                this.toVanilla(this.tag),
                this.item,
                this.toVanilla(this.amount),
                this.toVanilla(this.damage),
                this.toVanilla(this.enchantments),
                this.toVanilla(this.storedEnchantments),
                this.potion,
                this.toVanilla(this.data)
        );
    }

    @SuppressWarnings("unchecked")
    private <T> ITag<T> toVanilla(final MCTag<T> tag) {
        if (tag == null) return null;
        return (ITag<T>) tag.getInternal();
    }

    private MinMaxBounds.IntBound toVanilla(final IntRange range) {
        return range == null? MinMaxBounds.IntBound.UNBOUNDED : range.toVanillaIntBound();
    }

    private EnchantmentPredicate[] toVanilla(final Map<Enchantment, EnchantmentData> map) {
        return map.entrySet().stream().map(entry -> entry.getValue().toVanilla(entry.getKey())).toArray(EnchantmentPredicate[]::new);
    }

    private NBTPredicate toVanilla(final IData data) {
        if (data == null) return NBTPredicate.ANY;
        return new NBTPredicate(((MapData) data).getInternal()); // Safe otherwise we would have thrown already
    }
}
