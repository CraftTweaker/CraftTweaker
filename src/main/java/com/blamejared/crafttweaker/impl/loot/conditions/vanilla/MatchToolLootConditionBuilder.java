package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.ItemPredicate;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Builder for the 'MatchTool' loot condition.
 *
 * This condition checks the tool that the {@link net.minecraft.loot.LootContext} reports as having been used to break a
 * block or perform any other action, leveraging the provided {@link ItemPredicate}.
 *
 * The condition then passes if and only if the predicate used to build the condition reports the tool as valid.
 *
 * If no predicate is specified, then the condition simply acts as a check of presence for the tool.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.MatchTool")
@Document("vanilla/api/loot/conditions/vanilla/MatchTool")
public final class MatchToolLootConditionBuilder implements ILootConditionTypeBuilder {
    private ItemPredicate predicate;

    MatchToolLootConditionBuilder() {
        this.predicate = new ItemPredicate();
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be matched against the tool.
     *
     * Any changes that have already been made to the predicate will be overwritten, effectively replacing the previous
     * predicate, if any.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder withPredicate(final Consumer<ItemPredicate> builder) {
        final ItemPredicate predicate = new ItemPredicate();
        builder.accept(predicate);
        this.predicate = predicate;
        return this;
    }

    // Quick CraftTweaker helpers
    /**
     * Sets the tool that should be matched by this loot condition.
     *
     * The check will ignore any damage, amount, and NBT data that may be attached to the tool.
     *
     * This acts as a helper method that allows a more streamlined configuration of the condition instead of having to
     * manually build the predicate.
     *
     * If a tool has already been specified, then the newly given one will overwrite the previous check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param tool The tool that should be matched.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool) {
        return this.matching(tool, false);
    }
    
    /**
     * Sets the tool that should be matched by this loot condition, optionally ignoring damage.
     *
     * The check will ignore any amount and NBT data that may be attached to the tool.
     *
     * This acts as a helper method that allows a more streamlined configuration of the condition instead of having to
     * manually build the predicate.
     *
     * If a tool has already been specified, then the newly given one will overwrite the previous check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param tool The tool that should be matched.
     * @param matchDamage Whether damage should be taken into account when matching.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool, final boolean matchDamage) {
        return this.matching(tool, matchDamage, false);
    }
    
    /**
     * Sets the tool that should be matched by this loot condition, optionally ignoring damage or count.
     *
     * The check will ignore any NBT data that may be attached to the tool.
     *
     * This acts as a helper method that allows a more streamlined configuration of the condition instead of having to
     * manually build the predicate.
     *
     * If a tool has already been specified, then the newly given one will overwrite the previous check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param tool The tool that should be matched
     * @param matchDamage Whether damage should be taken into account when matching.
     * @param matchCount Whether the amount should be taken into account when matching.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool, final boolean matchDamage, final boolean matchCount) {
        return this.matching(tool, matchDamage, matchCount, false);
    }
    
    /**
     * Sets the tool that should be matched by this loot condition, optionally ignoring damage, count, or NBT data.
     *
     * This acts as a helper method that allows a more streamlined configuration of the condition instead of having to
     * manually build the predicate.
     *
     * If a tool has already been specified, then the newly given one will overwrite the previous check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param tool The tool that should be matched
     * @param matchDamage Whether damage should be taken into account when matching.
     * @param matchCount Whether the amount should be taken into account when matching.
     * @param matchNbt Whether the NBT data should be taken into account when matching.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool, final boolean matchDamage, final boolean matchCount, final boolean matchNbt) {
        return this.withPredicate(predicate -> predicate.matching(tool, matchDamage, matchCount, matchNbt));
    }

    @Override
    public ILootCondition finish() {
        final net.minecraft.advancements.criterion.ItemPredicate vanilla = this.predicate.toVanillaPredicate();
        return context -> {
            final IItemStack stack = ExpandLootContext.getTool(context);
            return stack != null && stack.getInternal() != null && vanilla.test(stack.getInternal());
        };
    }
}
