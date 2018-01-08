package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityAnimal")
@ZenRegister
public interface IEntityAnimal extends IEntityAgeable {

    @ZenMethod
    boolean isBreedingItem(IItemStack itemStack);
    
    @ZenMethod
    void setInLove(@Optional IPlayer player);
    
    @ZenGetter("loveCause")
    IPlayer getLoveCause();
    
    @ZenGetter
    boolean isInLove();
    
    @ZenMethod
    void resetInLove();
    
    @ZenMethod
    boolean canMateWith(IEntityAnimal other);
}
