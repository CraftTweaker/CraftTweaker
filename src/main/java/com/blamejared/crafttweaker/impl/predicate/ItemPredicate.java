package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.ItemPredicate")
@Document("vanilla/api/predicate/ItemPredicate")
public final class ItemPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.ItemPredicate> {
    private final List<EnchantmentPredicate> enchantments;
    private final List<EnchantmentPredicate> storedEnchantments;

    private IntRangePredicate amount;
    private IntRangePredicate damage;
    private NBTPredicate data;
    private Item item;
    private MCTag<Item> tag;
    private Potion potion;

    public ItemPredicate() {
        super(net.minecraft.advancements.criterion.ItemPredicate.ANY);
        this.amount = IntRangePredicate.unbounded();
        this.damage = IntRangePredicate.unbounded();
        this.data = new NBTPredicate();
        this.enchantments = new ArrayList<>();
        this.storedEnchantments = new ArrayList<>();
    }

    @ZenCodeType.Method
    public ItemPredicate withMinimumAmount(final int min) {
        this.amount = IntRangePredicate.mergeLowerBound(this.amount, min);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withMaximumAmount(final int max) {
        this.amount = IntRangePredicate.mergeUpperBound(this.amount, max);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withRangedAmount(final int min, final int max) {
        this.amount = IntRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withExactAmount(final int value) {
        return this.withRangedAmount(value, value);
    }

    @ZenCodeType.Method
    public ItemPredicate withMinimumDamage(final int min) {
        this.damage = IntRangePredicate.mergeLowerBound(this.damage, min);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withMaximumDamage(final int max) {
        this.damage = IntRangePredicate.mergeUpperBound(this.damage, max);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withRangedDamage(final int min, final int max) {
        this.damage = IntRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withExactDamage(final int value) {
        return this.withRangedDamage(value, value);
    }

    @ZenCodeType.Method
    public ItemPredicate withDataPredicate(final Consumer<NBTPredicate> builder) {
        final NBTPredicate predicate = new NBTPredicate();
        builder.accept(predicate);
        this.data = predicate;
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
    public ItemPredicate withEnchantmentPredicate(final Consumer<EnchantmentPredicate> builder) {
        final EnchantmentPredicate predicate = new EnchantmentPredicate();
        builder.accept(predicate);
        this.enchantments.add(predicate);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withEnchantmentPredicate(final Enchantment enchantment, final Consumer<EnchantmentPredicate> builder) {
        return this.withEnchantmentPredicate(predicate -> {
            predicate.withEnchantment(enchantment);
            builder.accept(predicate);
        });
    }

    @ZenCodeType.Method
    public ItemPredicate withStoredEnchantmentPredicate(final Consumer<EnchantmentPredicate> builder) {
        final EnchantmentPredicate predicate = new EnchantmentPredicate();
        builder.accept(predicate);
        this.storedEnchantments.add(predicate);
        return this;
    }

    @ZenCodeType.Method
    public ItemPredicate withStoredEnchantmentPredicate(final Enchantment enchantment, final Consumer<EnchantmentPredicate> builder) {
        return this.withStoredEnchantmentPredicate(predicate -> {
            predicate.withEnchantment(enchantment);
            builder.accept(predicate);
        });
    }

    // Quick CraftTweaker helpers
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack) {
        return this.matching(stack, false);
    }
    
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack, final boolean matchDamage) {
        return this.matching(stack, matchDamage, false);
    }
    
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack, final boolean matchDamage, final boolean matchCount) {
        return this.matching(stack, matchDamage, matchCount, false);
    }
    
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack, final boolean matchDamage, final boolean matchCount, final boolean matchNbt) {
        this.withItem(stack);
        if (matchDamage) {
            this.withExactDamage(stack.getDamage());
        }
        if (matchCount) {
            this.withExactAmount(stack.getAmount());
        }
        if (matchNbt) {
            final IData tag = stack.getTag();
            if (tag != null) this.withDataPredicate(predicate -> predicate.withData(tag));
        }
        return this;
    }

    @Override
    public boolean isAny() {
        return this.enchantments.isEmpty() && this.storedEnchantments.isEmpty() && this.amount.isAny() && this.damage.isAny()
                && this.data.isAny() && this.item == null && this.tag == null && this.potion == null;
    }

    @Override
    public net.minecraft.advancements.criterion.ItemPredicate toVanilla() {
        if (this.tag != null && this.item != null) {
            CraftTweakerAPI.logWarning("'ItemPredicate' specifies both an item and a tag: the second will take precedence");
        }
        return new net.minecraft.advancements.criterion.ItemPredicate(
                this.tag != null? CraftTweakerHelper.getITag(this.tag) : null,
                this.item,
                this.amount.toVanillaPredicate(),
                this.damage.toVanillaPredicate(),
                this.toVanilla(this.enchantments),
                this.toVanilla(this.storedEnchantments),
                this.potion,
                this.data.toVanillaPredicate()
        );
    }

    private net.minecraft.advancements.criterion.EnchantmentPredicate[] toVanilla(final List<EnchantmentPredicate> list) {
        return list.stream().map(AnyDefaulting::toVanillaPredicate).toArray(net.minecraft.advancements.criterion.EnchantmentPredicate[]::new);
    }
}
