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

/**
 * Represents the predicate for an {@link Item}.
 *
 * This predicate will match an item against either a specific {@link Item} or item tag ({@link MCTag}), with the second
 * taking precedence over the first. If this initial check succeeds, then the predicate may also verify additional item
 * properties, such as its current amount, its damage, or internal NBT data via {@link NBTPredicate}. The predicate can
 * also check the enchantments that are currently either applied to or stored onto the item via
 * {@link EnchantmentPredicate}s, or the potion effect that is currently present on the item, if any.
 *
 * By default, any item will be able to pass the checks of this predicate.
 */
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
    
    /**
     * Sets the minimum amount of items to <code>min</code>.
     *
     * If the amount had already some bounds specified, then the minimum value of the bound will be overwritten with the
     * value specified in <code>min</code>. On the other hand, if the amount didn't have any bounds set, the minimum is
     * set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the amount should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withMinimumAmount(final int min) {
        
        this.amount = IntRangePredicate.mergeLowerBound(this.amount, min);
        return this;
    }
    
    /**
     * Sets the maximum amount of items to <code>max</code>.
     *
     * If the amount had already some bounds specified, then the maximum value of the bound will be overwritten with the
     * value specified in <code>max</code>. On the other hand, if the amount didn't have any bounds set, the maximum is
     * set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the amount should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withMaximumAmount(final int max) {
        
        this.amount = IntRangePredicate.mergeUpperBound(this.amount, max);
        return this;
    }
    
    /**
     * Sets both the minimum and maximum amount of items to <code>min</code> and <code>max</code> respectively.
     *
     * If the amount had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the amount should be.
     * @param max The maximum value the amount should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withRangedAmount(final int min, final int max) {
        
        this.amount = IntRangePredicate.bounded(min, max);
        return this;
    }
    
    /**
     * Sets the amount to exactly match the given <code>value</code>.
     *
     * If the amount had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param value The exact value the amount should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withExactAmount(final int value) {
        
        return this.withRangedAmount(value, value);
    }
    
    /**
     * Sets the minimum damage of the item to <code>min</code>.
     *
     * If the damage had already some bounds specified, then the minimum value of the bound will be overwritten with the
     * value specified in <code>min</code>. On the other hand, if the damage didn't have any bounds set, the minimum is
     * set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the damage should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withMinimumDamage(final int min) {
        
        this.damage = IntRangePredicate.mergeLowerBound(this.damage, min);
        return this;
    }
    
    /**
     * Sets the maximum damage of the item to <code>max</code>.
     *
     * If the damage had already some bounds specified, then the maximum value of the bound will be overwritten with the
     * value specified in <code>max</code>. On the other hand, if the damage didn't have any bounds set, the maximum is
     * set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the damage should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withMaximumDamage(final int max) {
        
        this.damage = IntRangePredicate.mergeUpperBound(this.damage, max);
        return this;
    }
    
    /**
     * Sets both the minimum and maximum damage of the item to <code>min</code> and <code>max</code> respectively.
     *
     * If the damage had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the damage should be.
     * @param max The maximum value the damage should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withRangedDamage(final int min, final int max) {
        
        this.damage = IntRangePredicate.bounded(min, max);
        return this;
    }
    
    /**
     * Sets the damage to exactly match the given <code>value</code>.
     *
     * If the damage had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param value The exact value the damage should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withExactDamage(final int value) {
        
        return this.withRangedDamage(value, value);
    }
    
    /**
     * Creates and sets the {@link NBTPredicate} that will be matched against the item's NBT data.
     *
     * Any changes that have already been made to the NBT predicate will be overwritten, effectively replacing the
     * previous one, if any.
     *
     * @param builder A consumer that will be used to configure the {@link NBTPredicate}.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withDataPredicate(final Consumer<NBTPredicate> builder) {
        
        final NBTPredicate predicate = new NBTPredicate();
        builder.accept(predicate);
        this.data = predicate;
        return this;
    }
    
    /**
     * Sets the {@link Item} that this predicate should match.
     *
     * If a tag to match against has already been set, then the tag check will take precedence over this check.
     *
     * @param definition The item the predicate should match.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withItem(final Item definition) {
        
        this.item = definition;
        return this;
    }
    
    /**
     * Sets the {@link Item} that this predicate should match to the one contained in the given {@link IItemStack}.
     *
     * If a tag to match against has already been set, then the tag check will take precedence over this check.
     *
     * @param itemStack The stack containing the item that the predicate should match.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withItem(final IItemStack itemStack) {
        
        return this.withItem(itemStack.getDefinition());
    }
    
    /**
     * Sets the {@link MCTag} that this predicate should use for matching.
     *
     * The predicate will successfully match only if the supplied item is contained within the given tag.
     *
     * Specifying both a tag and an item to match against will make the tag take precedence over the item.
     *
     * @param tag The tag the predicate should use for matching.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withTag(final MCTag<Item> tag) {
        
        this.tag = tag;
        return this;
    }
    
    /**
     * Sets the potion effect that should be present on the target item.
     *
     * @param potion The potion effect.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withPotion(final Potion potion) {
        
        this.potion = potion;
        return this;
    }
    
    /**
     * Creates and adds a new {@link EnchantmentPredicate} to the list of predicates to match against the item's
     * enchantments.
     *
     * The added predicate is simply added to the list. No validity checks are performed, meaning that there may be
     * multiple predicates that target a single enchantment. In this case, they all need to match, thus they have to
     * have compatible bounds.
     *
     * @param builder A consumer that will be used to configure the {@link EnchantmentPredicate}.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withEnchantmentPredicate(final Consumer<EnchantmentPredicate> builder) {
        
        final EnchantmentPredicate predicate = new EnchantmentPredicate();
        builder.accept(predicate);
        this.enchantments.add(predicate);
        return this;
    }
    
    /**
     * Creates and adds a new {@link EnchantmentPredicate} for the given {@link Enchantment} to the list of predicates
     * to match against the item's enchantments.
     *
     * The predicate that will be configured is already configured to target the specified enchantment.
     *
     * The added predicate is simply added to the list. No validity checks are performed, meaning that there may be
     * multiple predicates that target a single enchantment. In this case, they all need to match, thus they have to
     * have compatible bounds.
     *
     * @param enchantment The enchantment that should be checked.
     * @param builder     A consumer that will be used to configure the {@link EnchantmentPredicate}.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withEnchantmentPredicate(final Enchantment enchantment, final Consumer<EnchantmentPredicate> builder) {
        
        return this.withEnchantmentPredicate(predicate -> {
            predicate.withEnchantment(enchantment);
            builder.accept(predicate);
        });
    }
    
    /**
     * Creates and adds a new {@link EnchantmentPredicate} to the list of predicates to match against the item's
     * stored enchantments.
     *
     * The added predicate is simply added to the list. No validity checks are performed, meaning that there may be
     * multiple predicates that target a single enchantment. In this case, they all need to match, thus they have to
     * have compatible bounds.
     *
     * @param builder A consumer that will be used to configure the {@link EnchantmentPredicate}.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withStoredEnchantmentPredicate(final Consumer<EnchantmentPredicate> builder) {
        
        final EnchantmentPredicate predicate = new EnchantmentPredicate();
        builder.accept(predicate);
        this.storedEnchantments.add(predicate);
        return this;
    }
    
    /**
     * Creates and adds a new {@link EnchantmentPredicate} for the given {@link Enchantment} to the list of predicates
     * to match against the item's stored enchantments.
     *
     * The predicate that will be configured is already configured to target the specified enchantment.
     *
     * The added predicate is simply added to the list. No validity checks are performed, meaning that there may be
     * multiple predicates that target a single enchantment. In this case, they all need to match, thus they have to
     * have compatible bounds.
     *
     * @param enchantment The enchantment that should be checked.
     * @param builder     A consumer that will be used to configure the {@link EnchantmentPredicate}.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate withStoredEnchantmentPredicate(final Enchantment enchantment, final Consumer<EnchantmentPredicate> builder) {
        
        return this.withStoredEnchantmentPredicate(predicate -> {
            predicate.withEnchantment(enchantment);
            builder.accept(predicate);
        });
    }
    
    // Quick CraftTweaker helpers
    
    /**
     * Sets this predicate to match the given {@link IItemStack} as closely as possible.
     *
     * Additional properties such as damage, count, or NBT data are ignored.
     *
     * @param stack The stack that should be matched.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack) {
        
        return this.matching(stack, false);
    }
    
    /**
     * Sets this predicate to match the given {@link IItemStack} as closely as possible, optionally considering damage.
     *
     * Additional properties such as count or NBT data are ignored.
     *
     * @param stack       The stack that should be matched.
     * @param matchDamage Whether to consider damage or not when matching the stack.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack, final boolean matchDamage) {
        
        return this.matching(stack, matchDamage, false);
    }
    
    /**
     * Sets this predicate to match the given {@link IItemStack} as closely as possible, optionally considering damage
     * and count.
     *
     * Additional properties such as NBT data are ignored.
     *
     * @param stack       The stack that should be matched.
     * @param matchDamage Whether to consider damage or not when matching the stack.
     * @param matchCount  Whether to consider the amount or not when matching the stack.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack, final boolean matchDamage, final boolean matchCount) {
        
        return this.matching(stack, matchDamage, matchCount, false);
    }
    
    /**
     * Sets this predicate to match the given {@link IItemStack} as closely as possible, optionally considering damage,
     * count, and NBT data.
     *
     * @param stack       The stack that should be matched.
     * @param matchDamage Whether to consider damage or not when matching the stack.
     * @param matchCount  Whether to consider the amount or not when matching the stack.
     * @param matchNbt    Whether to consider the NBT data or not when matching the stack.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public ItemPredicate matching(final IItemStack stack, final boolean matchDamage, final boolean matchCount, final boolean matchNbt) {
        
        this.withItem(stack);
        if(matchDamage) {
            this.withExactDamage(stack.getDamage());
        }
        if(matchCount) {
            this.withExactAmount(stack.getAmount());
        }
        if(matchNbt) {
            final IData tag = stack.getTag();
            if(tag != null) {
                this.withDataPredicate(predicate -> predicate.withData(tag));
            }
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
        
        if(this.tag != null && this.item != null) {
            CraftTweakerAPI.logWarning("'ItemPredicate' specifies both an item and a tag: the second will take precedence");
        }
        return new net.minecraft.advancements.criterion.ItemPredicate(
                this.tag != null ? CraftTweakerHelper.getITag(this.tag) : null,
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
        
        return list.stream()
                .map(AnyDefaulting::toVanillaPredicate)
                .toArray(net.minecraft.advancements.criterion.EnchantmentPredicate[]::new);
    }
    
}
