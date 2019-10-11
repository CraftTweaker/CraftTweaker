package com.blamejared.crafttweaker.api.item;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IItemStack")
@Document("vanilla/items/IItemStack")
@ZenWrapper(wrappedClass = "net.minecraft.item.ItemStack", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.getCommandString()")
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
     * Returns the max stack size of the Item in the ItemStack
     *
     * @return max stack size
     */
    @ZenCodeType.Getter("maxStackSize")
    default int getMaxStackSize() {
        return getInternal().getItem().getItemStackLimit(getInternal());
    }
    
    
    /**
     * Gets the display name of the ItemStack
     *
     * @return formatted display name of the ItemStack.
     */
    @ZenCodeType.Getter("displayName")
    default String getDisplayName() {
        return getInternal().getDisplayName().getFormattedText();
    }
    
    /**
     * Clears any custom name set for this ItemStack
     */
    @ZenCodeType.Method
    default void clearCustomName() {
        getInternal().clearCustomName();
    }
    
    /**
     * Returns true if the ItemStack has a display name.
     *
     * @return true if a display name is present on the ItemStack.
     */
    @ZenCodeType.Getter("hasDisplayName")
    default boolean hasDisplayName() {
        return getInternal().hasDisplayName();
    }
    
    /**
     * Returns true if this ItemStack has an effect.
     *
     * @return true if there is an effect.
     */
    @ZenCodeType.Getter("hasEffect")
    default boolean hasEffect() {
        return getInternal().hasEffect();
    }
    
    /**
     * Can this ItemStack be enchanted?
     *
     * @return true if this ItemStack can be enchanted.
     */
    @ZenCodeType.Getter("isEnchantable")
    default boolean isEnchantable() {
        return getInternal().isEnchantable();
    }
    
    /**
     * Is this ItemStack enchanted?
     *
     * @return true if this ItemStack is enchanted.
     */
    @ZenCodeType.Getter("isEnchanted")
    default boolean isEnchanted() {
        return getInternal().isEnchanted();
    }
    
    /**
     * Gets the repair cost of the ItemStack, or 0 if no repair is defined.
     *
     * @return ItemStack repair cost or 0 if no repair is set.
     */
    @ZenCodeType.Getter("getRepairCost")
    default int getRepairCost() {
        return getInternal().getRepairCost();
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
     * @return ItemStack's amount
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
    IItemStack withTag(IData tag);

    
    /**
     * Returns true if this ItemStack has a Tag
     *
     * @return true if tag is present.
     */
    @ZenCodeType.Getter("hasTag")
    default boolean hasTag() {
        return getInternal().hasTag();
    }
    
    /**
     * Returns the NBT tag attached to this ItemStack.
     *
     * @return IData of the ItemStack NBT Tag, null if it doesn't exist.
     */
    @ZenCodeType.Getter("tag")
    default IData getTag() {
        return NBTConverter.convert(getInternal().getTag());
    }
    
    /**
     * Returns the NBT tag attached to this ItemStack or makes a new tag.
     *
     * @return IData of the ItemStack NBT Tag, empty tag if it doesn't exist.
     */
    @ZenCodeType.Getter("getOrCreate")
    default IData getOrCreateTag() {
        if(getInternal().getTag() == null) {
            getInternal().setTag(new CompoundNBT());
        }
        return NBTConverter.convert(getInternal().getTag());
    }
    
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
     * Gets the use duration of the ItemStack
     *
     * @return use duration
     */
    @ZenCodeType.Getter("useDuration")
    default int getUseDuration() {
        return getInternal().getUseDuration();
    }
    
    /**
     * Returns true if this stack is considered a crossbow item
     *
     * @return true if stack is a crossbow
     */
    @ZenCodeType.Getter("isCrossbow")
    default boolean isCrossbowStack() {
        return getInternal().isCrossbowStack();
    }
    /**
     * Gets the internal {@link ItemStack} for this IItemStack.
     *
     * @return internal ItemStack
     */
    ItemStack getInternal();

    /**
     * TETATETETEAETGFGDH
     */
    @ZenCodeType.Caster
    default IItemStack asIItemStack() {return this;}
}
