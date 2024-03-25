package com.blamejared.crafttweaker.natives.item.type.projectileweapon;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/projectileweapon/CrossbowItem")
@NativeTypeRegistration(value = CrossbowItem.class, zenCodeName = "crafttweaker.api.item.type.projectileweapon.CrossbowItem")
public class ExpandCrossBowItem {
    
    /**
     * Checks if the stack is charged
     *
     * @param crossbowStack the stack to check
     *
     * @return true if charged, false otherwise.
     *
     * @docParam crossbowStack <item:minecraft:crossbow>
     */
    @ZenCodeType.StaticExpansionMethod
    public static boolean isCharged(ItemStack crossbowStack) {
        
        return CrossbowItem.isCharged(crossbowStack);
    }
    
    /**
     * Sets the charged value on the given stack.
     *
     * @param crossbowStack the stack to set the value on
     * @param charged       the charged value
     *
     * @docParam crossbowStack <item:minecraft:crossbow>
     * @docParam charged true
     */
    @ZenCodeType.StaticExpansionMethod
    public static void setCharged(ItemStack crossbowStack, boolean charged) {
        
        CrossbowItem.setCharged(crossbowStack, charged);
    }
    
    /**
     * Checks if the given stack has any charged items of the given type
     *
     * @param crossbowStack The stack to check
     * @param ammoItem      The ammo to check for
     *
     * @return true if it contains the charged item, false otherwise.
     *
     * @docParam crossbowStack <item:minecraft:crossbow>
     * @docParam ammoItem <item:minecraft:arrow>
     */
    @ZenCodeType.StaticExpansionMethod
    public static boolean containsChargedProjectile(ItemStack crossbowStack, Item ammoItem) {
        
        return CrossbowItem.containsChargedProjectile(crossbowStack, ammoItem);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, float velocity, float accuracy) {
        
        CrossbowItem.performShooting(level, shooter, hand, crossbowStack, velocity, accuracy);
    }
    
    /**
     * Gets the charged duration of the given stack.
     *
     * @param crossbowStack The stack to check
     *
     * @return the charged duration of the given stack.
     *
     * @docParam crossbowStack <item:minecraft:crossbow>
     */
    @ZenCodeType.StaticExpansionMethod
    public static int getChargeDuration(ItemStack crossbowStack) {
        
        return CrossbowItem.getChargeDuration(crossbowStack);
    }
    
}
