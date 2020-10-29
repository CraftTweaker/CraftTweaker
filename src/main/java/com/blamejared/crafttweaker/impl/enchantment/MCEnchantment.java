package com.blamejared.crafttweaker.impl.enchantment;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.util.MCDamageSource;
import com.blamejared.crafttweaker.impl.util.MCResourceLocation;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.enchantment.MCEnchantment")
@Document("vanilla/api/enchantment/MCEnchantment")
@ZenWrapper(wrappedClass = "net.minecraft.enchantment.Enchantment", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCEnchantment implements CommandStringDisplayable {
    private final Enchantment internal;

    public MCEnchantment(Enchantment internal){
        this.internal = internal;
    }

    public Enchantment getInternal() {
        return this.internal;
    }

    @ZenCodeType.Method
    public MCResourceLocation getRegistryName() {
        return new MCResourceLocation(internal.getRegistryName());
    }


    @ZenCodeType.Method
    public boolean isCurse() {
        return internal.isCurse();
    }


    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     */
    @ZenCodeType.Method
    public boolean canApply(IItemStack stack) {
        return internal.canApply((stack).getInternal());
    }


    @ZenCodeType.Method
    public int getMaxEnchantability(int enchantmentLevel) {
        return internal.getMaxEnchantability(enchantmentLevel);
    }


    @ZenCodeType.Method
    public boolean canApplyAtEnchantingTable(IItemStack stack) {
        return internal.canApplyAtEnchantingTable((stack).getInternal());
    }


    @ZenCodeType.Method
    public boolean isCompatibleWith(MCEnchantment enchantmentIn) {
        return internal.isCompatibleWith((enchantmentIn).getInternal());
    }


    @ZenCodeType.Method
    public MCTextComponent getDisplayName(int level) {
        return new MCTextComponent(internal.getDisplayName(level));
    }


    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    @ZenCodeType.Method
    public int getMinEnchantability(int enchantmentLevel) {
        return internal.getMinEnchantability(enchantmentLevel);
    }


    /**
     * Returns the minimum level that the enchantment can have.
     */
    @ZenCodeType.Method
    public int getMinLevel() {
        return internal.getMinLevel();
    }


    @ZenCodeType.Method
    public boolean isTreasureEnchantment() {
        return internal.isTreasureEnchantment();
    }


    /**
     * Returns the maximum level that the enchantment can have.
     */
    @ZenCodeType.Method
    public int getMaxLevel() {
        return internal.getMaxLevel();
    }


    /**
     * Calculates the damage protection of the enchantment based on level and damage source passed.
     */
    @ZenCodeType.Method
    public int calcModifierDamage(int level, MCDamageSource source) {
        return internal.calcModifierDamage(level, (source).getInternal());
    }


    /**
     * Is this enchantment allowed to be enchanted on books via Enchantment Table
     * @return false to disable the vanilla feature
     */
    @ZenCodeType.Method
    public boolean isAllowedOnBooks() {
        return internal.isAllowedOnBooks();
    }


    /**
     * Return the name of key in translation table of this enchantment.
     */
    @ZenCodeType.Method
    public String getName() {
        return (internal.getName());
    }


    /**
     * Checks if the enchantment can be sold by villagers in their trades.
     */
    @ZenCodeType.Method
    public boolean canVillagerTrade() {
        return internal.canVillagerTrade();
    }


    /**
     * Checks if the enchantment can be applied to loot table drops.
     */
    @ZenCodeType.Method
    public boolean canGenerateInLoot() {
        return internal.canGenerateInLoot();
    }

    @Override
    public String getCommandString() {
        return "<enchantment:" + this.getRegistryName().getInternal().toString() + ">";
    }
}
