package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.potions.IPotionEffect;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.entity.IEntityArrowTipped")
@ZenRegister
public interface IEntityArrowTipped extends IEntityArrow {

    @ZenMethod
    void setPotionEffect(IItemStack stack);

    @ZenMethod
    void addPotionEffect(IPotionEffect effect);
}