package crafttweaker.api.damage;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.damage.IDamageSource")
@ZenRegister
public interface IDamageSource {
	
	@ZenGetter("harmInCreative")
	@ZenMethod
	boolean canHarmInCreative();
	
	@ZenGetter("damageType")
	@ZenMethod
	String getDamageType();
	
	@ZenMethod
	String getDeathMessage(IEntity entity);
	
	@ZenMethod
	@ZenGetter("hunderDamage")
	float getHungerDamage();
	
	@ZenMethod
	@ZenGetter("immediateSource")
	IEntity getImmediateSource();
	
	@ZenGetter("trueSource")
	@ZenMethod
	IEntity getTrueSource();
	
	@ZenMethod
	@ZenGetter("creativePlayer")
	boolean isCreativePlayer();
	
	@ZenMethod
	@ZenGetter("damageAbsolute")
	boolean isDamageAbsolute();
	
	@ZenMethod
	@ZenGetter("difficultyScaled")
	boolean isDifficultyScaled();

	@ZenMethod
	@ZenGetter("explosion")
	boolean isExplosion();
	
	@ZenMethod
	@ZenGetter("fireDamage")
	boolean isFireDamage();
	
	@ZenMethod
	@ZenGetter("magicDamage")
	boolean isMagicDamage();
	
	@ZenMethod
	@ZenGetter("projectile")
	boolean isProjectile();
	
	@ZenMethod
	IDamageSource setDamageAllowedInCreativeMode();
	
	@ZenMethod
	IDamageSource setDamageBypassesArmor();
	
	@ZenMethod
	IDamageSource setDamageIsAbsolute();
	
	@ZenMethod
	IDamageSource setDifficultyScaled();
	
	@ZenMethod
	IDamageSource setExplosion();
	
	@ZenMethod
	IDamageSource setFireDamage();
	
	@ZenMethod
	IDamageSource setMagicDamage();
	
	@ZenMethod
	IDamageSource setProjectile();
    
    
    Object getInternal();
}
