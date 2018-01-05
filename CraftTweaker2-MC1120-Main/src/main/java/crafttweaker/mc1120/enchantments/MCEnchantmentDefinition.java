package crafttweaker.mc1120.enchantments;

import crafttweaker.api.enchantments.*;
import crafttweaker.api.item.IItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class MCEnchantmentDefinition implements IEnchantmentDefinition {
    
    public final Enchantment enchantment;
    
    public MCEnchantmentDefinition(Enchantment enchantment) {
        this.enchantment = enchantment;
    }
    
    public MCEnchantmentDefinition(int id) {
        this(Enchantment.getEnchantmentByID(id));
    }
    
    @Override
    public int getID() {
        return Enchantment.getEnchantmentID(enchantment);
    }
    
    @Override
    public String getName() {
        return enchantment.getName();
    }
    
    @Override
    public void setName(String name) {
        enchantment.setName(name);
    }
    
    @Override
    public boolean canApply(IItemStack itemStack) {
        return enchantment.canApply((ItemStack) itemStack.getInternal());
    }
    
    @Override
    public boolean canApplyAtEnchantmentTable(IItemStack itemStack) {
        return enchantment.canApplyAtEnchantingTable((ItemStack) itemStack.getInternal());
    }
    
    @Override
    public int getMaxLevel() {
        return enchantment.getMaxLevel();
    }
    
    @Override
    public int getMinLevel() {
        return enchantment.getMinLevel();
    }
    
    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return enchantment.getMaxEnchantability(enchantmentLevel);
    }
    
    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantment.getMinEnchantability(enchantmentLevel);
    }
    
    @Override
    public String getTranslatedName(int enchantmentLevel) {
        return enchantment.getTranslatedName(enchantmentLevel);
    }
    
    @Override
    public boolean isAllowedOnBooks() {
        return enchantment.isAllowedOnBooks();
    }
    
    @Override
    public boolean isCompatibleWith(IEnchantmentDefinition other) {
        return enchantment.isCompatibleWith((Enchantment) other.getInternal());
    }
    
    @Override
    public boolean isCurse() {
        return enchantment.isCurse();
    }
    
    @Override
    public boolean isTreasureEnchantment() {
        return enchantment.isTreasureEnchantment();
    }
    
    @Override
    public IEnchantment makeEnchantment(int level) {
        return new MCEnchantment(enchantment, level);
    }
    
    @Override
    public Object getInternal() {
        return enchantment;
    }
}
