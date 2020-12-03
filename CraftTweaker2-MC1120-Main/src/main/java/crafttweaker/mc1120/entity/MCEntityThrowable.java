package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.entity.IEntityThrowable;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.projectile.EntityThrowable;

public class MCEntityThrowable extends MCEntity implements IEntityThrowable {
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
	public IEntityLivingBase getThrower() {
		return CraftTweakerMC.getIEntityLivingBase(entityThrowable.getThrower());
	}

	@Override
	public int getThrowableShake() {
		return entityThrowable.throwableShake;
	}
}
