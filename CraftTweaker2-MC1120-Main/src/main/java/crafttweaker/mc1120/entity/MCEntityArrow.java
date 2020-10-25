package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityArrow;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;

public class MCEntityArrow extends MCEntity implements IEntityArrow {
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
	public void shoot(IEntity shooter, float pitch, float yaw, float roll, float velocity, float inaccuracy) {
		entityArrow.shoot(CraftTweakerMC.getEntity(shooter), pitch, yaw, roll, velocity, inaccuracy);
	}

	@Override
	public void setDamage(double damage) {
		entityArrow.setDamage(damage);
	}

	@Override
	public double getDamage() {
		return entityArrow.getDamage();
	}

	@Override
	public void setIsCritical(boolean critical) {
		entityArrow.setIsCritical(critical);
	}

	@Override
	public boolean getIsCritical() {
		return entityArrow.getIsCritical();
	}

	@Override
	public void setKnockbackStrength(int knockbackStrength) {
		entityArrow.setKnockbackStrength(knockbackStrength);
	}

	@Override
	public int arrowShake() {
		return entityArrow.arrowShake;
	}

	@Override
	public IEntity getShooter() {
		return CraftTweakerMC.getIEntity(entityArrow.shootingEntity);
	}
	
	@Override
	public void setShooter(IEntity shooter) {
		entityArrow.shootingEntity = CraftTweakerMC.getEntity(shooter);
	}

	@Override
	public String getPickupStatus() {
		return String.valueOf(entityArrow.pickupStatus);
	}
	
	@Override
	public void setPickupStatus(String pickupStatus) {
		PickupStatus.valueOf(pickupStatus);
	}

	@Override
	public void setPickupDisallowed() {
		entityArrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
	}

	@Override
	public void setPickupAllowed() {
		entityArrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
	}

	@Override
	public void setPickupCreativeOnly() {
		entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	}
}
