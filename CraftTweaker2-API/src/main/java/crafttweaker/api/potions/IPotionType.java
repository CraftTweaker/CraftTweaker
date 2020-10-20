package crafttweaker.api.potions;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.potions.IPotionType")
@ZenRegister
public interface IPotionType {

    Object getInternal();

    @ZenGetter("effects")
    IPotionEffect[] getPotionEffects();
}