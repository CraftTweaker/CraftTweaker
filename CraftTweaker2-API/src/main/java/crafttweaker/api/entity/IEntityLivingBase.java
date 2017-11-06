package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.potions.IPotion;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.entity.IEntityLivingBase")
@ZenRegister
public interface IEntityLivingBase extends IEntity {
    @ZenMethod
    boolean canBreatheUnderwater();

    @ZenMethod
    boolean isPotionActive(IPotion potion);

    @ZenMethod
    @ZenGetter("health")
    float getHealth();

    @ZenMethod
    @ZenSetter("health")
    void setHealth(float amount);

    @ZenMethod
    boolean isChild();

    @ZenMethod
    void clearActivePotions();

    @ZenMethod
    boolean isUndead();

    @ZenMethod
    void heal(int amount);

    @ZenMethod
    boolean attackEntityFrom(IDamageSource source, float amount);

    @ZenMethod
    @ZenGetter("maxHealth")
    float getMaxHealth();

    @ZenMethod
    @ZenGetter("mainHandHeldItem")
    IItemStack getHeldItemMainHand();

    @ZenMethod
    @ZenGetter("offHandHeldItem")
    IItemStack getHeldItemOffHand();

    /* TODO: When Adding New Objects from ContentTweaker
     * void addPotion(IPotion potion);
     *
     * void removePotion(IPotion potion);
     *
     * IItemStack getHeldItem(Hand hand);
     */
}
