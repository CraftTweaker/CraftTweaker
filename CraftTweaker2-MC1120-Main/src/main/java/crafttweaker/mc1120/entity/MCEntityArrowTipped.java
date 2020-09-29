package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityArrow;
import crafttweaker.api.entity.IEntityArrowTipped;
import crafttweaker.api.entity.IProjectile;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;

import javax.annotation.Nullable;

public class MCEntityArrowTipped extends MCEntityArrow implements IProjectile, IEntityArrow, IEntityArrowTipped {
    private final EntityTippedArrow entityArrowTipped;

    public MCEntityArrowTipped(EntityTippedArrow entityArrowTipped) {
        super(entityArrowTipped);
        this.entityArrowTipped = entityArrowTipped;
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        entityArrowTipped.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    public void setDamage(double damage) {
        entityArrowTipped.setDamage(damage);
    }

    @Override
    public double getDamage() {
        return entityArrowTipped.getDamage();
    }

    @Override
    public void setIsCritical(boolean critical) {
        entityArrowTipped.setIsCritical(critical);
    }

    @Override
    public boolean getIsCritical() {
        return entityArrowTipped.getIsCritical();
    }

    @Override
    public void setKnockbackStrength(int knockbackStrength) {
        entityArrowTipped.setKnockbackStrength(knockbackStrength);
    }

    @Override
    public int arrowShake() {
        return entityArrowTipped.arrowShake;
    }

    @Override
    @Nullable
    public IEntity getShooter() {
        return CraftTweakerMC.getIEntity(entityArrowTipped.shootingEntity);
    }

    @Override
    public void setShooter(IEntity shooter) {
        entityArrowTipped.shootingEntity = CraftTweakerMC.getEntity(shooter);
    }

    @Override
    public String getPickupStatus() {
        return String.valueOf(entityArrowTipped.pickupStatus);
    }

    @Override
    public void setPickupStatus(String pickupStatus) {
        EntityArrow.PickupStatus.valueOf(pickupStatus);
    }

    @Override
    public void setPickupDisallowed() {
        entityArrowTipped.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
    }

    @Override
    public void setPickupAllowed() {
        entityArrowTipped.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
    }

    @Override
    public void setPickupCreativeOnly() {
        entityArrowTipped.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
    }

    @Override
    public void shoot(IEntity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
        entityArrowTipped.shoot(CraftTweakerMC.getEntity(shooter), pitch, yaw, p_184547_4_, velocity, inaccuracy);
    }

    @Override
    public void setPotionEffect(IItemStack stack) {
        entityArrowTipped.setPotionEffect(CraftTweakerMC.getItemStack(stack));
    }

    @Override
    public void addPotionEffect(IPotionEffect effect) {
        entityArrowTipped.addEffect(CraftTweakerMC.getPotionEffect(effect));
    }
}
