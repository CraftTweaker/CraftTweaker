package com.blamejared.crafttweaker.api.item;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IItemStack")
public interface IItemStack extends IIngredient {
    
    /**
     * Returns if the ItemStack is empty
     *
     * @return true if empty, false if not
     */
    @ZenCodeType.Getter("empty")
    default boolean isEmpty() {
        return getInternal().isEmpty();
    }
    
    /**
     * Gets the display name of the ItemStack
     *
     * @return formatted display name of the ItemStack
     */
    @ZenCodeType.Getter("displayName")
    default String getDisplayName() {
        return getInternal().getDisplayName().getFormattedText();
    }
    
    
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
    default int getAmount() {
        return getInternal().getCount();
    }
    
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
    default boolean isStackable() {
        return getInternal().isStackable();
    }
    
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
    default boolean isDamageable() {
        return getInternal().isDamageable();
    }
    
    /**
     * Returns if the ItemStack is damaged
     * I.E a Swords that is no at full durability is damaged.
     *
     * @return true if this ItemStack is damaged.
     */
    @ZenCodeType.Getter("damaged")
    default boolean isDamaged() {
        return getInternal().isDamaged();
    }
    
    /**
     * Returns the max damage of the ItemStack
     * This is the max durability of the ItemStack.
     *
     * @return The ItemStack's max durability.
     */
    @ZenCodeType.Getter("maxDamage")
    default int getMaxDamage() {
        return getInternal().getMaxDamage();
    }
    
    /**
     * Returns the unlocalized Name of the Item in the ItemStack
     *
     * @return the unlocalized name.
     */
    @ZenCodeType.Getter("translationKey")
    default String getTranslationKey() {
        return getInternal().getTranslationKey();
    }
    
    
    @ZenCodeType.Method
    IItemStack withTag(MapData tag);
    
    @Override
    default boolean matches(IItemStack stack) {
        ItemStack stack1 = getInternal();
        ItemStack stack2 = stack.getInternal();
        
        if(stack1.isEmpty() != stack2.isEmpty()) {
            return false;
        }
        if(stack1.getItem() != stack2.getItem()) {
            return false;
        }
        if(stack1.getCount() > stack2.getCount()) {
            return false;
        }
        if(stack1.getDamage() != stack2.getDamage()) {
            return false;
        }
        CompoundNBT stack1Tag = stack1.getTag();
        CompoundNBT stack2Tag = stack2.getTag();
        if(stack1.hasTag() != stack2.hasTag()) {
            return false;
        }
        if(stack1Tag == null && stack2Tag == null) {
            return true;
        }
        
        // Lets just use the partial nbt
        if(!NBTConverter.convert(stack2Tag).contains(NBTConverter.convert(stack1Tag))) {
            return false;
        }
        
        return true;
    }
    
    
    /**
     * Gets the internal {@link ItemStack} for this IItemStack.
     *
     * @return internal ItemStack
     */
    ItemStack getInternal();
    
    
}
