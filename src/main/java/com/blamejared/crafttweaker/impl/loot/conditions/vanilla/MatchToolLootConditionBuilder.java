package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.ItemPredicate;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.MatchTool")
@Document("vanilla/api/loot/conditions/MatchTool")
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

    // quick CraftTweaker helper
    // TODO("Booleans to exclude certain things like count or damage")
    @ZenCodeType.Method
    public MatchToolLootConditionBuilder matching(final IItemStack tool) {
        return this.withPredicate(predicate -> predicate.matching(tool));
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
