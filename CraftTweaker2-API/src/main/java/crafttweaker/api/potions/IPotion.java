package crafttweaker.api.potions;

import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.api.IPotion")
public interface IPotion {
    
    @ZenGetter("name")
    String name();
    
    @ZenGetter("badEffect")
    boolean isBadEffect();
    
    @ZenGetter("liquidColor")
    int getLiquidColor();
    
    @ZenGetter("liquidColour")
    int getLiquidColour();
    
    Object getInternal();
}