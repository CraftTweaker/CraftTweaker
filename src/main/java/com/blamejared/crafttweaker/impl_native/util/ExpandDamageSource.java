package com.blamejared.crafttweaker.impl_native.util;

import javax.annotation.Nullable;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

import java.util.HashMap;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/util/DamageSource")
@NativeTypeRegistration(value = DamageSource.class, zenCodeName = "crafttweaker.api.util.DamageSource")
public class ExpandDamageSource {
    public static Map<String, DamageSource> PRE_REGISTERED_DAMAGE_SOURCES = new HashMap<>();
    
    static {
        PRE_REGISTERED_DAMAGE_SOURCES.put("inFire", DamageSource.IN_FIRE);
        PRE_REGISTERED_DAMAGE_SOURCES.put("lightningBolt", DamageSource.LIGHTNING_BOLT);
        PRE_REGISTERED_DAMAGE_SOURCES.put("onFire", DamageSource.ON_FIRE);
        PRE_REGISTERED_DAMAGE_SOURCES.put("lava", DamageSource.LAVA);
        PRE_REGISTERED_DAMAGE_SOURCES.put("hotFloor", DamageSource.HOT_FLOOR);
        PRE_REGISTERED_DAMAGE_SOURCES.put("inWall", DamageSource.IN_WALL);
        PRE_REGISTERED_DAMAGE_SOURCES.put("cramming", DamageSource.CRAMMING);
        PRE_REGISTERED_DAMAGE_SOURCES.put("drown", DamageSource.DROWN);
        PRE_REGISTERED_DAMAGE_SOURCES.put("starve", DamageSource.STARVE);
        PRE_REGISTERED_DAMAGE_SOURCES.put("cactus", DamageSource.CACTUS);
        PRE_REGISTERED_DAMAGE_SOURCES.put("fall", DamageSource.FALL);
        PRE_REGISTERED_DAMAGE_SOURCES.put("flyIntoWall", DamageSource.FLY_INTO_WALL);
        PRE_REGISTERED_DAMAGE_SOURCES.put("outOfWorld", DamageSource.OUT_OF_WORLD);
        PRE_REGISTERED_DAMAGE_SOURCES.put("generic", DamageSource.GENERIC);
        PRE_REGISTERED_DAMAGE_SOURCES.put("magic", DamageSource.MAGIC);
        PRE_REGISTERED_DAMAGE_SOURCES.put("wither", DamageSource.WITHER);
        PRE_REGISTERED_DAMAGE_SOURCES.put("anvil", DamageSource.ANVIL);
        PRE_REGISTERED_DAMAGE_SOURCES.put("fallingBlock", DamageSource.FALLING_BLOCK);
        PRE_REGISTERED_DAMAGE_SOURCES.put("dragonBreath", DamageSource.DRAGON_BREATH);
        PRE_REGISTERED_DAMAGE_SOURCES.put("dryout", DamageSource.DRYOUT);
        PRE_REGISTERED_DAMAGE_SOURCES.put("sweetBerryBush", DamageSource.SWEET_BERRY_BUSH);
    }

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
}