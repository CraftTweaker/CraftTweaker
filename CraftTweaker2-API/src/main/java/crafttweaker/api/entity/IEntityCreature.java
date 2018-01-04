package crafttweaker.api.entity;

import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityCreature")
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
