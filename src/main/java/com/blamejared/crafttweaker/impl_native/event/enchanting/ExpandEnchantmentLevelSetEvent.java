package com.blamejared.crafttweaker.impl_native.event.enchanting;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fired when the enchantment level is set for each of the three potential enchantments in the enchanting table.
 * The level is set to the vanilla value and can be modified by this event handler.
 *
 * The enchantRow is used to determine which enchantment level is being set, 1, 2, or 3. The power is a number
 * from 0-15 and indicates how many bookshelves surround the enchanting table. The itemStack representing the item being
 * enchanted is also available.
 */
@ZenRegister
@Document("vanilla/api/event/enchanting/MCEnchantmentLevelSetEvent")
@NativeTypeRegistration(value = EnchantmentLevelSetEvent.class, zenCodeName = "crafttweaker.api.event.enchanting.MCEnchantmentLevelSetEvent")
public class ExpandEnchantmentLevelSetEvent {
    
    /**
     * Gets the World where the Enchanting Table is.
     *
     * @return The World where the event is fired in.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("world")
    public static World getWorld(EnchantmentLevelSetEvent internal) {
        
        return internal.getWorld();
    }
    
    /**
     * Gets the position of the Enchanting Table firing this event.
     *
     * @return The positiong of the Enchanting Table.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(EnchantmentLevelSetEvent internal) {
        
        return internal.getPos();
    }
    
    /**
     * Gets the row in the Enchanting Table that this event is fired for.
     *
     * @return The row that this event is fired for.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantRow")
    public static int getEnchantRow(EnchantmentLevelSetEvent internal) {
        
        return internal.getEnchantRow();
    }
    
    /**
     * Gets the Enchanting Power of the Enchanting Table.
     *
     * @return The Enchanting Power of the Enchanting Table.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("power")
    public static int getPower(EnchantmentLevelSetEvent internal) {
        
        return internal.getPower();
    }
    
    /**
     * Gets the ItemStack that is being enchanted.
     *
     * @return The ItemStack that is being enchanted.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("item")
    public static IItemStack getItem(EnchantmentLevelSetEvent internal) {
        
        return new MCItemStack(internal.getItem());
    }
    
    /**
     * Gets the original level of the enchantment for this row.
     *
     * @return The original level of the enchantment for this row.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("originalLevel")
    public static int getOriginalLevel(EnchantmentLevelSetEvent internal) {
        
        return internal.getOriginalLevel();
    }
    
    /**
     * Gets the current level of the enchantment for this row.
     *
     * This value can be changed by other mods.
     *
     * @return The current level of the enchantment for this row.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    public static int getLevel(EnchantmentLevelSetEvent internal) {
        
        return internal.getLevel();
    }
    
    /**
     * Sets the level of the enchantment for this row.
     *
     * Setting this to `0` will make this row not have an enchantment.
     *
     * @param level The new enchantment level.
     *
     * @docParam level 5
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("level")
    public static void setLevel(EnchantmentLevelSetEvent internal, int level) {
        
        internal.setLevel(level);
    }
    
}
