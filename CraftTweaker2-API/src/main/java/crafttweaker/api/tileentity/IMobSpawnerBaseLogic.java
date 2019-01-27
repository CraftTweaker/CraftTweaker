package crafttweaker.api.tileentity;

import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

public interface IMobSpawnerBaseLogic {
    
    @ZenSetter("entityDefinition")
    void setEntityDefinition(IEntityDefinition entityDefinition);
    
    @ZenMethod
    void updateSpawner();
    
    @ZenGetter("nbtData")
    IData getNbtData();
    
    @ZenSetter("nbtData")
    void setNbtData(IData nbtData);
    
    @ZenMethod
    void setDelayToMin();
    
    @ZenGetter("world")
    IWorld getWorld();
    
    @ZenGetter("blockPos")
    IBlockPos getBlockPos();
    
    
}
