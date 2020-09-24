package crafttweaker.mc1120.entity;

import javax.annotation.Nullable;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityArrow;
import crafttweaker.api.entity.IProjectile;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;

public class MCEntityArrow extends MCEntity implements IEntityArrow, IProjectile {
    private final EntityArrow entityArrow;
    
    public MCEntityArrow(EntityArrow entityArrow) {
        super(entityArrow);
        this.entityArrow = entityArrow;
    }

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		entityArrow.shoot(x, y, z, velocity, inaccuracy);
	}

	@Override
	public IEntityArrow setDamage(double damage) {
		entityArrow.setDamage(damage);
		return (IEntityArrow) entityArrow;
	}

	@Override
	public double getDamage() {
		return entityArrow.getDamage();
	}

	@Override
	public IEntityArrow setIsCritical(boolean critical) {
		entityArrow.setIsCritical(critical);
		return (IEntityArrow) entityArrow;
	}

	@Override
	public boolean getIsCritical() {
		return entityArrow.getIsCritical();
	}

	@Override
	public IEntityArrow setKnockbackStrength(int knockbackStrength) {
		entityArrow.setKnockbackStrength(knockbackStrength);
		return (IEntityArrow) entityArrow;
	}

	@Override
	public int arrowShake() {
		return entityArrow.arrowShake;
	}

	@Override
	@Nullable
	public IEntity getShooter() {
		return CraftTweakerMC.getIEntity(entityArrow.shootingEntity);
	}
	
	@Override
	public IEntityArrow setShooter(IEntity shooter) {
		entityArrow.shootingEntity = CraftTweakerMC.getEntity(shooter);
		return (IEntityArrow) entityArrow;
	}

	@Override
	public String getPickupStatus() {
		return String.valueOf(entityArrow.pickupStatus);
	}
	
	@Override
	public IEntityArrow setPickupStatus(String pickupStatus) {
		PickupStatus.valueOf(pickupStatus);
		return (IEntityArrow) entityArrow;
	}

	@Override
	public IEntityArrow setPickupDisallowed() {
		entityArrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
		return (IEntityArrow) entityArrow;
	}

	@Override
	public IEntityArrow setPickupAllowed() {
		entityArrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
		return (IEntityArrow) entityArrow;
	}

	@Override
	public IEntityArrow setPickupCreativeOnly() {
		entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
		return (IEntityArrow) entityArrow;
	}
}
