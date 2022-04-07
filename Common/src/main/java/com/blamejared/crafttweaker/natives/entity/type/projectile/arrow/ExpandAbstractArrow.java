package com.blamejared.crafttweaker.natives.entity.type.projectile.arrow;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/arrow/Arrow")
@NativeTypeRegistration(value = AbstractArrow.class, zenCodeName = "crafttweaker.api.entity.type.projectile.arrow.Arrow")
public class ExpandAbstractArrow {
    
    /**
     * Sets the sound event that this arrow plays when it hits an entity or block.
     *
     * @param event The sound event to play when
     *
     * @docParam soundevent <soundevent:minecraft:ambient.cave>
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("soundEvent")
    public static void setSoundEvent(AbstractArrow internal, SoundEvent event) {
        
        internal.setSoundEvent(event);
    }
    
    /**
     * Sets the base damage that this arrow does.
     *
     * @param damage The base damage.
     *
     * @docParam damage 0.5
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("baseDamage")
    public static void setBaseDamage(AbstractArrow internal, double damage) {
        
        internal.setBaseDamage(damage);
    }
    
    /**
     * Gets the base damage that this arrow does.
     *
     * @return The base damage of this arrow.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("baseDamage")
    public static double getBaseDamage(AbstractArrow internal) {
        
        return internal.getBaseDamage();
    }
    
    /**
     * Sets the knockback of this arrow.
     *
     * @param knockback The knockback value.
     *
     * @docParam knockback 5
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("knockback")
    public static void setKnockback(AbstractArrow internal, int knockback) {
        
        internal.setKnockback(knockback);
    }
    
    /**
     * Gets the knockback of this arrow.
     *
     * @return The knockback of this arrow.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("knockback")
    public static int getKnockback(AbstractArrow internal) {
        
        return internal.getKnockback();
    }
    
    /**
     * Sets this the crit value of this arrow.
     *
     * @param crit The crit value to set.
     *
     * @docParam crit true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("isCritArrow")
    public static void setCritArrow(AbstractArrow internal, boolean crit) {
        
        internal.setCritArrow(crit);
    }
    
    /**
     * Checks whether this arrow is a crit arrow.
     *
     * @return true if this is a crit arrow, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCritArrow")
    public static boolean isCritArrow(AbstractArrow internal) {
        
        return internal.isCritArrow();
    }
    
    /**
     * Sets the pierce level of this arrow.
     *
     * @param pieceLevel The pierce level of this arrow.
     *
     * @docParam pierceLevel 5
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("pierceLevel")
    public static void setPierceLevel(AbstractArrow internal, byte pieceLevel) {
        
        internal.setPierceLevel(pieceLevel);
    }
    
    /**
     * Gets the pierce level of this arrow.
     *
     * @return the pierce level of this arrow.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("pierceLevel")
    public static byte getPierceLevel(AbstractArrow internal) {
        
        return internal.getPierceLevel();
    }
    
    /**
     * Sets if this arrow was shot from a crossbow or not.
     *
     * @param shotFromCrossbow If this arrow was shot from a crossbow or not.
     *
     * @docParam shotFromCrossbow true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("shotFromCrossbow")
    public static void setShotFromCrossbow(AbstractArrow internal, boolean shotFromCrossbow) {
        
        internal.setShotFromCrossbow(shotFromCrossbow);
    }
    
    /**
     * Checks if this arrow was shot from a crossbow.
     *
     * @return true if it was shot from a crossbow, false otherwise.
     */
    @ZenCodeType.Method
    public static boolean shotFromCrossbow(AbstractArrow internal) {
        
        return internal.shotFromCrossbow();
    }
    
    /**
     * Sets the values of this arrow based on the enchantments the given entity has. This handles setting the knockback if the bow has a knockback enchantment.
     *
     * @param entity         The entity that fired the arrow
     * @param distanceFactor How charged is the bow.
     *
     * @docParam entity entity
     * @docParam distanceFactor 1
     */
    @ZenCodeType.Method
    public static void setEnchantmentEffectsFromEntity(AbstractArrow internal, LivingEntity entity, float distanceFactor) {
        
        internal.setEnchantmentEffectsFromEntity(entity, distanceFactor);
    }
    
    /**
     * Sets if this arrow has physics or not.
     *
     * @param noPhysics If this arrow has physics or not.
     *
     * @docParam noPhysics true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("isNoPhysics")
    public static void setNoPhysics(AbstractArrow internal, boolean noPhysics) {
        
        internal.setNoPhysics(noPhysics);
    }
    
    /**
     * Checks if this arrow has physics or not.
     *
     * @return true if this arrow doesn't have physics, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isNoPhysics")
    public static boolean isNoPhysics(AbstractArrow internal) {
        
        return internal.isNoPhysics();
    }
    
}
