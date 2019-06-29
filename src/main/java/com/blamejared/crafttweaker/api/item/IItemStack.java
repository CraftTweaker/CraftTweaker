package com.blamejared.crafttweaker.api.item;


import com.blamejared.crafttweaker.api.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IItemStack")
public interface IItemStack extends IIngredient {
    
    /**
     * Returns if the ItemStack is empty
     *
     * @return true if empty, false if not
     */
    @ZenCodeType.Getter("empty")
    boolean isEmpty();
    
    /**
     * Gets the display name of the ItemStack
     *
     * @return formatted display name of the ItemStack
     */
    @ZenCodeType.Getter("displayName")
    String getDisplayName();
    
    
    /**
     * Sets the display name of the ItemStack
     *
     * @param name New name of the stack.
     */
    @ZenCodeType.Method
    IItemStack setDisplayName(String name);
    
    /**
     * Gets the amount of Items in the ItemStack
     *
     * @return ItemStackz` amount
     */
    @ZenCodeType.Getter("amount")
    int getAmount();
    
    /**
     * Sets the amount of the ItemStack
     *
     * @param amount new amount
     * @return
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    IItemStack setAmount(int amount);
    
    /**
     * Returns if the ItemStack can have an amount greater than 1
     * I.E Swords and tools are not stackable, sticks are.
     *
     * @return true if this ItemStack can contain more than one item.
     */
    @ZenCodeType.Getter("stackable")
    boolean isStackable();
    
    /**
     * Sets the damage of the ItemStack
     * @param damage the new damage value
     */
    @ZenCodeType.Method
    IItemStack withDamage(int damage);
    
    /**
     * Returns if the ItemStack is damageable
     * I.E Swords and tools are damageable, sticks are not.
     *
     * @return true if this ItemStack can take damage.
     */
    @ZenCodeType.Getter("damageable")
    boolean isDamageable();
    
    /**
     * Returns if the ItemStack is damaged
     * I.E a Swords that is no at full durability is damaged.
     *
     * @return true if this ItemStack is damaged.
     */
    @ZenCodeType.Getter("damaged")
    boolean isDamaged();
    
    /**
     * Returns the max damage of the ItemStack
     * This is the max durability of the ItemStack.
     *
     * @return The ItemStack's max durability.
     */
    @ZenCodeType.Getter("maxDamage")
    int getMaxDamage();
    
    /**
     * Returns the unlocalized Name of the Item in the ItemStack
     *
     * @return the unlocalized name.
     */
    @ZenCodeType.Getter("translationKey")
    String getTranslationKey();
    
    
    /**
     * Gets the internal {@link ItemStack} for this IItemStack.
     *
     * @return internal ItemStack
     */
    ItemStack getInternal();
    
}
