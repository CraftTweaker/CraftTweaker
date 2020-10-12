package crafttweaker.api.potions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenClass("crafttweaker.potions.IPotionEffect")
@ZenRegister
public interface IPotionEffect {

    @ZenOperator(OperatorType.COMPARE)
    int compareTo(IPotion other);
    
    @ZenGetter("duration")
    int getDuration();
    
    @ZenGetter("potion")
    IPotion getPotion();
    
    @ZenGetter
    boolean doesShowParticles();
    
    @ZenGetter("amplifier")
    int getAmplifier();
    
    @ZenGetter("curativeItems")
    List<IItemStack> getCurativeItems();
    
    @ZenGetter("effectName")
    String getEffectName();
    
    @ZenGetter
    boolean isAmbient();
    
    @ZenGetter
    boolean isPotionDurationMax();
    
    @ZenMethod
    boolean isCurativeItem(IItemStack item);
    
    @ZenSetter("isPotionDurationMax")
    void setIsPotionDurationMax(boolean potionDurationMax);
    
    @ZenMethod
    void performEffect(IEntity entity);

    @ZenMethod
    default void combine(IPotionEffect other) {
    }

    Object getInternal();
}
