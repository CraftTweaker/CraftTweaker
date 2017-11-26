package crafttweaker.mc1120.entity;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.entity.attribute.IEntityAttributeInstance;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.potions.IPotion;
import crafttweaker.mc1120.entity.attribute.MCEntityAttributeInstance;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

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
    public void heal(int amount) {
        entityLivingBase.heal(amount);
    }

    @Override
    public boolean attackEntityFrom(IDamageSource source, float amount) {
        return false;
    }

    @Override
    public float getMaxHealth() {
        return 0;
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
}
