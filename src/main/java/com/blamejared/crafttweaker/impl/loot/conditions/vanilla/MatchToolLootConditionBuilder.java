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

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.MatchTool")
@Document("vanilla/api/loot/conditions/vanilla/MatchTool")
public final class MatchToolLootConditionBuilder implements ILootConditionTypeBuilder {
    private ItemPredicate predicate;

    MatchToolLootConditionBuilder() {
        this.predicate = new ItemPredicate();
    }

    @ZenCodeType.Method
    public MatchToolLootConditionBuilder withPredicate(final Consumer<ItemPredicate> builder) {
        final ItemPredicate predicate = new ItemPredicate();
        builder.accept(predicate);
        this.predicate = predicate;
        return this;
    }

    // Quick CraftTweaker helpers
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool) {
        return this.matching(tool, false);
    }
    
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool, final boolean matchDamage) {
        return this.matching(tool, matchDamage, false);
    }
    
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool, final boolean matchDamage, final boolean matchCount) {
        return this.matching(tool, matchDamage, matchCount, false);
    }
    
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
