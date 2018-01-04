package crafttweaker.api.player;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.player.IFoodStats")
@ZenRegister
public interface IFoodStats {

    @ZenMethod
    void addStats(int foodValue, float saturationLevel);
    
    @ZenMethod
    void onUpdate(IEntityPlayer player);
    
    @ZenCaster
    @ZenMethod
    IData asNBT();
    
    @ZenGetter("foodLevel")
    int getFoodLevel();
    
    @ZenSetter("foodLevel")
    void setFootLevel(int foodLevel);
    
    @ZenGetter
    boolean needFood();
    
    @ZenMethod
    void addExhaustion(float exhaustion);
    
    @ZenGetter("saturationLevel")
    float getSaturationLevel();
    
    @ZenSetter("saturationLevel")
    void setSaturationLevel(float saturationLevel);
}
