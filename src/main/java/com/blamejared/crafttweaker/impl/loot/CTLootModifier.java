package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.api.loot.ILootModifier;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class CTLootModifier extends LootModifier {
    private final Predicate<MCLootContext> conditions;
    private final ILootModifier function;

    public CTLootModifier(final List<ILootCondition> conditions, final ILootModifier function) {
        super(new net.minecraft.loot.conditions.ILootCondition[0]);
        this.conditions = context -> conditions.stream().allMatch(it -> it.test(context));
        this.function = function;
    }

    public CTLootModifier(final ILootCondition[] conditions, final ILootModifier function) {
        this(conditions == null? new ArrayList<>() : Arrays.asList(conditions), function);
    }

    @Override
    @Nonnull
    public final List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        final List<IItemStack> wrappedLoot = CraftTweakerHelper.getIItemStacks(generatedLoot);
        final MCLootContext wrappedContext = new MCLootContext(context);

        if (!this.conditions.test(wrappedContext)) return generatedLoot;
        return CraftTweakerHelper.getItemStacks(this.function.applyModifier(wrappedLoot, wrappedContext));
    }
}
