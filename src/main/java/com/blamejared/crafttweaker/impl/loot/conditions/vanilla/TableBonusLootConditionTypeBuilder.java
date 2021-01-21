package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.TableBonus")
@Document("vanilla/api/loot/conditions/vanilla/TableBonus")
public final class TableBonusLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private Enchantment enchantment;
    private float[] chances;

    TableBonusLootConditionTypeBuilder() {}

    @ZenCodeType.Method
    public TableBonusLootConditionTypeBuilder withEnchantment(final Enchantment enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    @ZenCodeType.Method
    public TableBonusLootConditionTypeBuilder withChances(final float[] chances) {
        this.chances = chances;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.enchantment == null) {
            throw new IllegalStateException("Enchantment not set");
        }
        if (this.chances.length == 0) {
            throw new IllegalStateException("Unable to have an empty set of chances for a table bonus");
        }

        boolean foundLower = false;
        boolean foundUpper = false;

        for (float chance : this.chances) {
            if (chance <= 0.0F && !foundLower) {
                CraftTweakerAPI.logWarning(
                        "A chance value in a 'TableBonus' condition is a number that is equal to or lower than 0.0: this will never pass! The full array will be printed later"
                );
                foundLower = true;
            }
            if (chance >= 1.0F && !foundUpper) {
                CraftTweakerAPI.logWarning(
                        "A chance value in a 'TableBonus' condition is a number that is equal to or higher than 1.0: this will always pass! The full array will be printed later"
                );
                foundUpper = true;
            }
        }

        if (foundLower || foundUpper) {
            CraftTweakerAPI.logWarning("{}", Arrays.toString(this.chances));
        }

        return context -> {
            final ItemStack stack = ExpandLootContext.getTool(context).getInternal();
            final int chancesIndex = stack == null? 0 : EnchantmentHelper.getEnchantmentLevel(this.enchantment, stack);
            final float chance = this.chances[Math.min(chancesIndex, this.chances.length - 1)];
            return context.getRandom().nextFloat() < chance;
        };
    }
}
