package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.entity.IEntityThrowable;
import crafttweaker.api.entity.IProjectile;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.projectile.EntityThrowable;

import javax.annotation.Nullable;

public class MCEntityThrowable extends MCEntity implements IEntityThrowable, IProjectile {
    private final EntityThrowable entityThrowable;
    
    public MCEntityThrowable(EntityThrowable entityThrowable) {
        super(entityThrowable);
        this.entityThrowable = entityThrowable;
    }

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		entityThrowable.shoot(x, y, z, velocity, inaccuracy);
	}

	@Override
	@Nullable
	public IEntityLivingBase getThrower() {
		return CraftTweakerMC.getIEntityLivingBase(entityThrowable.getThrower());
	}

	@Override
	public int getThrowableShake() {
		return entityThrowable.throwableShake;
	}
}
