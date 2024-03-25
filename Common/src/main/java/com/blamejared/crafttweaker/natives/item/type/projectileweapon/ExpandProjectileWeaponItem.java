package com.blamejared.crafttweaker.natives.item.type.projectileweapon;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Predicate;

@ZenRegister
@Document("vanilla/api/item/type/projectileweapon/ProjectileWeaponItem")
@NativeTypeRegistration(value = ProjectileWeaponItem.class, zenCodeName = "crafttweaker.api.item.type.projectileweapon.ProjectileWeaponItem")
public class ExpandProjectileWeaponItem {
    
    /**
     * A predicate which only accepts arrow items
     */
    @ZenCodeType.StaticExpansionMethod
    public static Predicate<ItemStack> ARROW_ONLY() {
        
        return ProjectileWeaponItem.ARROW_ONLY;
    }
    
    /**
     * A predicate which supports arrow items and fireworks
     */
    @ZenCodeType.StaticExpansionMethod
    public static Predicate<ItemStack> ARROW_OR_FIREWORK() {
        
        return ProjectileWeaponItem.ARROW_OR_FIREWORK;
    }
    
    /**
     * Gets a predicate for the supported held projectiles for this item.
     * <p>
     * held projectiles are projectiles that can only be used when held in the off-hand while shooting / charging
     *
     * @return a predicate for what held ammo items are supported.
     */
    @ZenCodeType.Getter("supportedHeldProjectiles")
    public static Predicate<ItemStack> getSupportedHeldProjectiles(ProjectileWeaponItem internal) {
        
        return internal.getSupportedHeldProjectiles();
    }
    
    /**
     * Gets a predicate for the supported projectiles for this item.
     *
     * @return a predicate for what ammo items are supported.
     */
    @ZenCodeType.Getter("allSupportedProjectiles")
    public static Predicate<ItemStack> getAllSupportedProjectiles(ProjectileWeaponItem internal) {
        
        return internal.getAllSupportedProjectiles();
    }
    
    /**
     * Gets the default projectile range for this item.
     *
     * @return the default projectile range
     */
    @ZenCodeType.Getter("defaultProjectileRange")
    public static int getDefaultProjectileRange(ProjectileWeaponItem internal) {
        
        return internal.getDefaultProjectileRange();
    }
    
}
