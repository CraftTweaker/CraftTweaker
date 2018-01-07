package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityAgeable")
@ZenRegister
public interface IEntityAgeable extends IEntityCreature {

    @ZenGetter("growingAge")
    int getGrowingAge();
    
    @ZenSetter("growingAge")
    void setGrowingAge(int age);
    
    @ZenMethod
    void ageUp(int seconds, @Optional boolean forced);
    
    @ZenMethod
    void addGrowth(int seconds);
    
    @ZenSetter("scaleForAge")
    void setScaleForAge(boolean scaleForAge);
}
