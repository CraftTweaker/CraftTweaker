package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
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
    private final String name;
    private final Predicate<LootContext> conditions;
    private final ILootModifier function;

    public CTLootModifier(final String name, final List<ILootCondition> conditions, final ILootModifier function) {
        super(new net.minecraft.loot.conditions.ILootCondition[0]);
        this.name = name;
        this.conditions = context -> conditions.stream().allMatch(it -> it.test(context));
        this.function = function;
    }

    public CTLootModifier(final String name, final ILootCondition[] conditions, final ILootModifier function) {
        this(name, conditions == null? new ArrayList<>() : Arrays.asList(conditions), function);
    }

    @Override
    @Nonnull
    public final List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        final List<IItemStack> wrappedLoot = CraftTweakerHelper.getIItemStacks(generatedLoot);

        if (!this.conditions.test(context)) return generatedLoot;
        try {
            return CraftTweakerHelper.getItemStacks(this.function.applyModifier(wrappedLoot, context));
        } catch (final Exception e) {
            CraftTweakerAPI.logThrowing("An error occurred while trying to run loot modifier '%s': %s", e, this.name, e.getMessage());
            return generatedLoot;
        }
    }
}
