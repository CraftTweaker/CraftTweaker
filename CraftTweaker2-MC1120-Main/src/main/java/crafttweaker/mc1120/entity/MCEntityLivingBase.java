package crafttweaker.mc1120.entity;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.*;
import crafttweaker.api.entity.attribute.IEntityAttributeInstance;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.potions.*;
import crafttweaker.mc1120.damage.MCDamageSource;
import crafttweaker.mc1120.entity.attribute.MCEntityAttributeInstance;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.potions.MCPotionEfect;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;

import java.util.List;
import java.util.stream.Collectors;

public class MCEntityLivingBase extends MCEntity implements IEntityLivingBase {
    private EntityLivingBase entityLivingBase;

    public MCEntityLivingBase(EntityLivingBase entity) {
        super(entity);
        this.entityLivingBase = entity;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return entityLivingBase.canBreatheUnderwater();
    }

    @Override
    public boolean isPotionActive(IPotion potion) {
        return entityLivingBase.isPotionActive((Potion) potion.getInternal());
    }

    @Override
    public float getHealth() {
        return entityLivingBase.getHealth();
    }

    @Override
    public void setHealth(float amount) {
        entityLivingBase.setHealth(amount);
    }

    @Override
    public boolean isChild() {
        return entityLivingBase.isChild();
    }

    @Override
    public void clearActivePotions() {
        entityLivingBase.clearActivePotions();
    }

    @Override
    public boolean isUndead() {
        return entityLivingBase.isEntityUndead();
    }

    @Override
    public void heal(float amount) {
        entityLivingBase.heal(amount);
    }

    @Override
    public float getMaxHealth() {
        return entityLivingBase.getMaxHealth();
    }

    @Override
    public IItemStack getHeldItemMainHand() {
        return new MCItemStack(entityLivingBase.getHeldItemMainhand());
    }

    @Override
    public IItemStack getHeldItemOffHand() {
        return new MCItemStack(entityLivingBase.getHeldItemOffhand());
    }

    @Override
    public IEntityAttributeInstance getAttribute(String name) {
        return new MCEntityAttributeInstance(entityLivingBase.getAttributeMap().getAttributeInstanceByName(name));
    }
    
    @Override
    public IEntityLivingBase getRevengeTarget() {
        EntityLivingBase target = entityLivingBase.getRevengeTarget();
        return target == null ? null : new MCEntityLivingBase(target);
    }
    
    @Override
    public void setRevengeTarger(IEntityLivingBase target) {
        entityLivingBase.setRevengeTarget((EntityLivingBase) target.getInternal());
    }
    
    @Override
    public IEntityLivingBase getLastAttackedEntity() {
        EntityLivingBase target = entityLivingBase.getLastAttackedEntity();
        return target == null ? null : new MCEntityLivingBase(target);
    }
    
    @Override
    public void setLastAttackedEntity(IEntityLivingBase entity) {
        entityLivingBase.setLastAttackedEntity((Entity) entity.getInternal());
    }
    
    @Override
    public int getLastAttackedEntityTime() {
        return entityLivingBase.getLastAttackedEntityTime();
    }
    
    @Override
    public List<IPotionEffect> getActivePotionEffects() {
        return entityLivingBase.getActivePotionEffects().stream().map(MCPotionEfect::new).collect(Collectors.toList());
    }
    
    @Override
    public IPotionEffect getActivePotionEffect(IPotion potion) {
        PotionEffect ret = entityLivingBase.getActivePotionEffect((Potion) potion.getInternal());
        return ret == null ? null : new MCPotionEfect(ret);
    }
    
    @Override
    public boolean isPotionEffectApplicable(IPotionEffect potionEffect) {
        return entityLivingBase.isPotionApplicable((PotionEffect) potionEffect.getInternal());
    }
    
    @Override
    public IDamageSource getLastDamageSource() {
        DamageSource src = entityLivingBase.getLastDamageSource();
        return src == null ? null : new MCDamageSource(src);
    }
    
    @Override
    public void onDeath(IDamageSource source) {
        entityLivingBase.onDeath((DamageSource) source.getInternal());
    }
    
    @Override
    public void knockBack(IEntity entity, float one, double two, double three) {
        entityLivingBase.knockBack((Entity) entity.getInternal(), one, two, three);
    }
    
    @Override
    public boolean isOnLadder() {
        return entityLivingBase.isOnLadder();
    }
    
    @Override
    public int getTotalArmorValue() {
        return entityLivingBase.getTotalArmorValue();
    }
    
    @Override
    public IEntityLivingBase getAttackingEntity() {
        EntityLivingBase target = entityLivingBase.getAttackingEntity();
        return target == null ? null : new MCEntityLivingBase(target);
    }
    
    @Override
    public int getArrowCountInEntity() {
        return entityLivingBase.getArrowCountInEntity();
    }
    
    @Override
    public void setArrowCountInEntity(int arrows) {
        entityLivingBase.setArrowCountInEntity(arrows);
    }
    
    @Override
    public float getAIMoveSpeed() {
        return entityLivingBase.getAIMoveSpeed();
    }
    
    @Override
    public void setAIMoveSpeed(float speed) {
        entityLivingBase.setAIMoveSpeed(speed);
    }
    
    @Override
    public void onLivingUpdate() {
        entityLivingBase.onLivingUpdate();
    }
    
    @Override
    public boolean canEntityBeSeen(IEntity other) {
        return entityLivingBase.canEntityBeSeen((Entity) other.getInternal());
    }
    
    @Override
    public void addPotionEffect(IPotionEffect potionEffect) {
        entityLivingBase.addPotionEffect((PotionEffect) potionEffect.getInternal());
    }
}
