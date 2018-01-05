package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityCreature")
@ZenRegister
public interface IEntityCreature extends IEntityLivingBase {

    @ZenGetter
    boolean hasPath();
    
    @ZenMethod
    boolean canSee(IEntity entity);
    
    @ZenGetter("attackTarget")
    IEntityLivingBase getAttackTarget();
    
    @ZenSetter("attackTarget")
    void setAttackTarget();
    
    @ZenGetter("talkInterval")
    int getTalkInterval();
    
    @ZenMethod
    void playLivingSound();
    

    
    
    
    
}
