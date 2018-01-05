package crafttweaker.api.enchantments;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.enchantments.IEnchantmentDefinition")
@ZenRegister
public interface IEnchantmentDefinition {
    
    @ZenGetter("id")
    int getID();
    
    @ZenGetter("name")
    String getName();
    
    @ZenSetter("name")
    void setName(String name);
    
    @ZenMethod
    boolean canApply(IItemStack itemStack);
    
    @ZenMethod
    boolean canApplyAtEnchantmentTable(IItemStack itemStack);
    
    @ZenGetter("maxLevel")
    int getMaxLevel();
    
    @ZenGetter("minLevel")
    int getMinLevel();
    
    @ZenMethod
    int getMaxEnchantability(int enchantmentLevel);
    
    @ZenMethod
    int getMinEnchantability(int enchantmentLevel);
    
    @ZenMethod
    String getTranslatedName(int enchantmentLevel);
    
    @ZenGetter
    boolean isAllowedOnBooks();
    
    @ZenMethod
    boolean isCompatibleWith(IEnchantmentDefinition other);
    
    @ZenGetter
    boolean isCurse();
    
    @ZenGetter
    boolean isTreasureEnchantment();
    
    @ZenOperator(OperatorType.MUL)
    @ZenMethod
    IEnchantment makeEnchantment(int level);
    
    Object getInternal();
}
