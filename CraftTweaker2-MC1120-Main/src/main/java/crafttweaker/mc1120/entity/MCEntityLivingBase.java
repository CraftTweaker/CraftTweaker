package crafttweaker.mc1120.entity;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.*;
import crafttweaker.api.entity.attribute.IEntityAttributeInstance;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.*;
import crafttweaker.mc1120.entity.attribute.MCEntityAttributeInstance;
import net.minecraft.entity.*;

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
        return entityLivingBase.isPotionActive(CraftTweakerMC.getPotion(potion));
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
        return CraftTweakerMC.getIItemStack(entityLivingBase.getHeldItemMainhand());
    }
    
    @Override
    public IItemStack getHeldItemOffHand() {
        return CraftTweakerMC.getIItemStack(entityLivingBase.getHeldItemOffhand());
    }
    
    @Override
    public IEntityAttributeInstance getAttribute(String name) {
        return new MCEntityAttributeInstance(entityLivingBase.getAttributeMap().getAttributeInstanceByName(name));
    }
    
    @Override
    public IEntityLivingBase getRevengeTarget() {
        return CraftTweakerMC.getIEntityLivingBase(entityLivingBase.getRevengeTarget());
    }
    
    @Override
    public void setRevengeTarger(IEntityLivingBase target) {
        entityLivingBase.setRevengeTarget(CraftTweakerMC.getEntityLivingBase(target));
    }
    
    @Override
    public IEntityLivingBase getLastAttackedEntity() {
        return CraftTweakerMC.getIEntityLivingBase(entityLivingBase.getLastAttackedEntity());
    }
    
    @Override
    public void setLastAttackedEntity(IEntityLivingBase entity) {
        entityLivingBase.setLastAttackedEntity(CraftTweakerMC.getEntityLivingBase(entity));
    }
    
    @Override
    public int getLastAttackedEntityTime() {
        return entityLivingBase.getLastAttackedEntityTime();
    }
    
    @Override
    public List<IPotionEffect> getActivePotionEffects() {
        return entityLivingBase.getActivePotionEffects().stream().map(CraftTweakerMC::getIPotionEffect).collect(Collectors.toList());
    }
    
    @Override
    public IPotionEffect getActivePotionEffect(IPotion potion) {
        return CraftTweakerMC.getIPotionEffect(entityLivingBase.getActivePotionEffect(CraftTweakerMC.getPotion(potion)));
    }

    @Override
    public void removePotionEffect(IPotion potion) {
        entityLivingBase.removePotionEffect(CraftTweakerMC.getPotion(potion));
    }
    
    @Override
    public boolean isPotionEffectApplicable(IPotionEffect potionEffect) {
        return entityLivingBase.isPotionApplicable(CraftTweakerMC.getPotionEffect(potionEffect));
    }
    
    @Override
    public IDamageSource getLastDamageSource() {
        return CraftTweakerMC.getIDamageSource(entityLivingBase.getLastDamageSource());
    }
    
    @Override
    public void onDeath(IDamageSource source) {
        entityLivingBase.onDeath(CraftTweakerMC.getDamageSource(source));
    }
    
    @Override
    public void knockBack(IEntity entity, float strength, double xRatio, double zRatio) {
        entityLivingBase.knockBack(CraftTweakerMC.getEntity(entity), strength, xRatio, zRatio);
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
        return CraftTweakerMC.getIEntityLivingBase(entityLivingBase.getAttackingEntity());
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
        return entityLivingBase.canEntityBeSeen(CraftTweakerMC.getEntity(other));
    }
    
    @Override
    public void addPotionEffect(IPotionEffect potionEffect) {
        entityLivingBase.addPotionEffect(CraftTweakerMC.getPotionEffect(potionEffect));
    }
    
    @Override
    public void setItemToSlot(IEntityEquipmentSlot slot, IItemStack itemStack) {
        entityLivingBase.setItemStackToSlot(CraftTweakerMC.getEntityEquipmentSlot(slot), CraftTweakerMC.getItemStack(itemStack));
    }
    
    @Override
    public boolean hasItemInSlot(IEntityEquipmentSlot slot) {
        return entityLivingBase.hasItemInSlot(CraftTweakerMC.getEntityEquipmentSlot(slot));
    }
    
    @Override
    public IItemStack getItemInSlot(IEntityEquipmentSlot slot) {
        return CraftTweakerMC.getIItemStack(entityLivingBase.getItemStackFromSlot(CraftTweakerMC.getEntityEquipmentSlot(slot)));
    }
}
