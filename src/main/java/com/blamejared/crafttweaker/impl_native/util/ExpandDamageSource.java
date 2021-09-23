package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/util/DamageSource")
@NativeTypeRegistration(value = DamageSource.class, zenCodeName = "crafttweaker.api.util.DamageSource")
public class ExpandDamageSource {
    
    public static Map<String, DamageSource> PRE_REGISTERED_DAMAGE_SOURCES = Util.make(() -> {
        Map<String, DamageSource> temp = new HashMap<>();
        temp.put("inFire", DamageSource.IN_FIRE);
        temp.put("lightningBolt", DamageSource.LIGHTNING_BOLT);
        temp.put("onFire", DamageSource.ON_FIRE);
        temp.put("lava", DamageSource.LAVA);
        temp.put("hotFloor", DamageSource.HOT_FLOOR);
        temp.put("inWall", DamageSource.IN_WALL);
        temp.put("cramming", DamageSource.CRAMMING);
        temp.put("drown", DamageSource.DROWN);
        temp.put("starve", DamageSource.STARVE);
        temp.put("cactus", DamageSource.CACTUS);
        temp.put("fall", DamageSource.FALL);
        temp.put("flyIntoWall", DamageSource.FLY_INTO_WALL);
        temp.put("outOfWorld", DamageSource.OUT_OF_WORLD);
        temp.put("generic", DamageSource.GENERIC);
        temp.put("magic", DamageSource.MAGIC);
        temp.put("wither", DamageSource.WITHER);
        temp.put("anvil", DamageSource.ANVIL);
        temp.put("fallingBlock", DamageSource.FALLING_BLOCK);
        temp.put("dragonBreath", DamageSource.DRAGON_BREATH);
        temp.put("dryout", DamageSource.DRYOUT);
        temp.put("sweetBerryBush", DamageSource.SWEET_BERRY_BUSH);
        return temp;
    });
    
    /**
     * Checks if the damage is caused by a projectile.
     *
     * @return Whether or not the damage is caused by a projectile.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("projectile")
    public static boolean isProjectileDamage(DamageSource internal) {
        
        return internal.isProjectile();
    }
    
    /**
     * Checks if the damage is caused by an explosion.
     *
     * @return Whether or not the damage is caused by an explosion.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("explosion")
    public static boolean isExplosionDamage(DamageSource internal) {
        
        return internal.isExplosion();
    }
    
    /**
     * Checks if the damage can be blocked.
     *
     * @return Whether or not the damage can be blocked.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("unblockable")
    public static boolean isUnblockableDamage(DamageSource internal) {
        
        return internal.isUnblockable();
    }
    
    /**
     * Gets the amount of exhaustion to add to the player's hunger bar if they
     * get hit by this damage.
     *
     * @return The amount of exhaustion to add to the player's hunger bar.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("hungerDamage")
    public static float getHungerDamage(DamageSource internal) {
        
        return internal.getHungerDamage();
    }
    
    /**
     * Checks if the damage can bypass creative mode.
     *
     * @return Whether or not the damage can bypass creative mode.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("bypassCreative")
    public static boolean canBypassCreative(DamageSource internal) {
        
        return internal.canHarmInCreative();
    }
    
    /**
     * Checks if the damage is absolute.
     *
     * @return Whether or not the damage is absolute.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("absolute")
    public static boolean isAbsoluteDamage(DamageSource internal) {
        
        return internal.isDamageAbsolute();
    }
    
    /**
     * Checks if the damage is caused by fire or burning.
     *
     * @return Whether or not the damage is caused by fire or burning.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("fire")
    public static boolean isFireDamage(DamageSource internal) {
        
        return internal.isFireDamage();
    }
    
    /**
     * Checks if the damage is caused by magic.
     *
     * @return Whether or not the damage is caused by magic.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("magic")
    public static boolean isMagicDamage(DamageSource internal) {
        
        return internal.isMagicDamage();
    }
    
    /**
     * Gets the type of damage.
     *
     * @return The type of damage.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static String getDamageType(DamageSource internal) {
        
        return internal.getDamageType();
    }
    
    
    /**
     * Gets the immediate source of the damage, like an arrow.
     *
     * @return The immediate source of the damage.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("immediateSource")
    @Nullable
    public static Entity getImmediateSource(DamageSource internal) {
        
        return internal.getImmediateSource();
    }
    
    /**
     * Gets the true source of the damage, like the player who shot the arrow.
     *
     * @return The true source of the damage.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("trueSource")
    @Nullable
    public static Entity getTrueSource(DamageSource internal) {
        
        return internal.getTrueSource();
    }
    
    /**
     * Gets whether the damage changes strength based on the current difficulty.
     *
     * @return Whether the damage changes strength according to difficulty.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("difficultyScaled")
    public static boolean isDifficultyScaled(final DamageSource internal) {
        
        return internal.isDifficultyScaled();
    }
    
    /**
     * Gets whether the damage was inflicted by a creative player.
     *
     * @return Whether the damage was inflicted by a creative player.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("creativePlayer")
    public static boolean isCreativePlayer(final DamageSource internal) {
        
        return internal.isCreativePlayer();
    }
    
    /**
     * Gets the location where the damage occurred.
     *
     * @return The location of the damage.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("location")
    @ZenCodeType.Nullable
    public static Vector3d getDamageLocation(final DamageSource internal) {
        
        return internal.getDamageLocation();
    }
    
}
