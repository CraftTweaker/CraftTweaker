package crafttweaker.api.potions;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenClass("crafttweaker.potions.IPotion")
public interface IPotion {
    
    @ZenGetter("name")
    String name();
    
    @ZenGetter("badEffect")
    boolean isBadEffect();
    
    @ZenGetter("liquidColor")
    int getLiquidColor();
    
    @ZenGetter("liquidColour")
    int getLiquidColour();
    
    @ZenMethod
    IPotionEffect makePotionEffect(int duration, int amplifier);
    
    @ZenMethod
    IPotionEffect makePotionEffect(int duration, int amplifier, boolean ambient, boolean showParticles);
    
    @ZenGetter("curativeItems")
    List<IItemStack> getCurativeItems();
    
    @ZenGetter
    boolean hasStatusIcon();
    
    @ZenGetter
    boolean isBeneficial();
    
    @ZenGetter
    boolean isInstant();
    
    Object getInternal();
}