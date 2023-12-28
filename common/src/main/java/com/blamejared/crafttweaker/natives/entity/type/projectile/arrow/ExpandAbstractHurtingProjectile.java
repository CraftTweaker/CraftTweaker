package com.blamejared.crafttweaker.natives.entity.type.projectile.arrow;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/AbstractHurtingProjectile")
@NativeTypeRegistration(value = AbstractHurtingProjectile.class, zenCodeName = "crafttweaker.api.entity.type.projectile.AbstractHurtingProjectile")
public class ExpandAbstractHurtingProjectile {
    
    /**
     * Gets the x power of this projectile.
     *
     * @return The x power of this projectile.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("xPower")
    public static double getXPower(AbstractHurtingProjectile internal) {
        
        return internal.xPower;
    }
    
    /**
     * Sets the x power of this projectile.
     *
     * @param xPower The x power of this projectile.
     *
     * @docParam xPower 4
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("xPower")
    public static void setXPower(AbstractHurtingProjectile internal, double xPower) {
        
        internal.xPower = xPower;
    }
    
    /**
     * Gets the y power of this projectile.
     *
     * @return The y power of this projectile.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("yPower")
    public static double getYPower(AbstractHurtingProjectile internal) {
        
        return internal.yPower;
    }
    
    /**
     * Sets the y power of this projectile.
     *
     * @param yPower The y power of this projectile.
     *
     * @docParam yPower 4
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("yPower")
    public static void setYPower(AbstractHurtingProjectile internal, double yPower) {
        
        internal.yPower = yPower;
    }
    
    /**
     * Gets the z power of this projectile.
     *
     * @return The z power of this projectile.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("zPower")
    public static double getZPower(AbstractHurtingProjectile internal) {
        
        return internal.zPower;
    }
    
    /**
     * Sets the z power of this projectile.
     *
     * @param zPower The z power of this projectile.
     *
     * @docParam zPower 4
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("zPower")
    public static void setZPower(AbstractHurtingProjectile internal, double zPower) {
        
        internal.zPower = zPower;
    }
    
}
