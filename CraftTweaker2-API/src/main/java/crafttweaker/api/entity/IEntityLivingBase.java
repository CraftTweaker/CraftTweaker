package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.potions.IPotion;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.entity.IEntityLivingBase")
@ZenRegister
public interface IEntityLivingBase {
    boolean canBreatheUnderwater();
    
    void setAir(int amount);
    
    int getAir();
    
    boolean isPotionAction(IPotion potion);
    
    int getHealth();
    
    boolean setHealth(int amount);
    
    boolean isChild();
    
    void clearActivePotions();
    
    void addPotion(IPotion potion);
    
    boolean isUndead();
    
    void removePotion(IPotion potion);
    
    void heal(int amount);
    
    boolean attackEntityFrom(IDamageSource source, float amount);

    float getMaxHealt();
    
    IItemStack getHeldItemMainHand();
    
    IItemStack getHeldItemOffHand();
}
