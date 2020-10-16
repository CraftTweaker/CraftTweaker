package crafttweaker.api.damage;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IVector3d;
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

	@ZenGetter("hungerDamage")
	default float getHungerDamageFixed() {
		return 0.0f;
	}
	
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
	@ZenGetter("damageUnblockable")
	boolean isDamageUnblockable();
	
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
	@ZenGetter("damageLocation")
	IVector3d getDamageLocation();
	
	@ZenMethod
	@ZenGetter("setDamageAllowedInCreativeMode")
	IDamageSource setDamageAllowedInCreativeMode();
	
	@ZenMethod
	@ZenGetter("setDamageBypassesArmor")
	IDamageSource setDamageBypassesArmor();
	
	@ZenMethod
	@ZenGetter("setDamageIsAbsolute")
	IDamageSource setDamageIsAbsolute();
	
	@ZenMethod
	@ZenGetter("setDifficultyScaled")
	IDamageSource setDifficultyScaled();
	
	@ZenMethod
	@ZenGetter("setExplosionDamage")
	IDamageSource setExplosion();
	
	@ZenMethod
	@ZenGetter("setFireDamage")
	IDamageSource setFireDamage();
	
	@ZenMethod
	@ZenGetter("setMagicDamage")
	IDamageSource setMagicDamage();
	
	@ZenMethod
	@ZenGetter("setProjectileDamage")
	IDamageSource setProjectile();

    
    Object getInternal();
}
